package com.flamerson.kanban.services;

import com.flamerson.kanban.models.Projeto;
import com.flamerson.kanban.models.Responsavel;
import com.flamerson.kanban.models.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class ProjetoService {

    private final Map<String, Projeto> projetos = new HashMap<>();

    public Projeto criar(String nome, Status status, List<Responsavel> responsavel, LocalDateTime inicioPrevisto, LocalDateTime terminoPrevisto, LocalDateTime inicioRealizado, LocalDateTime terminoRealizado, Number diasDeAtraso, String percentualDeTempoRestante) {

        var statusToString = status.getValorString();
        var horarioExato = LocalDateTime.now();

        var projeto = new Projeto(UUID.randomUUID().toString(), nome, statusToString , responsavel, inicioPrevisto, terminoPrevisto, inicioRealizado, terminoRealizado, diasDeAtraso, percentualDeTempoRestante, horarioExato, horarioExato);
        projetos.put(projeto.id(), projeto);
        return projeto;
    }

    public List<Projeto> listar() {
        return projetos.values().stream().toList();
    }

    public Optional<Projeto> buscarPorId(String id) {
        return Optional.ofNullable(projetos.get(id));
    }

    public Optional<Projeto> deletar(String id){
        var projetoEncontrado = buscarPorId(id);
        projetoEncontrado.orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado!"));
        projetos.remove(id);

        return projetoEncontrado;
    }

    public Projeto atualizar(final String id, final String nome, final Status status, final List<Responsavel> responsavel, final LocalDateTime inicioPrevisto, final LocalDateTime terminoPrevisto, final LocalDateTime inicioRealizado, final LocalDateTime terminoRealizado, final Number diasDeAtraso, final String percentualDeTempoRestante){
        if(projetos.containsKey(id)){
            var statusToString = status.getValorString();
            var horarioExato = LocalDateTime.now();
            projetos.put(id, new Projeto(id, nome, statusToString , responsavel, inicioPrevisto, terminoPrevisto, inicioRealizado, terminoRealizado, diasDeAtraso, percentualDeTempoRestante, projetos.get(id).createdAt() , horarioExato));
            return projetos.get(id);
        }

        throw new IllegalArgumentException("Projeto não encontrado!");
    }
}
