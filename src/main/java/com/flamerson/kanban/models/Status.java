package com.flamerson.kanban.models;

public enum Status {
    AIniciar("A iniciar"),
    EmAndamento("Em Andamento"),
    Atrasado("Atrasado"),
    Concluido("Concluído");

    private final String valorString;

    Status(String valorString){
        this.valorString = valorString;
    }

    public String getValorString() {
        return valorString;
    }
}
