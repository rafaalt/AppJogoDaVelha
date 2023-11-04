package com.example.jogodavelha.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jogodavelha.R;
import com.example.jogodavelha.bll.JogoBLL;
import com.example.jogodavelha.dao.AppDB;
import com.example.jogodavelha.model.JogoDaVelha;

import org.w3c.dom.Text;

import java.util.Random;

public class JogoActivity extends AppCompatActivity {
    public JogoDaVelha jogoDaVelha;
    public boolean turnJogador1 = true;
    public boolean jogoEncerrado = false;

    public String nomeVencedor = "";
    AppDB appDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);
        Intent intent = getIntent();
        jogoDaVelha = (JogoDaVelha) intent.getSerializableExtra("jogo");
        appDB = new AppDB(this);
        TextView txtJogadorTurno = (TextView) findViewById(R.id.txtNomeJogador);
        txtJogadorTurno.setText(jogoDaVelha.getNomeJogador1());

        Button btnRecomecar = (Button) findViewById(R.id.btnRecomearPartida);
        Button btnNovoJogo = (Button) findViewById(R.id.btnNovoJogo);

        btnRecomecar.setOnClickListener(view -> {
            recomecarPartida();
        });

        btnNovoJogo.setOnClickListener(view -> {
            this.finish();
        });

        GridLayout tabuleiro = (GridLayout) findViewById(R.id.gridLayout);
        int tamanho = jogoDaVelha.getTamanhoTabuleiro();
        tabuleiro.setRowCount(tamanho);
        tabuleiro.setColumnCount(tamanho);
        int tamanhoQuadrado = (int) 900/tamanho;
        int tag = 0;
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                tabuleiro.addView(createCell(tamanhoQuadrado, tag));
                tag++;
            }
        }
    }
    public TextView createCell(int tamanho, int tag){
        GradientDrawable background = new GradientDrawable();
        background.setShape(GradientDrawable.RECTANGLE);
        background.setColor(Color.WHITE);
        background.setStroke(2, Color.BLACK);
        TextView cell = new TextView(this);
        cell.setText("");
        cell.setWidth(tamanho);
        cell.setHeight(tamanho);
        cell.setTextSize(tamanho/5);
        cell.setLayoutParams(new GridLayout.LayoutParams());
        cell.setBackground(background);
        cell.setGravity(Gravity.CENTER);
        cell.setTag(tag);
        cell.setOnClickListener(view -> {
            if(cell.getText() == ""){
                jogar(cell);
            }
        });
        return cell;
    }
    public void jogar(TextView cell){
        if(!jogoEncerrado) {
            TextView txtJogadorTurno = (TextView) findViewById(R.id.txtNomeJogador);
            if (turnJogador1) {//Jogador 1 Joga
                int corJ1 = ContextCompat.getColor(this, R.color.jogador1);
                int corJ2 = ContextCompat.getColor(this, R.color.jogador2);
                cell.setText("X");
                cell.setTextColor(corJ1);
                txtJogadorTurno.setText(jogoDaVelha.getNomeJogador2());
                txtJogadorTurno.setTextColor(corJ2);
                jogoDaVelha.fazerJogada(1, (int) cell.getTag());
                turnJogador1 = false;
            } else {//Jogador 2 Joga
                int corJ1 = ContextCompat.getColor(this, R.color.jogador1);
                int corJ2 = ContextCompat.getColor(this, R.color.jogador2);
                cell.setText("O");
                cell.setTextColor(corJ2);
                txtJogadorTurno.setText(jogoDaVelha.getNomeJogador1());
                txtJogadorTurno.setTextColor(corJ1);
                jogoDaVelha.fazerJogada(2, (int) cell.getTag());
                turnJogador1 = true;
            }
            verificarVencedor();
        }
        if(jogoDaVelha.isBot())
            jogarBot();
    }
    public void jogarBot(){
        if(!jogoEncerrado) {
            turnJogador1 = true;
            Random random = new Random();
            TextView txtJogadorTurno = (TextView) findViewById(R.id.txtNomeJogador);
            int posicao = jogoDaVelha.melhorJogadaBot();
            jogoDaVelha.fazerJogada(2, posicao);
            GridLayout tabuleiro = (GridLayout) findViewById(R.id.gridLayout);
            TextView txt = (TextView) tabuleiro.getChildAt(posicao);
            txt.setText("O");
            int corJ2 = ContextCompat.getColor(this, R.color.jogador2);
            int corJ1 = ContextCompat.getColor(this, R.color.jogador1);
            txt.setTextColor(corJ2);
            txtJogadorTurno.setText(jogoDaVelha.getNomeJogador1());
            txtJogadorTurno.setTextColor(corJ1);
            verificarVencedor();
        }
    }

    public void recomecarPartida(){
        GridLayout tabuleiro = (GridLayout) findViewById(R.id.gridLayout);
        int tam = jogoDaVelha.getTamanhoTabuleiro();
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                int indice = i * tam + j;
                TextView txt = (TextView) tabuleiro.getChildAt(indice);
                txt.setText("");
            }
        }
        TextView txt1 = (TextView) findViewById(R.id.txtVezJogador);
        TextView txt2 = (TextView) findViewById(R.id.txtNomeJogador);
        txt1.setText("Vez do Jogador");
        txt2.setText(jogoDaVelha.getNomeJogador1());
        int cor = ContextCompat.getColor(this, R.color.jogador1);
        txt2.setTextColor(cor);
        turnJogador1 = true;
        jogoEncerrado = false;
        jogoDaVelha.reiniciar();
    }
    public void verificarVencedor(){
        int result = jogoDaVelha.verificarVencedor();
        if(result == 1){
            TextView txt1 = (TextView) findViewById(R.id.txtVezJogador);
            TextView txt2 = (TextView) findViewById(R.id.txtNomeJogador);
            txt1.setText("VENCEDOR! \uD83C\uDFC6");
            txt2.setText(jogoDaVelha.getNomeJogador1());
            int cor = ContextCompat.getColor(this, R.color.jogador1);
            txt2.setTextColor(cor);
            jogoEncerrado = true;
            Toast.makeText(this, "Parabens " + jogoDaVelha.getNomeJogador1() + "! Você Venceu!", Toast.LENGTH_SHORT).show();
            this.jogoDaVelha.setVencedor(1);
            this.nomeVencedor = "Parabéns " + jogoDaVelha.getNomeJogador1() + "!";
        }
        else if(result == 2){
            TextView txt1 = (TextView) findViewById(R.id.txtVezJogador);
            TextView txt2 = (TextView) findViewById(R.id.txtNomeJogador);
            if(jogoDaVelha.isBot()){//Bot que venceu, o jogador perdeu.
                txt1.setText("VITÓRIA DO BOT!");
                txt2.setText(jogoDaVelha.getNomeJogador2());
                int cor = ContextCompat.getColor(this, R.color.jogador2);
                txt2.setTextColor(cor);
                Toast.makeText(this, "Vitória do Bot!", Toast.LENGTH_SHORT).show();
                jogoEncerrado = true;
                this.jogoDaVelha.setVencedor(2);
                this.nomeVencedor = "O Robô te venceu!";
            }
            else{
                txt1.setText("\uD83C\uDFC6 VENCEDOR \uD83C\uDFC6");
                txt2.setText(jogoDaVelha.getNomeJogador2());
                int cor = ContextCompat.getColor(this, R.color.jogador2);
                txt2.setTextColor(cor);
                Toast.makeText(this, "Parabens " + jogoDaVelha.getNomeJogador2() + "! Você Venceu!", Toast.LENGTH_SHORT).show();
                jogoEncerrado = true;
                this.jogoDaVelha.setVencedor(2);
                this.nomeVencedor = "Parabéns " + jogoDaVelha.getNomeJogador2() + "!";
            }
        }
        else if(result == 3){
            TextView txt1 = (TextView) findViewById(R.id.txtVezJogador);
            TextView txt2 = (TextView) findViewById(R.id.txtNomeJogador);
            txt1.setText("DEU VELHA!");
            txt2.setText("Jogo Empatado");
            int cor = ContextCompat.getColor(this, R.color.empate);
            txt2.setTextColor(cor);
            Toast.makeText(this, "Jogo Empatado! Deu Velha!", Toast.LENGTH_SHORT).show();
            jogoEncerrado = true;
            this.jogoDaVelha.setVencedor(3);
        }
        if(result!=0){
            salvarPartida();//Vai pro banco de Dados
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.aviso_final);
            Button btnVoltar = dialog.findViewById(R.id.avisoBtnVoltar);
            Button btnReiniciar = dialog.findViewById(R.id.avisoBtnReiniciar);
            TextView txt1 = dialog.findViewById(R.id.avisoTexto);
            TextView txt2 = dialog.findViewById(R.id.avisoTxtJogador);
            if(result == 3){
                txt1.setText("DEU VELHA");
                txt2.setText("Jogo Empatado!");
            }
            else {
                txt1.setText("\uD83C\uDFC6 FIM DE JOGO \uD83C\uDFC6");
                txt2.setText(nomeVencedor);
            }

            btnVoltar.setOnClickListener(view -> {
                dialog.dismiss();
            });

            btnReiniciar.setOnClickListener(view -> {
                dialog.dismiss();
                recomecarPartida();
            });
            dialog.show();
        }
    }
    public void salvarPartida(){
        JogoBLL jogoBLL = new JogoBLL(appDB);
        boolean wasCreated = jogoBLL.create(jogoDaVelha);

        if (wasCreated) {
            //Toast.makeText(this, "Partida salva no banco de dados!", Toast.LENGTH_SHORT).show();
            //this.finish();
        } else {
            Toast.makeText(this, "Erro ao salvar a partida.", Toast.LENGTH_SHORT).show();
        }
    }

    }