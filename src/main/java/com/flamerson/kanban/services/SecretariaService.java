package com.flamerson.kanban.services;

import com.flamerson.kanban.exception.BusinessException;
import com.flamerson.kanban.models.Secretaria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class SecretariaService {

    private final Map<String, Secretaria> secretarias = new HashMap<>();

    public Secretaria criarSecretaria(String nome){
        var secretaria = new Secretaria(UUID.randomUUID().toString(), nome);
        secretarias.put(secretaria.id(), secretaria);
        return secretaria;
    }

    public List<Secretaria> listarSecretaria(){
        return secretarias.values().stream().toList();
    }

    public Optional<Secretaria> listarSecretariaPorId(String id){
        return Optional.ofNullable(secretarias.get(id));
    }

    public Secretaria atualizarSecretaria(String id, String nome){
        if(secretarias.containsKey(id)){
            secretarias.put(id, new Secretaria(id, nome));
            return secretarias.get(id);
        }

        throw new BusinessException("Secretaria não encontrada!");
    }

    public Optional<Secretaria> deletarSecretaria(String id){
        var secretariaEncontrada = listarSecretariaPorId(id);
        secretariaEncontrada.orElseThrow(() -> new BusinessException("Projeto não encontrado!"));
        secretarias.remove(id);
        return secretariaEncontrada;
    }

}
