package projeto_back_end.projeto_back_end.DTO.CategoriasDTOs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import projeto_back_end.projeto_back_end.DTO.BasicDTO;
import projeto_back_end.projeto_back_end.Models.Categoria;

@Data
@EqualsAndHashCode(callSuper = false)
public class CriarCategoriaResponse extends BasicDTO {
  private Categoria categoria;
}
