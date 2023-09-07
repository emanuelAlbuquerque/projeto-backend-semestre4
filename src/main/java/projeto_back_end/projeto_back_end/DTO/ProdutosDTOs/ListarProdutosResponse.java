package projeto_back_end.projeto_back_end.DTO.ProdutosDTOs;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import projeto_back_end.projeto_back_end.DTO.BasicDTO;
import projeto_back_end.projeto_back_end.Models.Produto;

public class ListarProdutosResponse extends BasicDTO {
  @JsonProperty("produtos")
  private List<ProdutoResponse> produtos = new ArrayList<>();

  @JsonProperty("quantidade")
  private int quantidade;

  public void setProdutos(List<Produto> produtos) {
    for (var i = 0; i < produtos.size(); i++) {
      ProdutoResponse produtoResponse = new ProdutoResponse(produtos.get(i));
      this.produtos.add(i, produtoResponse);
    }
  }

  public void setQuantidade(int quantidade) {
    this.quantidade = quantidade;
  }
}
