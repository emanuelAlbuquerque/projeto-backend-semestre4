package projeto_back_end.projeto_back_end.Models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity(name = "categorias")
public class Categoria {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long categoriaID;

  @Column(name = "nome", columnDefinition = "VARCHAR(40)", nullable = false)
  public String nome;

  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "categorias")
  List<Produto> produtos;
}