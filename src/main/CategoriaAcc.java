package br.edu.unifesspa.acc.model;

public class CategoriaAcc {

    private Integer idCategoriaAcc;
    private String tipoCategoria;
    private String descricao;

    public CategoriaAcc() {
    }

    public CategoriaAcc(Integer idCategoriaAcc, String tipoCategoria, String descricao) {
        this.idCategoriaAcc = idCategoriaAcc;
        this.tipoCategoria = tipoCategoria;
        this.descricao = descricao;
    }

    public Integer getIdCategoriaAcc() {
        return idCategoriaAcc;
    }

    public void setIdCategoriaAcc(Integer idCategoriaAcc) {
        this.idCategoriaAcc = idCategoriaAcc;
    }

    public String getTipoCategoria() {
        return tipoCategoria;
    }

    public void setTipoCategoria(String tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "CategoriaAcc{idCategoriaAcc=" + idCategoriaAcc + ", tipoCategoria='" + tipoCategoria
                + "', descricao='" + descricao + "'}";
    }
}
