package com.flamerson.kanban.services;

import com.flamerson.kanban.exception.BusinessException;
import com.flamerson.kanban.models.Projeto;
import com.flamerson.kanban.models.Responsavel;
import com.flamerson.kanban.models.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProjetoService {

    private final Map<String, Projeto> projetos = new HashMap<>();

    public Projeto criar(String nome, Status status, List<Responsavel> responsavel, LocalDateTime inicioPrevisto, LocalDateTime terminoPrevisto, LocalDateTime inicioRealizado, LocalDateTime terminoRealizado) {
        var horarioExato = LocalDateTime.now();
        int diasDeAtraso = 0;
        var percentualDeTempoRestante = "0";

        // Regras para criação de tarefas, por status.
        if(status == Status.AIniciar && (inicioRealizado != null || terminoRealizado != null)){
            inicioRealizado = null;
            terminoRealizado = null;
        }

        if(status == Status.EmAndamento && !terminoPrevisto.isAfter(LocalDateTime.now())){
            throw new BusinessException("Data do termino previsto precisa ser maior que o dia de hoje!");
        }

        if(status == Status.EmAndamento && inicioRealizado == null){
            inicioRealizado = horarioExato;
        }

        if(status == Status.EmAndamento && terminoRealizado != null){
            terminoRealizado = null;
        }

        if(status == Status.Atrasado && (inicioPrevisto.isAfter(LocalDateTime.now()) && inicioRealizado != null)){
            throw new BusinessException("Verifique as datas antes de criar a tarefa em Atrasado!");
        }

        if(status == Status.Atrasado && (!terminoPrevisto.isAfter(LocalDateTime.now()) && terminoRealizado != null)){
            throw new BusinessException("Verifique as datas antes de criar a tarefa em Atrasado!");
        }

        if(status == Status.Concluido && terminoRealizado == null){
            throw new BusinessException("Verifique as datas antes de criar a tarefa em Concluido!");
        }

        if(status != Status.Concluido && status != Status.AIniciar){
            diasDeAtraso = verificaAtraso(inicioPrevisto, terminoPrevisto);
        }

        var projeto = new Projeto(UUID.randomUUID().toString(), nome, status , responsavel, inicioPrevisto, terminoPrevisto, inicioRealizado, terminoRealizado, diasDeAtraso, percentualDeTempoRestante, horarioExato, horarioExato);
        projetos.put(projeto.id(), projeto);
        return projeto;
    }

    public List<Projeto> listar() {
        return projetos.values().stream().toList();
    }

    public Optional<Projeto> buscarPorId(String id) {
        return Optional.ofNullable(projetos.get(id));
    }

    public List<Projeto> listarPorStatus(Status status) {
        return projetos.values().stream().filter(p -> p.status().equals(status)).collect(Collectors.toList());
    }

    public Optional<Projeto> deletar(String id){
        var projetoEncontrado = buscarPorId(id);
        projetoEncontrado.orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado!"));
        projetos.remove(id);

        return projetoEncontrado;
    }

    public Projeto atualizar(final String id, final String nome, Status status, final List<Responsavel> responsavel, LocalDateTime inicioPrevisto, LocalDateTime terminoPrevisto, LocalDateTime inicioRealizado, LocalDateTime terminoRealizado){
        if(projetos.containsKey(id)){
            var horarioExato = LocalDateTime.now();

            if(status != projetos.get(id).status()){
                if((projetos.get(id).status() == Status.AIniciar || projetos.get(id).status() == Status.Concluido) && status == Status.EmAndamento){
                    if(inicioRealizado == null || projetos.get(id).inicioRealizado() == null){
                        inicioRealizado = LocalDateTime.now();
                    }
                    terminoRealizado = null;
                }

                if(projetos.get(id).status() == Status.AIniciar && status == Status.Atrasado){
                    if(inicioPrevisto.isAfter(LocalDateTime.now()) || projetos.get(id).inicioRealizado().isAfter(LocalDateTime.now())){
                        throw new BusinessException("Verifique a data de inicio realizado antes de atualizar o status!");
                    }
                }

                if(status == Status.Concluido){
                    if(terminoRealizado == null){
                        terminoRealizado = LocalDateTime.now();
                    }
                }

                if(status == Status.AIniciar){
                    inicioRealizado = null;
                    inicioPrevisto = LocalDateTime.now();
                    terminoPrevisto = LocalDateTime.now();
                }

                if(projetos.get(id).status() == Status.EmAndamento && status == Status.Atrasado){
                    inicioRealizado = LocalDateTime.now();
                    terminoRealizado = LocalDateTime.now();
                }

                if(projetos.get(id).status() == Status.Atrasado && status == Status.EmAndamento){
                    if((inicioRealizado.isAfter(LocalDateTime.now()) || terminoRealizado.isAfter(LocalDateTime.now()) || (projetos.get(id).inicioRealizado().isAfter(LocalDateTime.now()) || projetos.get(id).terminoPrevisto().isAfter(LocalDateTime.now())))){
                        throw new BusinessException("Não é possivel alterar o status pois as datas são maiores que a data atual.");
                    }
                }

                if(projetos.get(id).status() == Status.Concluido && status == Status.Atrasado){
                    if(!inicioRealizado.isAfter(LocalDateTime.now()) || !projetos.get(id).inicioRealizado().isAfter(LocalDateTime.now())){
                        throw new BusinessException("Não é possivel a atualização de status pois, não cumpre os requistos de data!");
                    }
                    terminoRealizado = null;
                }

            }else{

                if((inicioPrevisto != null || inicioRealizado != null || terminoPrevisto != null ) && status != Status.EmAndamento){
                    status = Status.EmAndamento;
                }

                if(terminoRealizado != null && status != Status.Concluido){
                    status = Status.Concluido;
                }
            }

            projetos.put(id, new Projeto(id, nome, status , responsavel, inicioPrevisto, terminoPrevisto, inicioRealizado, terminoRealizado, projetos.get(id).diasDeAtraso(), projetos.get(id).percentualDeTempoRestante(), projetos.get(id).createdAt() , horarioExato));
            return projetos.get(id);
        }

        throw new BusinessException("Projeto não encontrado!");
    }

    public Projeto atualizarStatus(final String id, final Status status){
        var projetoEncontrado = buscarPorId(id);
        var horarioExato = LocalDateTime.now();
        projetos.put(id, new Projeto(id, projetoEncontrado.get().nome(), status, projetoEncontrado.get().responsavels(), projetoEncontrado.get().inicioPrevisto(), projetoEncontrado.get().terminoPrevisto(), projetoEncontrado.get().inicioRealizado(), projetoEncontrado.get().terminoRealizado(), projetoEncontrado.get().diasDeAtraso(), projetoEncontrado.get().percentualDeTempoRestante(), projetoEncontrado.get().createdAt(), horarioExato ));
        return projetos.get(id);
    }

    public int verificaAtraso(LocalDateTime inicioPrevisto, LocalDateTime terminoPrevisto) {

        if (inicioPrevisto == null) {
            return 0;
        }

        LocalDate hoje = LocalDate.now();

        LocalDate inicio = inicioPrevisto.toLocalDate();

        if (inicio.isAfter(hoje)) {
            return 0;
        }

        LocalDate termino = terminoPrevisto.toLocalDate();

        long total = java.time.temporal.ChronoUnit.DAYS.between(inicio, termino);
        long usado = java.time.temporal.ChronoUnit.DAYS.between(inicio, hoje);
        long restante = total - usado;

        if (total <= 0) return 0;
        if (hoje.isAfter(termino)) return 0;
        if (restante < 0) return 0;

        return (int) restante;
    }

}
