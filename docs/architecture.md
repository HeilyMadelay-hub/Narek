# Architecture

This document expands on the component diagram in the main [README](../README.md#how-it-works). It describes what each service in Narek is responsible for, how a request flows through the system end to end, and how the pieces map onto the local development stack.

## Components

**Dashboard (Angular).** The UI for configuring agents, triggering runs, and inspecting execution history. Talks to the backend only through the Spring Boot API; it has no direct access to the database, message broker, or any AI provider.

**Spring Boot API.** The single entry point into the backend. Authenticates requests, exposes the REST surface the dashboard consumes, and delegates all actual work to the Agent Runtime. It does not contain agent logic itself.

**Agent Runtime.** Coordinates the execution of a single agent run from start to finish. For a given run, it is responsible for pulling in whatever the agent needs, consulting the Policy Engine, reading and writing Memory, querying Knowledge Retrieval, and calling tools through the Tool Registry, then routing the assembled prompt to the Model Router. It is the only component that calls the other four runtime services directly.

**Policy Engine.** Governs what an agent is allowed to do: which tools it can call, which knowledge sources it can read, and under what conditions a run is denied or constrained. The Agent Runtime consults it before taking an action, not after, so a disallowed action never reaches a tool or the model.

**Memory.** Persists conversation and execution context across agent runs, so a later run can pick up where an earlier one left off. Backed by PostgreSQL as the source of truth; a cache sits in front of it for read performance (see Data stores below), but losing the cache never loses memory.

**Knowledge Retrieval.** Handles document ingestion and vector search. Documents are chunked, embedded, and stored in PostgreSQL via the `pgvector` extension; at query time, the Agent Runtime asks this service for the chunks most relevant to the current step, which get folded into the prompt sent downstream.

**Tool Registry.** Connects agents to external tools and APIs. An agent's available tools are declared here, and the Agent Runtime calls through the registry rather than invoking external systems directly, which keeps tool access auditable and subject to the Policy Engine.

**Model Router.** The boundary between agent logic and any specific model vendor, implemented behind Spring AI's model abstraction. It receives an assembled prompt from the Agent Runtime and dispatches it to the configured AI provider; which model a given request uses is a routing decision made here, not something baked into agent code.

**AI Provider.** The actual model doing inference. Locally this is Ollama; in production this is OpenRouter (see [ADR 0002](adr/0002-openrouter-over-bedrock.md) for why). Because the Model Router abstracts this, swapping or adding a provider does not require changes anywhere upstream.

**Execution Trace.** Records every run end to end, from the initial request through retrieval, policy checks, tool calls, and the model response. This is Narek's own application-level audit trail; it is distinct from infrastructure-level observability (see Deployment below).

## Request flow

1. A user configures and triggers an agent from the Dashboard.
2. The Spring Boot API receives the request and hands it to the Agent Runtime.
3. The Agent Runtime consults the Policy Engine to confirm the requested action is allowed.
4. The Agent Runtime reads relevant context from Memory and queries Knowledge Retrieval for supporting documents.
5. If the agent needs an external capability, the Agent Runtime calls it through the Tool Registry.
6. The Agent Runtime assembles the prompt and sends it to the Model Router, which dispatches it to the configured AI Provider.
7. The response comes back through the Model Router to the Agent Runtime, which updates Memory and writes the full run to the Execution Trace.
8. The result is returned to the Dashboard.

Steps 3–6 are not strictly linear in a multi-step agent: a single run can consult the Policy Engine and call tools multiple times before producing a final response. The Execution Trace captures each of those steps individually, not just the final input and output.

## Data stores

| Store | Role |
|-|-|
| PostgreSQL + `pgvector` | Source of truth for conversation/execution memory and the vector store for Knowledge Retrieval |
| Cache (Redis locally, ElastiCache for Valkey in AWS — [ADR 0001](adr/0001-valkey-over-redis.md)) | Read-through cache in front of Postgres; never the only copy of memory |
| RabbitMQ (Amazon MQ in AWS) | Messaging between the Agent Runtime and any asynchronous workflow steps |

## Local vs. production

Locally, every component above runs as a container via Docker Compose, with Ollama as the AI provider. This is deliberate: the architecture is cloud-agnostic, so the same component boundaries hold whether the backend is running on a laptop or on managed AWS infrastructure.

The planned production deployment maps each of these components onto managed AWS services — compute, data stores, networking, and observability. That mapping, along with the operational detail behind it (scaling, backups, encryption, CI/CD), is documented separately in [deployment-aws.md](deployment-aws.md) rather than duplicated here, since it changes independently of the application's component architecture.
