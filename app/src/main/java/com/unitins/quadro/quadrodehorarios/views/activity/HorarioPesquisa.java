package com.unitins.quadro.quadrodehorarios.views.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.unitins.quadro.quadrodehorarios.R;
import com.unitins.quadro.quadrodehorarios.controllers.AlocacaoSalaC;
import com.unitins.quadro.quadrodehorarios.controllers.CriptografiaC;
import com.unitins.quadro.quadrodehorarios.controllers.CursoC;
import com.unitins.quadro.quadrodehorarios.controllers.SalaC;
import com.unitins.quadro.quadrodehorarios.controllers.SemestreC;
import com.unitins.quadro.quadrodehorarios.models.AlocacaoSala;
import com.unitins.quadro.quadrodehorarios.models.Criptografia;
import com.unitins.quadro.quadrodehorarios.models.Curso;
import com.unitins.quadro.quadrodehorarios.models.Periodo;
import com.unitins.quadro.quadrodehorarios.models.Sala;
import com.unitins.quadro.quadrodehorarios.models.Semestre;
import com.unitins.quadro.quadrodehorarios.models.Unidade;
import com.unitins.quadro.quadrodehorarios.services.ConexaoTestador;
import com.unitins.quadro.quadrodehorarios.services.asynctask.DadosD;
import com.unitins.quadro.quadrodehorarios.services.asynctask.OfertaD;

import java.text.ParseException;
import java.util.ArrayList;

public class HorarioPesquisa extends AppCompatActivity implements View.OnClickListener {

    //referencias visuais
    private Spinner cursoCombo = null;
    private Spinner semestreCombo = null;
    private Spinner periodoCombo = null;
    private Spinner salaCombo = null;
    private Spinner turnoCombo = null;
    private Spinner diaCombo = null;
    private Button okBtn = null;

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
    private ArrayList<Integer> turnosId;
    private ArrayList<String> diasSemana;
    private ArrayList<Integer> diasId;

    //testador de conexao
    private ConexaoTestador conecta;
    private OfertaD baixaDados =  null;
    private AlocacaoSalaC alocacaoc = null;

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
        okBtn = (Button) findViewById(R.id.pesquisar_btnok);

        conecta = new ConexaoTestador();
        alocacaoc = new AlocacaoSalaC(this);

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

        //
        okBtn.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        //eventos de click
        try{
            switch (v.getId()) {
                case R.id.pesquisar_btnok:
                    clickOk();
                    break;
            }
        } catch (Exception e){
            Toast.makeText(this.getApplicationContext(), "Atenção: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    //metodo click
    public void clickOk() throws ParseException {
        //obriga pelo menos a selecionar o semestre
        if(semestreAdapter.getItem(semestrePos).getId() != 0){
            //verifica se tem alguim dado no banco
            ArrayList<AlocacaoSala> vrAloc = alocacaoc.listar(true,cursoAdapter.getItem(cursoPos).getId(),semestreAdapter.getItem(semestrePos).getId(),
                    periodoAdapter.getItem(periodoPos).getNum(),salaAdapter.getItem(salaPos).getId(),turnosId.get(turnoPos),
                    diasId.get(diaPos),false);

            if(vrAloc.isEmpty()){
                //chama a async task se retornar vazio
                if (conecta.testaRede(this.getApplication())){
                    //cria uma nova asynctask,.
                    baixaDados = new OfertaD(this,semestreAdapter.getItem(semestrePos).getId(),cursoAdapter.getItem(cursoPos).getId(),
                            periodoAdapter.getItem(periodoPos).getNum(), salaAdapter.getItem(salaPos).getId(),turnosId.get(turnoPos), diasId.get(diaPos));
                    //chama o asynctask com a URL
                    baixaDados.execute();
                } else {
                    //se não houver conexão, ele retorna um toast e nao carrega a lista
                    Toast.makeText(this.getApplicationContext(), "Sem conexão com a internet!", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                finalizarIntent();
            }
        }
        else{
            Toast.makeText(this.getApplicationContext(), "Selecione o semestre!", Toast.LENGTH_SHORT).show();
        }

    }

    //finaliza a intent
    public void finalizarIntent(){
        //Dicionario de dados
        Bundle dicionario = new Bundle();
        dicionario.putBoolean("voltar", false);
        dicionario.putBoolean("pesquisa", true);
        dicionario.putString("tela", "PesquisarHorarios");
        dicionario.putInt("curso", cursoAdapter.getItem(cursoPos).getId());
        dicionario.putInt("semestre", semestreAdapter.getItem(semestrePos).getId());
        dicionario.putInt("periodo", periodoAdapter.getItem(periodoPos).getNum());
        dicionario.putInt("sala", salaAdapter.getItem(salaPos).getId());
        dicionario.putInt("turno", turnosId.get(turnoPos));
        dicionario.putInt("dia", diasId.get(diaPos));
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
        aux.setDescricao("SEMESTRE - Obrigatório selecionar");
        semestres.add(aux);

        //foreach para preencher
        for(Semestre array: auxSemestre){
            semestres.add(array);
        }
    }

    private void loadPeriodos(){
        this.periodos = new ArrayList<Periodo>();

        Periodo aux = new Periodo();
        aux.setDescricao("PERÍODO");
        aux.setNum(-1);
        this.periodos.add(aux);

        aux = new Periodo();
        aux.setDescricao("Regularização/Especial");
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
        this.turnosId = new ArrayList<Integer>();
        this.turnos.add("TURNO");
        this.turnosId.add(0);
        this.turnos.add("Matutino");
        this.turnosId.add(9);
        this.turnos.add("Vespertino");
        this.turnosId.add(10);
        this.turnos.add("Noturno");
        this.turnosId.add(11);
    }

    private void loadDiassemana(){
        this.diasSemana = new ArrayList<String>();
        this.diasId = new ArrayList<Integer>();
        this.diasSemana.add("DIA");
        this.diasId.add(0);
        this.diasSemana.add("Segunda-Feira");
        this.diasId.add(13);
        this.diasSemana.add("Terça-Feira");
        this.diasId.add(14);
        this.diasSemana.add("Quarta-Feira");
        this.diasId.add(15);
        this.diasSemana.add("Quinta-Feira");
        this.diasId.add(16);
        this.diasSemana.add("Sexta-Feira");
        this.diasId.add(17);
        this.diasSemana.add("Sábado");
        this.diasId.add(18);
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
