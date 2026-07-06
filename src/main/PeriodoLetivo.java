package br.edu.unifesspa.acc.model;

public class PeriodoLetivo {

    private Integer idPeriodo;
    private String semestre;
    private Integer ano;

    public PeriodoLetivo() {
    }

    public PeriodoLetivo(Integer idPeriodo, String semestre, Integer ano) {
        this.idPeriodo = idPeriodo;
        this.semestre = semestre;
        this.ano = ano;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        return "PeriodoLetivo{idPeriodo=" + idPeriodo + ", semestre='" + semestre + "', ano=" + ano + "}";
    }
}
