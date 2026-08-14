# Dependency policy

Direct dependencies are pinned in `gradle/libs.versions.toml` and resolved only from Google's Maven repository, Maven Central, and the Gradle plugin portal where required for Gradle plugins.

The project prefers dependencies and third-party assets under Apache-2.0, MIT, BSD, or SIL OFL-compatible terms. GPL, LGPL, AGPL, or unusual licenses require explicit review before introduction. No Firebase, proprietary UI framework, custom backend, or arbitrary analytics SDK is included in Phase 0.

Pull requests run GitHub dependency review, secret scanning, and a repository license-policy check. Dependabot opens reviewable Gradle and GitHub Actions update pull requests; updates are not auto-merged.
