# 0003 – Authenticate to RDS Proxy with IAM instead of a password

## Context

The AWS deployment runs the Spring Boot backend on ECS Fargate with service auto scaling, meaning the number of running tasks changes over time. Each task opens its own connection pool to PostgreSQL. Without something in front of the database, autoscaling events can exhaust RDS's maximum connection limit.

RDS Proxy solves the connection-exhaustion problem by multiplexing many application-level connections over a smaller number of actual database connections. Separately, Secrets Manager rotates the RDS credentials automatically. If the backend authenticated to the database with that password directly, a rotation event would strand any task that cached the old credential: ECS only injects secrets when a task starts, so already-running tasks would keep using the stale password until their next deploy, and start failing to open new connections.

## Decision

Put RDS Proxy in front of PostgreSQL, and have the backend authenticate to the proxy using IAM database authentication (short-lived auth tokens signed by the task's IAM role) instead of a stored password.

The task role is granted `rds-db:connect` on the proxy. The database password itself is still stored in Secrets Manager and still rotates automatically, but only RDS Proxy needs to know it; the application never handles it.

## Consequences

- Credential rotation no longer affects running tasks: IAM tokens are generated on demand per connection, so there's nothing in the application to go stale when the underlying database password rotates.
- Connection pooling is handled by the proxy, so ECS scale-out events don't risk exhausting RDS's connection limit.
- The task role needs `rds-db:connect` IAM permission in addition to whatever it already needed; this is a small, explicit, least-privilege addition tracked alongside the task's other IAM policies.
- There's a small amount of added latency per new connection for IAM token generation, which is negligible given that the proxy pools and reuses underlying connections.
- Local development (Docker Compose) is unaffected: it continues to connect to Postgres directly with a password, since there's no ECS autoscaling or Secrets Manager rotation to account for locally.
