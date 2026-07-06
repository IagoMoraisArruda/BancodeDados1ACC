package br.edu.unifesspa.acc.dao;

import br.edu.unifesspa.acc.connection.ConnectionFactory;
import br.edu.unifesspa.acc.model.Documento;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DocumentoDAO implements Dao<Documento, Integer> {

    @Override
    public Documento save(Documento d) throws SQLException {
        String sql = "INSERT INTO Documento (AtividadeACC_idAtividadeACC, Instituicao_idInstituicao, "
                + "NomeArquivo, TipoDocumento, DataEnvio) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, d.getAtividadeAccId());
            ps.setInt(2, d.getInstituicaoId());
            ps.setString(3, d.getNomeArquivo());
            ps.setString(4, d.getTipoDocumento());
            ps.setTimestamp(5, Timestamp.valueOf(d.getDataEnvio()));
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) d.setIdDocumento(keys.getInt(1));
            }
            return d;
        }
    }

    @Override
    public Optional<Documento> findById(Integer id) throws SQLException {
        String sql = "SELECT idDocumento, AtividadeACC_idAtividadeACC, Instituicao_idInstituicao, "
                + "NomeArquivo, TipoDocumento, DataEnvio FROM Documento WHERE idDocumento = ?";
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
    public List<Documento> findAll() throws SQLException {
        String sql = "SELECT idDocumento, AtividadeACC_idAtividadeACC, Instituicao_idInstituicao, "
                + "NomeArquivo, TipoDocumento, DataEnvio FROM Documento ORDER BY DataEnvio DESC";
        List<Documento> result = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) result.add(map(rs));
        }
        return result;
    }

    @Override
    public boolean update(Documento d) throws SQLException {
        String sql = "UPDATE Documento SET AtividadeACC_idAtividadeACC = ?, Instituicao_idInstituicao = ?, "
                + "NomeArquivo = ?, TipoDocumento = ?, DataEnvio = ? WHERE idDocumento = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, d.getAtividadeAccId());
            ps.setInt(2, d.getInstituicaoId());
            ps.setString(3, d.getNomeArquivo());
            ps.setString(4, d.getTipoDocumento());
            ps.setTimestamp(5, Timestamp.valueOf(d.getDataEnvio()));
            ps.setInt(6, d.getIdDocumento());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM Documento WHERE idDocumento = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Documento> findCertificadosPorAluno(Integer alunoId) throws SQLException {
        String sql = """
                SELECT d.idDocumento, d.AtividadeACC_idAtividadeACC, d.Instituicao_idInstituicao,
                       d.NomeArquivo, d.TipoDocumento, d.DataEnvio
                FROM Documento d
                JOIN AtividadeACC at ON at.idAtividadeACC = d.AtividadeACC_idAtividadeACC
                WHERE at.Aluno_idAluno = ? AND d.TipoDocumento = 'CERTIFICADO'
                ORDER BY d.DataEnvio DESC
                """;
        List<Documento> result = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, alunoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) result.add(map(rs));
            }
        }
        return result;
    }

    private Documento map(ResultSet rs) throws SQLException {
        Timestamp ts = rs.getTimestamp("DataEnvio");
        LocalDateTime dataEnvio = ts != null ? ts.toLocalDateTime() : null;
        return new Documento(
                rs.getInt("idDocumento"),
                rs.getInt("AtividadeACC_idAtividadeACC"),
                rs.getInt("Instituicao_idInstituicao"),
                rs.getString("NomeArquivo"),
                rs.getString("TipoDocumento"),
                dataEnvio
        );
    }
}
