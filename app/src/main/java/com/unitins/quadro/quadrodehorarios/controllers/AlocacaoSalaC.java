package com.unitins.quadro.quadrodehorarios.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unitins.quadro.quadrodehorarios.models.AlocacaoSala;
import com.unitins.quadro.quadrodehorarios.models.SemestreLetivo;
import com.unitins.quadro.quadrodehorarios.services.DatabaseHelper;
import com.unitins.quadro.quadrodehorarios.services.Preferencias;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by savio on 14/10/2017.
 */

public class AlocacaoSalaC {

    private SQLiteDatabase db;
    private DatabaseHelper helper;

    private AlocacaoSala alocSala;
    private SemestreLetivoC findSemLetivo;
    private SalaC findSala;
    private OfertaC findOferta;

    private Cursor cursor;
    private Context context;

    public AlocacaoSalaC(Context context) {
        helper = new DatabaseHelper(context);
        db = helper.getDatabase();
        this.findSemLetivo = new SemestreLetivoC(context);
        this.findSala = new SalaC(context);
        this.findOferta = new OfertaC(context);
        this.context = context;
    }

    //LISTAR
    public ArrayList<AlocacaoSala> listar(Boolean pesquisa,int curso, int semestre, int periodo,
                      int sala, String turno, String dia, Boolean refresh) throws ParseException {

        ArrayList<AlocacaoSala> result = new ArrayList<AlocacaoSala>();
        cursor = db.query("AlocacaoSala", new String[]{"id", "semestre", "sala", "oferta"},
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            alocSala = new AlocacaoSala();
            alocSala.setId(cursor.getInt(0));
            //preenche o objeto
            alocSala.setSemestre(findSemLetivo.findById(cursor.getInt(1)));
            alocSala.setSala(findSala.findById(cursor.getInt(2)));
            alocSala.setOferta(findOferta.findById(cursor.getInt(3)));
            result.add(alocSala);
            cursor.moveToNext();
        }
        cursor.close();

        //cria um arraylist de copia
        ArrayList<AlocacaoSala> copia = new ArrayList<AlocacaoSala>();
        int args = 0;

        //vai verificar a consulta geral e filtar os resultados
        if(pesquisa){
            //soma quantos argumentos vieram diferentes do padrão
            if(curso != 0) args++;
            if(semestre != 0) args++;
            if(periodo != -1) args++;
            if(sala != 0) args++;
            if(!turno.equals("TURNO")) args++;
            if(!dia.equals("DIA")) args++;

            for (AlocacaoSala aux: result) {
                int soma = 0;
                //verifica os parametros
                if(aux.getOferta().getCurso().getId() == curso) soma++;
                if(aux.getSemestre().getSemestre().getId() == semestre) soma++;
                if(aux.getOferta().getPeriodo() == periodo) soma++;
                if(aux.getSala().getId() == sala) soma++;
                if(aux.getOferta().getTurno().equals(turno)) soma++;
                if(aux.getOferta().getDiaSemana().equals(dia)) soma++;

                //verifica a soma
                if(soma == args) copia.add(aux);
            }
            result = copia;
        }
        else
        if (Preferencias.getBoolean(context, "salvo") && !refresh){
            //pega as preferencias,  caso nao haja consulta
            semestre = Preferencias.getInt(context,"semestre");
            curso = Preferencias.getInt(context,"curso");

            if(semestre != 0) args++;
            if(curso != 0) args++;

            for (AlocacaoSala aux: result) {
                int soma = 0;
                //verifica os parametros
                if(aux.getOferta().getCurso().getId() == curso) soma++;
                if(aux.getSemestre().getSemestre().getId() == semestre) soma++;

                //verifica a soma
                if(soma == args) copia.add(aux);
            }
            result = copia;
        }
        return result;
    }

    //INSERCAO
    //Só vai ser chamado quando for feita a sincronia dos dados
    public void inserir(AlocacaoSala dados) {
        ContentValues cv = new ContentValues();
        cv.put("id", dados.getId());
        cv.put("semestre", dados.getSemestre().getId());
        cv.put("sala", dados.getSala().getId());
        cv.put("oferta", dados.getOferta().getId());
        db.insert("AlocacaoSala", null, cv);
    }

    //UPDATE
    //Só vai ser chamado quando for feita a sincronia dos dados
    public void atualizar(SemestreLetivo dados) {

    }

    //DELETAR
    public void deletar(int id) {
        db.execSQL("DELETE FROM AlocacaoSala WHERE  id =" + id);

    }

    //CONSULTA POR ID
    public AlocacaoSala findById(int id) throws ParseException {
        alocSala = new AlocacaoSala();
        cursor = db.query("AlocacaoSala", new String[]{"id", "semestre", "sala", "oferta"},
                "id = " + id, null, null, null, null);

        cursor.moveToFirst();
        alocSala.setId(cursor.getInt(0));
        //preenche o objeto
        alocSala.setSemestre(findSemLetivo.findById(cursor.getInt(1)));
        alocSala.setSala(findSala.findById(cursor.getInt(2)));
        alocSala.setOferta(findOferta.findById(cursor.getInt(3)));

        cursor.close();

        //retorna o objeto
        return alocSala;
    }


}
