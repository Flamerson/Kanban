package com.flamerson.kanban.services;

import com.flamerson.kanban.exception.BusinessException;
import com.flamerson.kanban.models.Responsavel;
import com.flamerson.kanban.models.Secretaria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ResponsavelServiceTest {

    private ResponsavelService responsavelService;
    private Secretaria secretaria;

    @BeforeEach
    void setUp() {
        responsavelService = new ResponsavelService();
        // cria uma secretaria fake apenas para testes
        secretaria = new Secretaria("1", "Secretaria de Educação");
    }

    @Test
    @DisplayName("Deve criar um novo responsável com sucesso")
    void criarResponsavel() {
        var responsavel = responsavelService.criarResponsavel(
                "Flamerson Andrade",
                "flamerson@email.com",
                "Desenvolvedor",
                secretaria
        );

        assertNotNull(responsavel.id());
        assertEquals("Flamerson Andrade", responsavel.nome());
        assertEquals("Desenvolvedor", responsavel.cargo());
        assertEquals(secretaria, responsavel.secretaria());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar responsável com email duplicado")
    void criarResponsavelEmailDuplicado() {
        responsavelService.criarResponsavel("Flamerson", "teste@email.com", "Dev", secretaria);

        assertThrows(BusinessException.class, () -> responsavelService.criarResponsavel(
                "Outro Nome",
                "teste@email.com",
                "Designer",
                secretaria
        ));
    }

    @Test
    @DisplayName("Deve listar todos os responsáveis cadastrados")
    void listarResponsaveis() {
        responsavelService.criarResponsavel("Pessoa 1", "p1@email.com", "Cargo 1", secretaria);
        responsavelService.criarResponsavel("Pessoa 2", "p2@email.com", "Cargo 2", secretaria);

        List<Responsavel> lista = responsavelService.listarResponveis();

        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("Deve buscar um responsável pelo ID")
    void listarResponsavelPorId() {
        var criado = responsavelService.criarResponsavel("Pessoa", "pessoa@email.com", "Cargo", secretaria);

        Optional<Responsavel> encontrado = responsavelService.listarReponsavelPorId(criado.id());

        assertTrue(encontrado.isPresent());
        assertEquals(criado.id(), encontrado.get().id());
    }

    @Test
    @DisplayName("Deve atualizar os dados de um responsável existente")
    void atualizarResponsavel() {
        var criado = responsavelService.criarResponsavel("Antigo", "antigo@email.com", "Estagiário", secretaria);

        var atualizado = responsavelService.atualizarResponsavel(
                criado.id(),
                "Novo Nome",
                "novo@email.com",
                "Gerente",
                secretaria
        );

        assertEquals("Novo Nome", atualizado.nome());
        assertEquals("novo@email.com", atualizado.email());
        assertEquals("Gerente", atualizado.cargo());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar um responsável inexistente")
    void atualizarResponsavelInexistente() {
        assertThrows(BusinessException.class, () -> responsavelService.atualizarResponsavel(
                "id-invalido",
                "Nome",
                "email@email.com",
                "Cargo",
                secretaria
        ));
    }

    @Test
    @DisplayName("Deve deletar um responsável existente")
    void deletarResponsavel() {
        var criado = responsavelService.criarResponsavel("Deletar", "deletar@email.com", "Analista", secretaria);

        var deletado = responsavelService.deletarResponsavel(criado.id());

        assertTrue(deletado.isPresent());
        assertEquals(criado.id(), deletado.get().id());
        assertTrue(responsavelService.listarResponveis().isEmpty());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar um responsável inexistente")
    void deletarResponsavelInexistente() {
        assertThrows(BusinessException.class, () -> responsavelService.deletarResponsavel("id-invalido"));
    }
}
