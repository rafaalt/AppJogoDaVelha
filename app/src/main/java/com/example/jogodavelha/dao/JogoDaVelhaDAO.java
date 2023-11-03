package com.example.jogodavelha.dao;

public class JogoDaVelhaDAO {
    public static final String CREATE_SCRIPT =
            "CREATE TABLE IF NOT EXISTS jogos (\n" +
                    "\tid INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                    "\tdia TEXT NOT NULL, \n" +
                    "\thorario TEXT NOT NULL, \n" +
                    "\tjogador1 TEXT NOT NULL, \n" +
                    "\tjogador2 TEXT NOT NULL, \n" +
                    "\tvencedor INTEGER NOT NULL, \n" +
                    ");";

    public static final String TABLE_NAME = "jogos";
    public static final String ID_COLUMN = "id";
    public static final String DIA_COLUMN = "dia";
    public static final String HORARIO_COLUMN = "horario";
    public static final String JOGADOR1_COLUMN = "jogador1";
    public static final String JOGADOR2_COLUMN = "jogador2";
    public static final String VENCEDOR_COLUMN = "vencedor";



}
