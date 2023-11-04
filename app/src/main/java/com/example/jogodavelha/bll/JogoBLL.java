package com.example.jogodavelha.bll;

import com.example.jogodavelha.dao.AppDB;
import com.example.jogodavelha.dao.JogoDAO;
import com.example.jogodavelha.model.JogoDaVelha;

import java.util.ArrayList;
import java.util.List;

public class JogoBLL {

    private AppDB appDB;

    public JogoBLL(AppDB appDB) {
        this.appDB = appDB;
    }

    public boolean create(JogoDaVelha jogo) {
        jogo.setDataHorario();
        JogoDAO jogoDAO = new JogoDAO(this.appDB);
        boolean result = jogoDAO.create(jogo);
        return result;
    }

    public List<JogoDaVelha> getAll() {
        List<JogoDaVelha> partidas;
        JogoDAO jogoDAO = new JogoDAO(this.appDB);
        partidas = jogoDAO.getAll();
        return partidas;
    }

    public boolean delete(JogoDaVelha jogo){
        JogoDAO jogoDAO = new JogoDAO(this.appDB);
        return jogoDAO.delete(jogo);
    }
}
