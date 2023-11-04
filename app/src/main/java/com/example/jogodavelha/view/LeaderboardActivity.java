package com.example.jogodavelha.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jogodavelha.R;

public class LeaderboardActivity extends AppCompatActivity {
    RankingAdapter rankingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        TextView btnVoltar = (TextView) findViewById(R.id.btnLeaderBoardsVoltar);
        ListView lista = this.findViewById(R.id.listaLeaderboard);
        rankingAdapter = new RankingAdapter(this);
        lista.setAdapter(rankingAdapter);
        btnVoltar.setOnClickListener(view -> {
            this.finish();
        });
    }
}