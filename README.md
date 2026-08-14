# Luvoq

Your phone, on cue.

[![CI](https://github.com/daniel-kindl/luvoq/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/daniel-kindl/luvoq/actions/workflows/ci.yml)
[![License: GPLv3](https://img.shields.io/badge/License-GPLv3-blue.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)](https://developer.android.com/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?logo=jetpackcompose&logoColor=white)](https://developer.android.com/compose)
[![Material 3](https://img.shields.io/badge/Material%203-757575?logo=materialdesign&logoColor=white)](https://m3.material.io/)

Luvoq is a local-first Android phone-automation app intended to make useful routines understandable to normal users without programming concepts.

## Current state: Phase 1 feasibility spike

This repository currently contains the project and repository foundation only:

- Kotlin, Jetpack Compose, Material 3, Hilt, Room, DataStore, Coroutines, Flow, and kotlinx.serialization setup
- A single-activity navigation shell with Home, Templates, Create, History, Settings, Routine Editor, and Paywall placeholders
- Framework-free domain models for routines, triggers, conditions, actions, and execution results
- A placeholder automation engine boundary and an unvalidated capability registry
- Initial Android platform adapters for charging broadcasts and scheduled time
  events, dispatching normalized events to the existing no-op engine
- Room entities for routine and execution storage
- Local entitlement and no-op analytics abstractions
- GPLv3 governance, contribution, security, support, and trademark documentation

No automation capability is currently promised or executing routines. The Phase 1 spike only validates event delivery and normalization; it does not yet match persisted routines or execute actions. Platform mechanisms must be validated on representative Pixel and Samsung devices before any capability is labeled reliable.

## Build and test

Use Android Studio with JDK 17 and an installed Android SDK matching the project configuration.

```text
./gradlew test
./gradlew lintDebug
./gradlew assembleDebug
```

On Windows, use `gradlew.bat`.

## Architecture

The application is split into a small number of purpose-driven modules. Domain models in `core:model` do not depend on Android. Platform adapters and real automation execution are intentionally absent from Phase 0. The `app` module owns application bootstrap, Hilt wiring, and navigation.

Routine shape:

```text
WHEN one trigger
ONLY IF zero or more AND conditions
DO one or more ordered actions
```

## Repository workflow

GitHub is the source-code repository and source of truth. Work should use short-lived `feature/...`, `fix/...`, `docs/...`, or `refactor/...` branches and merge through pull requests into a releasable `main` branch. There is no long-lived `develop` branch.

Commits use Conventional Commits and DCO sign-off. See [CONTRIBUTING.md](CONTRIBUTING.md).

## Privacy and data

There is no account, backend, cloud automation, or Firebase dependency. Android automatic backup and device transfer are disabled. Explicit user-controlled JSON backup/import remains a future boundary and is not implemented in Phase 0.

## License and branding

The source code is licensed under GPLv3. Official Luvoq names, logos, icons, artwork, and other brand identifiers are reserved separately; see [TRADEMARKS.md](TRADEMARKS.md).
