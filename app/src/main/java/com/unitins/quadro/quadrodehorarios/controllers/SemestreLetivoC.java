package com.unitins.quadro.quadrodehorarios.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unitins.quadro.quadrodehorarios.models.Predio;
import com.unitins.quadro.quadrodehorarios.models.SemestreLetivo;
import com.unitins.quadro.quadrodehorarios.services.DatabaseHelper;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by savio on 08/10/2017.
 */

public class SemestreLetivoC {

    private SQLiteDatabase db;
    private DatabaseHelper helper;

    private SemestreLetivo semLetivo;
    private SemestreC findSemestre;
    private CursoC findCurso;
    private Cursor cursor;

    public SemestreLetivoC(Context context){
        helper = new DatabaseHelper(context);
        db = helper.getDatabase();
        this.findSemestre = new SemestreC(context);
        this.findCurso = new CursoC(context);
    }

    //LISTAR
    public ArrayList<SemestreLetivo> listar() throws ParseException {
        ArrayList<SemestreLetivo> result = new ArrayList<SemestreLetivo>();
        cursor = db.query("semestreletivo", new String[]{"id","semestre", "curso"},
                null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            semLetivo = new SemestreLetivo();
            semLetivo.setId(cursor.getInt(0));
            //preenche o objeto
            semLetivo.setSemestre(findSemestre.findById(cursor.getInt(1)));
            semLetivo.setCurso(findCurso.findById(cursor.getInt(2)));
            result.add(semLetivo);
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }

    //INSERCAO
    //Só vai ser chamado quando for feita a sincronia dos dados
    public void inserir(SemestreLetivo dados)
    {
        ContentValues cv =new ContentValues();
        cv.put("id", dados.getId());
        cv.put("semestre", dados.getIdsemestre());
        cv.put("curso", dados.getIdcurso());
        db.insert("semestreletivo", null, cv);
        System.out.println("Inseriu Semestre Letivo!");
    }

    //UPDATE
    //Só vai ser chamado quando for feita a sincronia dos dados
    public void atualizar(SemestreLetivo dados){
        ContentValues cv =new ContentValues();
        cv.put("semestre", dados.getIdsemestre());
        cv.put("curso", dados.getIdcurso());
        db.update("semestreletivo", cv,  "id = ?", new String[]{String.valueOf(dados.getId())});
        System.out.println("Atualizou Semestre Letivo!");
    }

    //DELETAR
    public void deletar(int id){
        db.execSQL("DELETE FROM semestreletivo WHERE  id ="+id);

    }

    //CONSULTA POR ID
    public SemestreLetivo findById(int id) throws ParseException {
        semLetivo = null;
        cursor = db.query("semestreletivo", new String[]{"id","semestre", "curso"},"id = "+id,
                null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            semLetivo = new SemestreLetivo();
            semLetivo.setId(cursor.getInt(0));
            semLetivo.setSemestre(findSemestre.findById(cursor.getInt(1)));
            semLetivo.setCurso(findCurso.findById(cursor.getInt(2)));
        }
        cursor.close();
        //retorna o objeto
        return semLetivo;
    }

}
