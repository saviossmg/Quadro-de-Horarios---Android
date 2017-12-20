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
            oferta.setNomeTurma(cursor.getString(1));
            oferta.setDiaSemana(cursor.getString(3));
            oferta.setPeriodo(cursor.getInt(4));
            oferta.setDisciplina(cursor.getString(5));
            oferta.setDescricaoPeriodoLetivo(cursor.getString(6));
            oferta.setHoraInicialA(cursor.getString(7));
            oferta.setHoraFinalA(cursor.getString(8));
            oferta.setIntervaloInicio(cursor.getString(9));
            oferta.setIntervaloFim(cursor.getString(10));
            oferta.setHoraInicialB(cursor.getString(11));
            oferta.setHoraFinalB(cursor.getString(12));
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
        cv.put("nometurma", dados.getNomeTurma());
        cv.put("curso", dados.getCurso().getId());
        cv.put("diasemana", dados.getDiaSemana());
        cv.put("periodo", dados.getPeriodo());
        cv.put("disciplina", dados.getDisciplina());
        cv.put("disciplina", dados.getDisciplina());
        cv.put("descricaoperiodoLetivo", dados.getDescricaoPeriodoLetivo());
        cv.put("descricaoperiodoLetivo", dados.getDescricaoPeriodoLetivo());
        cv.put("horainiciala", dados.getHoraInicialA());
        cv.put("horafinala", dados.getHoraFinalA());
        cv.put("intervaloinicio", dados.getIntervaloInicio());
        cv.put("intervalofinal", dados.getIntervaloFim());
        cv.put("horainicialb", dados.getHoraInicialB());
        cv.put("horafinalb", dados.getHoraFinalB());
        cv.put("turno", dados.getTurno());
        cv.put("professortitular", dados.getProfessor());
        db.insert("oferta", null, cv);
    }

    //UPDATE
    //Só vai ser chamado quando for feita a sincronia dos dados
    public void atualizar(Oferta dados) {

    }

    //DELETAR
    public void deletar(int id) {
        db.execSQL("DELETE FROM oferta WHERE  id =" + id);

    }

    //CONSULTA POR ID
    public Oferta findById(int id) {
        oferta = new Oferta();
        cursor = db.query("oferta", new String[]{"id", "nometurma", "curso", "diasemana", "periodo", "disciplina",
                "descricaoperiodoLetivo", "horainiciala", "horafinala", "intervaloinicio", "intervalofinal", "horainicialb",
                "horafinalb", "turno","professorTitular"}, "id = " + id, null, null, null, null);

        cursor.moveToFirst();
        oferta.setId(cursor.getInt(0));
        oferta.setNomeTurma(cursor.getString(1));
        oferta.setDiaSemana(cursor.getString(3));
        oferta.setPeriodo(cursor.getInt(4));
        oferta.setDisciplina(cursor.getString(5));
        oferta.setDescricaoPeriodoLetivo(cursor.getString(6));
        oferta.setHoraInicialA(cursor.getString(7));
        oferta.setHoraFinalA(cursor.getString(8));
        oferta.setIntervaloInicio(cursor.getString(9));
        oferta.setIntervaloFim(cursor.getString(10));
        oferta.setHoraInicialB(cursor.getString(11));
        oferta.setHoraFinalB(cursor.getString(12));
        oferta.setTurno(cursor.getString(13));
        oferta.setProfessor(cursor.getString(14));
        //preenche o objeto
        oferta.setCurso(findCurso.findById(cursor.getInt(2)));

        cursor.close();

        //retorna o objeto
        return oferta;
    }

}
