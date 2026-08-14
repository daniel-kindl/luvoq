# Capability certification

The capability registry is a catalog boundary, not a reliability claim. Each capability must have a stable type ID, localized title and description keys, category, configuration schema version, required access, Android availability, entitlement tier, and reliability classification.

Reliability classifications:

- GREEN: reliable enough to promise
- YELLOW: permission, Android-version, or OEM caveat exists
- RED: not reliable enough to ship normally

Phase 0 leaves initial platform capabilities YELLOW until Phase 1 tests foreground, background, killed-process, locked-screen, battery-saver, reboot, Pixel, and Samsung scenarios. See the [Phase 1 automation feasibility matrix](phase-1-feasibility.md) for the required evidence and classification gate.
