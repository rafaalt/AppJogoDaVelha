package com.example.jogodavelha.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jogodavelha.model.JogoDaVelha;

import java.util.ArrayList;
import java.util.List;

public class JogoDAO {
    public static final String CREATE_SCRIPT =
            "CREATE TABLE IF NOT EXISTS jogos (\n" +
                    "\tid INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                    "\tdia TEXT NOT NULL, \n" +
                    "\thorario TEXT NOT NULL, \n" +
                    "\tjogador1 TEXT NOT NULL, \n" +
                    "\tjogador2 TEXT NOT NULL, \n" +
                    "\tvencedor INTEGER NOT NULL, \n" +
                    "\ttamanho INTEGER NOT NULL \n" +
                    ");";

    public static final String TABLE_NAME = "jogos";
    public static final String ID_COLUMN = "id";
    public static final String DIA_COLUMN = "dia";
    public static final String HORARIO_COLUMN = "horario";
    public static final String JOGADOR1_COLUMN = "jogador1";
    public static final String JOGADOR2_COLUMN = "jogador2";
    public static final String VENCEDOR_COLUMN = "vencedor";

    public static final String TAMANHO_COLUMN = "tamanho";
    private AppDB appDB;

    public JogoDAO(AppDB appDB) {
        this.appDB = appDB;
    }

    public boolean create(JogoDaVelha jogoDaVelha) {
        boolean result = true;
        SQLiteDatabase writeDb = this.appDB.getWritableDatabase();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(JOGADOR1_COLUMN, jogoDaVelha.getNomeJogador1());
            contentValues.put(JOGADOR2_COLUMN, jogoDaVelha.getNomeJogador2());
            contentValues.put(VENCEDOR_COLUMN, jogoDaVelha.getVencedor());
            contentValues.put(DIA_COLUMN, jogoDaVelha.getData());
            contentValues.put(HORARIO_COLUMN, jogoDaVelha.getHorario());
            contentValues.put(TAMANHO_COLUMN, jogoDaVelha.getTamanhoTabuleiro());
            writeDb.insert(TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            result = false;
        } finally {
            writeDb.close();
        }
        return result;
    }
    @SuppressLint("Range")
    public List<JogoDaVelha> getAll() {
        List<JogoDaVelha> partidas = new ArrayList<>();
        SQLiteDatabase readDB = this.appDB.getReadableDatabase();

        try {
            Cursor res = readDB.query(TABLE_NAME, null, null, null, null, null, null);
            if (res.moveToFirst()) {
                do {
                    int id = res.getInt(res.getColumnIndex(ID_COLUMN));
                    String jogador1 = res.getString(res.getColumnIndex(JOGADOR1_COLUMN));
                    String jogador2 = res.getString(res.getColumnIndex(JOGADOR2_COLUMN));
                    int vencedor = res.getInt(res.getColumnIndex(VENCEDOR_COLUMN));
                    int tamanhoTabuleiro = res.getInt(res.getColumnIndex(TAMANHO_COLUMN));
                    String data = res.getString(res.getColumnIndex(DIA_COLUMN));
                    String horario = res.getString(res.getColumnIndex(HORARIO_COLUMN));
                    partidas.add(new JogoDaVelha(id, jogador1, jogador2, tamanhoTabuleiro, vencedor, data, horario));
                } while (res.moveToNext());
            }
        } catch(Exception e) {
            partidas = null;
        } finally {
            readDB.close();
        }
        return partidas;
    }
    public boolean delete(JogoDaVelha jogo){
        boolean result = true;
        SQLiteDatabase removeDb = this.appDB.getWritableDatabase();

        try {
            int id = jogo.getId();
            String whereClause = "id=?";
            String[] params = new String[] { String.valueOf(id) };
            removeDb.delete(TABLE_NAME, whereClause, params);
        } catch (Exception e) {
            result = false;
        } finally {
            removeDb.close();
        }

        return result;
    }
}
