# Kanban

Foi realizado a criação das rotas solicitadas

## Rotas
- Projetos
<p>criar, listar, buscarPorId, atualizar e deletar</p>
<p>Passando os seguintes dados de projeto em formato de json {id, nome, status, responsavel, inicioPrevisto, terminoPrevisto, inicioRealizado, terminoRealizado, diasDeAtraso, percentualDeTempoRestante, createdAt, updatedAt}</p>
<p>Verificasse que createdAt e updatedAt foram criados para auditoria minima nas tarefas.</p>

- Responsavel
<p>criarRResponsavel, listarResponsaveis, listarResponsavelPorId, atualizarResponsavel, deletarResponsavel</p>
<p>Passando os seguintes dados de Responsavel em formato de json {id, nome, cargo, secretaria}</p>

- Secretaria
<p>criarSecretaria, listarSecretaria, listarSecretariaPorId, atualizarSecretaria, deletarSecretaria</p>
<p>Passando os seguintes dados de projeto em formato de json {id, nome}</p>


## Como testar o projeto?
Para testar o projeto basta realizar o clone do mesmo, adicionar na IDE de preferencia, então instalar as dependencia com Maven e clicar em iniciar.
<p>O projeto estara rodando na porta 8080, então basta acessar a mesma e utilizar o caminho : localhost:8080/graphiql </p>
<p>Dentro da mesma basta realizar as consultas segundo o informado e então vai estar tudo funcionando corretamente.</p>
