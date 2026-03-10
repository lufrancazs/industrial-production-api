# Industrial Production API

Backend da aplicação de gerenciamento de insumos e otimização de produção industrial, desenvolvido com Spring Boot + Java.

## Tecnologias

- Java 17+
- Spring Boot
- Spring Data JPA
- Oracle Database (Docker)
- Flyway
- Maven
- JUnit 5

## Pré-requisitos

- Java 17 ou superior
- Maven
- Docker e Docker Compose

## Como rodar localmente

### 1. Clone o repositório

\`\`\`bash
git clone https://github.com/lufrancazs/industrial-production-api.git
cd industrial-production-api
\`\`\`

### 2. Suba o banco de dados Oracle via Docker

\`\`\`bash
docker-compose up -d
\`\`\`

> ⚠️ O Oracle pode levar até 2 minutos para inicializar completamente.
> Aguarde o status `healthy` antes de rodar a aplicação:
> \`\`\`bash
> docker ps
> \`\`\`

### 3. Rode o projeto

\`\`\`bash
./mvnw spring-boot:run
\`\`\`

A API estará disponível em: [http://localhost:8080](http://localhost:8080)

> As migrations do banco são executadas automaticamente via Flyway na inicialização.

### 4. Rode os testes unitários

\`\`\`bash
./mvnw test
\`\`\`

## Configuração do banco de dados

| Propriedade | Valor |
|---|---|
| Host | localhost |
| Porta | 1523 |
| Service | XEPDB1 |
| Usuário | production |
| Senha | production |

## Endpoints principais

| Método | Endpoint | Descrição |
|---|---|---|
| GET | /raw-material | Lista matérias-primas |
| POST | /raw-material | Cria matéria-prima |
| PUT | /raw-material/{id} | Atualiza matéria-prima |
| DELETE | /raw-material/{id} | Remove matéria-prima |
| GET | /product | Lista produtos |
| POST | /product | Cria produto |
| PUT | /product/{id} | Atualiza produto |
| DELETE | /product/{id} | Remove produto |
| GET | /product-ingredients/product/{id} | Lista ingredientes do produto |
| POST | /product-ingredients | Adiciona ingrediente |
| DELETE | /product-ingredients/{id} | Remove ingrediente |
| GET | /production/simulation | Simula produção otimizada |

## Repositório Frontend

> https://github.com/lufrancazs/industrial-production-frontend
