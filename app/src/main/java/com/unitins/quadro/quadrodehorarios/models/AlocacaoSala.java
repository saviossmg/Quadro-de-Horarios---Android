package com.unitins.quadro.quadrodehorarios.models;

/**
 * Created by savio on 26/09/2017.
 */

public class AlocacaoSala {

    private int id;
    private SemestreLetivo semestre;
    private Sala sala;
    private Oferta oferta;
    private int idsala;
    private int idoferta;
    private int idsemestre;

    public AlocacaoSala() {
        this.id = id;
        this.semestre = semestre;
        this.sala = sala;
        this.oferta = oferta;
        this.idsala = idsala;
        this.idoferta = idoferta;
        this.idsemestre = idsemestre;
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

    public int getIdsala() {
        return idsala;
    }

    public void setIdsala(int idsala) {
        this.idsala = idsala;
    }

    public int getIdoferta() {
        return idoferta;
    }

    public void setIdoferta(int idoferta) {
        this.idoferta = idoferta;
    }

    public int getIdsemestre() {
        return idsemestre;
    }

    public void setIdsemestre(int idsemestre) {
        this.idsemestre = idsemestre;
    }

}
