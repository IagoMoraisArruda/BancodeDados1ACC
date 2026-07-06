package br.edu.unifesspa.acc.dao;

import br.edu.unifesspa.acc.connection.ConnectionFactory;
import br.edu.unifesspa.acc.model.PeriodoLetivo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PeriodoLetivoDAO implements Dao<PeriodoLetivo, Integer> {

    @Override
    public PeriodoLetivo save(PeriodoLetivo p) throws SQLException {
        String sql = "INSERT INTO PeriodoLetivo (Semestre, Ano) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getSemestre());
            ps.setInt(2, p.getAno());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    p.setIdPeriodo(keys.getInt(1));
                }
            }
            return p;
        }
    }

    @Override
    public Optional<PeriodoLetivo> findById(Integer id) throws SQLException {
        String sql = "SELECT idPeriodo, Semestre, Ano FROM PeriodoLetivo WHERE idPeriodo = ?";
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
    public List<PeriodoLetivo> findAll() throws SQLException {
        String sql = "SELECT idPeriodo, Semestre, Ano FROM PeriodoLetivo ORDER BY Ano, Semestre";
        List<PeriodoLetivo> result = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) result.add(map(rs));
        }
        return result;
    }

    @Override
    public boolean update(PeriodoLetivo p) throws SQLException {
        String sql = "UPDATE PeriodoLetivo SET Semestre = ?, Ano = ? WHERE idPeriodo = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getSemestre());
            ps.setInt(2, p.getAno());
            ps.setInt(3, p.getIdPeriodo());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM PeriodoLetivo WHERE idPeriodo = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private PeriodoLetivo map(ResultSet rs) throws SQLException {
        return new PeriodoLetivo(rs.getInt("idPeriodo"), rs.getString("Semestre"), rs.getInt("Ano"));
    }
}
