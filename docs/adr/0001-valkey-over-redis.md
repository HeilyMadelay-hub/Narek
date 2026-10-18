# 0001 – Use ElastiCache for Valkey instead of Redis OSS

## Context

Narek uses Redis locally (via Docker Compose) as a cache in front of PostgreSQL, and the planned AWS deployment needs a managed equivalent. Amazon ElastiCache supports two compatible engines: Redis OSS and Valkey.

Redis OSS stopped receiving new engine versions after Redis Ltd. changed its license away from open source. All new ElastiCache engine features since version 8.0, including memory-efficiency improvements, Bloom filters, native vector search, and durability via a Multi-AZ transactional log in 9.0, are only being released for Valkey, the actively maintained open-source fork.

## Decision

Deploy ElastiCache for Valkey instead of ElastiCache for Redis OSS.

Valkey is wire-compatible with Redis, so the application's existing Redis client works without code changes. Only the ElastiCache engine selection changes, not the client library or the cache usage pattern.

## Consequences

- Narek's AWS deployment gets ongoing engine improvements and security fixes; Redis OSS on ElastiCache is effectively a frozen engine at this point.
- No application-level changes are required; this is purely an infrastructure choice.
- The cache continues to be used strictly as a cache in front of Postgres. Conversation and execution memory are never Valkey-only, so losing the cache never loses data.
- Local development can keep using the `redis` image in Docker Compose, since the wire protocol is the same; this only affects the managed AWS deployment.
