package com.example.jogodavelha.model;

public class Ranking {
    private int posicao;
    private String nome;

    private int qtVitorias;

    public Ranking(int posicao, String nome, int qtVitorias) {
        this.posicao = posicao;
        this.nome = nome;
        this.qtVitorias = qtVitorias;
    }

    public int getPosicao() {
        return posicao;
    }
    public int getId(){
        return posicao;
    }
    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtVitorias() {
        return qtVitorias;
    }

    public void setQtVitorias(int qtVitorias) {
        this.qtVitorias = qtVitorias;
    }
}
