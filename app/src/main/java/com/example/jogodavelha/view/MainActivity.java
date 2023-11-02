package com.example.jogodavelha.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.jogodavelha.R;
import com.example.jogodavelha.model.JogoDaVelha;

public class MainActivity extends AppCompatActivity {
    public RadioButton[] tab;

    public JogoDaVelha jogoDaVelha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText textJogador1 = (EditText) findViewById(R.id.editTextJogador1);
        EditText textJogador2 = (EditText) findViewById(R.id.editTextJogador2);
        Button btnComecarPartida = (Button) findViewById(R.id.btnComecarPartida);
        this.tab = new RadioButton[11];
        this.jogoDaVelha = new JogoDaVelha();

        btnComecarPartida.setOnClickListener(view -> {
            String jogador1 = textJogador1.getText().toString();
            String jogador2 = textJogador2.getText().toString();
            //Verificacao dos Nomes:
            if(this.jogoDaVelha.isBot()){
                if(jogador1.length() <= 0 || jogador1.contains(" ")){
                    Toast.makeText(MainActivity.this, "Jogador 1 Inválido. Por favor retire os espaços.", Toast.LENGTH_SHORT).show();
                }//Só precisa verificar jogador 1.
                this.jogoDaVelha.setNomeJogador1(textJogador1.toString());
                this.jogoDaVelha.setNomeJogador2("Robô");
                Intent intent = new Intent(this, JogoActivity.class);
                intent.putExtra("jogo", this.jogoDaVelha);
                startActivity(intent);
            }
            else{//vs Jogador
                if(jogador1.length() <= 0 || jogador1.contains(" ")){
                    Toast.makeText(MainActivity.this, "Jogador 1 Inválido. Por favor retire os espaços.", Toast.LENGTH_SHORT).show();
                }
                else if(jogador2.length() <= 0 || jogador2.contains(" ")){
                    Toast.makeText(MainActivity.this, "Jogador 2 Inválido. Por favor retire os espaços.", Toast.LENGTH_SHORT).show();
                }
                else{//Tudo certo!
                    this.jogoDaVelha.setNomeJogador2(jogador2);
                    this.jogoDaVelha.setNomeJogador1(jogador1);
                    Intent intent = new Intent(this, JogoActivity.class);
                    intent.putExtra("jogo", this.jogoDaVelha);
                    startActivity(intent);
                }
            }
        });
    }

    public void onClickedTipoJogo(View view) {
        RadioButton btnJogador = (RadioButton) findViewById(R.id.radio_jogador);
        EditText textJogador2 = (EditText) findViewById(R.id.editTextJogador2);
        if(btnJogador.isChecked()) {//Clicou no Jogador1
            textJogador2.setText("");
            textJogador2.setEnabled(true);
            this.jogoDaVelha.setBot(false);
        }
        else {// vs Bot
            textJogador2.setEnabled(false);
            textJogador2.setText("Robô");
            this.jogoDaVelha.setBot(true);
        }
    }
    public void onClickedTamanho(View view){
        RadioButton btn = (RadioButton) view;
        tab[3] = (RadioButton) findViewById(R.id.tamTabuleiro3);
        tab[4] = (RadioButton) findViewById(R.id.tamTabuleiro4);
        tab[5] = (RadioButton) findViewById(R.id.tamTabuleiro5);
        tab[6] = (RadioButton) findViewById(R.id.tamTabuleiro6);
        tab[7] = (RadioButton) findViewById(R.id.tamTabuleiro7);
        tab[8] = (RadioButton) findViewById(R.id.tamTabuleiro8);
        tab[9] = (RadioButton) findViewById(R.id.tamTabuleiro9);
        tab[10] = (RadioButton) findViewById(R.id.tamTabuleiro10);

        for(int i = 3;i<11;i++){
            tab[i].setChecked(false);
        }
        btn.setChecked(true);
        this.jogoDaVelha.setTamanhoTabuleiro(Integer.parseInt(btn.getTag().toString()));
    }
}