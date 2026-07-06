package br.edu.unifesspa.acc.dao;

import br.edu.unifesspa.acc.connection.ConnectionFactory;
import br.edu.unifesspa.acc.model.TipoACC;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TipoACCDAO implements Dao<TipoACC, Integer> {

    @Override
    public TipoACC save(TipoACC t) throws SQLException {
        String sql = "INSERT INTO TipoACC (CategoriaAcc_idCategoriaAcc, Inciso, NomeTipo, Descricao, PontuacaoMaxima) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, t.getCategoriaAccId());
            ps.setString(2, t.getInciso());
            ps.setString(3, t.getNomeTipo());
            ps.setString(4, t.getDescricao());
            ps.setBigDecimal(5, t.getPontuacaoMaxima());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) t.setIdTipoACC(keys.getInt(1));
            }
            return t;
        }
    }

    @Override
    public Optional<TipoACC> findById(Integer id) throws SQLException {
        String sql = "SELECT idTipoACC, CategoriaAcc_idCategoriaAcc, Inciso, NomeTipo, Descricao, PontuacaoMaxima "
                + "FROM TipoACC WHERE idTipoACC = ?";
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
    public List<TipoACC> findAll() throws SQLException {
        String sql = "SELECT idTipoACC, CategoriaAcc_idCategoriaAcc, Inciso, NomeTipo, Descricao, PontuacaoMaxima "
                + "FROM TipoACC ORDER BY Inciso";
        List<TipoACC> result = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) result.add(map(rs));
        }
        return result;
    }

    @Override
    public boolean update(TipoACC t) throws SQLException {
        String sql = "UPDATE TipoACC SET CategoriaAcc_idCategoriaAcc = ?, Inciso = ?, NomeTipo = ?, "
                + "Descricao = ?, PontuacaoMaxima = ? WHERE idTipoACC = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, t.getCategoriaAccId());
            ps.setString(2, t.getInciso());
            ps.setString(3, t.getNomeTipo());
            ps.setString(4, t.getDescricao());
            ps.setBigDecimal(5, t.getPontuacaoMaxima());
            ps.setInt(6, t.getIdTipoACC());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM TipoACC WHERE idTipoACC = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public Map<String, Long> findVolumeParticipacaoPorTipo() throws SQLException {
        String sql = """
                SELECT t.NomeTipo, COUNT(a.idAtividadeACC) AS total
                FROM TipoACC t
                LEFT JOIN AtividadeACC a ON a.TipoACC_idTipoACC = t.idTipoACC
                GROUP BY t.idTipoACC, t.NomeTipo
                ORDER BY total DESC
                """;
        Map<String, Long> result = new LinkedHashMap<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.put(rs.getString("NomeTipo"), rs.getLong("total"));
            }
        }
        return result;
    }

    private TipoACC map(ResultSet rs) throws SQLException {
        BigDecimal pontuacao = rs.getBigDecimal("PontuacaoMaxima");
        return new TipoACC(
                rs.getInt("idTipoACC"),
                rs.getInt("CategoriaAcc_idCategoriaAcc"),
                rs.getString("Inciso"),
                rs.getString("NomeTipo"),
                rs.getString("Descricao"),
                pontuacao
        );
    }
}
