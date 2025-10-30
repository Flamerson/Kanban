package com.flamerson.kanban.models;

import java.time.LocalDateTime;
import java.util.List;

public record Projeto(String id, String nome, String status, List<Responsavel> responsavels, LocalDateTime inicioPrevisto, LocalDateTime terminoPrevisto, LocalDateTime inicioRealizado, LocalDateTime terminoRealizado, Number diasDeAtraso, String percentualDeTempoRestante, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
