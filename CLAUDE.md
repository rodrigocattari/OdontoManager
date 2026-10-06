# CLAUDE.md — OdontoManager API

API REST para gestão de clínica odontológica (pacientes, funcionários, doenças e anamneses).
Este arquivo define as normas de desenvolvimento do projeto. Siga-as em todo código novo e,
ao tocar em código existente que as viole, corrija no escopo da mudança (sem refatorações amplas não pedidas).

## Stack

- Java 21, Spring Boot 3.4.x, Maven (use o wrapper `./mvnw`)
- Spring Web, Spring Data JPA (Hibernate), Bean Validation, MySQL
- Lombok, ModelMapper, springdoc-openapi (Swagger UI na raiz do context path)
- Context path: `/odontomanager-api`; endpoints em `/api/<recurso>`

## Comandos

```bash
./mvnw clean verify                 # compila + testes (rodar antes de todo commit)
./mvnw test -Dtest=PatientServiceTest   # um teste específico
./mvnw spring-boot:run              # sobe com perfil dev (padrão)
./mvnw spring-boot:run -Pprd        # perfis Maven: dev | sta | prd -> spring.profiles.active
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev,ddl   # gera target/ddl/schema.sql a partir das entidades
```

Os testes (`contextLoads`) precisam do MySQL local rodando (`odonto_db`, usuário `root`).

Swagger: `http://localhost:8080/odontomanager-api/`

## Estrutura de pacotes

Base: `com.gmc.odontomanager`

| Pacote | Responsabilidade |
|---|---|
| `config` | Beans de configuração (ModelMapper, OpenAPI, segurança, auditoria) |
| `controller` | Endpoints REST. Só HTTP: recebe DTO, chama service, devolve `ResponseEntity` |
| `controller.swagger` | Interfaces com as anotações OpenAPI que os controllers implementam |
| `service` | Regras de negócio e fronteira transacional |
| `repository` | Interfaces Spring Data JPA |
| `entity` | Entidades JPA (`entity.base` para superclasses, `entity.enums` para enums) |
| `entity.dtos` | DTOs de request/response/summary |
| `mapper` | Conversão entidade <-> DTO |
| `exception` | Exceções de domínio e `GlobalExceptionHandler` (`exception.handler`) |

Dependências entre camadas são de cima para baixo: `controller -> service -> repository`.
Controller nunca acessa repository nem entidade; repository nunca conhece DTO.

## Convenções de código

- **Idioma**: código, nomes, mensagens de log e de exceção em **inglês**. Documentação (este arquivo, README) e commits podem ser em português.
- **Nomenclatura**: classes `PascalCase`, métodos/campos `camelCase`, constantes `UPPER_SNAKE_CASE`, tabelas e colunas `snake_case`. Sufixos obrigatórios: `Controller`, `Service`, `Repository`, `Mapper`, `RequestDTO`, `ResponseDTO`, `SummaryDTO`, `Exception`.
- **Injeção de dependência**: sempre por construtor com campos `private final`. Nunca `@Autowired` em campo.
- **Imports**: sem imports curingas (`.*`) em código novo; remover imports não usados.
- **Nulos**: prefira `Optional` em retornos de busca; nunca retorne `null` para coleções (retorne vazia). Use `StringUtils.hasText` em vez de `x != null && !x.isBlank()` repetido.
- **Imutabilidade**: prefira `record` para DTOs novos e objetos de valor; `final` em campos sempre que possível.
- **Métodos curtos** e com uma responsabilidade. Lógica duplicada entre serviços (ex.: validação de CPF/email entre Patient e Employee) deve ir para um componente compartilhado.
- **Comentários**: explicar o *porquê*, não o *o quê*. Sem código comentado no repositório.
- **Datas**: `LocalDate`/`LocalDateTime`/`Instant` (nunca `java.util.Date`). Fuso padrão `America/Sao_Paulo`.
- **Dinheiro**: `BigDecimal`, nunca `double`/`float`.
- **Logs**: SLF4J (`@Slf4j` do Lombok). Nunca `System.out`. Nunca logar senha, CPF completo ou dados clínicos.

## Lombok

- Entidades: `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@SuperBuilder` quando houver herança.
- **Proibido `@Data` e `@ToString` sem exclusões em entidades JPA** (lazy loading e recursão em relacionamentos).
- `@EqualsAndHashCode(onlyExplicitlyIncluded = true)` incluindo apenas o `id`.
- Coleções com `@Builder.Default` inicializadas (`new HashSet<>()`).

