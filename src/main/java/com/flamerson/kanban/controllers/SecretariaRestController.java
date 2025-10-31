package com.flamerson.kanban.controllers;

import com.flamerson.kanban.models.Secretaria;
import com.flamerson.kanban.services.SecretariaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gerenciamento de Secretarias.
 *
 * Este controlador é usado apenas para documentação no Swagger,
 * mantendo a lógica principal no {@link SecretariaService}.
 */
@RestController
@RequestMapping("/api/secretarias")
@Tag(name = "Secretarias", description = "Endpoints REST para gerenciar secretarias (Swagger)")
public class SecretariaRestController {

    private final SecretariaService secretariaService;

    public SecretariaRestController(SecretariaService secretariaService) {
        this.secretariaService = secretariaService;
    }

    @Operation(summary = "Cria uma nova secretaria", description = "Cria uma nova secretaria informando o nome.")
    @ApiResponse(responseCode = "200", description = "Secretaria criada com sucesso")
    @PostMapping
    public Secretaria criarSecretaria(@RequestParam String nome) {
        return secretariaService.criarSecretaria(nome);
    }

    @Operation(summary = "Lista todas as secretarias", description = "Retorna todas as secretarias cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public List<Secretaria> listarSecretaria() {
        return secretariaService.listarSecretaria();
    }

    @Operation(summary = "Busca secretaria por ID", description = "Retorna uma secretaria específica pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Secretaria encontrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Secretaria não encontrada")
    @GetMapping("/{id}")
    public Optional<Secretaria> listarSecretariaPorId(@PathVariable String id) {
        return secretariaService.listarSecretariaPorId(id);
    }

    @Operation(summary = "Atualiza uma secretaria existente", description = "Atualiza o nome de uma secretaria com base no ID informado.")
    @ApiResponse(responseCode = "200", description = "Secretaria atualizada com sucesso")
    @PutMapping("/{id}")
    public Secretaria atualizarSecretaria(@PathVariable String id, @RequestParam String nome) {
        return secretariaService.atualizarSecretaria(id, nome);
    }

    @Operation(summary = "Deleta uma secretaria", description = "Remove uma secretaria do sistema com base no ID informado.")
    @ApiResponse(responseCode = "200", description = "Secretaria deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Secretaria não encontrada")
    @DeleteMapping("/{id}")
    public Optional<Secretaria> deletarSecretaria(@PathVariable String id) {
        return secretariaService.deletarSecretaria(id);
    }
}
