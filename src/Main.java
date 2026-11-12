import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Biblioteca biblioteca = new Biblioteca();

        try {

            biblioteca.carregarDados();

            if (biblioteca.ordenarPorTitulo().isEmpty()) {

                biblioteca.cadastrarLivro(
                        new Livro("O Hobbit", "J.R.R. Tolkien", 1937));

                biblioteca.cadastrarLivro(
                        new Livro("1984", "George Orwell", 1949));

                biblioteca.cadastrarLivro(
                         new Livro("Revolução do bichos", "George Orwell", 1899));

                biblioteca.cadastrarLivro(
                          new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", 1954));
            }


            biblioteca.cadastrarUsuario(
                    new Usuario("Dennis A", "dennis@email.com"));

            biblioteca.cadastrarUsuario(
                    new Usuario("Carlos ", "carlos@email.com"));

            // listar livros
            System.out.println("\n=== TODOS OS LIVROS ===");
            biblioteca.listarLivros();

            // listar usuario

            System.out.println("\n=== USUÁRIOS CADASTRADOS ===");
            biblioteca.listarUsuarios();

            // buscar livro
            System.out.println("\n=== BUSCAR LIVRO ===");

            biblioteca.buscarLivro("O Hobbit")
                    .ifPresentOrElse(
                            System.out::println, () -> System.out.println("Livro não encontrado!")
                    );


            System.out.println("\n=== LIVROS ORDENADOS POR TÍTULO ===");

            biblioteca.ordenarPorTitulo()
                    .forEach(System.out::println);


            System.out.println("\n=== LIVROS ORDENADOS POR ANO ===");

            biblioteca.ordenarPorAno()
                    .forEach(System.out::println);


            System.out.println("\n=== LIVROS DE J.R.R. TOLKIEN ===");

            biblioteca.listarPorAutor("J.R.R. Tolkien")
                    .forEach(System.out::println);


            System.out.println("\n=== BUSCAR USUÁRIO ===");

            biblioteca.buscarUsuarioPorEmail("dennis@email.com")
                    .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("Usuário não encontrado!")
                    );


            System.out.println("\n=== LIVROS AGRUPADOS POR AUTOR ===");

            Map<String, List<Livro>> grupos =
                    biblioteca.agruparPorAutor();

            grupos.forEach((autor, livros) -> {
                System.out.println("\nAutor: " + autor);
                livros.forEach(System.out::println);
            });


            System.out.println("\n=== REALIZAR EMPRÉSTIMO ===");

            biblioteca.realizarEmprestimo(
                    "O Hobbit",
                    "carlos@email.com"
            );


            System.out.println("\n=== TESTAR LIVRO INDISPONÍVEL ===");

            try {
                biblioteca.realizarEmprestimo(
                        "O Hobbit",
                        "dennis@email.com"
                );

            } catch (LivrosIndisponiveis e) {
                System.out.println("Erro: " + e.getMessage());
            }

            System.out.println("\n=== LIVROS EMPRESTADOS ===");
            biblioteca.listarEmprestados() .forEach(System.out::println);

            // devolver livro
            System.out.println("\n=== DEVOLUÇÃO ===");
            biblioteca.devolverLivro("O Hobbit");

            // listar livros disponiveis
            System.out.println("\n=== LIVROS DISPONÍVEIS ===");
            biblioteca.listarDisponiveis()
                    .forEach(System.out::println);

        } catch (Exception e) {

            System.out.println("Erro na execução: " + e.getMessage());

        } finally {

              System.out.println("\n=== FINALIZANDO SISTEMA ===");

            biblioteca.salvarDados();

            System.out.println("Dados salvos. Programa finalizado!");
        }
    }
}