## Entidades e JPA

- Todas as entidades estendem `Auditable` (`created_at`, `updated_at`). Auditoria precisa de `@EnableJpaAuditing`.
- Declare `@Table(name = ...)` e `@Column(name = ..., nullable = ..., length = ...)` explicitamente.
- Enums sempre `@Enumerated(EnumType.STRING)`.
- Relacionamentos `@ManyToOne`/`@OneToOne` com `fetch = FetchType.LAZY`. Evite `EAGER` (inclusive em `@ElementCollection`).
- Evite N+1: listagens paginadas que expõem relacionamentos devem usar `@EntityGraph` ou `JOIN FETCH`.
- `@Version` para entidades sujeitas a edição concorrente; o `version` deve trafegar no DTO de update e ser comparado no service (conflito -> 409).
- **Schema do banco**: gerenciado exclusivamente pelo **Flyway** (ver seção "Migrations"). `ddl-auto` é `validate`: o Hibernate só confere se as entidades batem com o banco, nunca altera o schema.
- Tipos devem bater com o mapeamento do Hibernate (ex.: `Boolean` -> `BIT`, `LocalDateTime` -> `DATETIME(6)`, enum `STRING` -> `VARCHAR` com `@JdbcTypeCode(SqlTypes.VARCHAR)`).

## Migrations (Flyway)

```
src/main/resources/db/
├── migration/            # schema — roda em todos os ambientes
│   ├── v0.1/V1__create_initial_schema.sql
│   └── v0.2/V2__...      # uma subpasta por versão de release
└── seed-dev/             # dados de desenvolvimento — só no perfil dev
    └── R__seed_dev_data.sql
```

**Fluxo para alterar o banco:**
1. Altere a entidade Java.
2. Gere o DDL: `./mvnw spring-boot:run -Dspring-boot.run.profiles=dev,ddl` -> `target/ddl/schema.sql` (schema completo das entidades; não altera o banco).
3. Escreva a nova migration com **apenas a diferença** (`ALTER TABLE`, `CREATE TABLE`...), usando o arquivo gerado como referência de tipos e nomes.
4. Suba a aplicação/testes: o Flyway aplica e o `validate` confirma que entidade e banco batem.

**Regras:**
- Numeração sequencial e única no projeto inteiro (`V1`, `V2`...), independente da subpasta. Antes de criar, confira o maior número existente.
- Nome descritivo em inglês e `snake_case`: `V7__add_cancel_reason_to_appointment.sql`.
- Uma migration pequena por mudança, no **mesmo commit** da alteração da entidade.
- Migration já aplicada **nunca** é editada nem renomeada/movida de versão; correção vai em nova versão.
- Mudanças estruturais (ex.: herança de tabelas, renomear coluna) exigem migration de **dados**, não só de estrutura.
- Nomeie constraints explicitamente: `uk_<tabela>_<coluna>`, `fk_<tabela>_<referência>`, `idx_<tabela>_<coluna>`.
- Seeds de dev são repetíveis (`R__`) e idempotentes (`INSERT IGNORE` sobre coluna única); nunca colocar dados de teste em `db/migration`.
- Views/procedures, se existirem, usam migrations repetíveis `R__` em `db/migration`.
- Se o time crescer e houver migrations em paralelo, trocar para versão por timestamp (`V2026_10_06_1430__...`).
- Restrições de unicidade e integridade existem no banco **e** são verificadas no service para gerar erro amigável; ainda assim trate `DataIntegrityViolationException` no handler global.

## DTOs e mapeamento

- Nunca expor entidade na API. Entrada: `XxxRequestDTO`; saída: `XxxResponseDTO`; referência resumida dentro de outro DTO: `XxxSummaryDTO`.
- Senha (mesmo com hash) **nunca** aparece em ResponseDTO. `roles` podem ser exibidas, mas nunca são definidas pelo cliente em requests de cadastro.
- Validação com Bean Validation no RequestDTO (`@NotBlank`, `@Email`, `@CPF`, `@Size`, `@Past`...). Mensagens claras em inglês.
- Normalizar entradas antes de persistir/consultar (ex.: CPF só dígitos, email em minúsculas e `trim`).
- Mappers são `@Component` com métodos `toEntity`, `toResponseDTO`, `updateEntityFromDTO`. Update deve ter semântica definida e consistente: `PUT` substitui o recurso inteiro; atualização parcial só via `PATCH`.
- Referências por id em requests (ex.: `diseaseIds`) devem ser validadas: id inexistente gera 404/400, nunca é descartado silenciosamente.

