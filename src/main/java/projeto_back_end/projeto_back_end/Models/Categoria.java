package projeto_back_end.projeto_back_end.Models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity(name = "categorias")
public class Categoria {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "categoria_id")
  private Long id;

  @Column(name = "nome", columnDefinition = "VARCHAR(40)", nullable = false)
  private String nome;

  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "categorias")
  private List<Produto> produtos;
}