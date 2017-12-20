package com.unitins.quadro.quadrodehorarios.models;

/**
 * Created by savio on 18/10/2017.
 */

public class Periodo {

    private String descricao;
    private int num;

    public Periodo() {
        this.descricao = descricao;
        this.num = num;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String toString(){
        String ret = getDescricao();
        return ret;
    }
}
