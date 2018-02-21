package com.unitins.quadro.quadrodehorarios.controllers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unitins.quadro.quadrodehorarios.models.Criptografia;
import com.unitins.quadro.quadrodehorarios.services.DatabaseHelper;


/**
 * Created by savio on 08/02/2018.
 */

public class CriptografiaC {

    private SQLiteDatabase db;
    private DatabaseHelper helper;

    private Criptografia criptografia;
    private Cursor cursor;

    public CriptografiaC(Context context){
        helper = new DatabaseHelper(context);
        db = helper.getDatabase();
    }

    //CONSULTA POR ID
    public Criptografia findById(int id)
    {
        criptografia = new Criptografia();
        cursor = db.query("Criptografia", new String[]{"id","chave"},"id = "+id,
                null, null, null, null);

        cursor.moveToFirst();
        criptografia.setId(cursor.getInt(0));
        criptografia.setChave(cursor.getString(1));

        cursor.close();

        //retorna o objeto
        return criptografia;
    }

}
