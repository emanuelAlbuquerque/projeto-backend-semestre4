package projeto_back_end.projeto_back_end.Models;

import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity(name = "produtos")
public class Produto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "produto_id")
  private Long id;

  @NotNull(message = "O código não pode ser vazio")
  @Column(name = "codigo_produto", columnDefinition = "uuid", nullable = false)
  private UUID codigo_produto;

  @Length(max = 80, message = "O nome não pode ultrapassar 60 caracteres")
  @NotBlank(message = "O nome não pode ser vazio")
  @Column(name = "nome", columnDefinition = "VARCHAR(60)", nullable = false)
  private String nome;

  @Length(max = 80, message = "O descrição não pode ultrapassar 255 caracteres")
  @NotBlank(message = "A descrição não pode ser vazia")
  @Column(name = "descricao", columnDefinition = "VARCHAR(255)", nullable = false)
  private String descricao;

  @Positive(message = "O preço deve ser maior que 0")
  @NotNull(message = "O preço não pode ser vazio")
  @Column(name = "preco", columnDefinition = "double precision", nullable = false)
  private Double preco;

  @NotBlank(message = "O tamanho não pode ser vazio")
  @Column(name = "tamanho", columnDefinition = "VARCHAR(10)", nullable = false)
  private String tamanho;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "categoria_produto", joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
  private List<Categoria> categorias;

  @OneToMany(mappedBy = "produto", fetch = FetchType.LAZY)
  private List<ItemPedido> itensPedidos;
}