package com.flamerson.kanban.services;

import com.flamerson.kanban.models.Responsavel;
import com.flamerson.kanban.models.Secretaria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ResponsavelService {

    private final Map<String, Responsavel> responsaveis = new HashMap<>();

    public Responsavel criarResponsavel(String nome, String email, String cargo, Secretaria secretaria){
        if (responsaveis.values().stream().anyMatch(r -> r.email().equalsIgnoreCase(email))) {
            throw new IllegalArgumentException("Email já cadastrado!");
        }
        var responsavel = new Responsavel(UUID.randomUUID().toString(), nome, email,cargo, secretaria);
        responsaveis.put(responsavel.id(), responsavel);

        return responsavel;
    }

    public List<Responsavel> listarResponveis(){
        return responsaveis.values().stream().toList();
    }

    public Optional<Responsavel> listarReponsavelPorId(String id){
        return Optional.ofNullable(responsaveis.get(id));
    }

    public Responsavel atualizarResponsavel(String id, String nome, String email, String cargo, Secretaria secretaria){
        if(responsaveis.containsKey(id)){
            responsaveis.put(id, new Responsavel(id, nome, email, cargo, secretaria));
            return responsaveis.get(id);
        }

        throw new IllegalArgumentException("Responsável não encontrado!");
    }

    public Optional<Responsavel> deletarResponsavel(String id){
        var responsavelEncontrado = listarReponsavelPorId(id);
        responsavelEncontrado.orElseThrow(() -> new IllegalArgumentException("Responsável não encontrado!"));
        responsaveis.remove(id);

        return responsavelEncontrado;
    }


}
