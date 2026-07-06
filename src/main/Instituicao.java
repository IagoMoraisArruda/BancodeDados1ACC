package br.edu.unifesspa.acc.model;

public class Instituicao {

    private Integer idInstituicao;
    private String tipoEntidade;
    private String nome;
    private String cnpj;
    private String cidade;
    private String estado;

    public Instituicao() {
    }

    public Instituicao(Integer idInstituicao, String tipoEntidade, String nome, String cnpj,
                        String cidade, String estado) {
        this.idInstituicao = idInstituicao;
        this.tipoEntidade = tipoEntidade;
        this.nome = nome;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Integer getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(Integer idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    public String getTipoEntidade() {
        return tipoEntidade;
    }

    public void setTipoEntidade(String tipoEntidade) {
        this.tipoEntidade = tipoEntidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Instituicao{idInstituicao=" + idInstituicao + ", nome='" + nome
                + "', tipoEntidade='" + tipoEntidade + "', cidade='" + cidade + "/" + estado + "'}";
    }
}
