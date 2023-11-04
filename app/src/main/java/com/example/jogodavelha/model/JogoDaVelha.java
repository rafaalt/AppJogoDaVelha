package com.example.jogodavelha.model;

import android.util.Log;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class JogoDaVelha implements Serializable {
    private int id;
    private String nomeJogador1;
    private String nomeJogador2;
    private boolean versusBot;
    private int tamanhoTabuleiro;
    private int vencedor;
    private int[][] jogadasTabuleiro;

    private int ultimaJogadaBot;
    private String data;
    private String horario;
    private int qtJogadas;
    public JogoDaVelha() {
        this.nomeJogador1 = "";
        this.nomeJogador2 = "";
        this.versusBot = false;
        this.tamanhoTabuleiro = 3;
        this.vencedor = 0;
        this.qtJogadas = 0;
        this.jogadasTabuleiro = new int[3][3];
        this.data = "";
        this.horario = "";
        this.id = 0;
        this.ultimaJogadaBot = -1;
    }

    public JogoDaVelha(int id, String nomeJogador1, String nomeJogador2, int tamanhoTabuleiro, int vencedor, String data, String horario) {
        this.nomeJogador1 = nomeJogador1;
        this.nomeJogador2 = nomeJogador2;
        this.tamanhoTabuleiro = tamanhoTabuleiro;
        this.vencedor = vencedor;
        this.data = data;
        this.horario = horario;
        this.qtJogadas = 0;
        this.jogadasTabuleiro = new int[tamanhoTabuleiro][tamanhoTabuleiro];
        this.versusBot = false;
        this.id = id;
    }

    public void reiniciar(){
        this.vencedor = 0;
        this.qtJogadas = 0;
        this.jogadasTabuleiro = new int[tamanhoTabuleiro][tamanhoTabuleiro];
        this.ultimaJogadaBot = -1;
    }
    //Verifica no tabuleiro de Jogadas se a posicao ja foi jogada.
    public boolean posicaoExiste(int posicao){
        if(posicao>=tamanhoTabuleiro*tamanhoTabuleiro)
            return true;
        if(posicao < 0)
            return true;
        int tab = this.jogadasTabuleiro[posicao/tamanhoTabuleiro][posicao%tamanhoTabuleiro];
        if(tab == 0)
            return false;
        else
            return true;
    }
    public int verificarVencedor() {
        // 0 - Jogo em andamento ainda
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

    public void setDataHorario(){
        LocalDateTime dataHoraAtual = LocalDateTime.now();

        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

        this.data = dataHoraAtual.format(formatoData);
        this.horario = dataHoraAtual.format(formatoHora);

    }
    public int melhorJogada(int posBusca){
        int tamanho = this.jogadasTabuleiro.length;
        int posicao = -1;
        // Verifica se está a um movimento de ganhar ou perder.
        // Mesma ideia da funcao verificarVencedor
        // Verificar as linhas
        for(int i = 0;i<tamanho;i++){
            posicao = faltaUmPraGanhar(this.jogadasTabuleiro[i], posBusca);
            if(posicao != -1){
                posicao = i*tamanho + posicao;
                break;
            }
        }
        //Verifica as colunas
        if(posicao == -1){
            int newP = -1;
            int falta = 0;
            for (int j = 0; j < tamanho; j++) {
                falta = 0;
                for (int i = 0; i < tamanho; i++) {
                    if (this.jogadasTabuleiro[i][j] == 0) {
                        falta++;
                        newP = tamanho * i + j;
                    }
                    else if(this.jogadasTabuleiro[i][j] == posBusca){
                        falta = 10;
                    }
                }
                if (falta == 1) {
                    posicao = newP;
                    break;
                }
            }
        }
        //Verifica Diagonal Principal
        if(posicao == -1){
            int falta = 0;
            int newP = 0;
            for(int i = 0;i<tamanho;i++){
                if(this.jogadasTabuleiro[i][i] == 0){
                    falta++;
                    newP = tamanho*i + i;
                }
                else if(this.jogadasTabuleiro[i][i] == posBusca){
                    falta = 10;
                }
            }
            if(falta == 1)
                posicao = newP;
        }
        //Verifica Diagonal Secundaria
        if(posicao == -1){
            int falta = 0;
            int newP = 0;
            for(int i = 0;i<tamanho;i++){
                if(this.jogadasTabuleiro[i][tamanho - i - 1] == 0){
                    falta++;
                    newP = tamanho*i + tamanho - i - 1;
                }
                else if(this.jogadasTabuleiro[i][tamanho - 1 - i] == posBusca){
                    falta = 10;
                }
            }
            if(falta == 1)
                posicao = newP;
        }
        return posicao;
    }

    public int botJogaPerto(int ultimaJogada){
        //16
        int aux = tamanhoTabuleiro*2;
        int jogar;
        Random random = new Random();
        int numTentativas = 0;
        do{
            jogar = ultimaJogada;
            if(ultimaJogada != -1){
                if(numTentativas > 15){
                    for(int i = 0;i<tamanhoTabuleiro;i++){
                        for(int j = 0;j<tamanhoTabuleiro;j++){
                            if(!posicaoExiste(i*tamanhoTabuleiro + j)) {
                                this.ultimaJogadaBot = i*tamanhoTabuleiro + j;
                                return i * tamanhoTabuleiro + j;
                            }
                        }
                    }
                }
                int tentativa = random.nextInt(aux);
                //Log.d("BotJogaPerto", "Tentativa: "+ tentativa);
                //Log.d("BotJogaPerto", "Aux: "+ aux);
                jogar -= (aux/2);
                jogar += tentativa;
                if(posicaoExiste(jogar)){
                    //.d("BotJogaPerto", "Posicao Existe!: "+aux);
                    //Log.d("BotJogaPerto", "Posicao Existe JOGAR: "+jogar);
                    if(aux < tamanhoTabuleiro*tamanhoTabuleiro) {
                        aux++;
                        numTentativas++;
                    }
                }
                else {
                    aux = tamanhoTabuleiro*2;
                    break;
                }
            }
            else{//Primeira jogada do bot.
                jogar = random.nextInt(tamanhoTabuleiro*tamanhoTabuleiro);
                if(!posicaoExiste(jogar))
                    break;
            }
        }while(true);
        //Log.d("BotJogaPerto", "Ultima: "+ultimaJogadaBot);
        //Log.d("BotJogaPerto", "Jogar: "+jogar);
        this.ultimaJogadaBot = jogar;
        return jogar;
    }
    public int melhorJogadaBot(){
        // PARA O BOT GANHAR
        int posicao = melhorJogada(1);
        // Passa como parametro o valor do jogador1 pois está verificando se uma linha
        // possui apenas valores do bot ou vazio

        if(posicao == -1){//Nao ganhou
            // PARA O BOT NAO PERDER
            posicao = melhorJogada(2);// Agora para verificar se nao esta prester a perder.
        }
        if(posicao == -1){//Nao ta proximo de perder nem ganhar vai jogar proximo a ultima jogada.
            posicao = botJogaPerto(ultimaJogadaBot);
            ultimaJogadaBot = posicao;
        }
        return posicao;
    }
    public static int faltaUmPraGanhar(int[] array, int posBusca) {
        int posicao = -1;
        int falta = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                falta++;
                posicao = i;
            }
            else if(array[i] == posBusca){
                falta = 10;
            }
        }
        if(falta==1)
            return posicao;
        return -1;
    }
    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public String getHorario() {
        return horario;
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

    public int getVencedor() {
        return vencedor;
    }

    public void setVencedor(int vencedor) {
        this.vencedor = vencedor;
    }
}
