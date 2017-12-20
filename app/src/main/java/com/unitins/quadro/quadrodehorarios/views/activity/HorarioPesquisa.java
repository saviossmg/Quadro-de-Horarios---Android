package com.unitins.quadro.quadrodehorarios.views.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.unitins.quadro.quadrodehorarios.R;
import com.unitins.quadro.quadrodehorarios.controllers.CursoC;
import com.unitins.quadro.quadrodehorarios.controllers.SalaC;
import com.unitins.quadro.quadrodehorarios.controllers.SemestreC;
import com.unitins.quadro.quadrodehorarios.models.Curso;
import com.unitins.quadro.quadrodehorarios.models.Periodo;
import com.unitins.quadro.quadrodehorarios.models.Sala;
import com.unitins.quadro.quadrodehorarios.models.Semestre;

import java.text.ParseException;
import java.util.ArrayList;

public class HorarioPesquisa extends AppCompatActivity {

    //referencias visuais
    private Spinner cursoCombo = null;
    private Spinner semestreCombo = null;
    private Spinner periodoCombo = null;
    private Spinner salaCombo = null;
    private Spinner turnoCombo = null;
    private Spinner diaCombo = null;

    //adapters dos spinners
    private ArrayAdapter<Curso> cursoAdapter;
    private ArrayAdapter<Semestre> semestreAdapter;
    private ArrayAdapter<Periodo> periodoAdapter;
    private ArrayAdapter<Sala> salaAdapter;
    private ArrayAdapter<String> turnoAdapter;
    private ArrayAdapter<String> diaAdapter;

    //posicoes de referencia dos adaptets
    private int cursoPos;
    private int semestrePos;
    private int periodoPos;
    private int salaPos;
    private int turnoPos;
    private int diaPos;

