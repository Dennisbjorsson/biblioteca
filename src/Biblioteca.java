import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Biblioteca {

    private List<Livro> livros;
    private List<Usuario> usuarios;

    public Biblioteca() {
        this.livros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }


    public void cadastrarLivro(Livro livro) {
         livros.add(livro);
        System.out.println("Livro cadastrado com sucesso!");
    }


    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        System.out.println("Usuário cadastrado com sucesso!");
    }


    public void listarLivros() {
        livros.forEach(System.out::println);
    }


    public void listarUsuarios() {
        usuarios.forEach(System.out::println);
    }


    public Optional<Livro> buscarLivro(String titulo) {

        return livros.stream()
                .filter(livro -> livro.getTitulo().equalsIgnoreCase(titulo))
                .findFirst();
    }


    public List<Livro> ordenarPorTitulo() {

        return livros.stream()
                .sorted()
                .collect(Collectors.toList());
    }


    public List<Livro> ordenarPorAno() {

            return livros.stream()
                .sorted(Comparator.comparingInt(
                        Livro::getAnoPublicacao))
                .collect(Collectors.toList());
    }

    public List<Livro> listarPorAutor(String autor) {

        return livros.stream()
                .filter(livro -> livro.getAutor().equalsIgnoreCase(autor))
                .collect(Collectors.toList());
    }


    public Optional<Usuario> buscarUsuarioPorEmail(String email) {

        return usuarios.stream()
                .map(usuario -> usuario)
                .filter(usuario -> usuario.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public Map<String, List<Livro>> agruparPorAutor() {

        return livros.stream()
                .collect(Collectors.groupingBy(Livro::getAutor));
    }


    public List<Livro> listarDisponiveis() {

        return livros.stream()
                .filter(livro -> !livro.isEmprestado())
                .collect(Collectors.toList());
    }


    public List<Livro> listarEmprestados() {

        return livros.stream()
                .filter(Livro::isEmprestado)
                .collect(Collectors.toList());
    }


    public void realizarEmprestimo(String titulo, String email)
            throws LivrosIndisponiveis {

        Livro livro = buscarLivro(titulo)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado!"));

        Usuario usuario = buscarUsuarioPorEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado!"));

        if (livro.isEmprestado()) {
             throw new LivrosIndisponiveis(
                    "O livro " + titulo + " já está emprestado!");
        }

        livro.emprestar(usuario);

        System.out.println("Empréstimo realizado com sucesso!");
        System.out.println("Livro: " + livro.getTitulo());
        System.out.println("Usuário: " + usuario.getNome());
    }


    public void devolverLivro(String titulo) {

        Livro livro = buscarLivro(titulo)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado!"));

        if (!livro.isEmprestado()) {
            System.out.println("Esse livro já está disponível.");
            return;
        }

        livro.devolver();

        System.out.println("Livro devolvido com sucesso!");
    }

    // salvar livros

    public void salvarLivros() {

        try (ObjectOutputStream saida =
                     new ObjectOutputStream(new FileOutputStream("livros.dat"))) {

            saida.writeObject(livros);

              System.out.println("Livros salvos com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro ao salvar livros: " + e.getMessage());
        }
    }


    public void salvarUsuarios() {

        try (ObjectOutputStream saida =
                     new ObjectOutputStream( new FileOutputStream("usuarios.dat"))) {

            saida.writeObject(usuarios);

            System.out.println("Usuários salvos com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }


    @SuppressWarnings("unchecked")
    public void carregarLivros() {

        File arquivo = new File("livros.dat");

        if (!arquivo.exists()) {
            return;
        }

        try (ObjectInputStream entrada =
                     new ObjectInputStream(new FileInputStream(arquivo))) {

            livros = (List<Livro>) entrada.readObject();

             System.out.println("Livros carregados com sucesso!");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar livros: " + e.getMessage());
        }
    }


    @SuppressWarnings("unchecked")
    public void carregarUsuarios() {

        File arquivo = new File("usuarios.dat");

        if (!arquivo.exists()) {
            return;
        }

        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(arquivo))) {

            usuarios = (List<Usuario>) entrada.readObject();

            System.out.println("Usuários carregados com sucesso!");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }


    public void salvarDados() {
        salvarLivros();
        salvarUsuarios();
    }


    public void carregarDados() {
          carregarUsuarios();
        carregarLivros();
    }
}