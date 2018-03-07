package com.unitins.quadro.quadrodehorarios.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unitins.quadro.quadrodehorarios.models.Oferta;
import com.unitins.quadro.quadrodehorarios.services.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by savio on 08/10/2017.
 */

public class OfertaC {

    private SQLiteDatabase db;
    private DatabaseHelper helper;

    private Oferta oferta;
    private CursoC findCurso;
    private Cursor cursor;

    public OfertaC(Context context) {
        helper = new DatabaseHelper(context);
        db = helper.getDatabase();
        this.findCurso = new CursoC(context);
    }

    //LISTAR
    public ArrayList<Oferta> listar() {
        ArrayList<Oferta> result = new ArrayList<Oferta>();
        cursor = db.query("oferta", new String[]{"id", "nometurma", "curso", "diasemana", "periodo", "disciplina", "descricaoperiodoLetivo",
                        "horainiciala", "horafinala", "intervaloinicio", "intervalofinal", "horainicialb", "horafinalb", "turno","professorTitular"},
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            oferta = new Oferta();
            oferta.setId(cursor.getInt(0));
            oferta.setNometurma(cursor.getString(1));
            oferta.setDiasemana(cursor.getString(3));
            oferta.setPeriodo(cursor.getInt(4));
            oferta.setDisciplina(cursor.getString(5));
            oferta.setDescricaoperiodoletivo(cursor.getString(6));
            oferta.setHorainiciala(cursor.getString(7));
            oferta.setHorafinala(cursor.getString(8));
            oferta.setIntervaloinicio(cursor.getString(9));
            oferta.setIntervalofim(cursor.getString(10));
            oferta.setHorainicialb(cursor.getString(11));
            oferta.setHorafinalb(cursor.getString(12));
            oferta.setTurno(cursor.getString(13));
            oferta.setProfessor(cursor.getString(14));
            //preenche o objeto
            oferta.setCurso(findCurso.findById(cursor.getInt(2)));
            result.add(oferta);
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }

    //INSERCAO
    //Só vai ser chamado quando for feita a sincronia dos dados
    public void inserir(Oferta dados) {
        ContentValues cv = new ContentValues();
        cv.put("id", dados.getId());
        cv.put("nometurma", dados.getNometurma());
        cv.put("curso", dados.getIdcurso());
        cv.put("diasemana", dados.getDiasemana());
        cv.put("periodo", dados.getPeriodo());
        cv.put("disciplina", dados.getDisciplina());
        cv.put("disciplina", dados.getDisciplina());
        cv.put("descricaoperiodoLetivo", dados.getDescricaoperiodoletivo());
        cv.put("horainiciala", dados.getHorainiciala());
        cv.put("horafinala", dados.getHorafinala());
        cv.put("intervaloinicio", dados.getIntervaloinicio());
        cv.put("intervalofinal", dados.getIntervalofim());
        cv.put("horainicialb", dados.getHorainicialb());
        cv.put("horafinalb", dados.getHorafinalb());
        cv.put("turno", dados.getTurno());
        cv.put("professortitular", dados.getProfessor());
        db.insert("oferta", null, cv);
    }

    //UPDATE
    //Só vai ser chamado quando for feita a sincronia dos dados
    public void atualizar(Oferta dados) {
        ContentValues cv = new ContentValues();
        cv.put("nometurma", dados.getNometurma());
        cv.put("curso", dados.getIdcurso());
        cv.put("diasemana", dados.getDiasemana());
        cv.put("periodo", dados.getPeriodo());
        cv.put("disciplina", dados.getDisciplina());
        cv.put("descricaoperiodoLetivo", dados.getDescricaoperiodoletivo());
        cv.put("horainiciala", dados.getHorainiciala());
        cv.put("horafinala", dados.getHorafinala());
        cv.put("intervaloinicio", dados.getIntervaloinicio());
        cv.put("intervalofinal", dados.getIntervalofim());
        cv.put("horainicialb", dados.getHorainicialb());
        cv.put("horafinalb", dados.getHorafinalb());
        cv.put("turno", dados.getTurno());
        cv.put("professortitular", dados.getProfessor());
        db.update("oferta", cv,  "id = ?", new String[]{String.valueOf(dados.getId())});
    }

    //DELETAR
    public void deletar(int id) {
        db.execSQL("DELETE FROM oferta WHERE  id =" + id);
    }

    //CONSULTA POR ID
    public Oferta findById(int id) {
        oferta = null;
        cursor = db.query("oferta", new String[]{"id", "nometurma", "curso", "diasemana", "periodo", "disciplina",
                "descricaoperiodoLetivo", "horainiciala", "horafinala", "intervaloinicio", "intervalofinal", "horainicialb",
                "horafinalb", "turno","professorTitular"}, "id = " + id, null, null, null, null);

        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            oferta = new Oferta();
            oferta.setId(cursor.getInt(0));
            oferta.setNometurma(cursor.getString(1));
            oferta.setDiasemana(cursor.getString(3));
            oferta.setPeriodo(cursor.getInt(4));
            oferta.setDisciplina(cursor.getString(5));
            oferta.setDescricaoperiodoletivo(cursor.getString(6));
            oferta.setHorainiciala(cursor.getString(7));
            oferta.setHorafinala(cursor.getString(8));
            oferta.setIntervaloinicio(cursor.getString(9));
            oferta.setIntervalofim(cursor.getString(10));
            oferta.setHorainicialb(cursor.getString(11));
            oferta.setHorafinalb(cursor.getString(12));
            oferta.setTurno(cursor.getString(13));
            oferta.setProfessor(cursor.getString(14));
            //preenche o objeto
            oferta.setCurso(findCurso.findById(cursor.getInt(2)));
        }

        cursor.close();

        //retorna o objeto
        return oferta;
    }

}