## Camada de serviço

- `@Transactional(readOnly = true)` em consultas; `@Transactional` em escrita. Transações só no service.
- Lançar exceções de domínio (`ResourceNotFoundException`, `BusinessException`, `ConflictException`...), não `RuntimeException` genérica. Evite usar `IllegalArgumentException` para regra de negócio em código novo.
- Busca por id + exceção é padrão repetido: centralize em um método privado `findEntityById(id)`.
- Antes de excluir, verifique dependências (ou use exclusão lógica) para não estourar FK como erro 500.

## API REST

- Recursos no **plural**, em inglês, minúsculas e `kebab-case` quando composto: `/api/patients`, `/api/employees`, `/api/diseases`, `/api/anamneses`. Sub-recursos: `/api/patients/{id}/anamneses`.
- Verbos e status:
  - `GET` lista (paginado, `Page<>`) -> 200; `GET /{id}` -> 200 / 404
  - `POST` -> 201 com header `Location`
  - `PUT /{id}` -> 200; `PATCH /{id}` -> 200
  - `DELETE /{id}` -> 204
  - Validação -> 400; não autenticado -> 401; sem permissão -> 403; conflito (duplicidade, versão, FK) -> 409
- Listagens sempre paginadas com `@PageableDefault`; limitar tamanho máximo de página (`spring.data.web.pageable.max-page-size`).
- Erros sempre no formato `StandardError`/`ValidationError` via `GlobalExceptionHandler`. Nenhuma exceção deve chegar ao cliente como 500 com stack trace; adicione handler genérico para `Exception` que loga e devolve mensagem neutra.
- Documentação OpenAPI vive nas interfaces de `controller.swagger` (`@Tag`, `@Operation`, `@ApiResponse` com todos os status possíveis). O controller só implementa.

## Segurança

- Senhas **sempre** com hash via o bean `PasswordEncoder` (BCrypt, `config/PasswordEncoderConfig`). Proibido texto puro e senha padrão fixa. Usuário sem senha (`password` nulo) não tem acesso ao sistema.
- Hoje o projeto usa apenas `spring-security-crypto` (sem autenticação). Ao implementar login, adotar `spring-boot-starter-security` com JWT stateless e autorização por `Role` nos endpoints.
- Credenciais e segredos fora do código: use variáveis de ambiente (`${DB_PASSWORD}`) por perfil. `application.yaml` versionado só com valores de dev local.
- Dados de saúde e CPF são dados pessoais sensíveis (LGPD): não logar, não expor além do necessário, controlar acesso por perfil (`Role`).
- Nunca confiar em dados do cliente para autorização (roles, ids de dono do recurso).
- `show-sql: true` apenas em dev.

## Testes

- Todo service novo ou alterado tem teste unitário (JUnit 5 + Mockito + AssertJ) em `src/test/java` espelhando o pacote.
- Controllers: `@WebMvcTest` + `MockMvc` cobrindo status HTTP, validação e formato de erro.
- Repositórios com queries customizadas: `@DataJpaTest` (preferencialmente com Testcontainers MySQL).
- Nome de teste descritivo: `shouldThrowNotFoundWhenPatientDoesNotExist`. Estrutura given/when/then.
- Bugs corrigidos ganham teste que reproduz o cenário.
- Não desabilitar nem apagar testes para fazer o build passar.

## Git e fluxo de trabalho

- Branches: `feature/<descricao>`, `fix/<descricao>`, `refactor/<descricao>` a partir de `main`.
- Commits no padrão **Conventional Commits**: `feat:`, `fix:`, `refactor:`, `test:`, `docs:`, `chore:`. Um assunto por commit.
- `./mvnw clean verify` deve passar antes de commitar.
- Não commitar `target/`, configs de IDE pessoais nem segredos.

## Checklist antes de concluir uma tarefa

1. Compila e todos os testes passam (`./mvnw clean verify`).
2. Testes novos cobrem o comportamento alterado.
3. Nenhuma entidade exposta, nenhum dado sensível em resposta ou log.
4. Erros mapeados para status HTTP corretos (sem 500 em cenário previsível).
5. Mudança de schema acompanhada de migration.
6. Documentação OpenAPI atualizada.
