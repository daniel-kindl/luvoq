# Release policy

Public releases use Semantic Versioning. Examples include `v0.1.0`, `v0.2.0-alpha.1`, and `v1.0.0`.

`versionName` follows SemVer. Android `versionCode` increases monotonically and remains independent enough for Play sequencing.

The normal path is local development, pull request, `main`, CI, release tag, protected GitHub environment, signed Android App Bundle, Google Play API, and internal/closed/production promotion. Production publishing requires explicit protected-environment approval. Published artifacts and tags are immutable.