    //listas que farão parte dos spinners
    private ArrayList<Curso> cursos;
    private ArrayList<Semestre> semestres;
    private ArrayList<Periodo> periodos;
    private ArrayList<Sala> salas;
    private ArrayList<String> turnos;
    private ArrayList<String> diasSemana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar);
        getSupportActionBar().setTitle("Pesquisa de Horários");

        //referencias visuais
        cursoCombo = (Spinner) findViewById(R.id.pesquisar_curso);
        semestreCombo = (Spinner) findViewById(R.id.pesquisar_semestre);
        periodoCombo = (Spinner) findViewById(R.id.pesquisar_periodo);
        salaCombo = (Spinner) findViewById(R.id.pesquisar_sala);
        turnoCombo = (Spinner) findViewById(R.id.pesquisar_turno);
        diaCombo = (Spinner) findViewById(R.id.pesquisar_dia);

        //carrega dados dos arraylists
        try {
            loadCursos();
            loadSemestres();
            loadPeriodos();
            loadSalas();
            loadTurnos();
            loadDiassemana();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //carrega adapters
        getadapCursos();
        getadapSemestres();
        getadapPeriodos();
        getadapSalas();
        getadapTurnos();
        getadapDiasemana();

        //listeners
        cursoCombo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView cursoD, View v, int posicao, long id) {
                cursoPos = posicao;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        semestreCombo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView semestreD, View v, int posicao, long id) {
                semestrePos = posicao;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        periodoCombo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView periodoD, View v, int posicao, long id) {
                periodoPos = posicao;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        salaCombo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView salaD, View v, int posicao, long id) {
                salaPos = posicao;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        turnoCombo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView turnoD, View v, int posicao, long id) {
                turnoPos = posicao;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        diaCombo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView diaD, View v, int posicao, long id) {
                diaPos = posicao;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

    //sobrescreve o botão de voltar da barra
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Dicionario de dados
                Bundle dicionario = new Bundle();
                dicionario.putBoolean("voltar", true);
                dicionario.putString("tela", "PesquisarHorarios");

                Intent resultado = new Intent();
                //Dicionario de dados
                resultado.putExtras(dicionario);
                //Seta o valor do resultado e os que serao retornados
                this.setResult(Activity.RESULT_OK, resultado);
                //volta para a activity anterior
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //metodo click
    public void clickOk(View Botao) {
        //Dicionario de dados
        Bundle dicionario = new Bundle();
        dicionario.putBoolean("voltar", false);
        dicionario.putBoolean("pesquisa", true);
        dicionario.putString("tela", "PesquisarHorarios");
        dicionario.putInt("curso", cursoAdapter.getItem(cursoPos).getId());
        dicionario.putInt("semestre", semestreAdapter.getItem(semestrePos).getId());
        dicionario.putInt("periodo", periodoAdapter.getItem(periodoPos).getNum());
        dicionario.putInt("sala", salaAdapter.getItem(salaPos).getId());
        dicionario.putString("turno", turnoAdapter.getItem(turnoPos).toString());
        dicionario.putString("dia", diaAdapter.getItem(diaPos).toString());

        Intent resultado = new Intent();
        //Dicionario de dados
        resultado.putExtras(dicionario);
        //Seta o valor do resultado e os que serao retornados
        this.setResult(Activity.RESULT_OK, resultado);
        //volta para a activity anterior
        this.finish();
    }

    //metodos que carregam os arraylists
    private void loadCursos(){
        this.cursos = new ArrayList<Curso>();

        //auxiliares
        CursoC findCursos = new CursoC(getApplicationContext());
        ArrayList<Curso> auxCursos =  findCursos.listar();
        Curso aux = new Curso();

        //cria um objeto com id 0 para descricao;
        aux.setId(0);
        aux.setNome("CURSO");
        cursos.add(aux);

        //foreach para preencher
        for(Curso array: auxCursos){
            cursos.add(array);
        }
    }

    private void loadSemestres() throws ParseException {
        this.semestres = new ArrayList<Semestre>();

        //auxiliares
        SemestreC findSemestres = new SemestreC(getApplicationContext());
        ArrayList<Semestre> auxSemestre =  findSemestres.listar();
        Semestre aux = new Semestre();

        //cria um objeto com id 0 para descricao;
        aux.setId(0);
        aux.setDescricao("SEMESTRE");
        semestres.add(aux);

        //foreach para preencher
        for(Semestre array: auxSemestre){
            semestres.add(array);
        }
    }

    private void loadPeriodos(){
        this.periodos = new ArrayList<Periodo>();

        Periodo aux = new Periodo();
        aux.setDescricao("PERIODO");
        aux.setNum(-1);
        this.periodos.add(aux);

        aux = new Periodo();
        aux.setDescricao("Especial");
        aux.setNum(0);
        this.periodos.add(aux);

        for(int i = 1; i <= 10; i++ ){
            aux = new Periodo();
            aux.setNum(i);
            aux.setDescricao(i+"° Periodo");
            periodos.add(aux);
        }
    }

    private void loadSalas(){
        this.salas = new ArrayList<Sala>();

        //auxiliares
        SalaC findSalas = new SalaC(getApplicationContext());
        ArrayList<Sala> auxSalas =  findSalas.listar();
        Sala aux = new Sala();

        //cria um objeto com id 0 para descricao;
        aux.setId(0);
        aux.setNome("SALA");
        salas.add(aux);

        //foreach para preencher
        for(Sala array: auxSalas){
            salas.add(array);
        }
    }

    private void loadTurnos(){
        this.turnos = new ArrayList<String>();
        this.turnos.add("TURNO");
        this.turnos.add("Matutino");
        this.turnos.add("Vespertino");
        this.turnos.add("Noturno");
    }

    private void loadDiassemana(){
        this.diasSemana = new ArrayList<String>();
        this.diasSemana.add("DIA");
        this.diasSemana.add("Segunda-Feira");
        this.diasSemana.add("Terça-Feira");
        this.diasSemana.add("Quarta-Feira");
        this.diasSemana.add("Quinta-Feira");
        this.diasSemana.add("Sexta-Feira");
        this.diasSemana.add("Sábado");
    }

    //metodos que criam os adapters
    private void getadapCursos() {
        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        cursoAdapter = new ArrayAdapter<Curso>(this, android.R.layout.simple_spinner_dropdown_item, cursos);
        cursoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cursoCombo.setAdapter(cursoAdapter);
        cursoAdapter.notifyDataSetChanged();
        this.cursoPos = 0;
    }

    private void getadapSemestres() {
        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        semestreAdapter = new ArrayAdapter<Semestre>(this, android.R.layout.simple_spinner_dropdown_item, semestres);
        semestreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        semestreCombo.setAdapter(semestreAdapter);
        semestreAdapter.notifyDataSetChanged();
        this.semestrePos = 0;
    }

    private void getadapPeriodos() {
        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        periodoAdapter = new ArrayAdapter<Periodo>(this, android.R.layout.simple_spinner_dropdown_item, periodos);
        periodoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        periodoCombo.setAdapter(periodoAdapter);
        periodoAdapter.notifyDataSetChanged();
        this.periodoPos = 0;
    }

    private void getadapSalas() {
        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        salaAdapter = new ArrayAdapter<Sala>(this, android.R.layout.simple_spinner_dropdown_item, salas);
        salaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        salaCombo.setAdapter(salaAdapter);
        salaAdapter.notifyDataSetChanged();
        this.salaPos = 0;
    }

    private void getadapTurnos() {
        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        turnoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, turnos);
        turnoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        turnoCombo.setAdapter(turnoAdapter);
        turnoAdapter.notifyDataSetChanged();
        this.turnoPos = 0;
    }

    private void getadapDiasemana() {
        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        diaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, diasSemana);
        diaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        diaCombo.setAdapter(diaAdapter);
        diaAdapter.notifyDataSetChanged();
        this.diaPos = 0;
    }

}
