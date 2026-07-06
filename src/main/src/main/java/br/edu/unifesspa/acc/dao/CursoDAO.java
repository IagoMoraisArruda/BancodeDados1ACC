package br.edu.unifesspa.acc.dao;

import br.edu.unifesspa.acc.connection.ConnectionFactory;
import br.edu.unifesspa.acc.model.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CursoDAO implements Dao<Curso, Integer> {

    @Override
    public Curso save(Curso c) throws SQLException {
        String sql = "INSERT INTO Curso (NomeCurso, CargaHorariaExigida) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getNomeCurso());
            ps.setInt(2, c.getCargaHorariaExigida());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    c.setIdCurso(keys.getInt(1));
                }
            }
            return c;
        }
    }

    @Override
    public Optional<Curso> findById(Integer id) throws SQLException {
        String sql = "SELECT idCurso, NomeCurso, CargaHorariaExigida FROM Curso WHERE idCurso = ?";
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
    public List<Curso> findAll() throws SQLException {
        String sql = "SELECT idCurso, NomeCurso, CargaHorariaExigida FROM Curso ORDER BY NomeCurso";
        List<Curso> result = new ArrayList<>();
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
    public boolean update(Curso c) throws SQLException {
        String sql = "UPDATE Curso SET NomeCurso = ?, CargaHorariaExigida = ? WHERE idCurso = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNomeCurso());
            ps.setInt(2, c.getCargaHorariaExigida());
            ps.setInt(3, c.getIdCurso());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM Curso WHERE idCurso = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Curso map(ResultSet rs) throws SQLException {
        return new Curso(
                rs.getInt("idCurso"),
                rs.getString("NomeCurso"),
                rs.getInt("CargaHorariaExigida")
        );
    }
}
