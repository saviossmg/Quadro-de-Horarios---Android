package com.unitins.quadro.quadrodehorarios.models;

/**
 * Created by savio on 26/09/2017.
 */

public class Oferta {

    private int id;
    private String nometurma;
    private Curso curso;
    private String diasemana;
    private int periodo;
    private String disciplina;
    private String descricaoperiodoletivo;
    private String horainiciala;
    private String horafinala;
    private String intervaloinicio;
    private String intervalofim;
    private String horainicialb;
    private String horafinalb;
    private String professor;
    private String turno;
    private int tipohorario;
    private int idcurso;

    public Oferta() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNometurma() {
        return nometurma;
    }

    public void setNometurma(String nometurma) {
        this.nometurma = nometurma;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getDiasemana() {
        return diasemana;
    }

    public void setDiasemana(String diasemana) {
        this.diasemana = diasemana;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getDescricaoperiodoletivo() {
        return descricaoperiodoletivo;
    }

    public void setDescricaoperiodoletivo(String descricaoperiodoletivo) {
        this.descricaoperiodoletivo = descricaoperiodoletivo;
    }

    public String getHorainiciala() {
        return horainiciala;
    }

    public void setHorainiciala(String horainiciala) {
        this.horainiciala = horainiciala;
    }

    public String getHorafinala() {
        return horafinala;
    }

    public void setHorafinala(String horafinala) {
        this.horafinala = horafinala;
    }

    public String getIntervaloinicio() {
        return intervaloinicio;
    }

    public void setIntervaloinicio(String intervaloinicio) {
        this.intervaloinicio = intervaloinicio;
    }

    public String getIntervalofim() {
        return intervalofim;
    }

    public void setIntervalofim(String intervalofim) {
        this.intervalofim = intervalofim;
    }

    public String getHorainicialb() {
        return horainicialb;
    }

    public void setHorainicialb(String horainicialb) {
        this.horainicialb = horainicialb;
    }

    public String getHorafinalb() {
        return horafinalb;
    }

    public void setHorafinalb(String horafinalb) {
        this.horafinalb = horafinalb;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public int getTipohorario() {
        return tipohorario;
    }

    public void setTipohorario(int tipohorario) {
        this.tipohorario = tipohorario;
    }

    public int getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(int idcurso) {
        this.idcurso = idcurso;
    }
}