package com.unitins.quadro.quadrodehorarios.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.unitins.quadro.quadrodehorarios.R;
import com.unitins.quadro.quadrodehorarios.models.AlocacaoSala;
import com.unitins.quadro.quadrodehorarios.views.activity.Principal;

import java.util.ArrayList;

/**
 * Created by savio on 22/10/2017.
 */

public class HorariosA extends ArrayAdapter<AlocacaoSala>{

    Principal copia = null;

    public HorariosA (Principal contexto, ArrayList<AlocacaoSala> vetHorarios){
        super(contexto, 0, vetHorarios);
        copia = contexto;
    }

    public View getView(int posicao, View celulaReciclado, ViewGroup pai){
        //CRIAR UMA CELULA PARA CADA ELEMENTO DO VETOR DE CARROS
        //PREENCHER ESSA CELULA COM OS DADOS DO VETOR DE CARROS NA POSICAO posicao
        AlocacaoSala c = this.getItem(posicao);

        if(celulaReciclado == null)
        {
            celulaReciclado = LayoutInflater.from(getContext()).inflate(R.layout.celula_horario, pai,false);
        }

        //pegar os dados do objeto e setar os elementos visuais da celula
        TextView dia = (TextView)celulaReciclado.findViewById(R.id.celhorario_dia);
        TextView local = (TextView)celulaReciclado.findViewById(R.id.celhorario_local);
        TextView curso = (TextView)celulaReciclado.findViewById(R.id.celhorario_curso);
        TextView disciplina = (TextView)celulaReciclado.findViewById(R.id.celhorario_disciplina);

        //seta os dados
        dia.setText(c.getOferta().getDiasemana());
        local.setText(c.getSala().getNome()+" - "+c.getSala().getPredio().getNome());
        String per = c.getOferta().getPeriodo()+"º Período";
        if(c.getOferta().getPeriodo() == 0)
            per = "Regularização";
        curso.setText(c.getOferta().getCurso().getNome()+" - "+per);
        disciplina.setText(c.getOferta().getDisciplina());

        return celulaReciclado;

    }


}
