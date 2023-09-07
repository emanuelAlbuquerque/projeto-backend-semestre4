package projeto_back_end.projeto_back_end.DTO.ProdutosDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import projeto_back_end.projeto_back_end.DTO.BasicDTO;
import projeto_back_end.projeto_back_end.Models.Produto;

public class PegarProdutoResponse extends BasicDTO {
  @JsonProperty("produto")
  private ProdutoResponse produtoResponse;

  public void setProduto(Produto produto) {
    this.produtoResponse = new ProdutoResponse(produto);
  }
}
