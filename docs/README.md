# 🏗️ Desafio Fullstack Integrado
# Sistema de Gerenciamento de Benefícios

Aplicação full-stack para gerenciamento de benefícios, permitindo cadastrar, listar, editar, excluir e transferir valores entre benefícios.

## Tecnologias utilizadas

### Backend
- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit 5
- MockMvc
- Springdoc OpenAPI / Swagger

### Frontend
- Angular
- TypeScript
- HTML
- CSS

## Funcionalidades

- Listagem de benefícios
- Busca de benefício por ID
- Cadastro de benefício
- Edição de benefício
- Exclusão de benefício
- Transferência de valor entre benefícios
- Documentação da API com Swagger
- Testes automatizados no backend

## Pré-requisitos
Antes de executar o projeto, é necessário ter instalado:

Java 17 ou superior

Maven

Node.js

Angular CLI

PostgreSQL

## Configuração do banco de dados

Crie um banco PostgreSQL e configure as credenciais no backend.

O projeto possui script de schema.sql e seed.sql, execute-os no banco antes de iniciar a aplicação.

pode utilizar do Database Client do vscode ou do pgAdmin para executar os scripts SQL.

```bash

## Como executar o backend
Acesse a pasta do backend:

```bash
cd backend-module
```
Execute a aplicação:

```bash
mvn spring-boot:run
```
O backend estará disponível em:

```text
http://localhost:8080
```

## Como executar o frontend
Acesse a pasta do frontend:

```bash
cd frontend
```
Instale as dependências:

```bash
npm install
```
Execute o projeto:

```bash
ng serve
```
O frontend estará disponível em:

```text
http://localhost:4200
```
## Testes do backend
Para executar os testes automatizados do backend:

```bash
cd backend-module
mvn test
```
## Os testes cobrem os principais endpoints do controller de benefícios:

- listagem

- busca por ID

- criação

- atualização

- exclusão

- transferência

## Documentação da API
Com o backend em execução, acesse a documentação Swagger em:

```text
http://localhost:8080/swagger-ui.html
```
A especificação OpenAPI também pode ser acessada em:

```text
http://localhost:8080/v3/api-docs
```
## Endpoints principais
- GET /api/v1/beneficios

- GET /api/v1/beneficios/{id}

- POST /api/v1/beneficios

- PUT /api/v1/beneficios/{id}

- DELETE /api/v1/beneficios/{id}

- POST /api/v1/beneficios/transfer

## Observações
O frontend consome a API do backend em http://localhost:8080.

O backend está configurado com CORS para permitir acesso do Angular em http://localhost:4200.

A documentação Swagger foi adicionada com Springdoc.

Os testes do backend foram implementados com MockMvc.

## Autor

Projeto desenvolvido por Marcus Thulio como parte de desafio tecnico de fullstack integrado.

## Feedback

Se você tiver qualquer feedback, me envie um email para marcusthuliok9@gmail.com