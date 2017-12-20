package com.unitins.quadro.quadrodehorarios.models;

/**
 * Created by savio on 26/09/2017.
 */

public class AlocacaoSala {

    private int id;
    private SemestreLetivo semestre;
    private Sala sala;
    private Oferta oferta;

    public AlocacaoSala() {
        this.id = id;
        this.semestre = semestre;
        this.sala = sala;
        this.oferta = oferta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SemestreLetivo getSemestre() {
        return semestre;
    }

    public void setSemestre(SemestreLetivo semestre) {
        this.semestre = semestre;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }

    //metodo sobrescrito
    public String toString(){
        String ret = getOferta().getDisciplina()+"\n\t\t"+getSala().getNome();
        return ret;
    }
}
