# Agência API — Cache com Redis

API REST desenvolvida em Java para gerenciamento de agências, com implementação de **cache utilizando Redis e `RedisTemplate`**.

O projeto foi desenvolvido com foco em praticar integração entre uma aplicação backend, banco de dados e Redis, utilizando o padrão **Cache-Aside**.

## 🚀 Tecnologias

* Java
* Quarkus
* REST API
* Hibernate ORM / Panache
* Redis
* `RedisTemplate`
* PostgreSQL
* Maven
* Docker

## 📌 Funcionalidades

* Cadastro de agência
* Consulta de agência por ID
* Listagem de agências
* Atualização de agência
* Exclusão de agência
* Cache de consultas utilizando Redis
* Invalidação do cache após atualização ou exclusão

## 🏗️ Arquitetura

O fluxo principal de consulta funciona da seguinte forma:

```text
Cliente
   │
   ▼
REST API
   │
   ▼
Service
   │
   ├──────────────► Redis
   │                 │
   │          Cache encontrado?
   │             /       \
   │           SIM        NÃO
   │            │          │
   │            ▼          ▼
   │          Retorna    Banco de dados
   │                         │
   │                         ▼
   │                       Redis
   │                         │
   └─────────────────────────┘
```

A aplicação primeiro verifica se a agência está disponível no Redis.

Caso exista no cache, o dado é retornado diretamente, evitando uma nova consulta ao banco de dados.

Caso não exista, a aplicação consulta o banco, salva o resultado no Redis e retorna a informação para o cliente.

## ⚡ Estratégia de Cache

O projeto utiliza o padrão **Cache-Aside**.

### Consulta

```text
GET /agencias/{id}
```

Fluxo:

1. Recebe a requisição.
2. Verifica a chave no Redis.
3. Se encontrar, retorna o objeto armazenado.
4. Caso não encontre, consulta o banco de dados.
5. Salva o resultado no Redis.
6. Retorna a agência.

### Atualização

```text
PUT /agencias/{id}
```

Após atualizar a agência no banco, o cache correspondente é invalidado para evitar que dados antigos permaneçam disponíveis.

### Exclusão

```text
DELETE /agencias/{id}
```

Ao excluir uma agência, sua chave também é removida do Redis.

## 🔑 Chaves utilizadas

As agências são armazenadas utilizando uma chave baseada no ID:

```text
agencia_{id}
```

Exemplo:

```text
agencia_1
agencia_2
agencia_10
```

## 🧠 Por que utilizar Redis?

O Redis permite armazenar dados em memória, possibilitando respostas mais rápidas para informações que são acessadas frequentemente.

Neste projeto, ele é utilizado para reduzir a quantidade de consultas realizadas diretamente no banco de dados.

```text
Sem cache:

Cliente → API → Banco de dados

Com cache:

Cliente → API → Redis
                 │
                 └── Cache Miss → Banco de dados
```

## 📦 Exemplo de JSON

### Criar agência

```json
{
  "nome": "Agência Centro",
  "cnpj": 12345678000190,
  "endereco": {
    "rua": "Rua das Flores",
    "numero": 100,
    "cidade": "Rio de Janeiro"
  }
}
```

> Para o CNPJ, recomenda-se utilizar `String` no backend, pois o valor não representa uma quantidade matemática e pode conter zeros à esquerda.

## 🔗 Endpoints

| Método   | Endpoint         | Descrição         |
| -------- | ---------------- | ----------------- |
| `POST`   | `/agencias`      | Cadastrar agência |
| `GET`    | `/agencias`      | Listar agências   |
| `GET`    | `/agencias/{id}` | Buscar agência    |
| `PUT`    | `/agencias/{id}` | Atualizar agência |
| `DELETE` | `/agencias/{id}` | Excluir agência   |

## 🐳 Redis com Docker

Para iniciar o Redis utilizando Docker:

```bash
docker run --name redis -p 6379:6379 -d redis
```

Verifique se o container está executando:

```bash
docker ps
```

O Redis ficará disponível em:

```text
localhost:6379
```

## ▶️ Executando o projeto

Clone o repositório:

```bash
git clone https://github.com/SEU-USUARIO/agencia-crud-redis.git
```

Entre no projeto:

```bash
cd agencia-crud-redis
```

Execute a aplicação:

```bash
./mvnw quarkus:dev
```

A API ficará disponível em:

```text
http://localhost:8080
```

## 🧪 Testando o Cache

Uma forma simples de verificar o funcionamento do cache é realizar a mesma consulta duas vezes:

```http
GET /agencias/1
```

Na primeira requisição:

```text
Redis → Cache Miss
        ↓
Banco de dados
        ↓
Salva no Redis
```

Na segunda requisição:

```text
Redis → Cache Hit
        ↓
Retorna agência
```

Isso demonstra o funcionamento do padrão **Cache-Aside**.

## 🎯 Objetivo do Projeto

Este projeto foi desenvolvido como estudo prático de:

* Desenvolvimento de APIs REST
* Integração com Redis
* Utilização do `RedisTemplate`
* Estratégia de Cache-Aside
* Cache Hit e Cache Miss
* Invalidação de cache
* Integração entre aplicação, cache e banco de dados
* Boas práticas no desenvolvimento de APIs Java

## 👨‍💻 Autor

**Cauã Couto**

Projeto desenvolvido para estudos de desenvolvimento backend Java, caching e arquitetura de aplicações.
