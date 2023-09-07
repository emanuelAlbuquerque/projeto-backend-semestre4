package projeto_back_end.projeto_back_end.DTO.CategoriasDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import projeto_back_end.projeto_back_end.DTO.BasicDTO;
import projeto_back_end.projeto_back_end.Models.Categoria;

public class PegarCategoriaResponse extends BasicDTO {
  @JsonProperty("categoria")
  private CategoriaResponse categoria;

  public void setCategoria(Categoria categoria) {
    this.categoria = new CategoriaResponse(categoria);
  }
}
