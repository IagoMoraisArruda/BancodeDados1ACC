
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class mainCadastros {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (Connection conn = ConexaoBD.conectar()) {
            System.out.println("Conectado ao banco de dados com sucesso!");

            boolean rodando = true;
            while (rodando) {
                exibirMenu();
                String opcao = sc.nextLine();

                try {
                    switch (opcao) {
                        case "1": Cadastros.cadastrarCurso(conn, sc); break;
                        case "2": Cadastros.cadastrarAluno(conn, sc); break;
                        case "3": Cadastros.cadastrarPeriodoLetivo(conn, sc); break;
                        case "4": Cadastros.cadastrarCategoriaAcc(conn, sc); break;
                        case "5": Cadastros.cadastrarTipoAcc(conn, sc); break;
                        case "6": Cadastros.cadastrarInstituicao(conn, sc); break;
                        case "7": Cadastros.cadastrarProfessor(conn, sc); break;
                        case "8": Cadastros.cadastrarAtividadeAcc(conn, sc); break;
                        case "9": Cadastros.cadastrarDocumento(conn, sc); break;
                        case "10": Cadastros.cadastrarValidacao(conn, sc); break;
                        case "11": ConsultasGerenciais.pontuacaoAcumuladaPorAluno(conn); break;
                        case "12": ConsultasGerenciais.alunosComPontuacaoMinimaAtingida(conn); break;
                        case "13": ConsultasGerenciais.alunosComPendencias(conn); break;
                        case "14": ConsultasGerenciais.categoriasMaisFrequentes(conn); break;
                        case "15":
                            System.out.print("Digite a matrícula do aluno: ");
                            String matricula = sc.nextLine();
                            ConsultasGerenciais.documentosPorAluno(conn, matricula);
                            break;
                        case "16": ConsultasGerenciais.atividadesAguardandoValidacao(conn); break;
                        case "17": ConsultasGerenciais.tiposComMaiorParticipacao(conn); break;
                        case "18": ConsultasGerenciais.atividadesPorPeriodoLetivo(conn); break;
                        case "19": ConsultasGerenciais.instituicoesMaisFrequentes(conn); break;
                        case "20": ConsultasGerenciais.professoresQueMaisValidaram(conn); break;
                        case "21": ConsultasGerenciais.atividadesRejeitadas(conn); break;
                        case "22": ConsultasGerenciais.executarSQLPersonalizada(conn, sc);break;

                        case "0":
                            rodando = false;
                            System.out.println("Encerrando o programa...");
                            break;
                        default:
                            System.out.println("Opção inválida!");
                    }
                } catch (SQLException e) {
                    System.out.println("Erro ao executar operação no banco: " + e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.println("Valor numérico inválido. Tente novamente.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Não foi possível conectar ao banco de dados.");
            System.out.println("Detalhes: " + e.getMessage());
        }

        sc.close();
    }

    private static void exibirMenu() {
        System.out.println("\n===================================================");
        System.out.println("   SISTEMA DE GESTÃO DE ACC - Unifesspa");
        System.out.println("===================================================");
        System.out.println("--- CADASTROS ---");
        System.out.println("1  - Cadastrar Curso");
        System.out.println("2  - Cadastrar Aluno");
        System.out.println("3  - Cadastrar Período Letivo");
        System.out.println("4  - Cadastrar Categoria de ACC");
        System.out.println("5  - Cadastrar Tipo de ACC");
        System.out.println("6  - Cadastrar Instituição");
        System.out.println("7  - Cadastrar Professor");
        System.out.println("8  - Cadastrar Atividade ACC (do aluno)");
        System.out.println("9  - Cadastrar Documento (comprovante)");
        System.out.println("10 - Registrar Validação (professor avalia atividade)");
        System.out.println("--- CONSULTAS GERENCIAIS ---");
        System.out.println("11 - Pontuação acumulada por aluno");
        System.out.println("12 - Alunos que atingiram a pontuação mínima");
        System.out.println("13 - Alunos com pendências");
        System.out.println("14 - Categorias de ACC mais frequentes");
        System.out.println("15 - Documentos apresentados por um aluno");
        System.out.println("16 - Atividades aguardando validação");
        System.out.println("17 - Tipos de ACC com maior participação");
        System.out.println("18 - Atividades por período letivo");
        System.out.println("19 - Instituições que mais emitiram documentos");
        System.out.println("20 - Professores que mais validaram atividades");
        System.out.println("21 - Atividades rejeitadas");
        System.out.println("22 - Executar SQL personalizada");
        System.out.println("0  - Sair");
        System.out.print("Escolha uma opção: ");
    }
}
