# Narek

> Infrastructure for building, orchestrating, and governing enterprise AI agents.

Narek is an open-source platform that provides the runtime, retrieval, memory, and governance layer AI applications need beyond a simple chat interface — so teams stop rebuilding the same infrastructure for every project.

`Java 21` · `Spring Boot 3` · `Spring AI 1.0` · `Angular 20` · `PostgreSQL + pgvector` · `Redis` · `RabbitMQ` · `Docker`

## Screenshots

<p align="center">
  <img src="docs/screenshots/Dashboard.png" alt="Dashboard" width="800"><br>
  <img src="docs/screenshots/AgentDetail_Configuration.png" alt="Agent Configuration" width="800"><br>
  <img src="docs/screenshots/RunDetails_ExecutionTrace.png" alt="Execution Trace" width="800">
</p>

---

## Overview

Most AI projects end up rebuilding the same infrastructure: an agent runtime, retrieval, prompt management, tool execution, memory, governance, and observability. Narek provides these as a reusable platform instead of a bespoke stack per application.

The platform is built around agents, not a single assistant: it manages how agents run, what they know, what they're allowed to do, and how every execution can be traced and audited. It targets provider-agnostic, cloud-agnostic deployment — it runs locally today via Docker and Ollama, and is designed to scale into enterprise environments without changing its core architecture.

---

## The Problem

Building an AI agent is easy. Operating one in production is not.

Runtime orchestration, retrieval, prompt versioning, tool execution, conversation memory, access control, and traceability are all required before an agent can be trusted with real workloads — and most teams end up building fragile, one-off versions of each. Narek exists so that infrastructure is solved once, as a platform, instead of once per project.

---

## Why Narek?

Narek is not a chatbot and not an LLM provider. It doesn't train models or compete with general-purpose workflow automation platforms.

It is the orchestration layer underneath AI agents: the place where agent execution, knowledge retrieval, prompt management, and governance live so that applications built on top of it don't have to reinvent them.

---

## Philosophy

- Build AI agents, not chatbots.
- Stay provider agnostic — no hard dependency on a single model vendor.
- Keep the architecture cloud-agnostic; a local Docker/Ollama setup and an enterprise cloud deployment share the same design.
- Govern by default — access control and observability are part of the platform, not an afterthought.
- Modular over monolithic — runtime, memory, retrieval, and tools are separable services.

---

## Key Features

**Agent Runtime** — Executes AI agents and coordinates their workflows end to end.

**Retrieval-Augmented Generation (RAG)** — Document ingestion and vector search over PostgreSQL + pgvector to ground agent responses in real knowledge.

**Prompt Management** — Stores and versions prompts used across agents.

**Tool Registry** — Connects agents to external tools and APIs.

**Memory** — Persists conversation and execution context across agent runs.

**Knowledge Management** — Ingests and organizes the documents agents retrieve from.

**Policy Engine** — Governs what agents are allowed to do and access.

**Observability** — Traces, logs, and metrics for every agent execution.

**Management Dashboard** — Angular UI for configuring agents, running them, and inspecting execution history.

**Multi-Provider AI Access** — Spring AI abstracts the model layer, with local execution via Ollama and multi-provider access via OpenRouter.

---

## How It Works

```
User
  │
Dashboard
  │
Agent Runtime
  │
Knowledge Retrieval
  │
Prompt Builder
  │
AI Provider
  │
Execution History
```

A user configures and triggers an agent from the dashboard. The agent runtime pulls relevant knowledge from the vector store, builds the prompt, sends it to the configured AI provider (local via Ollama or remote via OpenRouter), and records the full execution for later inspection.

---

## Why Narek Is Different

| Principle | What it means |
|-|-|
| **Agent-First Architecture** | Built around running and governing agents, not a single conversational assistant. |
| **Provider Abstraction** | Spring AI decouples agent logic from any one model vendor — swap providers without rewriting agents. |
| **Governance Built In** | A dedicated policy engine controls what agents can access and do, instead of relying on prompt-level rules. |
| **Full Execution Traceability** | Every agent run is recorded end to end, from retrieval to response, for audit and debugging. |
| **Cloud-Agnostic by Design** | The same architecture runs on a local Docker Compose stack or scales onto managed cloud infrastructure. |

---

## Architecture

<p align="center">
  <img src="docs/screenshots/architecture.svg" alt="Narek Architecture" width="900">
</p>

The Angular dashboard talks to a Spring Boot API, which fans out to three core services: the **Agent Runtime**, the **Policy Engine**, and the **Model Router**. The Agent Runtime coordinates execution and delegates to the **Memory Service** and **Tool Registry**; the **Knowledge Service** handles retrieval against a **pgvector**-backed vector store. The Model Router dispatches requests to the configured **AI Provider** — **Ollama** for local execution today, with **OpenRouter** as the path to multi-provider access.

> Full architecture documentation in [docs/architecture.md](./docs/architecture.md)

---

## Tech Stack

| Layer | Technology |
|-|-|
| **Frontend** | Angular 20, TypeScript, Angular Material, Angular Signals, RxJS |
| **Backend** | Java 21, Spring Boot 3, Spring Data JPA / Hibernate |
| **AI** | Spring AI 1.0 (LLM orchestration and provider abstraction), Ollama (local execution), OpenRouter (multi-provider access) |
| **Data / RAG** | PostgreSQL + pgvector (relational + vector storage), document ingestion pipeline |
| **Security** | Spring Security, Enterprise RBAC *(roadmap)* |
| **Messaging** | RabbitMQ |
| **Infrastructure** | Docker / Docker Compose |
| **Deployment (future)** | AWS — ECS (Fargate), ECR, RDS for PostgreSQL (pgvector), ElastiCache for Redis, Amazon MQ for RabbitMQ, S3, Application Load Balancer, CloudFront, Route 53, Secrets Manager, IAM |

---

## Quick Start

```bash
git clone https://github.com/yourusername/narek.git
cd narek

docker compose up -d
./mvnw spring-boot:run
```

```bash
cd frontend
npm install
ng serve
```

Open the dashboard at `http://localhost:4200`. The API runs on `http://localhost:8080` (`/actuator/health` for a quick check).

On first run, pull a local model for Ollama:

```bash
docker exec -it ollama ollama pull llama3.2
```

**Requirements:** Java 21+, Node.js 20+, Docker and Docker Compose.

---

## Documentation

| Document | Description |
|---|---|
| [Development Roadmap](./docs/development-roadmap.md) | Implementation milestones for contributors and maintainers |

Additional documents (architecture, API reference, RAG pipeline) will be added under [`docs/`](./docs) as those parts of the platform mature.

---

## Roadmap

| Phase | Focus |
|-|-|
| **MVP** | Spring Boot + Spring AI + Ollama backend, Angular dashboard, authentication, agent runtime, RAG with pgvector, conversation memory, observability basics |
| **v0.5** | Policy engine, tool registry, workflow engine |
| **v1.0** | Plugin SDK, multi-agent workflows, cost analytics, provider abstraction |
| **v2** | AWS deployment (ECS, RDS, ElastiCache, Amazon MQ), OpenRouter integration, enterprise RBAC |

> Implementation-level detail in the [Development Roadmap](./docs/development-roadmap.md)

---

## Contributing

Narek is evolving quickly. Open an issue before starting on a large pull request so scope can be agreed on first.

---

## License

MIT License.
