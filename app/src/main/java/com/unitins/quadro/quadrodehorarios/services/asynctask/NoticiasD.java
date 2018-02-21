package com.unitins.quadro.quadrodehorarios.services.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.unitins.quadro.quadrodehorarios.services.Datas;
import com.unitins.quadro.quadrodehorarios.models.Noticia;
import com.unitins.quadro.quadrodehorarios.views.activity.Principal;
import com.unitins.quadro.quadrodehorarios.views.adapters.NoticiasA;
import com.unitins.quadro.quadrodehorarios.views.fragments.FragmentNews;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by savio on 25/10/2017.
 */

public class NoticiasD extends AsyncTask<String, Void, ArrayList<Noticia>> {

    //Elementos que guardarão as referencias dos objetos da fragment
    private FragmentNews copia = null;
    private ListView copiaLista = null;
    ArrayList<Noticia> copiaNoticias = null;

    private ProgressDialog barraProgresso = null;
    private Datas datas;

    public int stop;
    private String msg = null;

    private Boolean carregou;
    private int ofset;

    //total de noticias p/ manipulação da lista
    private int totalNoticias;

    //construtor que pegara as referencias visuais da activity
    public NoticiasD(FragmentNews contexto, ListView lista, ArrayList<Noticia> noiticas) {
        this.copia = contexto;
        this.copiaLista = lista;
        this.datas = new Datas();
        this.copiaNoticias = noiticas;
        this.totalNoticias = copiaNoticias.size();
        this.stop = 0;
    }

    //prepara para a execução da thread
    @Override
    public void onPreExecute() {
        // Cria o ProgressDialog
        barraProgresso = new ProgressDialog(this.copia.getActivity());
        // Seta o titulo
        barraProgresso.setTitle("Noticias da Unitins");
        // Seta a mensagem
        barraProgresso.setMessage("Carregando...");
        // Nao permite cancelar a barra de progresso
        barraProgresso.setCancelable(false);
        // Seta a inderterminação
        barraProgresso.setIndeterminate(false);
        // Mostra o ProgressDialog
        barraProgresso.show();
    }

    @Override
    protected ArrayList<Noticia> doInBackground(String... params) {
        tentaBaixar(params[0]);
        return null;
    }

    private void tentaBaixar(String param) {
        //lista de noticias que será retornada
        //objeto auxiliar
        Noticia noticia = null;
        //String responsavel pela resposta
        String xml = null;
        //objeto e array json necessarios
        JSONObject aux;
        JSONArray objetos;
        try {
            //carregará p paginas de acordo com os parametros
            //cria a URL
            URL url = new URL(param);
            //Cria a requisição HTTP
            HttpClient client = new DefaultHttpClient();
            //padrão GET
            HttpGet request = new HttpGet(url.toString());
            // pega a resposta
            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            xml = client.execute(request, responseHandler);

            //cria um jsonarray
            objetos = new JSONArray(xml);
            for (int i = 0; i < objetos.length(); i++) {
                //instancia um objeto auxiliar
                aux = objetos.getJSONObject(i);
                noticia = new Noticia();
                noticia.setId(aux.getInt("id"));
                noticia.setTitulo(aux.getString("titulo"));
                noticia.setSubTitulo(aux.getString("subTitulo"));
                noticia.setTexto(aux.getString("texto"));
                noticia.setAutor(aux.getString("autor"));
                noticia.setPalavrasChave(aux.getString("palavrasChave"));
                noticia.setDataPublicacao(datas.getString2Data2(aux.getString("dataPublicacao")));
                noticia.setDataCriacao(datas.getString2Data2(aux.getString("dataCriacao")));

                // as vezes a data de atualização vem nula, e o date nao aceita valores nulos
                if (!aux.getString("dataAtualizacao").contains("null")) {
                    noticia.setDataAtualizacao(datas.getString2Data2(aux.getString("dataAtualizacao")));
                }
                //adiciona ao array
                copiaNoticias.add(noticia);
            }
            //ok
            stop = 1;
        } catch (Exception e) {
            //error
            msg = e.getMessage();
            stop = -1;
        }
    }

    //Thread finalizada (codigo executado na Thread principal (interface))
    public void onPostExecute(ArrayList<Noticia> noticias) {
        //desgaz a barra de progresso
        barraProgresso.dismiss();
        if (stop == 1) {
            NoticiasA adapt = new NoticiasA((Principal) copia.getActivity(), copiaNoticias);
            copiaLista.setAdapter(adapt);
            //deixa a lista aonde ela estava antes de recarregar
            //caso seja a primeira vez será 0 e nao fará essa operação
            if (totalNoticias > 0) {
                copiaLista.setSelection(totalNoticias - 1);
            }
            copia.setCarregou(true);
            copia.setOfset(copia.getOfset()+1);

        }
        if (stop == -1) {
            //se nao tiver salvo nada
            if(copiaNoticias.isEmpty())
                copia.setCarregou(false);
            Toast.makeText(copia.getActivity(), "Atenção: Noticias não carregadas.\nMotivo: "+msg, Toast.LENGTH_LONG).show();
        }
    }

}
