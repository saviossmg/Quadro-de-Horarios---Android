package com.unitins.quadro.quadrodehorarios.models;

import java.util.Date;

/**
 * Created by savio on 26/09/2017.
 */

public class Semestre {

    private int id;
    private String descricao;
    private Date dataInicio;
    private Date dataFim;

    public Semestre() {
        this.id = id;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String toString(){
        String ret = getDescricao();
        return ret;
    }

}
