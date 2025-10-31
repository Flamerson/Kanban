package com.flamerson.kanban.services;

import com.flamerson.kanban.exception.BusinessException;
import com.flamerson.kanban.models.Secretaria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SecretariaServiceTest {

    private SecretariaService secretariaService;

    @BeforeEach
    void setUp() {
        secretariaService = new SecretariaService();
    }

    @Test
    @DisplayName("Deve criar uma nova secretaria com sucesso")
    void criarSecretaria() {
        var secretaria = secretariaService.criarSecretaria("Secretaria de Saúde");

        assertNotNull(secretaria.id());
        assertEquals("Secretaria de Saúde", secretaria.nome());
        assertEquals(1, secretariaService.listarSecretaria().size());
    }

    @Test
    @DisplayName("Deve listar todas as secretarias cadastradas")
    void listarSecretaria() {
        secretariaService.criarSecretaria("Educação");
        secretariaService.criarSecretaria("Saúde");

        List<Secretaria> lista = secretariaService.listarSecretaria();

        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("Deve buscar secretaria pelo ID")
    void listarSecretariaPorId() {
        var criada = secretariaService.criarSecretaria("Secretaria de Obras");

        Optional<Secretaria> encontrada = secretariaService.listarSecretariaPorId(criada.id());

        assertTrue(encontrada.isPresent());
        assertEquals(criada.id(), encontrada.get().id());
    }

    @Test
    @DisplayName("Deve atualizar o nome de uma secretaria existente")
    void atualizarSecretaria() {
        var criada = secretariaService.criarSecretaria("Antigo Nome");

        var atualizada = secretariaService.atualizarSecretaria(criada.id(), "Novo Nome");

        assertEquals("Novo Nome", atualizada.nome());
        assertEquals(criada.id(), atualizada.id());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar secretaria inexistente")
    void atualizarSecretariaInexistente() {
        assertThrows(BusinessException.class, () -> secretariaService.atualizarSecretaria("id-invalido", "Novo Nome"));
    }

    @Test
    @DisplayName("Deve deletar uma secretaria existente")
    void deletarSecretaria() {
        var criada = secretariaService.criarSecretaria("Secretaria de Turismo");

        var deletada = secretariaService.deletarSecretaria(criada.id());

        assertTrue(deletada.isPresent());
        assertEquals(criada.id(), deletada.get().id());
        assertTrue(secretariaService.listarSecretaria().isEmpty());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar secretaria inexistente")
    void deletarSecretariaInexistente() {
        assertThrows(BusinessException.class, () -> secretariaService.deletarSecretaria("id-invalido"));
    }
}
