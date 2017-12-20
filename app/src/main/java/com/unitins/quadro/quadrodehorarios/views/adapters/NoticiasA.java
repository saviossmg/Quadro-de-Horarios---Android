package com.unitins.quadro.quadrodehorarios.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.unitins.quadro.quadrodehorarios.R;
import com.unitins.quadro.quadrodehorarios.services.Datas;
import com.unitins.quadro.quadrodehorarios.models.Noticia;
import com.unitins.quadro.quadrodehorarios.views.activity.Principal;

import java.util.ArrayList;

/**
 * Created by savio on 22/10/2017.
 */

public class NoticiasA extends ArrayAdapter<Noticia> {

    Principal copia = null;
    Datas datas;

    public NoticiasA (Principal contexto, ArrayList<Noticia> vetNoticias){
        super(contexto, 0, vetNoticias);
        copia = contexto;
        datas = new Datas();
    }

    public View getView(int posicao, View celulaReciclado, ViewGroup pai){
        //CRIAR UMA CELULA PARA CADA ELEMENTO DO VETOR DE CARROS
        //PREENCHER ESSA CELULA COM OS DADOS DO VETOR DE NOTICIAS NA POSICAO posicao
        Noticia c = this.getItem(posicao);

        if(celulaReciclado == null)
        {
            celulaReciclado = LayoutInflater.from(getContext()).inflate(R.layout.celula_noticia, pai,false);
        }

        //pegar os dados do objeto e setar os elementos visuais da celula
        TextView titulo = (TextView)celulaReciclado.findViewById(R.id.celnoticia_titulo);
        TextView subtitulo = (TextView)celulaReciclado.findViewById(R.id.celnoticia_subtitulo);
        TextView datapub = (TextView)celulaReciclado.findViewById(R.id.celnoticia_datapub);
        TextView dataatt = (TextView)celulaReciclado.findViewById(R.id.celnoticia_dataatt);

        //seta os dados
        try {
            titulo.setText(c.getTitulo());
            if(c.getSubTitulo().equals("null"))
                subtitulo.setText("-");
            else
                subtitulo.setText(c.getSubTitulo());

            datapub.setText("Publicada em "+datas.getData2String(c.getDataPublicacao())+" às "+datas.getData2StringHora(c.getDataPublicacao()));

            if(c.getDataAtualizacao() == null)
                dataatt.setText("Sem Atualização.");
            else
                dataatt.setText("Atualizada em "+datas.getData2String(c.getDataAtualizacao())+" às "+ datas.getData2StringHora(c.getDataAtualizacao()));

        } catch (Exception e) {
            Toast.makeText(copia,"Atenção: "+e.getMessage(), Toast.LENGTH_LONG);
            e.printStackTrace();
        }

        return celulaReciclado;

    }


}
