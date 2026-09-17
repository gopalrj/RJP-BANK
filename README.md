# Banking Domain Microservices Platform

A Spring Boot microservices platform for loan origination, built around the
workflow: **register customer → apply for loan → assess risk/underwriting
(third-party API) → e-sign agreement → post transactions**.

Every module targets **Java 8** and deliberately leans on Java 8 language
features (Streams, Optional, Lambdas, method references, functional
interfaces, `java.time`, `CompletableFuture`) instead of imperative
boilerplate — see the "Java 8 features" section below for exactly where.

## Architecture

```
                     ┌────────────────────┐
                     │   eureka-server     │  (service registry, :8761)
                     └─────────▲──────────┘
                               │ register/discover
        ┌──────────────┬──────┴───────┬───────────────┬───────────────┐
        │              │              │               │               │
 customer-service  loan-service  risk-assessment   esignature      transaction
     (:8081)         (:8084)      -service (:8082) -service (:8083)  -service (:8085)
                        │  \
                        │   \__ Feign ──> risk-assessment-service (/api/risk/assess)
                        │__ Feign ──> esignature-service (/api/esign/initiate)
```

- **eureka-server** – Netflix Eureka registry; every other service registers
  itself here and `loan-service` discovers `risk-assessment-service` and
  `esignature-service` by logical name via **OpenFeign**, not hardcoded URLs.
- **customer-service** – customer registration and profile management (H2 +
  JPA).
- **risk-assessment-service** – underwriting engine. Runs a rule-based
  scoring engine *and* calls a (simulated) third-party credit-bureau API,
  combining both results asynchronously.
- **esignature-service** – manages e-signature requests for loan agreements
  (initiate / complete / decline / expire).
- **loan-service** – the orchestrator: validates eligibility, calls
  risk-assessment-service, and on approval calls esignature-service to kick
  off signing. Also exposes portfolio analytics.
- **transaction-service** – posts and reports on account transactions
  (statements, balances, totals by type).

Each service owns its own **in-memory H2 database** (swap the datasource in
`application.yml` for a real database per service in production — a core
microservices principle is no shared database).

## Prerequisites

- JDK 8
- Maven 3.6+
- Internet access on first build (Maven needs to download dependencies)

## Build

From the project root:

```bash
mvn clean install
```

## Run (each in its own terminal, in this order)

```bash
cd eureka-server              && mvn spring-boot:run   # 1. registry first
cd customer-service            && mvn spring-boot:run   # 2.
cd risk-assessment-service     && mvn spring-boot:run   # 3.
cd esignature-service          && mvn spring-boot:run   # 4.
cd loan-service                && mvn spring-boot:run   # 5. depends on 3 & 4 via Eureka
cd transaction-service         && mvn spring-boot:run   # 6.
```

Eureka dashboard: http://localhost:8761

## Sample end-to-end flow (curl)

```bash
# 1. Register a customer
curl -X POST http://localhost:8081/api/customers/register \
  -H "Content-Type: application/json" \
  -d '{
        "fullName":"Asha Rao","email":"asha@example.com","phone":"9876543210",
        "dateOfBirth":"1992-04-15","address":"Pune, IN","panNumber":"ABCDE1234F"
      }'

# 2. Apply for a loan (id from step 1 as customerId)
curl -X POST http://localhost:8084/api/loans/apply \
  -H "Content-Type: application/json" \
  -d '{
        "customerId":1,"amount":500000,"tenureMonths":36,"purpose":"Home renovation",
        "annualIncome":900000,"existingLoanCount":1,"creditScore":720
      }'
# -> loan-service calls risk-assessment-service, and on approval calls
#    esignature-service; response includes riskScore, riskLevel and
#    signatureReference (e.g. ESIGN-AB12CD34)

# 3. Complete the e-signature
curl -X POST http://localhost:8083/api/esign/ESIGN-AB12CD34/complete

# 4. Post a disbursement transaction
curl -X POST http://localhost:8085/api/transactions \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":"ACC-1001","loanId":1,"amount":500000,"type":"CREDIT","description":"Loan disbursement"}'

# 5. Get the account statement (Stream/Collector-based aggregation)
curl http://localhost:8085/api/transactions/account/ACC-1001/statement

# 6. Loan portfolio analytics
curl http://localhost:8084/api/loans/summary
```

## Where each Java 8 feature is used

| Feature | Where |
|---|---|
| **Streams API** | `CustomerServiceImpl` (map/collect), `RuleEngine.evaluate`, `LoanApplicationServiceImpl.getPortfolioSummary` (sum/average/max/groupingBy), `TransactionServiceImpl` (filter/sort/reduce) |
| **Optional** | `CustomerRepository` return types, `CustomerServiceImpl.getById/exists`, `SignatureServiceImpl.findOrThrow` |
| **Lambdas & method references** | Throughout services and controllers, e.g. `Transaction::getAmount`, `RuleOutcome::getScoreAdjustment`, `Comparator.comparing(Transaction::getTimestamp).reversed()` |
| **Functional interfaces** (`Function`, `Predicate`, `Supplier`, custom `UnderwritingRule`) | `CustomerMapper.TO_RESPONSE` / `LoanMapper.TO_RESPONSE` (`Function`), `EligibilityRules` (`Predicate` composition with `.and(...)`), `referenceCodeGenerator` in `SignatureServiceImpl`/`TransactionServiceImpl` (`Supplier`), `UnderwritingRule` in risk-assessment-service |
| **`java.time`** | `LocalDate`, `LocalDateTime`, `Period.between(...)` for age calculation in `CustomerResponse` |
| **`CompletableFuture`** | `RiskAssessmentServiceImpl.assess` (parallel bureau call + rule engine, combined with `thenCombine`), `LoanApplicationServiceImpl.apply` (async Feign calls to risk-assessment-service and esignature-service) |
| **Collectors** (`toList`, `groupingBy`, `counting`, `joining`, `reducing`, `toMap`) | `LoanApplicationServiceImpl.getPortfolioSummary`, `TransactionServiceImpl.getStatement`, `GlobalExceptionHandler.handleValidation` |

## Notes on the third-party API integration

`risk-assessment-service`'s `CreditBureauClient` stands in for a real
credit-bureau/underwriting API call (e.g. Experian, CIBIL, Equifax). It's
implemented as a local simulation so the project runs without external
network access, but it is called exactly the way a real HTTP integration
would be — asynchronously, via `CompletableFuture`, feeding into the same
scoring pipeline. To point it at a real provider, inject a `RestTemplate`/
`WebClient` call to the bureau's endpoint (URL + API key externalized in
`application.yml`) inside `fetchBureauReport(...)`.
