package br.edu.unifesspa.acc.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Validacao {

    private Integer idValidacao;
    private Integer atividadeAccId;
    private Integer professorId;
    private LocalDateTime dataValidacao;
    private BigDecimal pontosAprovados;
    private String statusValidacao;

    public Validacao() {
    }

    public Validacao(Integer idValidacao, Integer atividadeAccId, Integer professorId,
                      LocalDateTime dataValidacao, BigDecimal pontosAprovados, String statusValidacao) {
        this.idValidacao = idValidacao;
        this.atividadeAccId = atividadeAccId;
        this.professorId = professorId;
        this.dataValidacao = dataValidacao;
        this.pontosAprovados = pontosAprovados;
        this.statusValidacao = statusValidacao;
    }

    public Integer getIdValidacao() {
        return idValidacao;
    }

    public void setIdValidacao(Integer idValidacao) {
        this.idValidacao = idValidacao;
    }

    public Integer getAtividadeAccId() {
        return atividadeAccId;
    }

    public void setAtividadeAccId(Integer atividadeAccId) {
        this.atividadeAccId = atividadeAccId;
    }

    public Integer getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Integer professorId) {
        this.professorId = professorId;
    }

    public LocalDateTime getDataValidacao() {
        return dataValidacao;
    }

    public void setDataValidacao(LocalDateTime dataValidacao) {
        this.dataValidacao = dataValidacao;
    }

    public BigDecimal getPontosAprovados() {
        return pontosAprovados;
    }

    public void setPontosAprovados(BigDecimal pontosAprovados) {
        this.pontosAprovados = pontosAprovados;
    }

    public String getStatusValidacao() {
        return statusValidacao;
    }

    public void setStatusValidacao(String statusValidacao) {
        this.statusValidacao = statusValidacao;
    }

    @Override
    public String toString() {
        return "Validacao{idValidacao=" + idValidacao + ", atividadeAccId=" + atividadeAccId
                + ", professorId=" + professorId + ", statusValidacao='" + statusValidacao
                + "', pontosAprovados=" + pontosAprovados + "}";
    }
}
