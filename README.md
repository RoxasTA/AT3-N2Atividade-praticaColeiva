# AT3-N2Atividade-praticaColeiva

# Sistema de Biblioteca com Java Sockets

Este repositório contém um projeto desenvolvido para a disciplina [Nome da Disciplina] da [Nome da Instituição], que implementa um sistema de controle de biblioteca utilizando sockets em Java. O sistema permite listar, alugar, devolver e cadastrar livros.

## Estrutura do Projeto

O projeto é dividido nas seguintes classes principais:
- `Book.java`: Define a estrutura de dados para os livros da biblioteca.
- `BooksWrapper.java`: Gerencia as operações de leitura e escrita no arquivo JSON que armazena os dados dos livros.
- `LibraryServer.java`: Implementa o servidor que processa as solicitações dos clientes usando sockets.
- `Cliente.java`: Interface de cliente que permite ao usuário interagir com o servidor para realizar operações no sistema de biblioteca.
- `ClientHandler.java`: Gerencia a conexão de cada cliente com o servidor, processando as solicitações enviadas.

## Funcionalidades

- **Listagem de Livros**: Exibe todos os livros disponíveis na biblioteca.
- **Aluguel de Livros**: Permite alugar um livro se estiver disponível.
- **Devolução de Livros**: Registra a devolução de livros alugados.
- **Cadastro de Livros**: Adiciona novos livros ao sistema.

## Como Executar

### Pré-requisitos
Certifique-se de ter o Java 17 instalado em sua máquina.

### Instruções

1. Clone o repositório:
   ```bash
   git clone [URL do Repositório]
