package projeto_back_end.projeto_back_end.DTO.CategoriasDTOs;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import projeto_back_end.projeto_back_end.DTO.BasicDTO;
import projeto_back_end.projeto_back_end.Models.Categoria;

@Data
public class VincularCategoriasAoProdutoResponse extends BasicDTO {
  private Long produtoID;
  private String nome;
  private List<CategoriaResponse> categoriasVinculadas = new ArrayList<>();

  public void setCategoriasVinculadas(List<Categoria> categorias) {
    for (var i = 0; i < categorias.size(); i++) {
      CategoriaResponse categoria = new CategoriaResponse(categorias.get(i));
      this.categoriasVinculadas.add(i, categoria);
    }
  }
}
