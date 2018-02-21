package com.unitins.quadro.quadrodehorarios.views.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.unitins.quadro.quadrodehorarios.R;
import com.unitins.quadro.quadrodehorarios.services.ConexaoTestador;
import com.unitins.quadro.quadrodehorarios.services.Datas;
import com.unitins.quadro.quadrodehorarios.models.Noticia;
import com.unitins.quadro.quadrodehorarios.views.activity.NoticiaDetalhes;
import com.unitins.quadro.quadrodehorarios.services.asynctask.NoticiasD;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNews extends Fragment {

    //componentes visuais
    private ListView lista = null;

    //classe auxiliares
    private ConexaoTestador conecta = null;
    private NoticiasD baixaNoticias =  null;

    //variaives de conontrole
    private boolean carregou;
    private Boolean onShow;
    private int ofset;

    //lista para o list view
    private ArrayList<Noticia> noticias;
    private Datas datas;

    public FragmentNews() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_news, container, false);

        //iniciaulizacão de variaveis
        conecta = new ConexaoTestador();
        datas = new Datas();

        ofset = 0;
        noticias = new ArrayList<>();
        lista = (ListView) result.findViewById(R.id.news_lista);
        //on scroll para fazer o carregamento dinamico
        lista.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //deixa nulo
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //verifica se o ultimo item está visivel
                //se estiver ele ira chamar novamente o metodo da asynctask
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount){
                    if(carregou){
                        carregaWeb();
                    }
                }
            }
        });
        //evento click
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    carregaDetalhes(position);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        carregou = false;
        onShow = false;
        this.setRetainInstance(true);
        return result;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            if(!this.carregou && !this.onShow){
                this.onShow = true;
                carregaWeb();
            }
        }
        if(!isVisibleToUser ){
            onShow = false;
        }
    }

    //metodo que vai carregar da web
    private void carregaWeb(){
        if (conecta.testaRede(this.getActivity())){
            //so marca como carregado caso haja conexão
            //cria uma nova asynctask
            baixaNoticias = new NoticiasD(this, lista,noticias);
            //chama o asynctask com a URL
            baixaNoticias.execute("https://www.unitins.br/webapi/api/noticia/?offset="+ofset);
        } else {
            //se não houver conexão, ele retorna um toast e nao carrega a lista
            if(noticias.isEmpty())
                carregou=false;

            Toast.makeText(this.getActivity(), "Atenção: sem conexão com a internet!", Toast.LENGTH_SHORT).show();
        }
    }

    //metodo que irá carregar a intent com os dados
    private void carregaDetalhes(int pos) throws ParseException {
        //Dicionario de dados
        Bundle dicionario = new Bundle();
        dicionario.putString("titulo",noticias.get(pos).getTitulo() );
        dicionario.putString("autor",noticias.get(pos).getAutor());
        dicionario.putString("criacaoD", datas.getData2String(noticias.get(pos).getDataCriacao()));
        dicionario.putString("criacaoH", datas.getData2StringHora(noticias.get(pos).getDataCriacao()));
        dicionario.putString("texto", noticias.get(pos).getTexto());

        //cria uma intenção e solicita a troca de telas
        Intent solicitacao = new Intent(getActivity(), NoticiaDetalhes.class);
        //coloca os dados na intent
        solicitacao.putExtras(dicionario);
        //Dicionario de dados
        startActivityForResult(solicitacao, 1);
    }

    //
    public void setCarregou(boolean carregou) {
        this.carregou = carregou;
    }

    public int getOfset() { return ofset; }

    public void setOfset(int ofset) {
        this.ofset = ofset;
    }

}
