package com.unitins.quadro.quadrodehorarios.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Classe com metodo para testar a CONEXÃO
 * Created by savio on 25/10/2017.
 */

public class ConexaoTestador {

    //metodo que testará a conexão
    public boolean testaRede(Context contexto) {
        ConnectivityManager connectivityManager = (ConnectivityManager) contexto.getSystemService(contexto.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
