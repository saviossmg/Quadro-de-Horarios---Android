package com.unitins.quadro.quadrodehorarios.models;

/**
 * Created by savio on 26/09/2017.
 */

public class SemestreLetivo {

    private int id;
    private Semestre semestre;
    private Curso curso;
    private int idsemestre;
    private int idcurso;

    public SemestreLetivo() {
        this.id = id;
        this.semestre = semestre;
        this.curso = curso;
        this.idsemestre = idsemestre;
        this.idcurso = idcurso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public int getIdsemestre() {
        return idsemestre;
    }

    public void setIdsemestre(int idsemestre) {
        this.idsemestre = idsemestre;
    }

    public int getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(int idcurso) {
        this.idcurso = idcurso;
    }
}
