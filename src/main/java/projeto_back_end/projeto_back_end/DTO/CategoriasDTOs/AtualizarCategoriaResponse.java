package projeto_back_end.projeto_back_end.DTO.CategoriasDTOs;

import lombok.Data;
import projeto_back_end.projeto_back_end.DTO.BasicDTO;
import projeto_back_end.projeto_back_end.Models.Categoria;

@Data
public class AtualizarCategoriaResponse extends BasicDTO {
  private Categoria categoria;
}
