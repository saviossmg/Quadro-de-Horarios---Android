package com.unitins.quadro.quadrodehorarios.views.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.unitins.quadro.quadrodehorarios.R;
import com.unitins.quadro.quadrodehorarios.controllers.AlocacaoSalaC;
import com.unitins.quadro.quadrodehorarios.controllers.CriptografiaC;
import com.unitins.quadro.quadrodehorarios.controllers.CursoC;
import com.unitins.quadro.quadrodehorarios.controllers.OfertaC;
import com.unitins.quadro.quadrodehorarios.controllers.PredioC;
import com.unitins.quadro.quadrodehorarios.controllers.SalaC;
import com.unitins.quadro.quadrodehorarios.controllers.SemestreC;
import com.unitins.quadro.quadrodehorarios.controllers.SemestreLetivoC;
import com.unitins.quadro.quadrodehorarios.controllers.UnidadeC;
import com.unitins.quadro.quadrodehorarios.models.AlocacaoSala;
import com.unitins.quadro.quadrodehorarios.models.Criptografia;
import com.unitins.quadro.quadrodehorarios.models.Curso;
import com.unitins.quadro.quadrodehorarios.models.Noticia;
import com.unitins.quadro.quadrodehorarios.models.Predio;
import com.unitins.quadro.quadrodehorarios.models.Sala;
import com.unitins.quadro.quadrodehorarios.models.Semestre;
import com.unitins.quadro.quadrodehorarios.models.SemestreLetivo;
import com.unitins.quadro.quadrodehorarios.models.Unidade;
import com.unitins.quadro.quadrodehorarios.services.ConexaoTestador;
import com.unitins.quadro.quadrodehorarios.services.asynctask.DadosD;
import com.unitins.quadro.quadrodehorarios.services.asynctask.NoticiasD;
import com.unitins.quadro.quadrodehorarios.views.activity.HorarioDetalhes;
import com.unitins.quadro.quadrodehorarios.views.activity.Principal;
import com.unitins.quadro.quadrodehorarios.views.activity.HorarioPesquisa;
import com.unitins.quadro.quadrodehorarios.views.adapters.HorariosA;

import java.io.UncheckedIOException;
import java.text.ParseException;
import java.util.ArrayList;

public class FragmentConsultas extends Fragment implements View.OnClickListener {

    //referencias visuais
    private ListView listaHorarios;
    private FloatingActionButton pesquisar;
    private FloatingActionButton recarregar;
    private FloatingActionButton sincronizar;
    private FloatingActionButton mostrar;

    //banco e id
    private static AlocacaoSalaC findAlocacao;
    private static OfertaC findOferta;

    //componentes de controle
    private AlocacaoSala alocacao;
    private ArrayList<AlocacaoSala> alocacoes;
    private Boolean refresh;

    //classe auxiliares
    private ConexaoTestador conecta = null;
    private DadosD baixaDados =  null;

    //variaveis de controle
    private boolean carregou;
    private boolean onShow;
    private boolean clickShow;

    private String mensagem;

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
        mostrar = (FloatingActionButton) view.findViewById(R.id.consulta_btnshow);

        conecta = new ConexaoTestador();

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
        mostrar.setOnClickListener(this);

        pesquisar.hide();
        recarregar.hide();
        sincronizar.hide();
        clickShow = false;

