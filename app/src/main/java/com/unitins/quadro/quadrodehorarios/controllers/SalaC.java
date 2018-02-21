package com.unitins.quadro.quadrodehorarios.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unitins.quadro.quadrodehorarios.models.Sala;
import com.unitins.quadro.quadrodehorarios.services.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by savio on 08/10/2017.
 */

public class SalaC {

    private SQLiteDatabase db;
    private DatabaseHelper helper;

    private Sala sala;
    private PredioC findPredio;
    private Cursor cursor;

    public SalaC(Context context){
        helper = new DatabaseHelper(context);
        db = helper.getDatabase();
        this.findPredio = new PredioC(context);
    }

    //LISTAR
    public ArrayList<Sala> listar() {
        ArrayList<Sala> result = new ArrayList<Sala>();
        cursor = db.query("sala", new String[]{"id","nome","piso", "predio", "tipo", "ativo"},
                null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            sala = new Sala();
            sala.setId(cursor.getInt(0));
            sala.setNome(cursor.getString(1));
            sala.setPiso(cursor.getInt(2));
            //preenche o objeto
            sala.setPredio(findPredio.findById(cursor.getInt(3)));
            sala.setTipo(cursor.getString(4));
            sala.setAtivo(Boolean.parseBoolean(cursor.getString(5)));
            result.add(sala);
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }

    //INSERCAO
    //Só vai ser chamado quando for feita a sincronia dos dados
    public void inserir(Sala dados)
    {
        ContentValues cv =new ContentValues();
        cv.put("id", dados.getId());
        cv.put("nome", dados.getNome());
        cv.put("piso", dados.getPiso());
        cv.put("predio", dados.getIdpredio());
        cv.put("tipo", dados.getTipo());
        cv.put("ativo", dados.getAtivo());
        db.insert("sala", null, cv);
        System.out.println("Inseriu Sala!");
    }

    //UPDATE
    //Só vai ser chamado quando for feita a sincronia dos dados
    public void atualizar(Sala dados)
    {
        ContentValues cv =new ContentValues();
        cv.put("nome", dados.getNome());
        cv.put("piso", dados.getPiso());
        cv.put("predio", dados.getIdpredio());
        cv.put("tipo", dados.getTipo());
        cv.put("ativo", dados.getAtivo());
        db.update("sala", cv,  "id = ?", new String[]{String.valueOf(dados.getId())});
        System.out.println("Atualizou Sala!");
    }

    //DELETAR
    public void deletar(int id){
        db.execSQL("DELETE FROM sala WHERE  id ="+id);

    }

    //CONSULTA POR ID
    public Sala findById(int id)
    {
        sala = null;
        cursor = db.query("sala", new String[]{"id","nome","piso", "predio", "tipo", "ativo"},"id = "+id,
                null, null, null, null);

        cursor.moveToFirst();
        if(cursor.getCount() > 0 ){
            sala = new Sala();
            sala.setId(cursor.getInt(0));
            sala.setNome(cursor.getString(1));
            sala.setPiso(cursor.getInt(2));
            sala.setPredio(findPredio.findById(cursor.getInt(3)));
            sala.setTipo(cursor.getString(4));
            sala.setAtivo(Boolean.parseBoolean(cursor.getString(5)));
        }

        cursor.close();

        //retorna o objeto
        return sala;
    }



}
