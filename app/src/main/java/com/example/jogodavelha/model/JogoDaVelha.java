package com.example.jogodavelha.model;

import java.io.Serializable;

public class JogoDaVelha implements Serializable {
    private String nomeJogador1;
    private String nomeJogador2;
    private boolean versusBot;
    private int tamanhoTabuleiro;
    private int vencedor;
    private int[][] jogadasTabuleiro;

    private int qtJogadas;
    public JogoDaVelha() {
        this.nomeJogador1 = "";
        this.nomeJogador2 = "";
        this.versusBot = false;
        this.tamanhoTabuleiro = 3;
        this.vencedor = 0;
        this.qtJogadas = 0;
        this.jogadasTabuleiro = new int[3][3];
    }

    public void reiniciar(){
        this.vencedor = 0;
        this.qtJogadas = 0;
        this.jogadasTabuleiro = new int[tamanhoTabuleiro][tamanhoTabuleiro];
    }
    //Verifica no tabuleiro de Jogadas se a posicao ja foi jogada.
    public boolean posicaoExiste(int posicao){
        int tab = this.jogadasTabuleiro[posicao/tamanhoTabuleiro][posicao%tamanhoTabuleiro];
        if(tab == 0)
            return false;
        else
            return true;
    }
    public int verificarVencedor() {
        // 0 - Tab Vazio
        // 1 - Jogador 1
        // 2 - Jogador 2
        // 3 - Velha
        int tamanho = this.jogadasTabuleiro.length;
        // Verificar as linhas
        for (int i = 0; i < tamanho; i++) {
            if (this.jogadasTabuleiro[i][0] != 0 && todasIguais(this.jogadasTabuleiro[i])) {
                return this.jogadasTabuleiro[i][0];
            }
        }

        // Verificar as colunas
        for (int j = 0; j < tamanho; j++) {
            int primeiro = this.jogadasTabuleiro[0][j];
            if (primeiro != 0) {
                boolean todasIguais = true;
                for (int i = 1; i < tamanho; i++) {
                    if (this.jogadasTabuleiro[i][j] != primeiro) {
                        todasIguais = false;
                        break;
                    }
                }
                if (todasIguais) {
                    return primeiro;
                }
            }
        }

        // Diagonal principal
        int primeiroDiagonalPrincipal = this.jogadasTabuleiro[0][0];
        if (primeiroDiagonalPrincipal != 0) {
            boolean todasIguaisDiagonalPrincipal = true;
            for (int i = 1; i < tamanho; i++) {
                if (this.jogadasTabuleiro[i][i] != primeiroDiagonalPrincipal) {
                    todasIguaisDiagonalPrincipal = false;
                    break;
                }
            }
            if (todasIguaisDiagonalPrincipal) {
                return primeiroDiagonalPrincipal;
            }
        }

        // Diagonal secundária
        int primeiroDiagonalSecundaria = this.jogadasTabuleiro[0][tamanho - 1];
        if (primeiroDiagonalSecundaria != 0) {
            boolean todasIguaisDiagonalSecundaria = true;
            for (int i = 1; i < tamanho; i++) {
                if (this.jogadasTabuleiro[i][tamanho - 1 - i] != primeiroDiagonalSecundaria) {
                    todasIguaisDiagonalSecundaria = false;
                    break;
                }
            }
            if (todasIguaisDiagonalSecundaria) {
                return primeiroDiagonalSecundaria;
            }
        }
        if(this.qtJogadas == (this.tamanhoTabuleiro*this.tamanhoTabuleiro))
            return 3;
        return 0;
    }
    public static boolean todasIguais(int[] array) {
        int primeiro = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] != primeiro) {
                return false;
            }
        }
        return true;
    }
    public void fazerJogada(int jogador, int posicao){
        this.jogadasTabuleiro[posicao/tamanhoTabuleiro][posicao%tamanhoTabuleiro] = jogador;
        this.qtJogadas++;
    }
    public void adicionaJogada(){
        this.qtJogadas++;
    }
    public int getQtJogadas() {
        return qtJogadas;
    }

    public void setQtJogadas(int qtJogadas) {
        this.qtJogadas = qtJogadas;
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
        return versusBot;
    }

    public void setBot(boolean bot) {
        versusBot = bot;
    }

    public int getTamanhoTabuleiro() {
        return tamanhoTabuleiro;
    }

    public void setTamanhoTabuleiro(int tamanhoTabuleiro) {
        this.tamanhoTabuleiro = tamanhoTabuleiro;
        this.jogadasTabuleiro = new int[tamanhoTabuleiro][tamanhoTabuleiro];
    }

    public int[][] getTabuleiro() {
        return jogadasTabuleiro;
    }

    public int getVencedor() {
        return vencedor;
    }

    public void setVencedor(int vencedor) {
        this.vencedor = vencedor;
    }
}
