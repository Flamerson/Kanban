package com.flamerson.kanban.controllers;

import com.flamerson.kanban.models.Responsavel;
import com.flamerson.kanban.models.Secretaria;
import com.flamerson.kanban.services.ResponsavelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gerenciar Responsáveis.
 *
 * Esse controlador serve para documentação no Swagger,
 * funcionando em paralelo ao controlador GraphQL.
 */
@RestController
@RequestMapping("/api/responsaveis")
@Tag(name = "Responsáveis", description = "Endpoints REST para gerenciar responsáveis (Swagger)")
public class ResponsavelRestController {

    private final ResponsavelService responsavelService;

    public ResponsavelRestController(ResponsavelService responsavelService) {
        this.responsavelService = responsavelService;
    }

    @Operation(summary = "Cria um novo responsável", description = "Cria um novo responsável com nome, e-mail, cargo e secretaria associados.")
    @ApiResponse(responseCode = "200", description = "Responsável criado com sucesso")
    @PostMapping
    public Responsavel criarResponsavel(
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String cargo,
            @RequestBody Secretaria secretaria) {
        return responsavelService.criarResponsavel(nome, email, cargo, secretaria);
    }

    @Operation(summary = "Lista todos os responsáveis", description = "Retorna uma lista com todos os responsáveis cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public List<Responsavel> listarResponsaveis() {
        return responsavelService.listarResponveis();
    }

    @Operation(summary = "Busca responsável por ID", description = "Busca um responsável específico pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Responsável encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Responsável não encontrado")
    @GetMapping("/{id}")
    public Optional<Responsavel> listarResponvalPorId(@PathVariable String id) {
        return responsavelService.listarReponsavelPorId(id);
    }

    @Operation(summary = "Atualiza um responsável existente", description = "Atualiza os dados de um responsável pelo ID.")
    @ApiResponse(responseCode = "200", description = "Responsável atualizado com sucesso")
    @PutMapping("/{id}")
    public Responsavel atualizarResponsavel(
            @PathVariable String id,
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String cargo,
            @RequestBody Secretaria secretaria) {
        return responsavelService.atualizarResponsavel(id, nome, email, cargo, secretaria);
    }

    @Operation(summary = "Deleta um responsável", description = "Remove um responsável do sistema pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Responsável deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Responsável não encontrado")
    @DeleteMapping("/{id}")
    public Optional<Responsavel> deletarResponsavel(@PathVariable String id) {
        return responsavelService.deletarResponsavel(id);
    }
}
