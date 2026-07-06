package br.edu.unifesspa.acc.dao;

import br.edu.unifesspa.acc.connection.ConnectionFactory;
import br.edu.unifesspa.acc.model.Validacao;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ValidacaoDAO implements Dao<Validacao, Integer> {

    @Override
    public Validacao save(Validacao v) throws SQLException {
        String sql = "INSERT INTO Validacao (AtividadeACC_idAtividadeACC, Professor_idProfessor, "
                + "DataValidacao, PontosAprovados, StatusValidacao) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, v.getAtividadeAccId());
            ps.setInt(2, v.getProfessorId());
            ps.setTimestamp(3, Timestamp.valueOf(v.getDataValidacao()));
            ps.setBigDecimal(4, v.getPontosAprovados());
            ps.setString(5, v.getStatusValidacao());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) v.setIdValidacao(keys.getInt(1));
            }
            return v;
        }
    }

    @Override
    public Optional<Validacao> findById(Integer id) throws SQLException {
        String sql = "SELECT idValidacao, AtividadeACC_idAtividadeACC, Professor_idProfessor, "
                + "DataValidacao, PontosAprovados, StatusValidacao FROM Validacao WHERE idValidacao = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Validacao> findAll() throws SQLException {
        String sql = "SELECT idValidacao, AtividadeACC_idAtividadeACC, Professor_idProfessor, "
                + "DataValidacao, PontosAprovados, StatusValidacao FROM Validacao ORDER BY DataValidacao DESC";
        List<Validacao> result = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) result.add(map(rs));
        }
        return result;
    }

    @Override
    public boolean update(Validacao v) throws SQLException {
        String sql = "UPDATE Validacao SET AtividadeACC_idAtividadeACC = ?, Professor_idProfessor = ?, "
                + "DataValidacao = ?, PontosAprovados = ?, StatusValidacao = ? WHERE idValidacao = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, v.getAtividadeAccId());
            ps.setInt(2, v.getProfessorId());
            ps.setTimestamp(3, Timestamp.valueOf(v.getDataValidacao()));
            ps.setBigDecimal(4, v.getPontosAprovados());
            ps.setString(5, v.getStatusValidacao());
            ps.setInt(6, v.getIdValidacao());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM Validacao WHERE idValidacao = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public BigDecimal findPontuacaoAcumulada(Integer alunoId) throws SQLException {
        String sql = """
                SELECT COALESCE(SUM(v.PontosAprovados), 0) AS total
                FROM Validacao v
                JOIN AtividadeACC at ON at.idAtividadeACC = v.AtividadeACC_idAtividadeACC
                WHERE at.Aluno_idAluno = ? AND v.StatusValidacao = 'APROVADO'
                """;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, alunoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getBigDecimal("total");
            }
        }
        return BigDecimal.ZERO;
    }

    public List<Integer> findAlunosComPendencias(double pontuacaoMinima) throws SQLException {
        String sql = """
                SELECT al.idAluno
                FROM Aluno al
                LEFT JOIN AtividadeACC at ON at.Aluno_idAluno = al.idAluno
                LEFT JOIN Validacao v ON v.AtividadeACC_idAtividadeACC = at.idAtividadeACC
                    AND v.StatusValidacao = 'APROVADO'
                GROUP BY al.idAluno
                HAVING COALESCE(SUM(v.PontosAprovados), 0) < ?
                """;
        List<Integer> result = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, pontuacaoMinima);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) result.add(rs.getInt("idAluno"));
            }
        }
        return result;
    }

    private Validacao map(ResultSet rs) throws SQLException {
        Timestamp ts = rs.getTimestamp("DataValidacao");
        LocalDateTime dataValidacao = ts != null ? ts.toLocalDateTime() : null;
        return new Validacao(
                rs.getInt("idValidacao"),
                rs.getInt("AtividadeACC_idAtividadeACC"),
                rs.getInt("Professor_idProfessor"),
                dataValidacao,
                rs.getBigDecimal("PontosAprovados"),
                rs.getString("StatusValidacao")
        );
    }
}
