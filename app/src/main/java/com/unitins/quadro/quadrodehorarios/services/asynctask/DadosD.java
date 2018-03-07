package com.unitins.quadro.quadrodehorarios.services.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
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
import com.unitins.quadro.quadrodehorarios.models.Curso;
import com.unitins.quadro.quadrodehorarios.models.Oferta;
import com.unitins.quadro.quadrodehorarios.models.Predio;
import com.unitins.quadro.quadrodehorarios.models.Sala;
import com.unitins.quadro.quadrodehorarios.models.Semestre;
import com.unitins.quadro.quadrodehorarios.models.SemestreLetivo;
import com.unitins.quadro.quadrodehorarios.models.Unidade;
import com.unitins.quadro.quadrodehorarios.models.modelos.ModeloAlocacao;
import com.unitins.quadro.quadrodehorarios.models.modelos.ModeloAlocacaoAtt;
import com.unitins.quadro.quadrodehorarios.models.modelos.ModeloCurso;
import com.unitins.quadro.quadrodehorarios.models.modelos.ModeloPredio;
import com.unitins.quadro.quadrodehorarios.models.modelos.ModeloSala;
import com.unitins.quadro.quadrodehorarios.models.modelos.ModeloSemestre;
import com.unitins.quadro.quadrodehorarios.models.modelos.ModeloSemestreLetivo;
import com.unitins.quadro.quadrodehorarios.models.modelos.ModeloUnidade;

import com.unitins.quadro.quadrodehorarios.views.activity.HorarioPesquisa;
import com.unitins.quadro.quadrodehorarios.views.fragments.FragmentConsultas;

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
 * Created by savio on 08/02/2018.
 */

public class DadosD extends AsyncTask<String, Void, Void> {

    //Elementos que guardarão as referencias dos objetos da fragment
    private FragmentConsultas copia = null;

    //variaveis de controle do progress dialog
    private ProgressDialog barraProgresso = null;
    public int stop;
    private String msg = null;
    private ArrayList<String> enderecos = null;
    private ArrayList<AlocacaoSala> listaAlocacao = null;
    private ArrayList<AlocacaoSala> listaAlocatt = null;
    private ArrayList<Oferta> listaOferta = null;

    //chave de acesso
    private CriptografiaC chave = null;

    //classes utilizadas
    private HttpClient client = null;
    private HttpPost post = null;
    private List<NameValuePair> parametros = null;
    private Gson vrGson = null;
    private AlocacaoSalaC alocc = null;

    private ModeloUnidade unidades = null;
    private ModeloPredio predios = null;
    private ModeloSala salas = null;
    private ModeloSemestre semestres = null;
    private ModeloSemestreLetivo semestresletivos = null;
    private ModeloCurso cursos = null;
    private ModeloAlocacao alocacoes = null;

    private String teste;

    //construtor que pegara as referencias visuais da activity
    public DadosD(FragmentConsultas contexto) {
        this.copia = contexto;
        this.stop = 0;
        //cria a lista de endereços
        this.enderecos = new ArrayList<>();
        this.enderecos.add("https://alocacaosalas.unitins.br/getUnidade.php");
        this.enderecos.add("https://alocacaosalas.unitins.br/getPredio.php");
        this.enderecos.add("https://alocacaosalas.unitins.br/getSala.php");
        this.enderecos.add("https://alocacaosalas.unitins.br/getSemestre.php");
        this.enderecos.add("https://alocacaosalas.unitins.br/getSemestreletivo.php");
        this.enderecos.add("https://alocacaosalas.unitins.br/getCurso.php");
        this.enderecos.add("https://alocacaosalas.unitins.br/attAlocacao.php");

        //chave
        this.chave = new CriptografiaC(contexto.getContext());

        //alocacoes - caso necessários
        this.listaAlocacao = new ArrayList<>();
        this.listaAlocatt = new ArrayList<>();
        this.listaOferta = new ArrayList<>();
        this.alocc = new AlocacaoSalaC(contexto.getContext());
    }

    //prepara para a execução da thread
    @Override
    public void onPreExecute() {
        // Cria o ProgressDialog
        barraProgresso = new ProgressDialog(this.copia.getActivity());
        // Seta o titulo
        barraProgresso.setTitle("Dados do Aplicativo.");
        // Seta a mensagem
        teste = "Baixando dados.";
        barraProgresso.setMessage(teste);
        // Nao permite cancelar a barra de progresso
        barraProgresso.setCancelable(false);
        // Seta a inderterminação
        barraProgresso.setIndeterminate(false);
        // Mostra o ProgressDialog
        barraProgresso.show();
    }

