package br.edu.unifesspa.acc.dao;

import br.edu.unifesspa.acc.connection.ConnectionFactory;
import br.edu.unifesspa.acc.model.Instituicao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InstituicaoDAO implements Dao<Instituicao, Integer> {

    @Override
    public Instituicao save(Instituicao i) throws SQLException {
        String sql = "INSERT INTO Instituicao (TipoEntidade, Nome, CNPJ, Cidade, Estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, i.getTipoEntidade());
            ps.setString(2, i.getNome());
            ps.setString(3, i.getCnpj());
            ps.setString(4, i.getCidade());
            ps.setString(5, i.getEstado());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) i.setIdInstituicao(keys.getInt(1));
            }
            return i;
        }
    }

    @Override
    public Optional<Instituicao> findById(Integer id) throws SQLException {
        String sql = "SELECT idInstituicao, TipoEntidade, Nome, CNPJ, Cidade, Estado FROM Instituicao WHERE idInstituicao = ?";
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
    public List<Instituicao> findAll() throws SQLException {
        String sql = "SELECT idInstituicao, TipoEntidade, Nome, CNPJ, Cidade, Estado FROM Instituicao ORDER BY Nome";
        List<Instituicao> result = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) result.add(map(rs));
        }
        return result;
    }

    @Override
    public boolean update(Instituicao i) throws SQLException {
        String sql = "UPDATE Instituicao SET TipoEntidade = ?, Nome = ?, CNPJ = ?, Cidade = ?, Estado = ? WHERE idInstituicao = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, i.getTipoEntidade());
            ps.setString(2, i.getNome());
            ps.setString(3, i.getCnpj());
            ps.setString(4, i.getCidade());
            ps.setString(5, i.getEstado());
            ps.setInt(6, i.getIdInstituicao());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM Instituicao WHERE idInstituicao = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Instituicao map(ResultSet rs) throws SQLException {
        return new Instituicao(
                rs.getInt("idInstituicao"),
                rs.getString("TipoEntidade"),
                rs.getString("Nome"),
                rs.getString("CNPJ"),
                rs.getString("Cidade"),
                rs.getString("Estado")
        );
    }
}
