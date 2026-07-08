import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

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
                        case "1": ConsultasGerenciais.pontuacaoAcumuladaPorAluno(conn); break;
                        case "2": ConsultasGerenciais.alunosComPontuacaoMinimaAtingida(conn); break;
                        case "3": ConsultasGerenciais.alunosComPendencias(conn); break;
                        case "4": ConsultasGerenciais.categoriasMaisFrequentes(conn); break;
                        case "5":
                            System.out.print("Digite a matrícula do aluno: ");
                            String matricula = sc.nextLine();
                            ConsultasGerenciais.documentosPorAluno(conn, matricula);
                            break;
                        case "6": ConsultasGerenciais.atividadesAguardandoValidacao(conn); break;
                        case "7": ConsultasGerenciais.tiposComMaiorParticipacao(conn); break;
                        case "8": ConsultasGerenciais.atividadesPorPeriodoLetivo(conn); break;
                        case "9": ConsultasGerenciais.instituicoesMaisFrequentes(conn); break;
                        case "10": ConsultasGerenciais.professoresQueMaisValidaram(conn); break;
                        case "11": ConsultasGerenciais.atividadesRejeitadas(conn); break;
                        case "12": ConsultasGerenciais.executarSQLPersonalizada(conn, sc);break;

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
        System.out.println("   Consultas Gerenciais");
        System.out.println("===================================================");
        System.out.println("1  - Pontuação acumulada por aluno");
        System.out.println("2  - Alunos que atingiram a pontuação mínima");
        System.out.println("3  - Alunos com pendências");
        System.out.println("4  - Categorias de ACC mais frequentes");
        System.out.println("5  - Documentos apresentados por um aluno");
        System.out.println("6  - Atividades aguardando validação");
        System.out.println("7  - Tipos de ACC com maior participação");
        System.out.println("8  - Atividades por período letivo");
        System.out.println("9  - Instituições que mais emitiram documentos");
        System.out.println("10 - Professores que mais validaram atividades");
        System.out.println("11 - Atividades rejeitadas");
        System.out.println("0  - Sair");
        System.out.print("Escolha uma opção: ");
    }
}
