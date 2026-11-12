import java.io.Serializable;

public class Livro implements Serializable, Comparable<Livro> {

    private static final long serialVersionUID = 1L;

    private String titulo;
    private String autor;
    private int anoPublicacao;
    private boolean emprestado;
    private Usuario usuarioEmprestimo;

    public Livro(String titulo, String autor, int anoPublicacao) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.emprestado = false;
        this.usuarioEmprestimo = null;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public boolean isEmprestado() {
        return emprestado;
    }

    public Usuario getUsuarioEmprestimo() {
        return usuarioEmprestimo;
    }

    public void emprestar(Usuario usuario) {
        this.emprestado = true;
        this.usuarioEmprestimo = usuario;
    }

    public void devolver() {
        this.emprestado = false;
        this.usuarioEmprestimo = null;
    }

    @Override
    public int compareTo(Livro outroLivro) {
        return this.titulo.compareToIgnoreCase(outroLivro.titulo);
    }

    @Override
    public String toString() {

        String situacao = emprestado
                ? "Emprestado para: " + usuarioEmprestimo.getNome()
                 : "Disponível";

        return "Título: " + titulo
                + " | Autor: " + autor
                + " | Ano: " + anoPublicacao
                + " | Situação: " + situacao;
    }
}
