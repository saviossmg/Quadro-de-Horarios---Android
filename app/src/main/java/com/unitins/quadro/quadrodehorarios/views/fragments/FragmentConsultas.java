package com.unitins.quadro.quadrodehorarios.views.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.unitins.quadro.quadrodehorarios.R;
import com.unitins.quadro.quadrodehorarios.controllers.AlocacaoSalaC;
import com.unitins.quadro.quadrodehorarios.controllers.OfertaC;
import com.unitins.quadro.quadrodehorarios.models.AlocacaoSala;
import com.unitins.quadro.quadrodehorarios.views.activity.HorarioDetalhes;
import com.unitins.quadro.quadrodehorarios.views.activity.Principal;
import com.unitins.quadro.quadrodehorarios.views.activity.HorarioPesquisa;
import com.unitins.quadro.quadrodehorarios.views.adapters.HorariosA;

import java.util.ArrayList;

public class FragmentConsultas extends Fragment implements View.OnClickListener {

    //referencias visuais
    private ListView listaHorarios;
    private FloatingActionButton pesquisar;
    private FloatingActionButton recarregar;
    private FloatingActionButton sincronizar;

    //banco e id
    private static AlocacaoSalaC findAlocacao;
    private static OfertaC findOferta;

    //componentes de controle
    private AlocacaoSala alocacao;
    private ArrayList<AlocacaoSala> alocacoes;
    private Boolean refresh;

    public FragmentConsultas() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultas, container, false);

        //referencia visual
        listaHorarios = (ListView) view.findViewById(R.id.consultas_listageral);
        pesquisar = (FloatingActionButton) view.findViewById(R.id.consulta_btnpesquisar);
        recarregar = (FloatingActionButton) view.findViewById(R.id.consulta_btnrecarregar);
        sincronizar = (FloatingActionButton) view.findViewById(R.id.consulta_btnsincronizar);

        this.refresh = false;

        //carrega os dados da alocação
        listaCarregar();
        if (!alocacoes.isEmpty()) {
            //listview
            mudarLista();
        }

        listaHorarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                carregaDetalhes(position);
            }
        });

        //evento onclick
        pesquisar.setOnClickListener(this);
        recarregar.setOnClickListener(this);
        sincronizar.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        //eventos de click
        switch (v.getId()) {
            case R.id.consulta_btnpesquisar:
                this.refresh = true;
                consultarHorarios();
                break;
            case  R.id.consulta_btnrecarregar:
                this.refresh = true;
                listaCarregar();
                mudarLista();
                Toast.makeText(getActivity(),"Lista recarregada com sucesso!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.consulta_btnsincronizar:
                this.refresh = true;
                Toast.makeText(getActivity(),"Dados sincronizados!",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //metodo que vai carregar os dados de alocação
    private void listaCarregar(){
        try {
            //instancia o objeto
            alocacoes = new ArrayList<AlocacaoSala>();
            alocacoes = findAlocacao.listar(false,0,0,0,0,null,null, this.refresh);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Erro em Lista: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    //metodo que vai carregar os dados de alocação
    public void carregarListaExterno(Boolean pesquisa,int curso, int semestre, int periodo,int sala, String turno, String dia){
        try {
            //instancia o objeto
            alocacoes = new ArrayList<AlocacaoSala>();
            alocacoes = findAlocacao.listar(pesquisa,curso,semestre,periodo,sala,turno,dia, this.refresh);
            mudarLista();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Erro em Lista: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    //modifica o adapter para a celula personalizada
    private void mudarLista() {
        HorariosA adapt = new HorariosA((Principal) getActivity(),(ArrayList<AlocacaoSala>)alocacoes);
        this.listaHorarios.setAdapter(adapt);
    }

    //Evento do botão de consultas
    private void consultarHorarios() {
        //cria uma intenção e solicita a troca de telas
        Intent solicitacao = new Intent(getActivity(), HorarioPesquisa.class);
        //Dicionario de dados
        startActivityForResult(solicitacao, 1);
    }

    //metodo que irá carregar a intent com os dados
    private void carregaDetalhes(int pos){
        //Dicionario de dados
        Bundle dicionario = new Bundle();
        dicionario.putString("sala",alocacoes.get(pos).getOferta().getDisciplina());
        dicionario.putString("semestre",alocacoes.get(pos).getSemestre().getSemestre().getDescricao());
        dicionario.putString("curso",alocacoes.get(pos).getSemestre().getCurso().getNome());
        dicionario.putInt("periodo",alocacoes.get(pos).getOferta().getPeriodo());
        dicionario.putString("dia",alocacoes.get(pos).getOferta().getDiaSemana());
        dicionario.putString("turno",alocacoes.get(pos).getOferta().getTurno());
        dicionario.putString("horario1",alocacoes.get(pos).getOferta().getHoraInicialA()+" às "+alocacoes.get(pos).getOferta().getHoraFinalA());
        //verifica se é um horario só
        if(alocacoes.get(pos).getOferta().getIntervaloInicio() == null)
            dicionario.putString("intervalo",null);
        else
            dicionario.putString("intervalo",alocacoes.get(pos).getOferta().getIntervaloInicio()+" às "+alocacoes.get(pos).getOferta().getIntervaloFim());

        //verifica se é um horario só
        if(alocacoes.get(pos).getOferta().getHoraInicialB() == null)
            dicionario.putString("horario2",null);
        else
            dicionario.putString("horario2",alocacoes.get(pos).getOferta().getHoraInicialB()+" às "+alocacoes.get(pos).getOferta().getHoraFinalB());

        dicionario.putString("professor",alocacoes.get(pos).getOferta().getProfessor());
        dicionario.putString("local",alocacoes.get(pos).getSala().getNome()+", "+alocacoes.get(pos).getSala().getPredio().getNome());

        //cria uma intenção e solicita a troca de telas
        Intent solicitacao = new Intent(getActivity(), HorarioDetalhes.class);
        //coloca os dados na intent
        solicitacao.putExtras(dicionario);
        //Dicionario de dados
        startActivityForResult(solicitacao, 1);
    }


    public static void validaBanco(AlocacaoSalaC paramAloc, OfertaC paramOferta) {
        findAlocacao = paramAloc;
        findOferta = paramOferta;
    }

}