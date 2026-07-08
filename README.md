# Sistema de Gestao de ACC - Unifesspa

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" />
  <img src="https://img.shields.io/badge/JDBC-Puro-blue?style=for-the-badge" alt="JDBC Puro" />
</p>

Uma aplicação desktop em Java (**JDBC puro, sem ORM**) integrada ao banco de dados MySQL para gerenciar Atividades Curriculares Complementares (ACC) de alunos. O sistema cobre desde o cadastro de cursos e documentos comprobatórios até a validação por professores e consultas gerenciais complexas.

> **Contexto do Projeto:** Desenvolvido como o **Produto 2** da disciplina de Banco de Dados I, da Faculdade de Sistemas de Informação (Unifesspa), sob a orientação do Prof. Dr. Hugo Kuribayashi. *(O Produto 1 consiste no modelo de banco de dados em arquivo `.mwb` separado)*.

---

## Sumario
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Modos de Execucao](#modos-de-execucao)
- [Consultas Gerenciais Disponiveis](#consultas-gerenciais-disponiveis)
- [Seguranca das Credenciais](#seguranca-das-credenciais)

---

## Estrutura do Projeto

```text
ProdutoACC/
└── src/
    ├── ConexaoBD.java           --> Gerencia a conexão JDBC com o banco (preencher antes de rodar)
    ├── Cadastros.java           --> Contém os 10 métodos de INSERT (um para cada tabela)
    ├── ConsultasGerenciais.java --> Implementa as consultas complexas (SELECT) + terminal SQL livre
    ├── Main.java                --> Menu ENXUTO (Apenas Consultas. Ideal para a apresentação)
    └── mainCadastros.java       --> Menu COMPLETO (Cadastros 1-10 + Consultas 11-22)
