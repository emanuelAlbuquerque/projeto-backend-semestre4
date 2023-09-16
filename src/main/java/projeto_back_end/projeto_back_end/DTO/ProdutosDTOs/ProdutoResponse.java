package projeto_back_end.projeto_back_end.DTO.ProdutosDTOs;

import java.util.List;
import java.util.UUID;

import lombok.Data;
import projeto_back_end.projeto_back_end.Models.Categoria;
import projeto_back_end.projeto_back_end.Models.Produto;

@Data
public class ProdutoResponse {
  private Long id;
  private UUID codigo_produto;
  private String nome;
  private String descricao;
  private Double preco;
  private String tamanho;

  public ProdutoResponse(Produto produto) {
    this.id = produto.getId();
    this.codigo_produto = produto.getCodigo_produto();
    this.descricao = produto.getDescricao();
    this.nome = produto.getNome();
    this.preco = produto.getPreco();
    this.tamanho = produto.getTamanho();
  }
}
