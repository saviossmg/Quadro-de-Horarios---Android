package com.unitins.quadro.quadrodehorarios.models;

/**
 * Created by savio on 26/09/2017.
 */

public class Sala {

    private int id;
    private String nome;
    private int piso;
    private Predio predio;
    private String tipo;
    private Boolean ativo;
    private int idpredio;

    public Sala() {
        this.id = id;
        this.nome = nome;
        this.piso = piso;
        this.predio = new Predio();
        this.tipo = tipo;
        this.ativo = ativo;
        this.idpredio = idpredio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public int getIdpredio() {
        return idpredio;
    }

    public void setIdpredio(int idpredio) {
        this.idpredio = idpredio;
    }

    public String toString(){
        String ret = getNome();
        return ret;
    }
}
