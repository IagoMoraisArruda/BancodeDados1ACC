package br.edu.unifesspa.acc.model;

public class Curso {

    private Integer idCurso;
    private String nomeCurso;
    private Integer cargaHorariaExigida;

    public Curso() {
    }

    public Curso(Integer idCurso, String nomeCurso, Integer cargaHorariaExigida) {
        this.idCurso = idCurso;
        this.nomeCurso = nomeCurso;
        this.cargaHorariaExigida = cargaHorariaExigida;
    }

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public Integer getCargaHorariaExigida() {
        return cargaHorariaExigida;
    }

    public void setCargaHorariaExigida(Integer cargaHorariaExigida) {
        this.cargaHorariaExigida = cargaHorariaExigida;
    }

    @Override
    public String toString() {
        return "Curso{idCurso=" + idCurso + ", nomeCurso='" + nomeCurso
                + "', cargaHorariaExigida=" + cargaHorariaExigida + "}";
    }
}
