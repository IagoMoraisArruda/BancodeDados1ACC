package br.edu.unifesspa.acc.dao;

import br.edu.unifesspa.acc.connection.ConnectionFactory;
import br.edu.unifesspa.acc.model.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessorDAO implements Dao<Professor, Integer> {

    @Override
    public Professor save(Professor p) throws SQLException {
        String sql = "INSERT INTO Professor (Siape, Nome, Email, Departamento) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getSiape());
            ps.setString(2, p.getNome());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getDepartamento());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) p.setIdProfessor(keys.getInt(1));
            }
            return p;
        }
    }

    @Override
    public Optional<Professor> findById(Integer id) throws SQLException {
        String sql = "SELECT idProfessor, Siape, Nome, Email, Departamento FROM Professor WHERE idProfessor = ?";
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
    public List<Professor> findAll() throws SQLException {
        String sql = "SELECT idProfessor, Siape, Nome, Email, Departamento FROM Professor ORDER BY Nome";
        List<Professor> result = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) result.add(map(rs));
        }
        return result;
    }

    @Override
    public boolean update(Professor p) throws SQLException {
        String sql = "UPDATE Professor SET Siape = ?, Nome = ?, Email = ?, Departamento = ? WHERE idProfessor = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getSiape());
            ps.setString(2, p.getNome());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getDepartamento());
            ps.setInt(5, p.getIdProfessor());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM Professor WHERE idProfessor = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Professor map(ResultSet rs) throws SQLException {
        return new Professor(
                rs.getInt("idProfessor"),
                rs.getString("Siape"),
                rs.getString("Nome"),
                rs.getString("Email"),
                rs.getString("Departamento")
        );
    }
}
