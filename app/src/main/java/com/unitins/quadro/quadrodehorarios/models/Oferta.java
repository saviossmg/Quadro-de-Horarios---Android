package com.unitins.quadro.quadrodehorarios.models;

/**
 * Created by savio on 26/09/2017.
 */

public class Oferta {

    private int id;
    private String nomeTurma;
    private Curso curso;
    private String diaSemana;
    private int periodo;
    private String disciplina;
    private String descricaoPeriodoLetivo;
    private String horaInicialA;
    private String horaFinalA;
    private String intervaloInicio;
    private String intervaloFim;
    private String horaInicialB;
    private String horaFinalB;
    private String professor;
    private String turno;

    public Oferta() {
        this.id = id;
        this.nomeTurma = nomeTurma;
        this.curso = curso;
        this.diaSemana = diaSemana;
        this.periodo = periodo;
        this.disciplina = disciplina;
        this.descricaoPeriodoLetivo = descricaoPeriodoLetivo;
        this.horaInicialA = horaInicialA;
        this.horaFinalA = horaFinalA;
        this.intervaloInicio = intervaloInicio;
        this.intervaloFim = intervaloFim;
        this.horaInicialB = horaInicialB;
        this.horaFinalB = horaFinalB;
        this.professor = professor;
        this.turno = turno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
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

    public String getDescricaoPeriodoLetivo() {
        return descricaoPeriodoLetivo;
    }

    public void setDescricaoPeriodoLetivo(String descricaoPeriodoLetivo) {
        this.descricaoPeriodoLetivo = descricaoPeriodoLetivo;
    }

    public String getHoraInicialA() {
        return horaInicialA;
    }

    public void setHoraInicialA(String horaInicialA) {
        this.horaInicialA = horaInicialA;
    }

    public String getHoraFinalA() {
        return horaFinalA;
    }

    public void setHoraFinalA(String horaFinalA) {
        this.horaFinalA = horaFinalA;
    }

    public String getIntervaloInicio() {
        return intervaloInicio;
    }

    public void setIntervaloInicio(String intervaloInicio) {
        this.intervaloInicio = intervaloInicio;
    }

    public String getIntervaloFim() {
        return intervaloFim;
    }

    public void setIntervaloFim(String intervaloFim) {
        this.intervaloFim = intervaloFim;
    }

    public String getHoraInicialB() {
        return horaInicialB;
    }

    public void setHoraInicialB(String horaInicialB) {
        this.horaInicialB = horaInicialB;
    }

    public String getHoraFinalB() {
        return horaFinalB;
    }

    public void setHoraFinalB(String horaFinalB) {
        this.horaFinalB = horaFinalB;
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
}