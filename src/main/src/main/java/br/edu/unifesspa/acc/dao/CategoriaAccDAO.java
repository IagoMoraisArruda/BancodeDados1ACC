package br.edu.unifesspa.acc.dao;

import br.edu.unifesspa.acc.connection.ConnectionFactory;
import br.edu.unifesspa.acc.model.CategoriaAcc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriaAccDAO implements Dao<CategoriaAcc, Integer> {

    @Override
    public CategoriaAcc save(CategoriaAcc c) throws SQLException {
        String sql = "INSERT INTO CategoriaAcc (TipoCategoria, Descricao) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getTipoCategoria());
            ps.setString(2, c.getDescricao());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) c.setIdCategoriaAcc(keys.getInt(1));
            }
            return c;
        }
    }

    @Override
    public Optional<CategoriaAcc> findById(Integer id) throws SQLException {
        String sql = "SELECT idCategoriaAcc, TipoCategoria, Descricao FROM CategoriaAcc WHERE idCategoriaAcc = ?";
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
    public List<CategoriaAcc> findAll() throws SQLException {
        String sql = "SELECT idCategoriaAcc, TipoCategoria, Descricao FROM CategoriaAcc ORDER BY TipoCategoria";
        List<CategoriaAcc> result = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) result.add(map(rs));
        }
        return result;
    }

    @Override
    public boolean update(CategoriaAcc c) throws SQLException {
        String sql = "UPDATE CategoriaAcc SET TipoCategoria = ?, Descricao = ? WHERE idCategoriaAcc = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getTipoCategoria());
            ps.setString(2, c.getDescricao());
            ps.setInt(3, c.getIdCategoriaAcc());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM CategoriaAcc WHERE idCategoriaAcc = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private CategoriaAcc map(ResultSet rs) throws SQLException {
        return new CategoriaAcc(rs.getInt("idCategoriaAcc"), rs.getString("TipoCategoria"), rs.getString("Descricao"));
    }
}
