package com.unitins.quadro.quadrodehorarios.models;

/**
 * Created by savio on 26/09/2017.
 */

public class Predio {

    private int id;
    private String nome;
    private int pisos;
    private Unidade unidade;
    private Boolean ativo;
    private int idunidade;

    public Predio() {
        this.id = id;
        this.nome = nome;
        this.pisos = pisos;
        this.unidade = unidade;
        this.ativo = ativo;
        this.idunidade = idunidade;
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

    public int getPisos() {
        return pisos;
    }

    public void setPisos(int pisos) {
        this.pisos = pisos;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public int getIdunidade() {
        return idunidade;
    }

    public void setIdunidade(int idunidade) {
        this.idunidade = idunidade;
    }
}
