package com.unitins.quadro.quadrodehorarios.views.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.unitins.quadro.quadrodehorarios.R;
import com.unitins.quadro.quadrodehorarios.services.Datas;

public class NoticiaDetalhes extends AppCompatActivity {

    private TextView titulo = null;
    private TextView autor = null;
    private TextView data = null;
    private WebView pagina = null;
    private ProgressBar progresso = null;

    private Datas datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia_detalhes);
        getSupportActionBar().setTitle("Detalhes da Noticia");

        //referencias visuais
        titulo = (TextView) findViewById(R.id.notdet_titulo);
        autor = (TextView) findViewById(R.id.notdet_autor);
        data = (TextView) findViewById(R.id.notdet_data);
        pagina = (WebView) findViewById(R.id.notdet_web);
        progresso = (ProgressBar) findViewById(R.id.notdet_progress);

        //pega o valor da itent
        Intent dados = getIntent();

        //seta os cammpos
        titulo.setText(dados.getExtras().getString("titulo"));
        autor.setText("Autor(a): "+dados.getExtras().getString("autor"));
        data.setText("Criada en "+dados.getExtras().getString("criacaoD")+" às "+dados.getExtras().getString("criacaoH"));

        //faz a web view carregar o texto da noticia
        pagina.loadDataWithBaseURL("",dados.getExtras().getString("texto"),"text/html","UTF-8","");
        //comportamento da barra de progresso e mensagem de erro
        pagina.setWebViewClient(new WebClient());


    }

    //sobrescreve o botão de voltar da barra
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Dicionario de dados
                Bundle dicionario = new Bundle();
                dicionario.putBoolean("voltar", false);
                dicionario.putString("tela", "NoticiasDetalhes");
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

    public class WebClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progresso.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progresso.setVisibility(View.INVISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            //cria uma intent para o navegador padrão do sistema
            //passa a url clicacada
            Uri uri = Uri.parse(url);
            Intent openBrowser = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(openBrowser);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(getApplicationContext(),"Erro ao carregar a página.", Toast.LENGTH_SHORT);
        }
    }


}
