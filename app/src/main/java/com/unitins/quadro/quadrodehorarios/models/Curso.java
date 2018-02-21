package com.unitins.quadro.quadrodehorarios.models;

/**
 * Created by savio on 26/09/2017.
 */

public class Curso {

    private int id;
    private String nome;
    private Unidade unidade;
    private String codCurso;
    private int idunidade;

    public Curso() {
        this.id = id;
        this.nome = nome;
        this.unidade = unidade;
        this.codCurso = codCurso;
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

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public String getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(String codCurso) {
        this.codCurso = codCurso;
    }

    public int getIdunidade() {
        return idunidade;
    }

    public void setIdunidade(int idunidade) {
        this.idunidade = idunidade;
    }

    public String toString(){
        String ret = getNome();
        return ret;
    }

}
