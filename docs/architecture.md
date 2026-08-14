# Architecture

## Phase 0 boundaries

`:app` owns application setup, the single activity, Hilt bootstrap, navigation, and theme bootstrap.

`:core:model` contains Android-independent domain types. It must not depend on `Context`, `Activity`, `Intent`, Android device classes, Room entities, BillingClient, or provider SDK types.

`:core:data` contains Room entities, DAOs, DataStore access, analytics abstractions, and entitlement storage. Room entities are separate from domain models.

`:core:ui` contains shared Compose theme and reusable visual components.

`:automation` contains the normalized event and engine boundaries. Platform event adapters and action implementations are deferred until Phase 1 feasibility work.

Feature modules contain screen-level Compose placeholders and must not implement Bluetooth, Wi-Fi, system settings, or other platform automation logic.

## Routine model

```text
Android platform event
    -> trigger adapter (Phase 1)
    -> normalized TriggerEvent
    -> AutomationEngine
    -> matching routines
    -> AND condition evaluation
    -> ordered action execution
    -> execution result and history
```

No scripting, variables, branching, loops, flowcharts, arbitrary intents, webhooks, cloud automation, or accessibility-driven UI automation belong in this model.
