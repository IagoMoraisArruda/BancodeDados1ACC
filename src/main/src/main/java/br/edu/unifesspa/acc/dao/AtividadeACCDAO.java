package br.edu.unifesspa.acc.dao;

import br.edu.unifesspa.acc.connection.ConnectionFactory;
import br.edu.unifesspa.acc.model.AtividadeACC;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AtividadeACCDAO implements Dao<AtividadeACC, Integer> {

    @Override
    public AtividadeACC save(AtividadeACC a) throws SQLException {
        String sql = "INSERT INTO AtividadeACC (Aluno_idAluno, TipoACC_idTipoACC, PeriodoLetivo_idPeriodo, "
                + "Titulo, Descricao, DataRealizacao, CargaHoraria, PontosSolicitados) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, a.getAlunoId());
            ps.setInt(2, a.getTipoAccId());
            ps.setInt(3, a.getPeriodoLetivoId());
            ps.setString(4, a.getTitulo());
            ps.setString(5, a.getDescricao());
            ps.setDate(6, Date.valueOf(a.getDataRealizacao()));
            ps.setBigDecimal(7, a.getCargaHoraria());
            ps.setBigDecimal(8, a.getPontosSolicitados());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) a.setIdAtividadeACC(keys.getInt(1));
            }
            return a;
        }
    }

    @Override
    public Optional<AtividadeACC> findById(Integer id) throws SQLException {
        String sql = "SELECT idAtividadeACC, Aluno_idAluno, TipoACC_idTipoACC, PeriodoLetivo_idPeriodo, "
                + "Titulo, Descricao, DataRealizacao, CargaHoraria, PontosSolicitados "
                + "FROM AtividadeACC WHERE idAtividadeACC = ?";
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
    public List<AtividadeACC> findAll() throws SQLException {
        String sql = "SELECT idAtividadeACC, Aluno_idAluno, TipoACC_idTipoACC, PeriodoLetivo_idPeriodo, "
                + "Titulo, Descricao, DataRealizacao, CargaHoraria, PontosSolicitados "
                + "FROM AtividadeACC ORDER BY DataRealizacao DESC";
        List<AtividadeACC> result = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) result.add(map(rs));
        }
        return result;
    }

    @Override
    public boolean update(AtividadeACC a) throws SQLException {
        String sql = "UPDATE AtividadeACC SET Aluno_idAluno = ?, TipoACC_idTipoACC = ?, "
                + "PeriodoLetivo_idPeriodo = ?, Titulo = ?, Descricao = ?, DataRealizacao = ?, "
                + "CargaHoraria = ?, PontosSolicitados = ? WHERE idAtividadeACC = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, a.getAlunoId());
            ps.setInt(2, a.getTipoAccId());
            ps.setInt(3, a.getPeriodoLetivoId());
            ps.setString(4, a.getTitulo());
            ps.setString(5, a.getDescricao());
            ps.setDate(6, Date.valueOf(a.getDataRealizacao()));
            ps.setBigDecimal(7, a.getCargaHoraria());
            ps.setBigDecimal(8, a.getPontosSolicitados());
            ps.setInt(9, a.getIdAtividadeACC());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM AtividadeACC WHERE idAtividadeACC = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<AtividadeACC> findAtividadesAguardandoValidacao() throws SQLException {
        String sql = """
                SELECT DISTINCT at.idAtividadeACC, at.Aluno_idAluno, at.TipoACC_idTipoACC,
                       at.PeriodoLetivo_idPeriodo, at.Titulo, at.Descricao, at.DataRealizacao,
                       at.CargaHoraria, at.PontosSolicitados
                FROM AtividadeACC at
                LEFT JOIN Validacao v ON v.AtividadeACC_idAtividadeACC = at.idAtividadeACC
                WHERE v.idValidacao IS NULL OR v.StatusValidacao IN ('PENDENTE', 'EM_ANALISE')
                ORDER BY at.DataRealizacao
                """;
        List<AtividadeACC> result = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) result.add(map(rs));
        }
        return result;
    }

    private AtividadeACC map(ResultSet rs) throws SQLException {
        LocalDate data = rs.getDate("DataRealizacao") != null
                ? rs.getDate("DataRealizacao").toLocalDate()
                : null;
        return new AtividadeACC(
                rs.getInt("idAtividadeACC"),
                rs.getInt("Aluno_idAluno"),
                rs.getInt("TipoACC_idTipoACC"),
                rs.getInt("PeriodoLetivo_idPeriodo"),
                rs.getString("Titulo"),
                rs.getString("Descricao"),
                data,
                rs.getBigDecimal("CargaHoraria"),
                rs.getBigDecimal("PontosSolicitados")
        );
    }
}
