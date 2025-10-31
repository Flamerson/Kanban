package com.flamerson.kanban.services;

import com.flamerson.kanban.exception.BusinessException;
import com.flamerson.kanban.models.Projeto;
import com.flamerson.kanban.models.Responsavel;
import com.flamerson.kanban.models.Secretaria;
import com.flamerson.kanban.models.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProjetoServiceTest {

    private ProjetoService projetoService;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        projetoService = new ProjetoService();
        now = LocalDateTime.now();
    }

    @Test
    @DisplayName("Deve criar um projeto com status AIniciar e limpar campos de datas realizadas")
    void criarProjetoAIniciar() {
        var projeto = projetoService.criar(
                "Projeto Teste",
                Status.AIniciar,
                List.of(new Responsavel("1", "Flamerson", "flamerson@email.com", "secretario", new Secretaria("1", "teste"))),
                now.plusDays(1),
                now.plusDays(3),
                now,
                now.plusDays(2),
                0,
                "100%"
        );

        assertNotNull(projeto.id());
        assertNull(projeto.inicioRealizado());
        assertNull(projeto.terminoRealizado());
        assertEquals(Status.AIniciar, projeto.status());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar projeto EmAndamento com término previsto no passado")
    void criarProjetoEmAndamentoComTerminoInvalido() {
        assertThrows(BusinessException.class, () -> projetoService.criar(
                "Projeto Inválido",
                Status.EmAndamento,
                List.of(),
                now.minusDays(3),
                now.minusDays(1),
                null,
                null,
                0,
                "0%"
        ));
    }

    @Test
    @DisplayName("Deve criar projeto EmAndamento com início realizado definido automaticamente")
    void criarProjetoEmAndamentoValido() {
        var projeto = projetoService.criar(
                "Projeto Válido",
                Status.EmAndamento,
                List.of(),
                now,
                now.plusDays(2),
                null,
                null,
                0,
                "0%"
        );

        assertNotNull(projeto.inicioRealizado());
        assertNull(projeto.terminoRealizado());
    }

    @Test
    @DisplayName("Deve listar todos os projetos criados")
    void listarProjetos() {
        projetoService.criar("Projeto 1", Status.AIniciar, List.of(), now, now.plusDays(1), null, null, 0, "0%");
        projetoService.criar("Projeto 2", Status.Concluido, List.of(), now, now.plusDays(1), now, now.plusDays(1), 0, "100%");

        var lista = projetoService.listar();

        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("Deve buscar projeto por ID existente")
    void buscarPorId() {
        var criado = projetoService.criar("Projeto ID", Status.AIniciar, List.of(), now, now.plusDays(1), null, null, 0, "0%");

        Optional<Projeto> encontrado = projetoService.buscarPorId(criado.id());

        assertTrue(encontrado.isPresent());
        assertEquals(criado.id(), encontrado.get().id());
    }

    @Test
    @DisplayName("Deve listar projetos por status")
    void listarPorStatus() {
        projetoService.criar("Projeto 1", Status.AIniciar, List.of(), now, now.plusDays(1), null, null, 0, "0%");
        projetoService.criar("Projeto 2", Status.Concluido, List.of(), now, now.plusDays(1), now, now.plusDays(1), 0, "100%");

        var lista = projetoService.listarPorStatus(Status.Concluido);

        assertEquals(1, lista.size());
        assertEquals(Status.Concluido, lista.get(0).status());
    }

    @Test
    @DisplayName("Deve deletar um projeto existente")
    void deletarProjeto() {
        var criado = projetoService.criar("Projeto Delete", Status.AIniciar, List.of(), now, now.plusDays(1), null, null, 0, "0%");

        var deletado = projetoService.deletar(criado.id());

        assertTrue(deletado.isPresent());
        assertTrue(projetoService.listar().isEmpty());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar um projeto inexistente")
    void deletarProjetoInexistente() {
        assertThrows(IllegalArgumentException.class, () -> projetoService.deletar("id-invalido"));
    }

    @Test
    @DisplayName("Deve atualizar o nome e status de um projeto existente")
    void atualizarProjeto() {
        var criado = projetoService.criar("Projeto Original", Status.AIniciar, List.of(), now, now.plusDays(2), null, null, 0, "0%");

        var atualizado = projetoService.atualizar(criado.id(), "Projeto Atualizado", Status.EmAndamento, List.of(), now, now.plusDays(2), null, null);

        assertEquals("Projeto Atualizado", atualizado.nome());
        assertEquals(Status.EmAndamento, atualizado.status());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar projeto inexistente")
    void atualizarProjetoInexistente() {
        assertThrows(BusinessException.class, () -> projetoService.atualizar("id-invalido", "Teste", Status.AIniciar, List.of(), now, now.plusDays(1), null, null));
    }

    @Test
    @DisplayName("Deve atualizar apenas o status de um projeto existente")
    void atualizarStatus() {
        var criado = projetoService.criar("Projeto Status", Status.AIniciar, List.of(), now, now.plusDays(1), null, null, 0, "0%");

        var atualizado = projetoService.atualizarStatus(criado.id(), Status.Concluido);

        assertEquals(Status.Concluido, atualizado.status());
    }
}
