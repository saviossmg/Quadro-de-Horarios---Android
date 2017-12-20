package com.unitins.quadro.quadrodehorarios.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe criada para os metodos de conversão de DATAS
 * Created by savio on 25/10/2017.
 */

public class Datas {

    //converte DATE para STRING
    public String getData2String(Date param) throws ParseException {
        String dateString = null;
        SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
        try{
            dateString = sdfr.format( param );
        }catch (Exception ex ){
            System.out.println(ex);
        }
        return dateString;
    }

    //converte DATE para STRING em  HORASA
    public String getData2StringHora(Date param) throws ParseException {
        String dateString;
        long timeInMillis = param.getTime();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(timeInMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateString = dateFormat.format(cal1.getTime());
        return dateString;
    }

    //converte STRING para DATE
    public Date getString2Data(String param) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date data = formato.parse(param);
        return data;
    }

    //converte STRING para DATE - segundo metodo
    public Date getString2Data2(String param) throws ParseException {
        //quebra a string
        String[] paramQuebrado = param.split("T");
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date data = formato.parse(paramQuebrado[0]+" "+paramQuebrado[1]);
        return data;
    }



}