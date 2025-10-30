package com.flamerson.kanban.controllers;

import com.flamerson.kanban.models.Projeto;
import com.flamerson.kanban.models.Responsavel;
import com.flamerson.kanban.models.Status;
import com.flamerson.kanban.services.ProjetoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class ProjetoController {

    private ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService){
        this.projetoService = projetoService;
    }

    @MutationMapping
    public Projeto criar(@Argument String nome ,@Argument Status status, @Argument List<Responsavel> responsavel, @Argument LocalDateTime inicioPrevisto, @Argument LocalDateTime terminoPrevisto, @Argument LocalDateTime inicioRealizado, @Argument LocalDateTime terminoRealizado, @Argument Number diasDeAtraso, @Argument String percentualDeTempoRestante){
        return projetoService.criar(nome, status, responsavel, inicioPrevisto, terminoPrevisto, inicioRealizado, terminoRealizado, diasDeAtraso, percentualDeTempoRestante);
    }

    @QueryMapping
    public List<Projeto> listar(){
        return projetoService.listar();
    }

    @QueryMapping
    public Optional<Projeto> buscarPorId(@Argument final String id){
        return projetoService.buscarPorId(id);
    }

    @MutationMapping
    public Projeto atualizar(@Argument String id, @Argument String nome, @Argument Status status, @Argument List<Responsavel> responsavel, @Argument LocalDateTime inicioPrevisto, @Argument LocalDateTime terminoPrevisto, @Argument LocalDateTime inicioRealizado, @Argument LocalDateTime terminoRealizado, @Argument Number diasDeAtraso, @Argument String percentualDeTempoRestante){
        return projetoService.atualizar(id, nome, status, responsavel, inicioPrevisto, terminoPrevisto, inicioRealizado, terminoRealizado, diasDeAtraso, percentualDeTempoRestante);
    }

    @MutationMapping
    public Optional<Projeto> deletar(@Argument String id){
        return projetoService.deletar(id);
    }

}
