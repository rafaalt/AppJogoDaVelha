package com.example.jogodavelha.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jogodavelha.R;
import com.example.jogodavelha.bll.JogoBLL;
import com.example.jogodavelha.dao.AppDB;
import com.example.jogodavelha.model.JogoDaVelha;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PartidaAdapter extends BaseAdapter {
    private Context context;
    private List<JogoDaVelha> partidas;
    public PartidaAdapter(Context context){
        AppDB appDB = new AppDB(context);
        JogoBLL jogoBLL = new JogoBLL(appDB);
        this.context = context;
        this.partidas = jogoBLL.getAll();
        //Pega o historico de forma decrescente
        Collections.reverse(this.partidas);
    }
    @Override
    public int getCount() {
        return this.partidas.size();
    }

    @Override
    public Object getItem(int i) {
        return this.partidas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.partidas.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        JogoDaVelha jogo = partidas.get(i);
        View retorno = LayoutInflater.from(this.context).inflate(R.layout.adapter_partida, viewGroup, false);
        TextView jogador1 = retorno.findViewById(R.id.adapterJogador1);
        TextView jogador2 = retorno.findViewById(R.id.adapterJogador2);
        TextView data = retorno.findViewById(R.id.adapterData);
        TextView horario = retorno.findViewById(R.id.adapterHorario);
        TextView tamanho = retorno.findViewById(R.id.adapterTamanho);
        jogador1.setText(jogo.getNomeJogador1());
        jogador2.setText(jogo.getNomeJogador2());
        if(jogo.getVencedor() == 1){
            jogador1.setText("✅ " + jogo.getNomeJogador1());
            jogador2.setText(jogo.getNomeJogador2() + "  ❌");
        }
        else if(jogo.getVencedor() == 2){
            jogador1.setText("❌  " + jogo.getNomeJogador1());
            jogador2.setText(jogo.getNomeJogador2() + "  ✅");
        }
        else if(jogo.getVencedor() == 3){
            jogador1.setText("⬛  " + jogo.getNomeJogador1());
            jogador2.setText(jogo.getNomeJogador2() + "  ⬛");
        }
        data.setText(jogo.getData());
        horario.setText(jogo.getHorario());
        tamanho.setText(jogo.getTamanhoTabuleiro() + "x" + jogo.getTamanhoTabuleiro());
        return retorno;
    }
}