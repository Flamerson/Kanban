package com.flamerson.kanban.controllers;

import com.flamerson.kanban.models.Responsavel;
import com.flamerson.kanban.models.Secretaria;
import com.flamerson.kanban.services.ResponsavelService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class ResponsavelController {

    private ResponsavelService responsavelService;

    public ResponsavelController(ResponsavelService responsavelService){
        this.responsavelService = responsavelService;
    }

    @MutationMapping
    public Responsavel criarResponsavel(@Argument String nome, @Argument String email, @Argument String cargo, @Argument Secretaria secretaria){
        return responsavelService.criarResponsavel(nome, email, cargo, secretaria);
    }

    @QueryMapping
    public List<Responsavel> listarResponsaveis(){
        return responsavelService.listarResponveis();
    }

    @QueryMapping
    public Optional<Responsavel> listarResponvalPorId(@Argument String id){
        return responsavelService.listarReponsavelPorId(id);
    }

    @MutationMapping
    public Responsavel atualizarResponsavel(@Argument String id, @Argument String nome, @Argument String email, @Argument String cargo, @Argument Secretaria secretaria){
        return responsavelService.atualizarResponsavel(id, nome, email, cargo, secretaria);
    }

    @MutationMapping
    public Optional<Responsavel> deletarResponsavel(@Argument String id){
        return responsavelService.deletarResponsavel(id);
    }
}
