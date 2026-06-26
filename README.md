# DeckBound

Tracker de partidas de Magic: The Gathering para uso entre amigos.

## Stack

- **Backend:** Java 21 + Spring Boot 3.2 + PostgreSQL 16
- **Frontend:** HTML + JavaScript (ES Modules) + Tailwind CSS
- **API externa:** Scryfall (busca de cartas)

## Rodando localmente

### Pre-requisitos

- Java 21
- Maven 3.9+
- Docker (para o banco de dados)

### 1. Subir o banco

```bash
docker-compose up -d
```

### 2. Subir o backend

```bash
cd backend
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

### 3. Abrir o frontend

Abra o arquivo `frontend/index.html` em um servidor local.
Com VS Code, use a extensão **Live Server**.
Ou via terminal:

```bash
cd frontend
npx serve .
```

## Estrutura

```
deckbound/
├── backend/          Spring Boot API
├── frontend/         HTML + JS + Tailwind
│   ├── index.html
│   ├── js/api.js     Camada de comunicação com a API
│   └── pages/        Páginas da aplicação
├── docs/             Diagramas e documentação
├── docker-compose.yml
└── README.md
```

## Endpoints principais

```
GET    /api/players
POST   /api/players
GET    /api/players/ranking

GET    /api/matches
POST   /api/matches
GET    /api/matches/{id}
DELETE /api/matches/{id}

POST   /api/commanders
GET    /api/commanders/search?name=Atraxa

GET    /api/matches/{id}/comments
POST   /api/matches/{id}/comments
```
