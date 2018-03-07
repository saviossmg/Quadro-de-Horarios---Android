package com.unitins.quadro.quadrodehorarios.views.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.unitins.quadro.quadrodehorarios.R;

public class HorarioDetalhes extends AppCompatActivity {

    private TextView sala = null;
    private TextView semestre = null;
    private TextView curso = null;
    private TextView periodo = null;
    private TextView dia = null;
    private TextView horario1 = null;
    private TextView intervalo = null;
    private TextView horario2 = null;
    private TextView professor = null;
    private TextView local = null;
    private TextView turno = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario_detalhes);
        getSupportActionBar().setTitle("Detalhes do Horário");

        sala = (TextView) findViewById(R.id.dethor_sala);
        semestre = (TextView) findViewById(R.id.dethor_semestre);
        curso = (TextView) findViewById(R.id.dethor_curso);
        periodo = (TextView) findViewById(R.id.dethor_periodo);
        dia = (TextView) findViewById(R.id.dethor_dia);
        turno = (TextView) findViewById(R.id.dethor_turno);
        horario1 = (TextView) findViewById(R.id.dethor_horario1);
        intervalo = (TextView) findViewById(R.id.dethor_intervalo);
        horario2 = (TextView) findViewById(R.id.dethor_horario2);
//        professor = (TextView) findViewById(R.id.dethor_professor);
        local = (TextView) findViewById(R.id.dethor_local);

        //pega o valor da itent
        Intent dados = getIntent();

        //seta os cammpos
        sala.setText(dados.getExtras().getString("sala"));

        semestre.setText(dados.getExtras().getString("semestre"));
        curso.setText(dados.getExtras().getString("curso"));

        String per = null;
        if(dados.getExtras().getInt("periodo") == 0)
            per = "Oferta Especial";
        else
            per = dados.getExtras().getInt("periodo")+"º Período";

        periodo.setText(per);

        dia.setText(dados.getExtras().getString("dia"));
        turno.setText(dados.getExtras().getString("turno"));
        horario1.setText(dados.getExtras().getString("horario1"));

        if(dados.getExtras().getString("intervalo") == null)
            intervalo.setText("Sem Intervalo.");
        else
            intervalo.setText(dados.getExtras().getString("intervalo"));

        if(dados.getExtras().getString("horario2") == null)
            horario2.setText("Sem 2º horário.");
        else
            horario2.setText(dados.getExtras().getString("horario2"));

//        professor.setText(dados.getExtras().getString("professor"));
        local.setText(dados.getExtras().getString("local"));

    }

    //sobrescreve o botão de voltar da barra
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Dicionario de dados
                Bundle dicionario = new Bundle();
                dicionario.putBoolean("voltar", false);
                dicionario.putString("tela", "HorarioDetalhes");
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

}
