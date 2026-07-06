package br.edu.unifesspa.acc.model;

import java.math.BigDecimal;

public class TipoACC {

    private Integer idTipoACC;
    private Integer categoriaAccId;
    private String inciso;
    private String nomeTipo;
    private String descricao;
    private BigDecimal pontuacaoMaxima;

    public TipoACC() {
    }

    public TipoACC(Integer idTipoACC, Integer categoriaAccId, String inciso, String nomeTipo,
                    String descricao, BigDecimal pontuacaoMaxima) {
        this.idTipoACC = idTipoACC;
        this.categoriaAccId = categoriaAccId;
        this.inciso = inciso;
        this.nomeTipo = nomeTipo;
        this.descricao = descricao;
        this.pontuacaoMaxima = pontuacaoMaxima;
    }

    public Integer getIdTipoACC() {
        return idTipoACC;
    }

    public void setIdTipoACC(Integer idTipoACC) {
        this.idTipoACC = idTipoACC;
    }

    public Integer getCategoriaAccId() {
        return categoriaAccId;
    }

    public void setCategoriaAccId(Integer categoriaAccId) {
        this.categoriaAccId = categoriaAccId;
    }

    public String getInciso() {
        return inciso;
    }

    public void setInciso(String inciso) {
        this.inciso = inciso;
    }

    public String getNomeTipo() {
        return nomeTipo;
    }

    public void setNomeTipo(String nomeTipo) {
        this.nomeTipo = nomeTipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPontuacaoMaxima() {
        return pontuacaoMaxima;
    }

    public void setPontuacaoMaxima(BigDecimal pontuacaoMaxima) {
        this.pontuacaoMaxima = pontuacaoMaxima;
    }

    @Override
    public String toString() {
        return "TipoACC{idTipoACC=" + idTipoACC + ", categoriaAccId=" + categoriaAccId
                + ", inciso='" + inciso + "', nomeTipo='" + nomeTipo
                + "', pontuacaoMaxima=" + pontuacaoMaxima + "}";
    }
}
