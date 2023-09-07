package projeto_back_end.projeto_back_end.DTO.CategoriasDTOs;

import lombok.Data;
import projeto_back_end.projeto_back_end.Models.Categoria;

@Data
class CategoriaResponse {
  private Long id;
  private String nome;

  public CategoriaResponse(Categoria categoria) {
    this.id = categoria.getId();
    this.nome = categoria.getNome();
  }
}