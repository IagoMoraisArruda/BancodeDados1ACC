package br.edu.unifesspa.acc.model;

public class Aluno {

    private Integer idAluno;
    private String matricula;
    private String nome;
    private String email;
    private Integer cursoId;

    public Aluno() {
    }

    public Aluno(Integer idAluno, String matricula, String nome, String email, Integer cursoId) {
        this.idAluno = idAluno;
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.cursoId = cursoId;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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

    public Integer getCursoId() {
        return cursoId;
    }

    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
    }

    @Override
    public String toString() {
        return "Aluno{idAluno=" + idAluno + ", matricula='" + matricula + "', nome='" + nome
                + "', email='" + email + "', cursoId=" + cursoId + "}";
    }
}
