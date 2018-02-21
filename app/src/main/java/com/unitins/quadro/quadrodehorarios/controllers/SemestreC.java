package com.unitins.quadro.quadrodehorarios.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unitins.quadro.quadrodehorarios.services.DatabaseHelper;
import com.unitins.quadro.quadrodehorarios.services.Datas;
import com.unitins.quadro.quadrodehorarios.models.Predio;
import com.unitins.quadro.quadrodehorarios.models.Semestre;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by savio on 08/10/2017.
 */

public class SemestreC {

    private SQLiteDatabase db;
    private DatabaseHelper helper;

    private Semestre semestre;
    private Cursor cursor;

    private Datas datas;

    public SemestreC(Context context){
        helper = new DatabaseHelper(context);
        db = helper.getDatabase();
        datas = new Datas();
    }

    //LISTAR
    public ArrayList<Semestre> listar() throws ParseException {
        ArrayList<Semestre> result = new ArrayList<Semestre>();
        cursor = db.query("semestre", new String[]{"id","descricao", "dataInicio","dataFim"},
                null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            semestre = new Semestre();
            semestre.setId(cursor.getInt(0));
            semestre.setDescricao(cursor.getString(1));
//            semestre.setDataInicio(datas.getString2Data(cursor.getString(2)));
//            semestre.setDataFim(datas.getString2Data(cursor.getString(3)));
            result.add(semestre);
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }

    //INSERCAO
    //Só vai ser chamado quando for feita a sincronia dos dados
    public void inserir(Semestre dados) throws ParseException {
        ContentValues cv =new ContentValues();
        cv.put("id", dados.getId());
        cv.put("descricao", dados.getDescricao());
//        cv.put("datainicio", datas.getData2String(dados.getDataInicio()));
//        cv.put("datafim", datas.getData2String(dados.getDataFim()));
        db.insert("semestre", null, cv);
        System.out.println("Inseriu Semestre!");
    }

    //UPDATE
    //Só vai ser chamado quando for feita a sincronia dos dados
    public void atualizar(Semestre dados) throws ParseException {
        ContentValues cv =new ContentValues();
        cv.put("descricao", dados.getDescricao());
//        cv.put("datainicio", datas.getData2String(dados.getDataInicio()));
//        cv.put("datafim", datas.getData2String(dados.getDataFim()));
        db.update("semestre", cv,  "id = ?", new String[]{String.valueOf(dados.getId())});
        System.out.println("Atualizou Semestre!");
    }

    //DELETAR
    public void deletar(int id){
        db.execSQL("DELETE FROM semestre WHERE id ="+id);
    }

    //CONSULTA POR ID
    public Semestre findById(int id) throws ParseException {
        semestre = null;
        cursor = db.query("semestre", new String[]{"id","descricao", "datainicio","datafim"},"id = "+id,
                null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            semestre = new Semestre();
            semestre.setId(cursor.getInt(0));
            semestre.setDescricao(cursor.getString(1));
//            semestre.setDataInicio(datas.getString2Data(cursor.getString(2)));
//            semestre.setDataFim(datas.getString2Data(cursor.getString(3)));
        }
        cursor.close();
        //retorna o objeto
        return semestre;
    }

}
