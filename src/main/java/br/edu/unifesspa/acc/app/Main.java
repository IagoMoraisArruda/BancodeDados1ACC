package br.edu.unifesspa.acc.app;

import br.edu.unifesspa.acc.dao.*;
import br.edu.unifesspa.acc.model.Curso;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        CursoDAO cursoDAO = new CursoDAO();
        AlunoDAO alunoDAO = new AlunoDAO();
        TipoACCDAO tipoACCDAO = new TipoACCDAO();
        ConsultasGerenciaisDAO consultas = new ConsultasGerenciaisDAO();

        try {
            Curso curso = new Curso(null, "Sistemas de Informação", 3200);
            cursoDAO.save(curso);
            System.out.println("Curso criado: " + curso);

            cursoDAO.findById(curso.getIdCurso())
                    .ifPresent(c -> System.out.println("Curso encontrado: " + c));

            curso.setCargaHorariaExigida(3300);
            cursoDAO.update(curso);
            System.out.println("Curso atualizado: " + curso);

            List<Curso> cursos = cursoDAO.findAll();
            System.out.println("Total de cursos cadastrados: " + cursos.size());

            List<?> alunosOk = alunoDAO.findAlunosComPontuacaoMinimaAtingida(51.0);
            System.out.println("Alunos com pontuacao minima (51 pts) atingida: " + alunosOk.size());

            Map<String, Long> volumePorTipo = tipoACCDAO.findVolumeParticipacaoPorTipo();
            System.out.println("Volume de participacao por tipo de ACC: " + volumePorTipo);

            Map<String, Long> categoriasFrequentes = consultas.findCategoriasMaisFrequentes();
            System.out.println("Categorias mais frequentes: " + categoriasFrequentes);

            boolean removido = cursoDAO.deleteById(curso.getIdCurso());
            System.out.println("Curso removido: " + removido);

        } catch (SQLException e) {
            System.err.println("Erro ao acessar o banco de dados Azure: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
