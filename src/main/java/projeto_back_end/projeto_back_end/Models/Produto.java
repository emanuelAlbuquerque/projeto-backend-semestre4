package projeto_back_end.projeto_back_end.Models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity(name = "produtos")
public class Produto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long produtoID;

  @Column(name = "codigo_produto", columnDefinition = "VARCHAR(60)", nullable = false)
  public String codigo_produto;

  @Column(name = "nome", columnDefinition = "VARCHAR(60)", nullable = false)
  public String nome;

  @Column(name = "descricao", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
  public String descricao;

  @Column(name = "preco", columnDefinition = "DOUBLE", nullable = false)
  public String preco;

  @Column(name = "tamanho", columnDefinition = "VARCHAR(10)", nullable = false)
  public String tamanho;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "categoria_produto", joinColumns = @JoinColumn(name = "produtoID"), inverseJoinColumns = @JoinColumn(name = "categoriaID"))
  List<Categoria> categorias;
}