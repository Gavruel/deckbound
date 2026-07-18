# DeckBound

Tracker de partidas de Magic: The Gathering para uso entre amigos, com suporte a múltiplos grupos de jogo (playgroups) isolados entre si.

## Stack

- **Backend:** Java 25 + Spring Boot 3.4.5 + PostgreSQL 16
- **Autenticação:** JWT (jjwt 0.12.6)
- **Frontend:** React (em migração — a versão anterior em HTML/JS + Tailwind está sendo substituída)
- **API externa:** Scryfall (busca de cartas)

## Arquitetura

O backend é multi-tenant: cada `Playgroup` é um grupo de jogo isolado. Um `User` (conta com login) pode ser membro de vários playgroups através de `PlaygroupMember`, que também define o papel (`ADMIN`/`MEMBER`) dentro daquele grupo. Todo endpoint que opera dentro de um playgroup específico exige que o usuário autenticado seja membro dele.

`Player` representa um participante dentro de um playgroup (nome + estatísticas) e não exige conta própria — permite registrar convidados que não têm login no sistema.

## Rodando localmente

### Pré-requisitos

- Java 25
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

### 3. Subir o frontend

```bash
cd frontend
npm install
npm run dev
```

## Estrutura

```
deckbound/
├── backend/          Spring Boot API
├── frontend/         React (em migração)
├── docs/             Diagramas e documentação
├── docker-compose.yml
└── README.md
```

## Autenticação

```
POST   /api/auth/register
POST   /api/auth/login
```

Login retorna um JWT que deve ser enviado em todas as demais chamadas via header `Authorization: Bearer <token>`.

## Endpoints principais

```
POST   /api/playgroups
GET    /api/playgroups/{playgroupId}
POST   /api/playgroups/join

GET    /api/playgroups/{playgroupId}/players
GET    /api/playgroups/{playgroupId}/players/{playerId}
POST   /api/playgroups/{playgroupId}/players
PUT    /api/playgroups/{playgroupId}/players/{playerId}
DELETE /api/playgroups/{playgroupId}/players/{playerId}
GET    /api/playgroups/{playgroupId}/players/ranking

GET    /api/playgroups/{playgroupId}/matches
GET    /api/playgroups/{playgroupId}/matches/{id}
POST   /api/playgroups/{playgroupId}/matches
PATCH  /api/playgroups/{playgroupId}/matches/{id}/observacoes
DELETE /api/playgroups/{playgroupId}/matches/{id}

GET    /api/matches/{playgroupId}/{matchId}/comments
POST   /api/matches/{playgroupId}/{matchId}/comments
DELETE /api/matches/{playgroupId}/{matchId}/comments/{commentId}

GET    /api/commanders
GET    /api/commanders/search?name=Atraxa
POST   /api/commanders
```