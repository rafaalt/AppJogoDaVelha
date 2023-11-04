package com.example.jogodavelha.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jogodavelha.R;

public class HistoricoActivity extends AppCompatActivity {
    PartidaAdapter partidaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        ListView lista = this.findViewById(R.id.listaHistorico);
        partidaAdapter = new PartidaAdapter(this);
        lista.setAdapter(partidaAdapter);

        Button btnLeaderboard = (Button) findViewById(R.id.btnLeaderBoards);
        TextView btnVoltar = (TextView) findViewById(R.id.btnHistoricoVoltar);
        btnLeaderboard.setOnClickListener(view -> {
            Intent intent = new Intent(this, LeaderboardActivity.class);
            startActivity(intent);
        });
        btnVoltar.setOnClickListener(view -> {
            this.finish();
        });
    }
}