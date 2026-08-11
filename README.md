# Narek

> Infrastructure for building, orchestrating, and governing enterprise AI agents.

Narek is a platform for running, orchestrating, and governing AI agents — built for engineering teams that need agent behavior backed by real infrastructure: a runtime, retrieval and memory layers, tool execution, and policy-based governance, rather than a single conversational assistant.

`Java 21` · `Spring Boot 3` · `Spring AI 1.0` · `Angular 20` · `PostgreSQL + pgvector` · `Redis` · `RabbitMQ` · `Docker`

## Screenshots

<p align="center">
  <img src="docs/screenshots/Dashboard.png" alt="Dashboard" width="800"><br>
  <img src="docs/screenshots/AgentDetail_Configuration.png" alt="Agent Configuration" width="800"><br>
  <img src="docs/screenshots/RunDetails_ExecutionTrace.png" alt="Execution Trace" width="800">
</p>

---

## Overview

The platform controls how agents execute, what knowledge they draw on, what they're permitted to do, and how every execution is traced and audited. It runs locally today on Docker and Ollama; its architecture is designed to scale toward enterprise deployments without changing that core model.

---

## The Problem

Building an AI agent is easy. Operating one in production is not.

Runtime orchestration, retrieval, prompt versioning, tool execution, conversation memory, access control, and execution traceability are all required before an agent can be trusted with a real workload. Most teams end up building fragile, one-off versions of each, tied to a single project and a single model provider.

---

## Why Narek?

Narek is not a chatbot and not an LLM provider. It doesn't train models, and it isn't a general-purpose workflow automation platform.

It is the orchestration layer underneath AI agents: the place where agent execution, knowledge retrieval, prompt management, and governance live, so applications built on top of it don't have to reimplement them.

---

## Philosophy

- Build AI agents, not chatbots.
- Stay provider agnostic — no hard dependency on a single model vendor.
- Keep the architecture cloud-agnostic — a local Docker/Ollama setup and a future cloud deployment share the same design.
- Govern by default — access control and observability are part of the platform, not an afterthought.
- Modular over monolithic — runtime, memory, retrieval, and tools are separable services.

---

## Key Features

**Agent Runtime** — Executes AI agents and coordinates their workflows end to end.

**Retrieval-Augmented Generation (RAG)** — Document ingestion and vector search over PostgreSQL + pgvector to ground agent responses in retrieved knowledge.

**Prompt Management** — Stores and versions prompts used across agents.

**Tool Registry** — Connects agents to external tools and APIs.

**Memory** — Persists conversation and execution context across agent runs.

**Knowledge Management** — Ingests and organizes the documents agents retrieve from.

**Policy Engine** — Governs what agents are allowed to do and access.

**Observability** — Traces, logs, and metrics for every agent execution.

**Management Dashboard** — Angular UI for configuring agents, running them, and inspecting execution history.

**Model Abstraction** — Spring AI decouples agent logic from the model layer; agents run locally via Ollama today, with OpenRouter integration planned for multi-provider access.

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

A user configures and triggers an agent from the dashboard. The runtime retrieves relevant knowledge from the vector store, builds the prompt, sends it to the configured AI provider — Ollama for local execution — and records the full execution for later inspection.

---

## Why Narek Is Different

| Principle | What it means |
|-|-|
| **Agent-First Architecture** | Built to run and govern agents as first-class units, not to wrap a single conversational assistant. |
| **Provider Abstraction** | Agent logic sits behind Spring AI's model abstraction, not a specific vendor SDK, so the LLM backend can change without touching agent code. |
| **Governance Built In** | A dedicated policy engine controls agent access and actions, instead of relying on prompt-level rules. |
| **Full Execution Traceability** | Every agent run is recorded end to end, from retrieval to response, for audit and debugging. |
| **Cloud-Agnostic Architecture** | The same design runs on a local Docker Compose stack today and is built to extend onto managed cloud infrastructure. |

---

## Architecture

<p align="center">
  <img src="docs/screenshots/architecture.svg" alt="Narek Architecture" width="900">
</p>

The Angular dashboard talks to a Spring Boot API, which fans out to three core services: the **Agent Runtime**, the **Policy Engine**, and the **Model Router**. The Agent Runtime coordinates execution and delegates to the **Memory Service** and **Tool Registry**; the **Knowledge Service** handles retrieval against a **pgvector**-backed vector store. The Model Router dispatches requests to the configured **AI Provider** — **Ollama** for local execution today, with **OpenRouter** planned for multi-provider access.

> Full architecture documentation in [docs/architecture.md](./docs/architecture.md)

---

## Tech Stack

| Layer | Technology |
|-|-|
| **Frontend** | Angular 20, TypeScript, Angular Material, Angular Signals, RxJS |
| **Backend** | Java 21, Spring Boot 3, Spring Data JPA / Hibernate |
| **AI** | Spring AI 1.0, Ollama (local execution) — OpenRouter *(planned)* |
| **Data / RAG** | PostgreSQL + pgvector, document ingestion pipeline |
| **Messaging** | RabbitMQ |
| **Security** | Spring Security — Enterprise RBAC *(planned)* |
| **Infrastructure** | Docker / Docker Compose |
| **Deployment** *(planned)* | AWS (ECS, RDS, ElastiCache, Amazon MQ, S3, ALB, CloudFront, IAM, Secrets Manager) |

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
| **MVP** | Core stack (Spring Boot, Spring AI, Ollama, Angular), agent runtime, RAG with pgvector, memory, and observability basics |
| **v0.5** | Policy engine, tool registry, workflow engine |
| **v1.0** | Plugin SDK, multi-agent workflows, cost analytics |
| **v2** | AWS deployment, OpenRouter integration, enterprise RBAC |

> Implementation-level detail in the [Development Roadmap](./docs/development-roadmap.md)

---

## Contributing

Narek is evolving quickly. Open an issue before starting on a large pull request so scope can be agreed on first.

---

## License

MIT License.
