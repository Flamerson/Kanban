# Kanban

Este é um projeto desenvolvido com **Spring Boot**, utilizando o padrão **GraphQL** para visualização.

O projeto consiste em uma API para um quadro *Kanban* simples, cujo objetivo é criar e gerenciar tarefas aplicando regras de negócio, além de utilizar o GraphQL como base para consultas mais precisas.

---

## 🧠 Primeiras Impressões e Decisões Técnicas

Desde o início, ficou claro que a tarefa pedia uma API com rotas bem definidas e detalhadas, permitindo a implementação tanto em **REST** quanto em **GraphQL**, com documentação clara e exemplos bem descritos.

Mesmo com uma documentação objetiva, algumas observações chamaram a atenção. A tarefa sugeria o uso do padrão **REST (Representational State Transfer)**, mas também solicitava a implementação com **GraphQL**.  
Dessa forma, optei por construir toda a API no padrão GraphQL — incluindo tratamento de erros e retornos no formato apropriado — mas também criei *controllers* no padrão REST, para demonstrar domínio em ambas as abordagens e manter compatibilidade com diferentes formas de consumo.

Outro ponto importante é que o desafio **não exige o uso de banco de dados**. Isso limita um pouco os testes e a avaliação do desempenho real da aplicação. Por isso, os dados foram armazenados em **cache**, mas o código foi estruturado de forma que possa ser facilmente adaptado para bancos de dados relacionais no futuro.  
Pretendo evoluir este projeto para um **MVP**, já que ele possui um ótimo potencial para estudos e experimentação.

---

## 🚀 Como Testar o Projeto

Para testar o projeto, basta clonar este repositório e seguir os passos abaixo para executá-lo com o **Docker**.

### 🐳 Comandos no Docker

```bash
docker build -t kanban .
docker run -p 8080:8080 kanban
```

Se tudo ocorrer bem e as dependências do Maven forem baixadas corretamente, você poderá acessar a aplicação pela seguinte rota padrão do Spring Boot:

```
http://localhost:8080
```

Para acessar o ambiente de testes do **GraphQL**, utilize a rota:

```
http://localhost:8080/graphiql
```

---

## 🔗 Consultas e Mutations Disponíveis

Você pode copiar o *schema* abaixo e colar diretamente na interface do **GraphiQL** para testar todas as rotas disponíveis:

```graphql
# Exemplo de consultas e mutations...
(querys e mutations mantidos iguais)
```

---

## 📦 Variáveis de Entidade

Essas são as variáveis utilizadas em cada entidade para a realização das operações CRUD.

### 🗂️ Projeto
```json
{
  "id": "string",
  "nome": "string",
  "status": "string",
  "responsavel": "Objeto de Responsavel",
  "inicioPrevisto": "2025-10-31T18:21:40",
  "terminoPrevisto": "2025-10-31T18:21:40",
  "inicioRealizado": "2025-10-31T18:21:40",
  "terminoRealizado": "2025-10-31T18:21:40"
}
```

### 👤 Responsável
```json
{
  "id": "string",
  "nome": "string",
  "cargo": "string",
  "secretaria": "Objeto de Secretaria"
}
```

### 🏢 Secretaria
```json
{
  "id": "string",
  "nome": "string"
}
```

---

## 🧱 Estrutura de Pastas

A estrutura do projeto foi organizada da seguinte forma:

- **models/** – contém todas as entidades (`Projeto`, `Responsavel`, `Secretaria` e `Status`, sendo `Status` um *enum*).  
- **controllers/** – contém as rotas da aplicação, tanto para o padrão **GraphQL** quanto **REST**.  
- **services/** – armazena toda a lógica da aplicação, conforme as regras de negócio solicitadas.  
- **config/** e **exception/** – tratam das configurações e do gerenciamento de erros, garantindo que o GraphQL exiba mensagens claras sem lançar exceções no console.

---

## 🔮 Próximos Passos

Um ponto importante é que o projeto ainda não possui um banco de dados. Essa seria a primeira melhoria a ser feita, seguida pela criação de uma **interface frontend** para visualizar o funcionamento completo do sistema.

Algo que não repetiria em uma próxima versão seria **começar pelo GraphQL** e só depois criar o padrão REST.  
Apesar de o GraphQL oferecer um controle mais preciso dos dados, para a maioria das integrações de frontend ainda é mais prático trabalhar com APIs REST.
