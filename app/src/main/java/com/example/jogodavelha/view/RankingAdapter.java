package com.example.jogodavelha.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jogodavelha.R;
import com.example.jogodavelha.bll.JogoBLL;
import com.example.jogodavelha.dao.AppDB;
import com.example.jogodavelha.model.JogoDaVelha;
import com.example.jogodavelha.model.Ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingAdapter extends BaseAdapter {
    private Context context;
    private List<Ranking> ranking;
    private List<JogoDaVelha> partidas;
    public RankingAdapter(Context context){
        AppDB appDB = new AppDB(context);
        JogoBLL jogoBLL = new JogoBLL(appDB);
        this.context = context;
        this.partidas = jogoBLL.getAll();
        this.ranking = getRanking();
    }
    @Override
    public int getCount() {
        return this.ranking.size();
    }

    @Override
    public Object getItem(int i) {
        return this.ranking.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.ranking.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Ranking jogo = ranking.get(i);
        View retorno = LayoutInflater.from(this.context).inflate(R.layout.adapter_ranking, viewGroup, false);
        TextView jogador1 = retorno.findViewById(R.id.adapterJogador);
        TextView posicao = retorno.findViewById(R.id.adapterPosicao);
        TextView vitorias = retorno.findViewById(R.id.adapterQtVitorias);
        jogador1.setText(jogo.getNome());
        if(jogo.getPosicao() == 1)
            posicao.setText("\uD83E\uDD47");
        else if(jogo.getPosicao() == 2)
            posicao.setText("\uD83E\uDD48");
        else if(jogo.getPosicao() == 3)
            posicao.setText("\uD83E\uDD49");
        else
            posicao.setText(" " + jogo.getPosicao() + " ");
        if(jogo.getQtVitorias() == 1)
            vitorias.setText(jogo.getQtVitorias() + " vitória");
        else
            vitorias.setText(jogo.getQtVitorias() + " vitórias");
        return retorno;
    }

    public ArrayList<Ranking> getRanking(){
        HashMap<String, Integer> contagem = new HashMap<>();
        ArrayList<String> vencedores = new ArrayList<>();
        for(JogoDaVelha jogo : this.partidas){
            if(jogo.getVencedor() == 1) {
                String nome = jogo.getNomeJogador1();
                vencedores.add(nome.substring(0,1).toUpperCase() + nome.substring(1).toLowerCase());
            }
            else if(jogo.getVencedor() == 2) {
                String nome = jogo.getNomeJogador2();
                vencedores.add(nome.substring(0,1).toUpperCase() + nome.substring(1).toLowerCase());
            }
        }
        for (String vencedor : vencedores) {
            if (contagem.containsKey(vencedor)) {
                contagem.put(vencedor, contagem.get(vencedor) + 1);
            } else {
                contagem.put(vencedor, 1);
            }
        }

        // Criar uma lista a partir do mapa
        List<Map.Entry<String, Integer>> listaRanking = new ArrayList<>(contagem.entrySet());

        // Classificar a lista com base no número de ocorrências (em ordem decrescente)
        Collections.sort(listaRanking, (a, b) -> b.getValue().compareTo(a.getValue()));
        ArrayList<Ranking> retorno = new ArrayList<>();
        int posicao = 1;
        // Exibir o ranking
        for (Map.Entry<String, Integer> entrada : listaRanking) {
            Ranking ranking = new Ranking(posicao, entrada.getKey(),  entrada.getValue());
            retorno.add(ranking);
            posicao++;
        }
        return retorno;
    }
}