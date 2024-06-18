# AT3/N2 - Atividade Prática Coletiva - Bimestre N2

Este é um projeto Java que implementa um servidor utilizando sockets para controlar um registro/cadastro de livros de uma biblioteca. O servidor é capaz de listar, alugar, devolver e cadastrar livros. As operações são realizadas por um cliente socket que se comunica com o servidor.

## Funcionalidades Principais

- **Listagem de Livros**: Exibe a lista de livros disponíveis na biblioteca.
- **Aluguel de Livros**: Permite que um usuário alugue um livro, diminuindo o número de exemplares disponíveis.
- **Devolução de Livros**: Permite que um usuário devolva um livro, aumentando o número de exemplares disponíveis.
- **Cadastro de Livros**: Permite adicionar novos livros ao registro da biblioteca.

## Estrutura do Projeto

A estrutura do projeto é a seguinte:


### Arquivos

#### `Book.java`
Define a classe `Book`, que representa um livro na biblioteca com os seguintes atributos:
- `author`: o autor do livro.
- `name`: o nome do livro.
- `genre`: o gênero do livro.
- `copies`: o número de exemplares disponíveis.

#### `BooksWrapper.java`
Um wrapper para a lista de livros, utilizado para facilitar a serialização e deserialização dos livros em JSON.

#### `Cliente.java`
Representa um cliente que se conecta ao servidor e envia comandos para realizar operações na biblioteca.

#### `ClientHandler.java`
Implementa `Runnable` e gerencia a comunicação com os clientes. Processa comandos como listar, registrar, alugar, devolver, buscar, atualizar e excluir livros.

#### `LibraryServer.java`
A classe principal que inicia o servidor e gerencia as conexões dos clientes. Ele carrega e salva os livros de um arquivo JSON e cria novas threads para cada cliente.

## Arquivo JSON

O arquivo JSON inicial contém 10 livros e serve como a base de dados da biblioteca. Todas as alterações (cadastro, aluguel e devolução) são refletidas neste arquivo.

## Requisitos

- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Apache Maven](https://maven.apache.org/install.html)

## Configuração do Ambiente

1. **Instalar o JDK 17**:
    - Verifique a instalação do JDK com:
      ```sh
      java -version
      javac -version
      ```

2. **Instalar o Maven**:
    - Verifique a instalação do Maven com:
      ```sh
      mvn -v
      ```

## Como Executar o Projeto

1. **Clone o Repositório para sua Máquina Local**:
    ```sh
    git clone https://github.com/yourusername/AT3_N2_AtividadePraticaColetiva.git
    cd AT3_N2_AtividadePraticaColetiva
    ```

2. **Compilar o Projeto**:
    ```sh
    mvn clean install
    ```

3. **Executar o Servidor**:
    ```sh
    mvn exec:java -Dexec.mainClass="yourpackage.LibraryServer"
    ```

4. **Executar o Cliente**:
    ```sh
    mvn exec:java -Dexec.mainClass="yourpackage.Cliente"
    ```

## Uso

Você pode interagir com o servidor através de comandos enviados pelo cliente. Aqui estão alguns exemplos de comandos:

1. **Listar Livros**:
    ```bash
    list
    ```

2. **Registrar um Livro**:
    ```bash
    register#Autor#Nome#Gênero#5
    ```

3. **Alugar um Livro**:
    ```bash
    rent#Nome do Livro
    ```

4. **Devolver um Livro**:
    ```bash
    return#Nome do Livro
    ```

5. **Sair**:
    ```bash
    exit
    ```

## Contribuição

Se você quiser contribuir com este projeto, sinta-se à vontade para enviar um pull request ou abrir uma issue.

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## Colaboradores
Victor Assis Oliveira
Samuel Mendonça Toledo de Lima
