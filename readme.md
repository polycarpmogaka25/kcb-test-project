# KCB Spring Boot Masking Project

## Overview

This project demonstrates a **reusable Spring Boot Starter** for masking sensitive data in logs, along with a **consumer Books API application**.

The goal is to provide a secure, configurable, and reusable masking mechanism while maintaining clean architecture, test coverage, and logging best practices.

**Modules:**
- `p11-masking-spring-boot-starter` – The reusable masking starter library
- `books-api-demo` – Sample Spring Boot CRUD application that uses the starter

---

## Architecture


**Flow:**
1. `books-api-demo` logs DTOs using Jackson serialization.
2. `MaskingSerializer` intercepts JSON serialization and applies masking.
3. Masking logic is in `MaskingEngine`, controlled by `MaskingProperties`.
4. Fields to mask can be **configured via YAML** or annotated with `@Mask`.

---

## Design Decisions

- **Jackson-based masking:** Keeps database values unmodified, applies masking only at serialization/logging time.
- **Dual configuration:** Masking can be controlled by YAML (`p11.masking.fields`) or `@Mask` annotation.
- **Recursive & List support:** Nested objects and lists handled automatically.
- **Conditional auto-configuration:** `MaskingAutoConfiguration` enables masking only if `p11.masking.enabled=true`.
- **SOLID design:** Each class has a single responsibility; low coupling.
- **Test coverage:** Unit and integration tests included; coverage ≥ 80%.

---

## Assumptions Made

- Applications use **Jackson** for JSON serialization and logging.
- Default **mask character = `*`** and **mask style = FULL**.
- Masking applies only during **logging/serialization**, not persistence.
- Nested DTOs and lists may contain sensitive fields.
- Fields without YAML configuration or `@Mask` annotation are not masked.

---

## Configuration Example

```yaml
p11:
  masking:
    enabled: true
    fields:
      - email
      - phoneNumber
    mask-style: PARTIAL
    mask-character: "*"
