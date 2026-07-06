package br.edu.unifesspa.acc.dao;

import br.edu.unifesspa.acc.connection.ConnectionFactory;
import br.edu.unifesspa.acc.model.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlunoDAO implements Dao<Aluno, Integer> {

    @Override
    public Aluno save(Aluno a) throws SQLException {
        String sql = "INSERT INTO Aluno (Matricula, Nome, Email, Curso_idCurso) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, a.getMatricula());
            ps.setString(2, a.getNome());
            ps.setString(3, a.getEmail());
            ps.setInt(4, a.getCursoId());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    a.setIdAluno(keys.getInt(1));
                }
            }
            return a;
        }
    }

    @Override
    public Optional<Aluno> findById(Integer id) throws SQLException {
        String sql = "SELECT idAluno, Matricula, Nome, Email, Curso_idCurso FROM Aluno WHERE idAluno = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Aluno> findAll() throws SQLException {
        String sql = "SELECT idAluno, Matricula, Nome, Email, Curso_idCurso FROM Aluno ORDER BY Nome";
        List<Aluno> result = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(map(rs));
            }
        }
        return result;
    }

    @Override
    public boolean update(Aluno a) throws SQLException {
        String sql = "UPDATE Aluno SET Matricula = ?, Nome = ?, Email = ?, Curso_idCurso = ? WHERE idAluno = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getMatricula());
            ps.setString(2, a.getNome());
            ps.setString(3, a.getEmail());
            ps.setInt(4, a.getCursoId());
            ps.setInt(5, a.getIdAluno());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM Aluno WHERE idAluno = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Aluno> findAlunosComPontuacaoMinimaAtingida(double pontuacaoMinima) throws SQLException {
        String sql = """
                SELECT al.idAluno, al.Matricula, al.Nome, al.Email, al.Curso_idCurso
                FROM Aluno al
                JOIN AtividadeACC at ON at.Aluno_idAluno = al.idAluno
                JOIN Validacao v ON v.AtividadeACC_idAtividadeACC = at.idAtividadeACC
                WHERE v.StatusValidacao = 'APROVADO'
                GROUP BY al.idAluno, al.Matricula, al.Nome, al.Email, al.Curso_idCurso
                HAVING SUM(v.PontosAprovados) >= ?
                ORDER BY al.Nome
                """;
        List<Aluno> result = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, pontuacaoMinima);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(map(rs));
                }
            }
        }
        return result;
    }

    private Aluno map(ResultSet rs) throws SQLException {
        return new Aluno(
                rs.getInt("idAluno"),
                rs.getString("Matricula"),
                rs.getString("Nome"),
                rs.getString("Email"),
                rs.getInt("Curso_idCurso")
        );
    }
}
