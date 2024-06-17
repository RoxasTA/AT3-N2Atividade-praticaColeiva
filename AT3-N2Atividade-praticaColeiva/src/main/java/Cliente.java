import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    private static final String HOST = "localhost";
    private static final int PORTA = 1337;

    private static final String OPCAO_LISTAR_LIVROS = "1";
    private static final String OPCAO_ALUGAR_LIVRO = "2";
    private static final String OPCAO_DEVOLVER_LIVRO = "3";
    private static final String OPCAO_CADASTRAR_LIVRO = "4";
    private static final String OPCAO_SAIR = "5";

    private static final String COMANDO_LISTAR = "listar";
    private static final String COMANDO_ALUGAR = "alugar";
    private static final String COMANDO_DEVOLVER = "devolver";
    private static final String COMANDO_CADASTRAR = "cadastrar";
    private static final String COMANDO_SAIR = "sair";

    //main
    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORTA);
             BufferedReader leitorServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter escritorServidor = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            String opcaoUsuario;
            do {
                exibirMenu();
                opcaoUsuario = scanner.nextLine().trim();

                switch (opcaoUsuario) {
                    case OPCAO_LISTAR_LIVROS:
                        listarLivros(escritorServidor, leitorServidor);
                        break;
                    case OPCAO_ALUGAR_LIVRO:
                        alugarLivro(escritorServidor, leitorServidor, scanner);
                        break;
                    case OPCAO_DEVOLVER_LIVRO:
                        devolverLivro(escritorServidor, leitorServidor, scanner);
                        break;
                    case OPCAO_CADASTRAR_LIVRO:
                        cadastrarLivro(escritorServidor, leitorServidor, scanner);
                        break;
                    case OPCAO_SAIR:
                        escritorServidor.println(COMANDO_SAIR);
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } while (!opcaoUsuario.equals(OPCAO_SAIR));

        } catch (IOException e) {
            System.err.println("Erro de I/O: " + e.getMessage());
        }
    }

    // Método para exibir o menu
    private static void exibirMenu() {
        System.out.println("Biblioteca:");
        System.out.println("1. Listar livros");
        System.out.println("2. Alugar livro");
        System.out.println("3. Devolver livro");
        System.out.println("4. Cadastrar livro");
        System.out.println("5. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // Método para listar livros
    private static void listarLivros(PrintWriter escritorServidor, BufferedReader leitorServidor) throws IOException {
        escritorServidor.println(COMANDO_LISTAR);
        String respostaServidor;
        while ((respostaServidor = leitorServidor.readLine()) != null) {
            if (respostaServidor.isEmpty()) break;
            System.out.println(respostaServidor);
        }
    }

    // Método para alugar livro
    private static void alugarLivro(PrintWriter escritorServidor, BufferedReader leitorServidor, Scanner scanner) throws IOException {
        System.out.print("Nome do livro: ");
        String nomeLivro = scanner.nextLine().trim();
        if (nomeLivro.isEmpty()) {
            System.out.println("Nome do livro não pode ser vazio.");
            return;
        }
        escritorServidor.println(COMANDO_ALUGAR + "#" + nomeLivro);
        String respostaServidor = leitorServidor.readLine();
        System.out.println(respostaServidor);
    }

    // Método para devolver livro
    private static void devolverLivro(PrintWriter escritorServidor, BufferedReader leitorServidor, Scanner scanner) throws IOException {
        System.out.print("Nome do livro: ");
        String nomeLivro = scanner.nextLine().trim();
        if (nomeLivro.isEmpty()) {
            System.out.println("Nome do livro não pode ser vazio.");
            return;
        }
        escritorServidor.println(COMANDO_DEVOLVER + "#" + nomeLivro);
        String respostaServidor = leitorServidor.readLine();
        System.out.println(respostaServidor);
    }

    // Método para cadastrar livro
    private static void cadastrarLivro(PrintWriter escritorServidor, BufferedReader leitorServidor, Scanner scanner) throws IOException {
        System.out.print("Autor: ");
        String autorLivro = scanner.nextLine().trim();
        if (autorLivro.isEmpty()) {
            System.out.println("O autor não pode ser vazio.");
            return;
        }

        System.out.print("Nome: ");
        String nomeLivro = scanner.nextLine().trim();
        if (nomeLivro.isEmpty()) {
            System.out.println("O nome não pode ser vazio.");
            return;
        }

        System.out.print("Gênero: ");
        String generoLivro = scanner.nextLine().trim();
        if (generoLivro.isEmpty()) {
            System.out.println("O gênero não pode ser vazio.");
            return;
        }

        System.out.print("Número de exemplares: ");
        int numeroExemplares;
        try {
            numeroExemplares = Integer.parseInt(scanner.nextLine().trim());
            if (numeroExemplares < 0) {
                System.out.println("O número de exemplares não pode ser negativo.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Número de exemplares inválido.");
            return;
        }

        String informacoesLivro = autorLivro + "," + nomeLivro + "," + generoLivro + "," + numeroExemplares;
        escritorServidor.println(COMANDO_CADASTRAR + "#" + informacoesLivro);
        String respostaServidor = leitorServidor.readLine();
        System.out.println(respostaServidor);
    }
}
