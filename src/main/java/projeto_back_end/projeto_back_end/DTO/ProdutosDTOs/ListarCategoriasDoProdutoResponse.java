package projeto_back_end.projeto_back_end.DTO.ProdutosDTOs;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import projeto_back_end.projeto_back_end.DTO.BasicDTO;
import projeto_back_end.projeto_back_end.DTO.CategoriasDTOs.CategoriaResponse;
import projeto_back_end.projeto_back_end.Models.Categoria;

@Data
public class ListarCategoriasDoProdutoResponse extends BasicDTO {
  private List<CategoriaResponse> categorias = new ArrayList<>();
  private int quantidade;

  public void setCategorias(List<Categoria> categorias) {
    for (var i = 0; i < categorias.size(); i++) {
      CategoriaResponse categoria = new CategoriaResponse(categorias.get(i));
      this.categorias.add(i, categoria);
    }
  }

  public void setQuantidade(int quantidade) {
    this.quantidade = quantidade;
  }
}
