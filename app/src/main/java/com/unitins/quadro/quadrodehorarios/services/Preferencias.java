package com.unitins.quadro.quadrodehorarios.services;

import android.content.Context;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by savio on 31/10/2017.
 */

public class Preferencias{

    //identificador do banco de dados destas preferencias
    public static final String PREF_ID = "quadrohorarios";
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    //setters
    public static void setInt(Context context, String chave, int valor){
        pref = context.getSharedPreferences(PREF_ID,0);
        editor = pref.edit();
        editor.putInt(chave, valor);
        editor.commit();
    }

    public static void setBoolean(Context context, String chave, boolean valor){
        pref = context.getSharedPreferences(PREF_ID,0);
        editor = pref.edit();
        editor.putBoolean(chave, valor);
        editor.commit();
    }

    //getter
    public static  int getInt(Context context, String chave){
        pref = context.getSharedPreferences(PREF_ID,0);
        int i = pref.getInt(chave,0);
        return i;
    }

    public static boolean getBoolean(Context context, String chave){
        pref = context.getSharedPreferences(PREF_ID,0);
        boolean b = pref.getBoolean(chave,false);
        return b;
    }


}
