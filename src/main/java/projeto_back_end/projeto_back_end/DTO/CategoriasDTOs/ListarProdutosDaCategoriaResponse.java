package projeto_back_end.projeto_back_end.DTO.CategoriasDTOs;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import projeto_back_end.projeto_back_end.DTO.BasicDTO;
import projeto_back_end.projeto_back_end.DTO.ProdutosDTOs.ProdutoResponse;
import projeto_back_end.projeto_back_end.Models.Produto;

@Data
public class ListarProdutosDaCategoriaResponse extends BasicDTO {
  private List<ProdutoResponse> produtos = new ArrayList<>();
  private int quantidade;

  public void setProdutos(List<Produto> produtosRequest) {
    for (var i = 0; i < produtosRequest.size(); i++) {
      produtos.add(new ProdutoResponse(produtosRequest.get(i)));
    }
  }

  public void setQuantidade(int quantidade) {
    this.quantidade = quantidade;
  }
}
