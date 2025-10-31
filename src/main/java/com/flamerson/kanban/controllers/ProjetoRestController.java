package com.flamerson.kanban.controllers;

import com.flamerson.kanban.models.Projeto;
import com.flamerson.kanban.models.Responsavel;
import com.flamerson.kanban.models.Status;
import com.flamerson.kanban.services.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projetos")
@Tag(name = "Projetos", description = "Endpoints REST para gerenciar projetos (Swagger)")
public class ProjetoRestController {

    private final ProjetoService projetoService;

    public ProjetoRestController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    @Operation(summary = "Cria um novo projeto", description = "Cria um novo projeto com base nos dados informados.")
    @ApiResponse(responseCode = "200", description = "Projeto criado com sucesso")
    @PostMapping
    public Projeto criar(@RequestParam String nome,
                         @RequestParam Status status,
                         @RequestBody List<Responsavel> responsavel,
                         @RequestParam LocalDateTime inicioPrevisto,
                         @RequestParam LocalDateTime terminoPrevisto,
                         @RequestParam(required = false) LocalDateTime inicioRealizado,
                         @RequestParam(required = false) LocalDateTime terminoRealizado) {
        return projetoService.criar(nome, status, responsavel, inicioPrevisto, terminoPrevisto, inicioRealizado, terminoRealizado);
    }

    @Operation(summary = "Lista todos os projetos")
    @GetMapping
    public List<Projeto> listar() {
        return projetoService.listar();
    }

    @Operation(summary = "Busca projeto por ID")
    @GetMapping("/{id}")
    public Optional<Projeto> buscarPorId(@PathVariable String id) {
        return projetoService.buscarPorId(id);
    }

    @Operation(summary = "Lista projetos por status")
    @GetMapping("/status/{status}")
    public List<Projeto> listarPorStatus(@PathVariable Status status) {
        return projetoService.listarPorStatus(status);
    }

    @Operation(summary = "Atualiza um projeto existente")
    @PutMapping("/{id}")
    public Projeto atualizar(@PathVariable String id,
                             @RequestParam String nome,
                             @RequestParam Status status,
                             @RequestBody List<Responsavel> responsavel,
                             @RequestParam LocalDateTime inicioPrevisto,
                             @RequestParam LocalDateTime terminoPrevisto,
                             @RequestParam(required = false) LocalDateTime inicioRealizado,
                             @RequestParam(required = false) LocalDateTime terminoRealizado) {
        return projetoService.atualizar(id, nome, status, responsavel, inicioPrevisto, terminoPrevisto, inicioRealizado, terminoRealizado);
    }

    @Operation(summary = "Atualiza o status de um projeto")
    @PatchMapping("/{id}/status")
    public Projeto atualizarStatus(@PathVariable String id, @RequestParam Status status) {
        return projetoService.atualizarStatus(id, status);
    }

    @Operation(summary = "Deleta um projeto pelo ID")
    @DeleteMapping("/{id}")
    public Optional<Projeto> deletar(@PathVariable String id) {
        return projetoService.deletar(id);
    }
}
