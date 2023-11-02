package com.example.jogodavelha.model;

import java.io.Serializable;

public class JogoDaVelha implements Serializable {
    private String nomeJogador1;
    private String nomeJogador2;
    private boolean againstBot;
    private int tamanhoTabuleiro;

    public JogoDaVelha() {
        this.nomeJogador1 = "";
        this.nomeJogador2 = "";
        this.againstBot = false;
        this.tamanhoTabuleiro = 3;
    }

    public String getNomeJogador1() {
        return nomeJogador1;
    }

    public void setNomeJogador1(String nomeJogador1) {
        this.nomeJogador1 = nomeJogador1;
    }

    public String getNomeJogador2() {
        return nomeJogador2;
    }

    public void setNomeJogador2(String nomeJogador2) {
        this.nomeJogador2 = nomeJogador2;
    }

    public boolean isBot() {
        return againstBot;
    }

    public void setBot(boolean bot) {
        againstBot = bot;
    }

    public int getTamanhoTabuleiro() {
        return tamanhoTabuleiro;
    }

    public void setTamanhoTabuleiro(int tamanhoTabuleiro) {
        this.tamanhoTabuleiro = tamanhoTabuleiro;
    }
}
