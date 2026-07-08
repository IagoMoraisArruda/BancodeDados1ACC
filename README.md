# 🎓 Sistema de Gestão de ACC — Unifesspa

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" />
  <img src="https://img.shields.io/badge/JDBC-Puro-blue?style=for-the-badge" alt="JDBC Puro" />
</p>

Uma aplicação desktop em Java (**JDBC puro, sem o uso de ORMs**) integrada ao banco de dados MySQL para automatizar e gerenciar o fluxo de Atividades Curriculares Complementares (ACC) de alunos. O sistema cobre desde o cadastro de cursos e documentos comprobatórios até a validação por professores e emissão de relatórios gerenciais.

> 📌 **Contexto do Projeto:** Desenvolvido como o **Produto 2** da disciplina de Banco de Dados I, da Faculdade de Sistemas de Informação (Unifesspa), sob a orientação do Prof. Dr. Hugo Kuribayashi. *(O Produto 1 consiste no modelo conceitual/lógico em formato `.mwb`)*.

---

## 🗺️ Sumário
- [📦 Estrutura do Projeto](#-estrutura-do-projeto)
- [⚙️ Pré-requisitos](#%EF%B8%8F-pré-requisitos)
- [🚀 Guia de Configuração e Instalação](#-guia-de-configuração-e-instalação)
  - [Passo 1: Driver JDBC do MySQL](#passo-1-baixar-o-driver-jdbc-do-mysql)
  - [Passo 2: Configurar Biblioteca no IntelliJ](#passo-2-adicionar-o-driver-como-biblioteca-no-intellij)
  - [Passo 3: Credenciais do Banco](#passo-3-configurar-a-conexão-com-o-banco)
  - [Passo 4: Execução](#passo-4-rodar-o-projeto)
- [🎮 Modos de Execução (Classes Main)](#-modos-de-execução-classes-main)
- [📊 Consultas Gerenciais Disponíveis](#-consultas-gerenciais-disponíveis)
- [🔒 Segurança das Credenciais](#-segurança-das-credenciais)

---

## 📦 Estrutura do Projeto

```text
ProdutoACC/
└── src/
    ├── ConexaoBD.java           --> Gerencia a conexão JDBC com o banco (configurar antes de rodar)
    ├── Cadastros.java           --> Contém os 10 métodos de INSERT (um para cada tabela)
    ├── ConsultasGerenciais.java --> Implementa as 11 consultas complexas (SELECT) + terminal SQL livre
    ├── Main.java                --> Menu ENXUTO (Apenas Consultas. Ideal para a apresentação)
    └── mainCadastros.java       --> Menu COMPLETO (Cadastros 1-10 + Consultas 11-22)
