# 🎓 Sistema de Gestão de ACC — Unifesspa

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" />
  <img src="https://img.shields.io/badge/JDBC-Puro-blue?style=for-the-badge" alt="JDBC Puro" />
</p>

Uma aplicação desktop em Java (**JDBC puro, sem ORM**) integrada ao banco de dados MySQL para gerenciar Atividades Curriculares Complementares (ACC) de alunos. O sistema cobre desde o cadastro de cursos e documentos comprobatórios até a validação por professores e consultas gerenciais complexas.

> 📌 **Contexto do Projeto:** Desenvolvido como o **Produto 2** da disciplina de Banco de Dados I, da Faculdade de Sistemas de Informação (Unifesspa), sob a orientação do Prof. Dr. Hugo Kuribayashi. *(O Produto 1 consiste no modelo de banco de dados em arquivo `.mwb` separado)*.

---

## 🗺️ Sumário do Documento
* 📦 Estrutura do Projeto
* 🎮 Modos de Execução (Classes Main)
* 📊 Consultas Gerenciais Disponíveis
* 🔒 Segurança das Credenciais

---

## 📦 Estrutura do Projeto

```text
ProdutoACC/
└── src/
    ├── ConexaoBD.java           --> Gerencia a conexão JDBC com o banco (preencher antes de rodar)
    ├── Cadastros.java           --> Contém os 10 métodos de INSERT (um para cada tabela)
    ├── ConsultasGerenciais.java --> Implementa as consultas complexas (SELECT) + terminal SQL livre
    ├── Main.java                --> Menu ENXUTO (Apenas Consultas. Ideal para a apresentação)
    └── mainCadastros.java       --> Menu COMPLETO (Cadastros 1-10 + Consultas 11-22)

  ```
  > [!IMPORTANT]
> Este repositório **não inclui** o driver JDBC do MySQL (`mysql-connector-j-x.x.x.jar`). Ele precisa ser guardado localmente na pasta do projeto conforme os passos abaixo.

## 🛠️ Pré-requisitos

* **JDK 17+** instalado e configurado no ambiente.
* **IntelliJ IDEA** (Community ou Ultimate).
* Instância de banco de dados **MySQL 8.x** ativa contendo as 10 tabelas criadas a partir do modelo do Produto 1 (seja local ou hospedada em nuvem, ex: *Azure Database for MySql*).

---

## 🚀 Como Configurar e Executar o Projeto

### Passo 1 — Organizar o Driver JDBC no Projeto
Como você já possui o arquivo do driver baixado, coloque-o dentro da estrutura do projeto para mantê-lo organizado:

1. Na raiz do projeto `ProdutoACC/`, crie uma pasta chamada `lib` (caso ela já não exista).
2. Cole o seu arquivo `mysql-connector-j-x.x.x.jar` dentro dessa pasta `lib`.

### Passo 2 — Adicionar o Driver como Biblioteca no IntelliJ
O IntelliJ precisa mapear esse arquivo para permitir a compilação das classes de conexão.

1. Abra o projeto dentro do IntelliJ IDEA.
2. Acesse o menu **File** ➡️ **Project Structure** (ou utilize o atalho `Ctrl + Alt + Shift + S`).
3. No menu lateral esquerdo, selecione a aba **Libraries**.
4. Clique no botão de adição **"+"** no topo da lista ➡️ escolha **Java**.
5. Navegue até a pasta `lib` do seu projeto e selecione o arquivo `mysql-connector-j-x.x.x.jar`.
6. Clique em **OK** e depois em **Apply**.

> [!NOTE]
> Sem esse passo, o projeto não compilará, pois a classe `ConexaoBD` depende diretamente deste driver para se comunicar com o banco de dados.

### Passo 3 — Configurar a conexão com o banco
Abra o arquivo `src/ConexaoBD.java`. Os campos com os parâmetros de conexão estão propositalmente vazios por questões de privacidade:

```java
private static final String HOST = "";     // ex: [acc-unifesspa-bd.mysql.database.azure.com:3306/nome_do_banco](https://acc-unifesspa-bd.mysql.database.azure.com:3306/nome_do_banco)
private static final String USUARIO = "";  // ex: adminacc1
private static final String SENHA = "";    // senha do usuário correspondente
