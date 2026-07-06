package br.edu.unifesspa.acc.model;

import java.time.LocalDateTime;

public class Documento {

    private Integer idDocumento;
    private Integer atividadeAccId;
    private Integer instituicaoId;
    private String nomeArquivo;
    private String tipoDocumento;
    private LocalDateTime dataEnvio;

    public Documento() {
    }

    public Documento(Integer idDocumento, Integer atividadeAccId, Integer instituicaoId,
                      String nomeArquivo, String tipoDocumento, LocalDateTime dataEnvio) {
        this.idDocumento = idDocumento;
        this.atividadeAccId = atividadeAccId;
        this.instituicaoId = instituicaoId;
        this.nomeArquivo = nomeArquivo;
        this.tipoDocumento = tipoDocumento;
        this.dataEnvio = dataEnvio;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Integer getAtividadeAccId() {
        return atividadeAccId;
    }

    public void setAtividadeAccId(Integer atividadeAccId) {
        this.atividadeAccId = atividadeAccId;
    }

    public Integer getInstituicaoId() {
        return instituicaoId;
    }

    public void setInstituicaoId(Integer instituicaoId) {
        this.instituicaoId = instituicaoId;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    @Override
    public String toString() {
        return "Documento{idDocumento=" + idDocumento + ", atividadeAccId=" + atividadeAccId
                + ", nomeArquivo='" + nomeArquivo + "', tipoDocumento='" + tipoDocumento + "'}";
    }
}
