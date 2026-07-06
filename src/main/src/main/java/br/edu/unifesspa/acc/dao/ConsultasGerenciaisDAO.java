package br.edu.unifesspa.acc.dao;

import br.edu.unifesspa.acc.connection.ConnectionFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConsultasGerenciaisDAO {

    public Map<String, Long> findCategoriasMaisFrequentes() throws SQLException {
        String sql = """
                SELECT c.TipoCategoria, COUNT(a.idAtividadeACC) AS total
                FROM CategoriaAcc c
                JOIN TipoACC t ON t.CategoriaAcc_idCategoriaAcc = c.idCategoriaAcc
                LEFT JOIN AtividadeACC a ON a.TipoACC_idTipoACC = t.idTipoACC
                GROUP BY c.idCategoriaAcc, c.TipoCategoria
                ORDER BY total DESC
                """;
        Map<String, Long> result = new LinkedHashMap<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.put(rs.getString("TipoCategoria"), rs.getLong("total"));
            }
        }
        return result;
    }

    public Map<Integer, BigDecimal> findCargaHorariaAprovadaPorAluno() throws SQLException {
        String sql = """
                SELECT al.idAluno, COALESCE(SUM(at.CargaHoraria), 0) AS totalHoras
                FROM Aluno al
                JOIN AtividadeACC at ON at.Aluno_idAluno = al.idAluno
                JOIN Validacao v ON v.AtividadeACC_idAtividadeACC = at.idAtividadeACC
                WHERE v.StatusValidacao = 'APROVADO'
                GROUP BY al.idAluno
                ORDER BY totalHoras DESC
                """;
        Map<Integer, BigDecimal> result = new LinkedHashMap<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.put(rs.getInt("idAluno"), rs.getBigDecimal("totalHoras"));
            }
        }
        return result;
    }

    public Map<String, Long> findAtividadesValidadasPorProfessor() throws SQLException {
        String sql = """
                SELECT p.Nome, COUNT(v.idValidacao) AS total
                FROM Professor p
                LEFT JOIN Validacao v ON v.Professor_idProfessor = p.idProfessor
                GROUP BY p.idProfessor, p.Nome
                ORDER BY total DESC
                """;
        Map<String, Long> result = new LinkedHashMap<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.put(rs.getString("Nome"), rs.getLong("total"));
            }
        }
        return result;
    }

    public Map<String, Long> findInstituicoesComMaisDocumentos() throws SQLException {
        String sql = """
                SELECT i.Nome, COUNT(d.idDocumento) AS total
                FROM Instituicao i
                LEFT JOIN Documento d ON d.Instituicao_idInstituicao = i.idInstituicao
                GROUP BY i.idInstituicao, i.Nome
                ORDER BY total DESC
                """;
        Map<String, Long> result = new LinkedHashMap<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.put(rs.getString("Nome"), rs.getLong("total"));
            }
        }
        return result;
    }

    public Map<String, Long> findAtividadesPorPeriodoLetivo() throws SQLException {
        String sql = """
                SELECT CONCAT(pl.Ano, '/', pl.Semestre) AS periodo, COUNT(a.idAtividadeACC) AS total
                FROM PeriodoLetivo pl
                LEFT JOIN AtividadeACC a ON a.PeriodoLetivo_idPeriodo = pl.idPeriodo
                GROUP BY pl.idPeriodo, pl.Ano, pl.Semestre
                ORDER BY pl.Ano DESC, pl.Semestre DESC
                """;
        Map<String, Long> result = new LinkedHashMap<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.put(rs.getString("periodo"), rs.getLong("total"));
            }
        }
        return result;
    }
}
