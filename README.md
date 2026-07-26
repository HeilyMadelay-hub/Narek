# Narek

> **Enterprise AI Agent Platform**

Build, orchestrate, govern and monitor enterprise AI agents.

![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-6DB33F?logo=springboot&logoColor=white)
![Angular](https://img.shields.io/badge/Angular-20-DD0031?logo=angular&logoColor=white)
![Spring AI](https://img.shields.io/badge/Spring%20AI-1.0-6DB33F?logo=spring&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600?logo=rabbitmq&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-pgvector-4169E1?logo=postgresql&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-blue)

<p align="center">
  <img src="./docs/images/hero.png" alt="Narek Dashboard">
</p>

<!-- TODO: add a 15s GIF here showing agent creation & execution once the first flow is working -->

---

# 🚧 Project Status

Narek is currently under active development.

The project is focused on building the core runtime, orchestration layer and dashboard before introducing advanced enterprise capabilities.

---

# ✨ Overview

Modern AI applications require orchestration, memory, governance, observability, security and tool execution. Narek brings these capabilities together in a single platform for building and operating production-ready AI agents.

---

# 🤔 Why Narek?

Traditional AI demos stop at the chatbot.

Narek focuses on everything around it:

- ✔ Runtime
- ✔ Governance
- ✔ Memory
- ✔ Monitoring
- ✔ Policies
- ✔ Workflows

---

# 🎯 Who is Narek for?

Narek is designed for teams building AI-powered applications that require:

- Agent orchestration
- Retrieval-Augmented Generation (RAG)
- Enterprise governance
- Workflow automation
- Tool integration
- Observability and monitoring

---

# 🚀 The Problem

Building AI agents for production quickly becomes complex.

Every project ends up reinventing the same components:

- Agent execution
- Prompt management
- Memory
- Tool integrations
- Authentication
- Authorization
- Observability
- Logs
- Metrics
- Cost estimation
- Knowledge management

Narek unifies all these capabilities into a single platform.

---

# ✨ Core Capabilities

- 🤖 AI Agent Management
- 🧠 Memory Management
- 📚 Knowledge Base
- 🔧 Tool Registry
- 📜 Prompt Management
- 🛡 Policy Engine
- 📊 Observability
- 📈 Metrics & Analytics
- 💰 Cost Estimation
- 🔍 Execution History
- 👥 User & Role Management
- ⚡ Workflow Execution
- 🧩 Plugin Architecture

---

# 🔄 Key Workflows

- Create and configure AI agents
- Attach knowledge sources
- Register external tools
- Execute agent workflows
- Monitor executions in real time
- Review execution history and analytics
- Enforce security policies

---

# 🖥 Dashboard

> *(Dashboard screenshots here)*

The Angular dashboard provides a centralized interface to manage every AI agent running inside the platform.

Modules include:

- Dashboard
- Agents
- Knowledge
- Memory
- Tools
- Policies
- Workflows
- Executions
- Analytics
- Users
- Settings

---

# 🔄 Example Workflow

```text
User
  │
  ▼
AI Agent
  │
  ▼
Knowledge Retrieval
  │
  ▼
Prompt Builder
  │
  ▼
AI Provider
  │
  ▼
Response
  │
  ▼
Execution History
```

---

# 🔎 At a Glance

```text
User
   │
Angular
   │
Spring Boot
   │
Agent Runtime
   │
Knowledge Retrieval
   │
AI Provider
```

---

# 🤖 AI Agent Lifecycle

```text
Create Agent
      │
      ▼
Configure Model
      │
      ▼
Attach Knowledge
      │
      ▼
Attach Tools
      │
      ▼
Configure Memory
      │
      ▼
Define Policies
      │
      ▼
Execute
      │
      ▼
Observe
      │
      ▼
Evaluate
```

---

# 🏗 Architecture

<p align="center">
  <img src="./docs/images/architecture.png" alt="Narek Architecture Diagram">
</p>

```text
Angular Dashboard
        │
Spring Boot API
        │
 ┌──────────────────────────┐
 │ Agent Runtime            │
 │ Memory Service           │
 │ Policy Engine            │
 │ Model Router             │
 │ Tool Registry            │
 └──────────────────────────┘
        │
PostgreSQL • Redis • RabbitMQ • AI Provider
```

<details>
<summary>Detailed component view</summary>

```text
                  Angular Dashboard
                          │
                          ▼
                  Spring Boot API
                          │
      ┌───────────────────┼──────────────────┐
      ▼                   ▼                  ▼
 Agent Runtime     Policy Engine      Model Router
      │                   │                  │
      ▼                   ▼                  ▼
Memory Service     Tool Registry     Prompt Manager
      │
      ▼
Knowledge Service
      │
      ▼
Vector Store (pgvector)
      │
      ▼
AI Provider
     │
 ┌───┴────────────┐
 │                │
 ▼                ▼
Ollama      Azure AI Foundry
(Local)       (Future)
```

</details>

---

# ⚙ Platform Modules

| Module | Description |
|---|---|
| 🤖 **Agent Runtime** | Executes AI agents and coordinates every workflow inside the platform. |
| 🧠 **Memory Service** | Stores conversation history, execution context and long-term memory. |
| 📚 **Knowledge Service** | Indexes and retrieves documents used as contextual information for AI agents. |
| 📜 **Prompt Manager** | Centralized prompt versioning and management. |
| 🔧 **Tool Registry** | Secure registry of external tools — REST APIs, databases, search, email, internal services. |
| 🛡 **Policy Engine** | Controls agent permissions — allowed tools/models, rate limits, approval workflows, security policies. |
| 📊 **Observability** | Execution traces, token estimation, latency, errors, logs and cost estimation. |

---

## 🎯 Design Principles

Narek follows a set of engineering principles that guide every architectural decision.

- Enterprise First
- Modular Architecture
- Cloud Agnostic
- Secure by Default
- Provider Agnostic
- Event Driven
- Observable
- Extensible
- Production Ready

---

# 🏛 Architecture Goals

Narek is designed around a small set of architectural goals:

- Cloud-agnostic
- Provider abstraction
- Modular architecture
- Local-first development
- Enterprise security
- Observable by default

---

# ⚡ Technology Stack

## Frontend

- Angular 20
- TypeScript
- Angular Material
- RxJS
- NgRx
- Angular Signals
- SCSS

## Backend

- Java 21
- Spring Boot 3
- Spring Security
- Spring AI
- Spring Data JPA
- Hibernate

## AI

- Spring AI
- Provider Abstraction
- Ollama (Local Development)
- Embedding Models
- Prompt Templates
- RAG Pipeline

## Data & Storage

- PostgreSQL
- pgvector
- Redis

## Messaging

- RabbitMQ

## Infrastructure

- Docker
- Docker Compose

## Observability

- OpenTelemetry
- Micrometer
- Structured Logging

## DevOps

- Git
- GitHub Actions

---

# 🖥 Local Development

Narek is fully designed to run in a local development environment without requiring any cloud resources.

```text
                Docker Compose
──────────────────────────────────────────

PostgreSQL
pgvector
Redis
RabbitMQ
Ollama

──────────────────────────────────────────
          Spring Boot API
──────────────────────────────────────────
         Angular Dashboard
```

Every service can be started locally using Docker Compose, enabling a complete development environment without relying on cloud infrastructure.

---

# 📸 Screenshots

Screenshots will be available after the first public release.

---

# 📂 Project Structure

```text
narek/

backend/
│
├── api/
├── application/
├── domain/
├── infrastructure/
├── runtime/
├── memory/
├── knowledge/
├── prompts/
├── tools/
├── policies/
└── monitoring/

frontend/
│
├── src/
├── app/
├── core/
├── shared/
├── features/
└── layouts/

docker/

docs/

.github/
```

---

# 🚀 Getting Started

Clone the repository

```bash
git clone https://github.com/yourusername/narek.git
```

Start infrastructure

```bash
docker compose up -d
```

Pull a local model (first run only)

```bash
docker exec -it ollama ollama pull llama3.2
```

> Swap `llama3.2` for any model supported by Ollama.

Run backend

```bash
./mvnw spring-boot:run
```

Run frontend

```bash
ng serve
```

Open

```
http://localhost:4200
```

---

# 📚 Documentation

- Architecture
- AI Pipeline
- Local Development
- API Reference
- Design Principles
- Roadmap
- [Development Roadmap](./docs/development-roadmap.md)

---

# 🛣 Roadmap

## v0.1

- Authentication
- Agent Runtime
- Dashboard
- Local Infrastructure

## v0.5

- Memory
- Knowledge Base
- Tool Registry
- Policy Engine

## v1.0

- Multi-Agent Workflows
- AI Evaluations
- Cost Analytics
- Plugin System
- Provider Abstraction

## v2

- Optional Cloud Deployment
- Azure AI Foundry Integration
- Multi-provider AI Support
- Plugin SDK

---

# 📄 License

MIT License.
