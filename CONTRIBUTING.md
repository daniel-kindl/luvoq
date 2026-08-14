# Contributing to Luvoq

## Development flow

Use local Android Studio development, then open a pull request into `main`. Keep `main` releasable. Use short-lived branches such as `feature/...`, `fix/...`, `docs/...`, and `refactor/...`; do not create a long-lived `develop` branch.

## Commits

Use Conventional Commits, for example:

- `feat(automation): add charging trigger`
- `fix(editor): preserve action ordering`
- `docs: document capability certification`

Breaking changes use `feat!: ...` or a `BREAKING CHANGE` footer.

All contributions must include DCO sign-off using `git commit -s`. No CLA is required initially. Contributors retain copyright in their contributions and submit them under GPLv3.

## Pull requests

Every pull request should include focused changes, tests for behavior, and documentation when public behavior or architecture changes. CI must pass before merge. Do not add secrets, signing material, private user data, or generated local configuration.

Do not label an automation capability GREEN based only on emulator results. Capability claims require the physical-device validation described in the roadmap.
