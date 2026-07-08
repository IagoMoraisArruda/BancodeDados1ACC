import java.sql.*;
import java.util.Scanner;

public class ConsultasGerenciais {

    
    private static void executarEImprimirCru(Connection conn, String sql) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            imprimirResultSetCru(rs);
        }
    }

    private static void imprimirResultSetCru(ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int colCount = meta.getColumnCount();

        StringBuilder header = new StringBuilder();
        for (int i = 1; i <= colCount; i++) {
            header.append(meta.getColumnName(i));
            if (i < colCount) header.append(" | ");
        }
        System.out.println(header);

        StringBuilder separador = new StringBuilder();
        for (int i = 0; i < header.length(); i++) separador.append("-");
        System.out.println(separador);

        int linhas = 0;
        while (rs.next()) {
            StringBuilder linha = new StringBuilder();
            for (int i = 1; i <= colCount; i++) {
                linha.append(rs.getString(i));
                if (i < colCount) linha.append(" | ");
            }
            System.out.println(linha);
            linhas++;
        }
    }

    public static void pontuacaoAcumuladaPorAluno(Connection conn) throws SQLException {
        String sql =
                "SELECT a.Matricula, a.Nome, COALESCE(SUM(v.PontosAprovados), 0) AS PontuacaoAcumulada " +
                        "FROM Aluno a " +
                        "LEFT JOIN AtividadeACC at ON at.Aluno_idAluno = a.idAluno " +
                        "LEFT JOIN Validacao v ON v.AtividadeACC_idAtividadeACC = at.idAtividadeACC AND v.StatusValidacao = 'APROVADO' " +
                        "GROUP BY a.idAluno, a.Matricula, a.Nome " +
                        "ORDER BY PontuacaoAcumulada DESC";
        executarEImprimirCru(conn, sql);
    }

    public static void alunosComPontuacaoMinimaAtingida(Connection conn) throws SQLException {
        String sql =
                "SELECT a.Matricula, a.Nome, c.NomeCurso, c.CargaHorariaExigida, " +
                        "COALESCE(SUM(v.PontosAprovados), 0) AS PontuacaoAcumulada " +
                        "FROM Aluno a " +
                        "JOIN Curso c ON c.idCurso = a.Curso_idCurso " +
                        "LEFT JOIN AtividadeACC at ON at.Aluno_idAluno = a.idAluno " +
                        "LEFT JOIN Validacao v ON v.AtividadeACC_idAtividadeACC = at.idAtividadeACC AND v.StatusValidacao = 'APROVADO' " +
                        "GROUP BY a.idAluno, a.Matricula, a.Nome, c.NomeCurso, c.CargaHorariaExigida " +
                        "HAVING COALESCE(SUM(v.PontosAprovados), 0) >= c.CargaHorariaExigida " +
                        "ORDER BY PontuacaoAcumulada DESC";
        executarEImprimirCru(conn, sql);
    }

    public static void alunosComPendencias(Connection conn) throws SQLException {
        String sql =
                "SELECT a.Matricula, a.Nome, c.CargaHorariaExigida, " +
                        "COALESCE(SUM(v.PontosAprovados), 0) AS PontuacaoAcumulada, " +
                        "(c.CargaHorariaExigida - COALESCE(SUM(v.PontosAprovados), 0)) AS Faltam " +
                        "FROM Aluno a " +
                        "JOIN Curso c ON c.idCurso = a.Curso_idCurso " +
                        "LEFT JOIN AtividadeACC at ON at.Aluno_idAluno = a.idAluno " +
                        "LEFT JOIN Validacao v ON v.AtividadeACC_idAtividadeACC = at.idAtividadeACC AND v.StatusValidacao = 'APROVADO' " +
                        "GROUP BY a.idAluno, a.Matricula, a.Nome, c.CargaHorariaExigida " +
                        "HAVING COALESCE(SUM(v.PontosAprovados), 0) < c.CargaHorariaExigida " +
                        "ORDER BY Faltam DESC";
        executarEImprimirCru(conn, sql);
    }

    public static void categoriasMaisFrequentes(Connection conn) throws SQLException {
        String sql =
                "SELECT ca.TipoCategoria, COUNT(at.idAtividadeACC) AS QtdAtividades " +
                        "FROM CategoriaAcc ca " +
                        "JOIN TipoACC t ON t.CategoriaAcc_idCategoriaAcc = ca.idCategoriaAcc " +
                        "JOIN AtividadeACC at ON at.TipoACC_idTipoACC = t.idTipoACC " +
                        "GROUP BY ca.idCategoriaAcc, ca.TipoCategoria " +
                        "ORDER BY QtdAtividades DESC";
        executarEImprimirCru(conn, sql);
    }

    public static void documentosPorAluno(Connection conn, String matricula) throws SQLException {
        String sql =
                "SELECT a.Nome, d.NomeArquivo, d.TipoDocumento, d.DataEnvio, i.Nome AS InstituicaoEmissora " +
                        "FROM Documento d " +
                        "JOIN AtividadeACC at ON at.idAtividadeACC = d.AtividadeACC_idAtividadeACC " +
                        "JOIN Aluno a ON a.idAluno = at.Aluno_idAluno " +
                        "JOIN Instituicao i ON i.idInstituicao = d.Instituicao_idInstituicao " +
                        "WHERE a.Matricula = ? " +
                        "ORDER BY d.DataEnvio DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            
            try (ResultSet rs = stmt.executeQuery()) {
                imprimirResultSetCru(rs);
            }
        }
    }

    public static void atividadesAguardandoValidacao(Connection conn) throws SQLException {
        String sql =
                "SELECT a.Nome AS Aluno, at.Titulo, at.DataRealizacao, v.StatusValidacao " +
                        "FROM AtividadeACC at " +
                        "JOIN Aluno a ON a.idAluno = at.Aluno_idAluno " +
                        "LEFT JOIN Validacao v ON v.AtividadeACC_idAtividadeACC = at.idAtividadeACC " +
                        "WHERE v.StatusValidacao IS NULL OR v.StatusValidacao IN ('PENDENTE', 'EM_ANALISE') " +
                        "ORDER BY at.DataRealizacao";
        executarEImprimirCru(conn, sql);
    }

    public static void tiposComMaiorParticipacao(Connection conn) throws SQLException {
        String sql =
                "SELECT t.NomeTipo, COUNT(at.idAtividadeACC) AS QtdParticipacoes " +
                        "FROM TipoACC t " +
                        "LEFT JOIN AtividadeACC at ON at.TipoACC_idTipoACC = t.idTipoACC " +
                        "GROUP BY t.idTipoACC, t.NomeTipo " +
                        "ORDER BY QtdParticipacoes DESC";
        executarEImprimirCru(conn, sql);
    }

    public static void atividadesPorPeriodoLetivo(Connection conn) throws SQLException {
        String sql =
                "SELECT p.Ano, p.Semestre, COUNT(at.idAtividadeACC) AS QtdAtividades " +
                        "FROM PeriodoLetivo p " +
                        "LEFT JOIN AtividadeACC at ON at.PeriodoLetivo_idPeriodo = p.idPeriodo " +
                        "GROUP BY p.idPeriodo, p.Ano, p.Semestre " +
                        "ORDER BY p.Ano, p.Semestre";
        executarEImprimirCru(conn, sql);
    }

    public static void instituicoesMaisFrequentes(Connection conn) throws SQLException {
        String sql =
                "SELECT i.Nome, i.TipoEntidade, COUNT(d.idDocumento) AS QtdDocumentos " +
                        "FROM Instituicao i " +
                        "JOIN Documento d ON d.Instituicao_idInstituicao = i.idInstituicao " +
                        "GROUP BY i.idInstituicao, i.Nome, i.TipoEntidade " +
                        "ORDER BY QtdDocumentos DESC";
        executarEImprimirCru(conn, sql);
    }

    public static void professoresQueMaisValidaram(Connection conn) throws SQLException {
        String sql =
                "SELECT p.Nome, p.Departamento, COUNT(v.idValidacao) AS QtdValidacoes " +
                        "FROM Professor p " +
                        "JOIN Validacao v ON v.Professor_idProfessor = p.idProfessor " +
                        "GROUP BY p.idProfessor, p.Nome, p.Departamento " +
                        "ORDER BY QtdValidacoes DESC";
        executarEImprimirCru(conn, sql);
    }

    public static void atividadesRejeitadas(Connection conn) throws SQLException {
        String sql =
                "SELECT a.Nome AS Aluno, at.Titulo, v.DataValidacao, p.Nome AS ProfessorResponsavel " +
                        "FROM Validacao v " +
                        "JOIN AtividadeACC at ON at.idAtividadeACC = v.AtividadeACC_idAtividadeACC " +
                        "JOIN Aluno a ON a.idAluno = at.Aluno_idAluno " +
                        "JOIN Professor p ON p.idProfessor = v.Professor_idProfessor " +
                        "WHERE v.StatusValidacao = 'REJEITADO' " +
                        "ORDER BY v.DataValidacao DESC";
        executarEImprimirCru(conn, sql);
    }

    public static void executarSQLPersonalizada(Connection conn, Scanner sc) {
        System.out.println("\n========================================");
        System.out.println(" TERMINAL SQL");
        System.out.println("========================================");
        System.out.println("Digite ou cole sua consulta SQL.");
        System.out.println("Você pode utilizar várias linhas.");
        System.out.println("A consulta será executada quando uma linha terminar com ';'");
        System.out.println();

        StringBuilder sqlBuilder = new StringBuilder();

        while (true) {
            String linha = sc.nextLine();
            sqlBuilder.append(linha).append("\n");
            if (linha.trim().endsWith(";")) {
                break;
            }
        }

        String sql = sqlBuilder.toString().trim();

        if (sql.endsWith(";")) {
            sql = sql.substring(0, sql.length() - 1);
        }

        if (sql.isEmpty()) {
            System.out.println("Nenhum comando informado.");
            return;
        }

        try (Statement stmt = conn.createStatement()) {
            boolean possuiResultado = stmt.execute(sql);

            if (possuiResultado) {
                try (ResultSet rs = stmt.getResultSet()) {
                    ResultSetMetaData meta = rs.getMetaData();
                    int colunas = meta.getColumnCount();

                    for (int i = 1; i <= colunas; i++) {
                        System.out.printf("%-25s", meta.getColumnLabel(i));
                    }

                    System.out.println();
                    System.out.println("-".repeat(Math.min(colunas * 25, 150)));

                    int linhas = 0;
                    while (rs.next()) {
                        for (int i = 1; i <= colunas; i++) {
                            Object valor = rs.getObject(i);
                            System.out.printf("%-25s", valor == null ? "NULL" : valor);
                        }
                        System.out.println();
                        linhas++;
                    }

                    System.out.println("-".repeat(Math.min(colunas * 25, 150)));
                    System.out.println("Total de linhas retornadas: " + linhas);
                }
            } else {
                System.out.println();
                System.out.println("Comando executado com sucesso.");
                System.out.println("Linhas afetadas: " + stmt.getUpdateCount());
            }

        } catch (SQLException e) {
            System.out.println();
            System.out.println("Erro ao executar SQL:");
            System.out.println(e.getMessage());
        }
    }
}
