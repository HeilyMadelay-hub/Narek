# 0002 – Use OpenRouter instead of Amazon Bedrock as the planned multi-provider AI gateway

## Context

Narek's model abstraction (Spring AI) decouples agent logic from any specific model vendor. For production, the project needs one AI provider that can route requests across multiple underlying LLMs without tying the application to a single vendor's SDK or being limited to the models that vendor hosts.

Amazon Bedrock was the originally planned option, since it would sit naturally inside the AWS deployment (IAM-based access, no separate API key). However, it only exposes the models each provider has specifically made available on Bedrock, which is a subset of what's available directly from each provider, and adding a new model family generally requires AWS to onboard it first.

Bedrock is also expensive at the volume a production agent platform would realistically generate. On-demand per-token pricing on Bedrock runs well above what the same model costs called directly or through an aggregator, and at agent-workload volumes, with multi-step reasoning and tool calls multiplying the number of model invocations per task, that gap compounds fast. Provisioned Throughput brings the per-token price down but requires committing to hourly capacity whether or not it is fully used, which doesn't fit a platform whose usage will scale unevenly across customers and agent types. Taking this to production on Bedrock would spike inference costs well beyond what the same workload costs elsewhere.

OpenRouter provides a single API that routes to a much broader and faster-moving set of models across providers, without being limited by what any one cloud vendor has chosen to host, and lets the workload pick the cheapest suitable model per call instead of being locked into Bedrock's pricing for whichever model is used.

## Decision

Use OpenRouter as the AI provider for multi-provider access instead of Amazon Bedrock.

In the AWS deployment, this means no dedicated AWS AI service is used for inference: the backend makes an outbound HTTPS call to OpenRouter via the NAT Gateway, authenticated with an API key stored in Secrets Manager, instead of an IAM-authenticated call to Bedrock.

## Consequences

- Avoids Bedrock's cost profile at production scale: no per-model on-demand markup and no Provisioned Throughput capacity to commit to and pay for whether or not it's fully used.
- Model selection is not constrained by what AWS has onboarded to Bedrock; any model OpenRouter supports is available.
- The backend now depends on an external API key (via Secrets Manager) rather than an AWS-native IAM permission, so key rotation and spend limits are managed through OpenRouter's own per-key credit cap, not through AWS IAM or Budgets.
- Outbound calls to OpenRouter go through the NAT Gateway and are billed as data processed, a cost Bedrock calls (staying inside the AWS network) would not have incurred.
- Because this sits behind Spring AI's model abstraction, switching providers again later (including adding Bedrock as one more option) does not require changes to agent logic.
