# spring-boot-sendGrid

[![Java CI with Maven](https://github.com/hendisantika/spring-boot-sendGrid/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/spring-boot-sendGrid/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.2-6DB33F?logo=springboot)
![Dependabot](https://img.shields.io/badge/Dependabot-enabled-025E8C?logo=dependabot)

A sample Spring Boot application that sends emails through the [SendGrid](https://sendgrid.com/) Web API, both as plain
text and with a SendGrid Dynamic Template.

## Tech Stack

- Java 25
- Spring Boot 4.1.2 (Web, DevTools)
- SendGrid Java SDK 4.10.3
- Lombok
- Maven (via the bundled Maven Wrapper)

## Prerequisites

- JDK 25 (e.g. [Temurin](https://adoptium.net/) or Corretto)
- A SendGrid account with:
    - an API key that has **Mail Send** permission
    - a verified Single Sender (used as the "from" address)
    - a Dynamic Template (only needed for the template endpoint)

See [SENDGRID_SETUP.md](SENDGRID_SETUP.md) for a step-by-step SendGrid setup guide.

## Configuration

All settings live in `src/main/resources/application.properties` and can be overridden with environment variables:

| Property               | Environment variable   | Description                            |
|------------------------|------------------------|----------------------------------------|
| `sendgrid.api.key`     | `SENDGRID_API_KEY`     | SendGrid API key                       |
| `sendgrid.template.id` | `SENDGRID_TEMPLATE_ID` | Dynamic Template ID (`d-...`)          |
| `sendgrid.from.email`  | `SENDGRID_FROM_EMAIL`  | Verified sender email address          |
| `sendgrid.to.email`    | `SENDGRID_TO_EMAIL`    | Recipient email address                |
| `server.port`          | `SERVER_PORT`          | HTTP port (default `8080`)             |

> Never commit a real API key. Prefer environment variables.

## Getting Started

1. Clone this repository:
   ```shell
   git clone https://github.com/hendisantika/spring-boot-sendGrid.git
   cd spring-boot-sendGrid
   ```
2. Export your SendGrid credentials:
   ```shell
   export SENDGRID_API_KEY=your_api_key
   export SENDGRID_TEMPLATE_ID=d-xxxxxxxxxxxxxxxx
   export SENDGRID_FROM_EMAIL=verified-sender@example.com
   export SENDGRID_TO_EMAIL=recipient@example.com
   ```
3. Run the application:
   ```shell
   ./mvnw clean spring-boot:run
   ```

## API Endpoints

| Method | Endpoint         | Description                                          |
|--------|------------------|------------------------------------------------------|
| GET    | `/api/send-text` | Sends a plain text email                             |
| GET    | `/api/send`      | Sends an email using the configured Dynamic Template |

Example:

```shell
curl http://localhost:8080/api/send-text
curl http://localhost:8080/api/send
```

## Build & Test

```shell
./mvnw clean verify
```

## CI/CD

- **GitHub Actions** ([`maven.yml`](.github/workflows/maven.yml)) builds and tests the project on JDK 25 for every push
  and pull request to `main`, and submits the Maven dependency graph to GitHub on pushes to `main`.
- **Dependabot** ([`dependabot.yml`](.github/dependabot.yml)) keeps dependencies up to date:
    - Maven dependencies, plugins and the wrapper are checked daily, with Spring, Maven plugin and minor/patch updates
      grouped into fewer PRs.
    - GitHub Actions are checked weekly and grouped into a single PR.
    - PRs use conventional commit prefixes (`chore(deps)`, `ci(deps)`) and are labelled automatically.
