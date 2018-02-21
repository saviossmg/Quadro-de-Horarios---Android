package com.unitins.quadro.quadrodehorarios.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unitins.quadro.quadrodehorarios.models.Curso;
import com.unitins.quadro.quadrodehorarios.services.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by savio on 08/10/2017.
 */

public class CursoC {

    private SQLiteDatabase db;
    private DatabaseHelper helper;

    private Curso curso;
    private UnidadeC findUnidade;
    private Cursor cursor;

    public CursoC(Context context){
        helper = new DatabaseHelper(context);
        db = helper.getDatabase();
        this.findUnidade = new UnidadeC(context);
    }

    //LISTAR
    public ArrayList<Curso> listar() {
        ArrayList<Curso> result = new ArrayList<Curso>();
        cursor = db.query("curso", new String[]{"id","nome", "unidade", "codcurso"},
                null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            curso = new Curso();
            curso.setId(cursor.getInt(0));
            curso.setNome(cursor.getString(1));
            //preenche o objeto
            curso.setUnidade(findUnidade.findById(cursor.getInt(2)));
            curso.setCodCurso(cursor.getString(3));
            result.add(curso);
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }

    //INSERCAO
    //Só vai ser chamado quando for feita a sincronia dos dados
    public void inserir(Curso dados)
    {
        ContentValues cv =new ContentValues();
        cv.put("id", dados.getId());
        cv.put("nome", dados.getNome());
        cv.put("unidade", dados.getIdunidade());
        cv.put("codcurso", dados.getCodCurso());
        db.insert("curso", null, cv);
        System.out.println("Inseriu Curso!");
    }

    //UPDATE
    //Só vai ser chamado quando for feita a sincronia dos dados
    public void atualizar(Curso dados)
    {
        ContentValues cv =new ContentValues();
        cv.put("nome", dados.getNome());
        cv.put("unidade", dados.getIdunidade());
        cv.put("codcurso", dados.getCodCurso());
        db.update("curso", cv,  "id = ?", new String[]{String.valueOf(dados.getId())});
        System.out.println("Atualizou Curso!");
    }

    //DELETAR
    public void deletar(int id){
        db.execSQL("DELETE FROM curso WHERE  id ="+id);

    }

    //CONSULTA POR ID
    public Curso findById(int id)
    {
        curso = null;
        cursor = db.query("curso", new String[]{"id","nome", "unidade", "codcurso"},"id = "+id,
                null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            curso = new Curso();
            curso.setId(cursor.getInt(0));
            curso.setNome(cursor.getString(1));
            curso.setUnidade(findUnidade.findById(cursor.getInt(2)));
            curso.setCodCurso(cursor.getString(3));
        }
        cursor.close();
        //retorna o objeto
        return curso;
    }

}
