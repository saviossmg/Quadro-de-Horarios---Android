package com.unitins.quadro.quadrodehorarios.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unitins.quadro.quadrodehorarios.models.Predio;
import com.unitins.quadro.quadrodehorarios.services.DatabaseHelper;

import java.util.ArrayList;


/**
 * Created by savio on 08/10/2017.
 */

public class PredioC {

    private SQLiteDatabase db;
    private DatabaseHelper helper;

    private Predio predio;
    private UnidadeC findUnidade;
    private Cursor cursor;

    public PredioC(Context context){
        helper = new DatabaseHelper(context);
        db = helper.getDatabase();
        this.findUnidade = new UnidadeC(context);
    }

    //LISTAR
    public ArrayList<Predio> listar() {
        ArrayList<Predio> result = new ArrayList<Predio>();
        cursor = db.query("predio", new String[]{"id","nome", "pisos","unidade", "ativo"},
                null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            predio = new Predio();
            predio.setId(cursor.getInt(0));
            predio.setNome(cursor.getString(1));
            predio.setPisos(cursor.getInt(2));
            //preenche o objeto
            predio.setUnidade(findUnidade.findById(cursor.getInt(3)));
            predio.setAtivo(Boolean.parseBoolean((cursor.getString(4))));
            result.add(predio);
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }

    //INSERCAO
    //Só vai ser chamado quando for feita a sincronia dos dados
    public void inserir(Predio dados)
    {
        ContentValues cv =new ContentValues();
        cv.put("id", dados.getId());
        cv.put("nome", dados.getNome());
        cv.put("pisos", dados.getPisos());
        cv.put("unidade", dados.getIdunidade());
        cv.put("ativo", dados.getAtivo());
        db.insert("predio", null, cv);
    }

    //UPDATE
    //Só vai ser chamado quando for feita a sincronia dos dados
    public void atualizar(Predio dados)
    {
        ContentValues cv =new ContentValues();
        cv.put("nome", dados.getNome());
        cv.put("pisos", dados.getPisos());
        cv.put("unidade", dados.getIdunidade());
        cv.put("ativo", dados.getAtivo());
        db.update("predio", cv,  "id = ?", new String[]{String.valueOf(dados.getId())});
    }

    //DELETAR
    public void deletar(int id){
        db.execSQL("DELETE FROM predio WHERE  id ="+id);
    }

    //CONSULTA POR ID
    public Predio findById(int id)
    {
        predio = null;
        cursor = db.query("predio", new String[]{"id","nome", "pisos","unidade", "ativo"},"id = "+id,
                null, null, null, null);

        cursor.moveToFirst();
        //caso nao encontre nada retornará nulo
        if(cursor.getCount() > 0){
            predio = new Predio();
            predio.setId(cursor.getInt(0));
            predio.setNome(cursor.getString(1));
            predio.setPisos(cursor.getInt(2));
            predio.setUnidade(findUnidade.findById(cursor.getInt(3)));
            predio.setAtivo(Boolean.parseBoolean(cursor.getString(4)));
        }
        cursor.close();

        //retorna o objeto
        return predio;
    }

}
