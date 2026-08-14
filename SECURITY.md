# Security Policy

## Reporting a vulnerability

Do not report security vulnerabilities in public GitHub Issues. Use GitHub's private vulnerability reporting feature when it is enabled for the repository, or contact the maintainers privately through the security contact configured for the repository.

Please include a reproducible description, affected version or commit, impact, and a safe contact method. Do not include passwords, tokens, purchase details, routine contents, Wi-Fi SSIDs, or Bluetooth identifiers unless strictly necessary and sanitized.

## Security principles

Luvoq treats imports, deep links, intents, and external files as untrusted input. Serialized data cannot grant permissions, entitlement, or privileged execution authority. Production logs must be sanitized and must not contain complete routine or configuration objects.
