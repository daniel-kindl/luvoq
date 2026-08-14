# Phase 1 automation feasibility

Phase 1 is a platform-mechanism spike. It is not a production automation
implementation and it does not promote any capability to a user-facing
promise. The purpose is to collect repeatable evidence for the event and
action boundaries already defined in [the architecture](architecture.md).

## Goals

Validate the following flow on representative physical devices:

```text
Android platform event
    -> trigger adapter
    -> normalized TriggerEvent
    -> AutomationEngine boundary
    -> condition evaluation
    -> ordered action execution
    -> execution result
```

The spike must cover:

- charging started and stopped
- battery below and above a threshold
- Bluetooth connected and disconnected
- time reached
- Wi-Fi connected and disconnected as a spike only
- media volume, brightness, opening an app, Do Not Disturb, and a Luvoq
  notification

The engine remains independent of Compose. Platform access belongs in adapters
and action implementations, not in feature UI. A permanent foreground or
background service is not a default solution; it may be considered only when a
specific platform test proves it is required.

## Capability matrix

The mechanism column is a hypothesis to test, not an implementation promise.
Record the Android API and OEM behavior observed during each test run.

| Capability | Candidate mechanism | Important risks | Phase 1 scope |
| --- | --- | --- | --- |
| Charging started | Power connection broadcast | Delivery while the process is dead; OEM battery policy | Validate |
| Charging stopped | Power disconnection broadcast | Delivery while the process is dead; OEM battery policy | Validate |
| Battery below threshold | Battery status broadcasts plus persisted threshold evaluation | Broadcast frequency, duplicate events, process death | Validate |
| Battery above threshold | Battery status broadcasts plus persisted threshold evaluation | Broadcast frequency, duplicate events, process death | Validate |
| Bluetooth connected | Platform Bluetooth connection event/callback | Permission changes, device filtering, OEM behavior | Validate |
| Bluetooth disconnected | Platform Bluetooth connection event/callback | Permission changes, device filtering, OEM behavior | Validate |
| Time reached | Exact or inexact `AlarmManager` alarm as permitted by Android | Doze, exact-alarm access, reboot recovery | Validate |
| Wi-Fi connected | Network callback observing validated transport state | Version/API differences, process death, OEM behavior | Spike only |
| Wi-Fi disconnected | Network callback observing validated transport state | Version/API differences, process death, OEM behavior | Spike only |
| Set media volume | `AudioManager` stream-volume API | Audio focus, stream semantics, user expectations | Validate |
| Set brightness | System brightness API and required special access | Special access, locked-screen behavior, OEM restrictions | Validate |
| Open app | Package launch request for a selected installed app | Background activity launch restrictions, app availability | Validate |
| Do Not Disturb | Notification policy access and interruption-filter API | User-granted special access, policy restrictions | Validate |
| Show Luvoq notification | Notification channel and notification API | Notification permission, channel settings, locked screen | Validate |

The following are explicitly outside the Phase 1 promise surface:

- direct Wi-Fi or Bluetooth toggles
- arbitrary Android intents or shell execution
- accessibility-driven UI automation
- app-open and notification-received triggers
- location/geofencing and NFC
- cloud automation, webhooks, accounts, or a custom backend

## Required test dimensions

Every validated trigger/action combination must be tested in the applicable
states below. Mark a dimension `N/A` only when the mechanism cannot reasonably
exercise it, and explain why in the evidence record.

| Dimension | Required cases |
| --- | --- |
| Device | A representative Google Pixel and a representative Samsung device |
| Android | Record model, Android release, API level, security patch, and OEM build |
| App state | Foreground, background, and process killed |
| Display | Unlocked and screen locked |
| Power | Normal operation and battery saver |
| Lifecycle | Reboot for persisted alarms, boot receivers, and other relevant state |
| Network | Offline wherever the capability does not require network access; connected and disconnected for the Wi-Fi spike |
| Repetition | Repeat each case enough to expose duplicate, missed, or stormed events; record the count |

The same routine configuration must be used for repeated runs. Do not use
routine names, device names, SSIDs, MAC addresses, or other sensitive values in
logs or issue reports.

## Test procedure

For each capability:

1. Define the smallest routine that exercises one trigger and one action.
2. Record the required permission or special-access explanation shown to the
   tester before access is requested.
3. Establish the initial device and routine state, including whether the
   process is alive.
4. Cause exactly one platform event or scheduled alarm.
5. Record whether the event was received, whether the routine matched, the
   ordered action result, and the elapsed time.
6. Repeat the applicable required dimensions and record duplicate or missed
   executions.
7. Revoke and re-grant relevant access, then repeat the affected case.
8. Preserve unsupported or restricted configurations for inspection; do not
   silently delete them from a routine.

The spike may use a developer-only diagnostic surface or instrumentation
harness, but it must not add broad startup permission requests, a backend,
analytics as a dependency, or a permanent service merely to make a test pass.

## Evidence record

Each test run should produce a sanitized record similar to this template:

```text
Capability:
Trigger/action pair:
Date and local timezone:
Device model and OEM:
Android release / API level / build:
App state:
Screen state:
Battery-saver state:
Network state:
Required permission or special access:
Setup:
Expected result:
Observed result:
Latency:
Duplicate or missed event count:
Result: PASS / FAIL / INCONCLUSIVE
Reliability proposal: GREEN / YELLOW / RED
Notes and remediation:
```

Do not attach full domain objects, routine configuration, notification
content, purchase information, or identifiers that can identify a person or
device. Production logs must remain sanitized.

## Reliability gate

The final classification must be evidence-based and capability-specific:

- **GREEN** — repeatable across the required physical-device and lifecycle
  cases, with no material uncommunicated caveat; reliable enough to promise.
- **YELLOW** — usable only with a special permission, Android-version caveat,
  process/lifecycle limitation, or meaningful OEM variation; the limitation
  must be explained in the editor and settings.
- **RED** — materially unreliable, restricted, or too dependent on unsupported
  behavior to ship as a normal Luvoq capability.

An emulator result is useful for software correctness but is not sufficient to
classify a capability GREEN. A capability is not ready for the product surface
until the evidence record identifies the tested devices, lifecycle states,
observed limitations, and remediation for denied or revoked access.

When a classification changes, update the central capability registry and the
capability documentation together. Persist stable type IDs and configuration
schema versions, not localized labels. Unsupported configurations must remain
representable so that a future implementation or import can recover them
safely.

## Phase 1 exit criteria

Phase 1 is complete when:

- each required trigger and action has a documented result or an explicit
  RED/inconclusive decision;
- Pixel and Samsung evidence covers foreground, background, process-killed,
  locked-screen, and battery-saver cases where applicable;
- reboot recovery is tested for mechanisms that need persisted state;
- permission and special-access denial/revocation behavior is documented;
- duplicate events and trigger storms have a bounded handling decision;
- no capability is labeled GREEN solely from emulator testing;
- the resulting capability classifications and user-facing caveats are
  approved before production UI work begins.

Production routine editing, import/export, billing, templates, history, and
the complete automation implementation remain follow-up work after this gate.
