package com.unitins.quadro.quadrodehorarios.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unitins.quadro.quadrodehorarios.models.Unidade;
import com.unitins.quadro.quadrodehorarios.services.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by savio on 08/10/2017.
 */

public class UnidadeC {

    private SQLiteDatabase db;
    private DatabaseHelper helper;

    private Unidade unidade;
    private Cursor cursor;

    public UnidadeC(Context context){
        helper = new DatabaseHelper(context);
        db = helper.getDatabase();
    }

    //LISTAR
    public ArrayList<Unidade> listar() {
        ArrayList<Unidade> result = new ArrayList<Unidade>();
        cursor = db.query("unidade", new String[]{"id","nome", "endereco", "cep", "latitude", "longitude"},
                null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            unidade = new Unidade();
            unidade.setId(cursor.getInt(0));
            unidade.setNome(cursor.getString(1));
            unidade.setEndereco(cursor.getString(2));
            unidade.setCep(cursor.getInt(3));
            unidade.setLatitude(cursor.getFloat(4));
            unidade.setLongitude(cursor.getFloat(5));
            result.add(unidade);
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }

    //INSERCAO
    //Só vai ser chamado quando for feita a sincronia dos dados
    public void inserir(Unidade dados)
    {
        ContentValues cv =new ContentValues();
        cv.put("id", dados.getId());
        cv.put("nome", dados.getNome());
        cv.put("endereco", dados.getEndereco());
        cv.put("cep", dados.getCep());
        cv.put("latitude", dados.getLatitude());
        cv.put("longitude", dados.getLongitude());
        db.insert("unidade", null, cv);
        System.out.println("Inseriu Unidade!");
    }

    //UPDATE
    //Só vai ser chamado quando for feita a sincronia dos dados
    public void atualizar(Unidade dados)
    {
        ContentValues cv =new ContentValues();
        cv.put("id", dados.getId());
        cv.put("nome", dados.getNome());
        cv.put("endereco", dados.getEndereco());
        cv.put("cep", dados.getCep());
        cv.put("latitude", dados.getLatitude());
        cv.put("longitude", dados.getLongitude());
        db.update("unidade", cv,  "id = ?", new String[]{String.valueOf(dados.getId())});
        System.out.println("Atualizou Unidade!");
    }

    //DELETAR
    public void deletar(int id){
        db.execSQL("DELETE FROM unidade WHERE  id ="+id);
    }

    //CONSULTA POR ID
    public Unidade findById(int id)
    {
        unidade = null;
        cursor = db.query("unidade", new String[]{"id","nome", "endereco", "cep", "latitude", "longitude"},"id = "+id,
                null, null, null, null);

        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            unidade = new Unidade();
            unidade.setId(cursor.getInt(0));
            unidade.setNome(cursor.getString(1));
            unidade.setEndereco(cursor.getString(2));
            unidade.setCep(cursor.getInt(3));
            unidade.setLatitude(cursor.getFloat(4));
            unidade.setLongitude(cursor.getFloat(5));
        }

        cursor.close();

        //retorna o objeto
        return unidade;
    }


}
