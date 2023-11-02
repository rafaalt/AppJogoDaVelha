package com.example.jogodavelha.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jogodavelha.R;
import com.example.jogodavelha.model.JogoDaVelha;

public class JogoActivity extends AppCompatActivity {
    JogoDaVelha jogoDaVelha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);

        Intent intent = getIntent();
        jogoDaVelha = (JogoDaVelha) intent.getSerializableExtra("jogo");
        Toast.makeText(this, ""+jogoDaVelha.getTamanhoTabuleiro(),Toast.LENGTH_SHORT).show();
    }
}