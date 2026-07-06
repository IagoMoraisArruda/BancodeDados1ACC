package br.edu.unifesspa.acc.model;

public class Professor {

    private Integer idProfessor;
    private String siape;
    private String nome;
    private String email;
    private String departamento;

    public Professor() {
    }

    public Professor(Integer idProfessor, String siape, String nome, String email, String departamento) {
        this.idProfessor = idProfessor;
        this.siape = siape;
        this.nome = nome;
        this.email = email;
        this.departamento = departamento;
    }

    public Integer getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(Integer idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getSiape() {
        return siape;
    }

    public void setSiape(String siape) {
        this.siape = siape;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return "Professor{idProfessor=" + idProfessor + ", siape='" + siape + "', nome='" + nome + "'}";
    }
}
