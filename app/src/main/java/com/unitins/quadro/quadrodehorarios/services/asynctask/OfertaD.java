package com.unitins.quadro.quadrodehorarios.services.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.unitins.quadro.quadrodehorarios.controllers.AlocacaoSalaC;
import com.unitins.quadro.quadrodehorarios.controllers.CriptografiaC;
import com.unitins.quadro.quadrodehorarios.controllers.OfertaC;
import com.unitins.quadro.quadrodehorarios.models.AlocacaoSala;
import com.unitins.quadro.quadrodehorarios.models.Oferta;
import com.unitins.quadro.quadrodehorarios.models.modelos.ModeloAlocacao;
import com.unitins.quadro.quadrodehorarios.views.activity.HorarioPesquisa;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by savio on 19/02/2018.
 */

public class OfertaD extends AsyncTask<String, Void, Void> {

    //Elementos que guardarão as referencias dos objetos da activity
    private HorarioPesquisa copia = null;

    //variaveis de controle do progress dialog
    private ProgressDialog barraProgresso = null;
    public int stop;
    private String msg = null;
    private String endereco = null;

    //chave de acesso
    private CriptografiaC chave = null;

    //variavel de parametro
    private ArrayList<Integer> parametros = null;

    //classes utilizadas
    private HttpClient client = null;
    private HttpPost post = null;
    private List<NameValuePair> parampost = null;
    private Gson vrGson = null;

    //modelo
    private ModeloAlocacao alocacoes = null;

    //construtor
    public OfertaD(HorarioPesquisa contexto, int semestre, int curso, int periodo, int sala, int turno, int dia){
        this.copia = contexto;
        this.stop = 0;
        this.endereco = "https://alocacaosalas.unitins.br/getAlocacao.php";
        //parametros
        this.parametros = new ArrayList<>();
        parametros.add(semestre);
        parametros.add(curso);
        parametros.add(periodo);
        parametros.add(sala);
        parametros.add(turno);
        parametros.add(dia);
        //chave
        this.chave = new CriptografiaC(contexto.getApplicationContext());
    }

    @Override
    public void onPreExecute() {
        // Prepara para a execução
        // Cria o ProgressDialog
        barraProgresso = new ProgressDialog(this.copia);
        // Seta o titulo
        barraProgresso.setTitle("Dados do Aplicativo.");
        // Seta a mensagem
        barraProgresso.setMessage("Baixando dados");
        // Nao permite cancelar a barra de progresso
        barraProgresso.setCancelable(false);
        // Seta a inderterminação
        barraProgresso.setIndeterminate(false);
        // Mostra o ProgressDialog
        barraProgresso.show();

    }

    @Override
    protected Void doInBackground(String... strings) {
        //executa
        //biblioteca que transformará o texto json em objeto
        vrGson = new Gson();
        //seta a chave que será utilizada
        parampost = new ArrayList<NameValuePair>();
        parampost.add(new BasicNameValuePair("hash", chave.findById(1).getChave()));
        try{
            //verifica os parametros que não vieram zerados, adicionando eles ao post
            if(parametros.get(0) != 0) parampost.add(new BasicNameValuePair("semestre", parametros.get(0).toString()));
            if(parametros.get(1) != 0) parampost.add(new BasicNameValuePair("curso", parametros.get(1).toString()));
            if(parametros.get(2) != -1) parampost.add(new BasicNameValuePair("periodo", parametros.get(2).toString()));
            if(parametros.get(3) != 0) parampost.add(new BasicNameValuePair("sala", parametros.get(3).toString()));
            if(parametros.get(4) != 0) parampost.add(new BasicNameValuePair("turno",parametros.get(4).toString()));
            if(parametros.get(5) != 0) parampost.add(new BasicNameValuePair("dia", parametros.get(5).toString()));

            //cria o cliente que fara a requisição http
            client = new DefaultHttpClient();
            //baixa o conteudo do webservice (bytes)
            post = new HttpPost(endereco);
            //coloca os paraemtros post na requisição
            post.setEntity(new UrlEncodedFormEntity(parampost));
            //cria o response handler para auxiliar na requisição
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            //converte os bytes para String
            String response = client.execute(post,responseHandler);
            //transforma a string em objeto json
            alocacoes = vrGson.fromJson(response, ModeloAlocacao.class);
            stop = 1;
        } catch (Exception e){
            msg = e.getMessage();
            stop = -1;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        //pós-execução
        super.onPostExecute(result);
        if(stop == 1){
            //salva no banco de dados
            Toast.makeText(copia, alocacoes.getMensagem(), Toast.LENGTH_LONG).show();
            if(alocacoes.isStatus()){
                //se vier dados, ele irá finalizar a intent
                salvarBanco();
                finalizar();
            }
            copia.finalizarIntent();
        }
        if(stop == -1){
            finalizar();
            Toast.makeText(copia, "Atenção: Dados não baixados.\nMotivo: "+msg, Toast.LENGTH_LONG).show();
        }
    }

    //metodo que fará as consultas no banco
    private void salvarBanco(){
        //primeiro insere as ofertas, depois as alocaçoes
        //se identificar o id insere um novo, se não ele atualiza
        //ordem: oferta e alocacaosala
        OfertaC vrOferta = new OfertaC(copia);
        Oferta auxoferta = null;
        for (Oferta aux: alocacoes.getOfer()){

            auxoferta = vrOferta.findById(aux.getId());
            if(auxoferta == null)
                vrOferta.inserir(aux);
            else
                vrOferta.atualizar(aux);


        }
        AlocacaoSalaC vrAlocacao = new AlocacaoSalaC(copia);
        AlocacaoSala auxaloc = null;
        for (AlocacaoSala aux: alocacoes.getAloc()){
            try {
                auxaloc = vrAlocacao.findById(aux.getId());
                if(auxaloc == null)
                    vrAlocacao.inserir(aux);
                else
                    vrAlocacao.atualizar(aux);
            } catch (ParseException e) {
                Toast.makeText(copia, "Atenção: "+e.getMessage(), Toast.LENGTH_LONG).show();;
            }
        }
    }

    private void finalizar(){
        barraProgresso.dismiss();
    }

}