        carregou = false;
        onShow = false;

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
                Toast.makeText(getActivity(),mensagem,Toast.LENGTH_SHORT).show();
                break;
            case R.id.consulta_btnsincronizar:
                this.refresh = true;
                carregaWeb();
                listaCarregar();
                mudarLista();
                break;
            case R.id.consulta_btnshow:
                if(clickShow){
                    pesquisar.hide();
                    recarregar.hide();
                    sincronizar.hide();
                    mostrar.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                    mostrar.setImageResource(R.drawable.plus);
                    clickShow = false;
                }
                else{
                    pesquisar.show();
                    recarregar.show();
                    sincronizar.show();
                    mostrar.setBackgroundTintList(getResources().getColorStateList(R.color.rosa));
                    mostrar.setImageResource(R.drawable.minus);
                    clickShow = true;
                }
                break;
        }
    }

    //metodo que vai carregar os dados de alocação
    private void listaCarregar(){
        try {
            //instancia o objeto
            ArrayList<AlocacaoSala> aux = new ArrayList<>();
            alocacoes = new ArrayList<AlocacaoSala>();
            aux = findAlocacao.listar(false,0,0,-1,0,0,0, this.refresh);
            if(!aux.isEmpty()){
                alocacoes = aux;
                mensagem = "Lista de Alocações recarregada com sucesso!";
            }
            else{
                mensagem = "Lista de Alocações vazias.";
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Erro em Lista: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    //metodo que vai carregar os dados de alocação
    public void carregarListaExterno(Boolean pesquisa,int curso, int semestre, int periodo,int sala, int turno, int dia){
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
        dicionario.putString("dia",alocacoes.get(pos).getOferta().getDiasemana());
        dicionario.putString("turno",alocacoes.get(pos).getOferta().getTurno());
        dicionario.putString("horario1",alocacoes.get(pos).getOferta().getHorainiciala()+" às "+alocacoes.get(pos).getOferta().getHorafinala());
        //verifica se é um horario só
        if(alocacoes.get(pos).getOferta().getIntervaloinicio() == null)
            dicionario.putString("intervalo",null);
        else
            dicionario.putString("intervalo",alocacoes.get(pos).getOferta().getIntervaloinicio()+" às "+alocacoes.get(pos).getOferta().getIntervalofim());

        //verifica se é um horario só
        if(alocacoes.get(pos).getOferta().getHorainicialb() == null)
            dicionario.putString("horario2",null);
        else
            dicionario.putString("horario2",alocacoes.get(pos).getOferta().getHorainicialb()+" às "+alocacoes.get(pos).getOferta().getHorafinalb());

        dicionario.putString("professor",alocacoes.get(pos).getOferta().getProfessor());
        dicionario.putString("local",alocacoes.get(pos).getSala().getNome()+", "+alocacoes.get(pos).getSala().getPredio().getNome()+" - "+alocacoes.get(pos).getSala().getPredio().getUnidade().getNome());

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

    //uso do web service
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            if(!this.carregou && !this.onShow){
                this.onShow = true;
                carregaPrimeira();
            }
        }
        if(!isVisibleToUser ){
            if(mostrar != null){
                pesquisar.hide();
                recarregar.hide();
                sincronizar.hide();
                mostrar.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                mostrar.setImageResource(R.drawable.plus);
            }
            clickShow = false;
            onShow = false;
        }
    }

    //metodo que vai carregar da web os dados basicos pela primeira vez, sempre verificará primeiro se há dados já salvos
    private void carregaPrimeira()  {
        //verifica se tem algum dos retornos está vazio
        UnidadeC checaUnidade = new UnidadeC(this.getContext());
        PredioC checaPredio = new PredioC(this.getContext());
        SalaC checaSala = new SalaC(this.getContext());
        CursoC checaCurso = new CursoC(this.getContext());
        SemestreC checaSeme = new SemestreC(this.getContext());
        SemestreLetivoC checaSemelet = new SemestreLetivoC(this.getContext());

        //se houver algum dado vazio, irá fazer o donwload.
        try {
            if(checaUnidade.listar().isEmpty() || checaPredio.listar().isEmpty() || checaSala.listar().isEmpty() ||
                    checaCurso.listar().isEmpty() || checaSeme.listar().isEmpty() || checaSemelet.listar().isEmpty()){
                //chama o metodo
                carregaWeb();
            }
            else{
                this.carregou = true;
            }
        } catch (ParseException e) {
            Toast.makeText(this.getActivity(), "Atenção: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Chama a asynctaks responsavel
    private void carregaWeb(){
        if (conecta.testaRede(this.getActivity())){
            ArrayList<Unidade> u = new ArrayList<>();
            //so marca como carregado caso haja conexão
            //cria uma nova asynctask,.
            baixaDados = new DadosD(this);
            //chama o asynctask com a URL
            baixaDados.execute();
        } else {
            //se não houver conexão, ele retorna um toast e nao carrega a lista
            Toast.makeText(this.getActivity(), "Sem conexão com a internet!", Toast.LENGTH_SHORT).show();
        }

    }

    public void setCarregou(boolean carregou) {
        this.carregou = carregou;
    }

}