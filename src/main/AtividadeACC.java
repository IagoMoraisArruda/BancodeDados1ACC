package br.edu.unifesspa.acc.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AtividadeACC {

    private Integer idAtividadeACC;
    private Integer alunoId;
    private Integer tipoAccId;
    private Integer periodoLetivoId;
    private String titulo;
    private String descricao;
    private LocalDate dataRealizacao;
    private BigDecimal cargaHoraria;
    private BigDecimal pontosSolicitados;

    public AtividadeACC() {
    }

    public AtividadeACC(Integer idAtividadeACC, Integer alunoId, Integer tipoAccId,
                         Integer periodoLetivoId, String titulo, String descricao,
                         LocalDate dataRealizacao, BigDecimal cargaHoraria,
                         BigDecimal pontosSolicitados) {
        this.idAtividadeACC = idAtividadeACC;
        this.alunoId = alunoId;
        this.tipoAccId = tipoAccId;
        this.periodoLetivoId = periodoLetivoId;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataRealizacao = dataRealizacao;
        this.cargaHoraria = cargaHoraria;
        this.pontosSolicitados = pontosSolicitados;
    }

    public Integer getIdAtividadeACC() {
        return idAtividadeACC;
    }

    public void setIdAtividadeACC(Integer idAtividadeACC) {
        this.idAtividadeACC = idAtividadeACC;
    }

    public Integer getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Integer alunoId) {
        this.alunoId = alunoId;
    }

    public Integer getTipoAccId() {
        return tipoAccId;
    }

    public void setTipoAccId(Integer tipoAccId) {
        this.tipoAccId = tipoAccId;
    }

    public Integer getPeriodoLetivoId() {
        return periodoLetivoId;
    }

    public void setPeriodoLetivoId(Integer periodoLetivoId) {
        this.periodoLetivoId = periodoLetivoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(LocalDate dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    public BigDecimal getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(BigDecimal cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public BigDecimal getPontosSolicitados() {
        return pontosSolicitados;
    }

    public void setPontosSolicitados(BigDecimal pontosSolicitados) {
        this.pontosSolicitados = pontosSolicitados;
    }

    @Override
    public String toString() {
        return "AtividadeACC{idAtividadeACC=" + idAtividadeACC + ", alunoId=" + alunoId
                + ", tipoAccId=" + tipoAccId + ", titulo='" + titulo
                + "', pontosSolicitados=" + pontosSolicitados + "}";
    }
}
