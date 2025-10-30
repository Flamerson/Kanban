package com.flamerson.kanban.controllers;

import com.flamerson.kanban.models.Secretaria;
import com.flamerson.kanban.services.SecretariaService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class SecretariaController {

    private SecretariaService secretariaService;

    public SecretariaController(SecretariaService secretariaService){
        this.secretariaService = secretariaService;
    }

    @MutationMapping
    public Secretaria criarSecretaria(@Argument String nome){
        return secretariaService.criarSecretaria(nome);
    }

    @QueryMapping
    public List<Secretaria> listarSecretaria(){
        return secretariaService.listarSecretaria();
    }

    @QueryMapping
    public Optional<Secretaria> listarSecretariaPorId(@Argument String id){
        return secretariaService.listarSecretariaPorId(id);
    }

    @MutationMapping
    public Secretaria atualizarSecretaria(@Argument String id, @Argument String nome){
        return secretariaService.atualizarSecretaria(id, nome);
    }

    @MutationMapping
    public Optional<Secretaria> deletarSecretaria(@Argument String id){
        return secretariaService.deletarSecretaria(id);
    }

}