    @Override
    protected Void doInBackground(String... strings) {
        //biblioteca que transformará o texto json em objeto
        vrGson = new Gson();
        //seta a chave que será utilizada
        parametros = new ArrayList<NameValuePair>();
        parametros.add(new BasicNameValuePair("hash", chave.findById(1).getChave()));
        try{
            client = new DefaultHttpClient();
            //baixa o conteudo do webservice (bytes)
            for(int i = 0;i < enderecos.size();i++){
                post = new HttpPost(enderecos.get(i));
                //coloca os paraemtros post na requisição
                post.setEntity(new UrlEncodedFormEntity(parametros));
                //cria o response handler para auxiliar na requisição
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                //converte os bytes para String
                String response = client.execute(post,responseHandler);
                //Converte a String JSON em objetos do tipo indicado
                if(i==0){ unidades = vrGson.fromJson(response, ModeloUnidade.class); }
                if(i==1){ predios = vrGson.fromJson(response, ModeloPredio.class); }
                if(i==2){ salas = vrGson.fromJson(response, ModeloSala.class); }
                if(i==3){ semestres = vrGson.fromJson(response, ModeloSemestre.class); }
                if(i==4){ semestresletivos = vrGson.fromJson(response, ModeloSemestreLetivo.class); }
                if(i==5){ cursos = vrGson.fromJson(response, ModeloCurso.class); }
                if(i==6){
                    //se tiver alocacoes ele irá atualizar apenas as que estão no banco
                    listaAlocacao = alocc.listar(false,0,0,-1,0,0,0,true);
                    if(!listaAlocacao.isEmpty()){
                        ModeloAlocacaoAtt auxatt = null;
                        //se a lista não estiver vazia, irá baixar as alocacoes e adicionar ao modelo
                        for(AlocacaoSala a: listaAlocacao){
                            //limpa a lista de parametros e adiciona mais uma vez
                            parametros.clear();
                            parametros.add(new BasicNameValuePair("hash", chave.findById(1).getChave()));
                            parametros.add(new BasicNameValuePair("idalocacao", String.valueOf(a.getId())));
                            //coloca o paraemtro post na requisição
                            post.setEntity(new UrlEncodedFormEntity(parametros));
                            //executa a requisição e converte para string
                            response = client.execute(post,responseHandler);
                            auxatt = vrGson.fromJson(response, ModeloAlocacaoAtt.class);
                            listaAlocatt.add(auxatt.getAloc());
                            listaOferta.add(auxatt.getOfer());
                        }
                    }
                }
            }
            stop = 1;
        } catch (Exception e){
            msg = e.getMessage();
            stop = -1;
        }
        return null;
    }

    //Thread finalizada (codigo executado na Thread principal (interface))
    @Override
    protected void onPostExecute(Void result){
        super.onPostExecute(result);
        barraProgresso.dismiss();
        if(stop == 1){
            //salva no banco de dados
            try {
                salvarBanco();
                copia.setCarregou(true);
                Toast.makeText(copia.getActivity(), "Dados sincronizados com sucesso.", Toast.LENGTH_LONG).show();
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(copia.getActivity(), "Atenção: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        if(stop == -1){
            copia.setCarregou(false);
            Toast.makeText(copia.getActivity(), "Atenção: Dados não carregados.\nMotivo: "+msg, Toast.LENGTH_LONG).show();
        }
    }

    //metodo que vai salvar
    private void salvarBanco() throws ParseException {
        //verifica primeiro se existem registros
        //Caso não haja nada, ele ira inserir um novo, se não ele irá atualizar
        //na ordem: unidade, predio, sala, semestre, semestreletivo, curso e oferta (se tiver)
        UnidadeC unic = new UnidadeC(copia.getContext());
        Unidade auxUni = null;
        for (Unidade a: unidades.getData()){
            auxUni = unic.findById(a.getId());
            if(auxUni == null)
                //inserir
                unic.inserir(a);
            else
                //atualizar
                unic.atualizar(a);

        }
        //controler e objeto auxiliares
        PredioC predc = new PredioC(copia.getContext());
        Predio auxPred = null;
        for (Predio a: predios.getData()){
            auxPred = predc.findById(a.getId());
            if(auxPred == null)
                //inserir
                predc.inserir(a);
            else
                //atualizar
                predc.atualizar(a);

        }
        SalaC salac = new SalaC(copia.getContext());
        Sala auxSala = null;
        for (Sala a: salas.getData()){
            auxSala = salac.findById(a.getId());
            if(auxSala == null)
                //inserir
                salac.inserir(a);
            else
                //atualizar
                salac.atualizar(a);

        }
        SemestreC semec = new SemestreC(copia.getContext());
        Semestre auxSemestre = null;
        for (Semestre a: semestres.getData()){
            auxSemestre = semec.findById(a.getId());
            if(auxSemestre == null)
                //inserir
                semec.inserir(a);
            else
                //atualizar
                semec.atualizar(a);

        }
        SemestreLetivoC selec = new SemestreLetivoC(copia.getContext());
        SemestreLetivo auxSemestreletivo = null;
        for (SemestreLetivo a: semestresletivos.getData()){
            auxSemestreletivo = selec.findById(a.getId());
            if(auxSemestreletivo == null)
                //inserir
                selec.inserir(a);
            else
                //atualizar
                selec.atualizar(a);

        }
        CursoC cursc = new CursoC(copia.getContext());
        Curso auxCurso = null;
        for (Curso a: cursos.getData()){
            auxCurso = cursc.findById(a.getId());
            if(auxCurso == null)
                //inserir
                cursc.inserir(a);
            else
                //atualizar
                cursc.atualizar(a);

        }
        //se não tiver vazia ele atualiza
        if(!listaAlocacao.isEmpty()){
            OfertaC oferc = new OfertaC(copia.getContext());
            Oferta auxOfer = null;
            AlocacaoSalaC alocc = new AlocacaoSalaC(copia.getContext());
            AlocacaoSala auxAloc = null;

            //oferta primeiro, pois ele pode precisar inserir uma nova
            for(Oferta a: listaOferta){
                Log.i("INFO",a.getDiasemana());
                auxOfer = oferc.findById(a.getId());
                if(auxOfer == null){
                    //atualiza
                    oferc.inserir(a);
                }
                else{
                    oferc.atualizar(a);
                }
            }
            //oferta só atualiza
            for(AlocacaoSala a: listaAlocatt){
                //cria um objeto novo com as referencias da atualização, pois necessita atualização
                  alocc.atualizar(a);
            }

        }

    }

}
