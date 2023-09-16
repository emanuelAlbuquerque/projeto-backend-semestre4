package projeto_back_end.projeto_back_end.DTO.CategoriasDTOs;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VincularCategoriasAoProdutoRequest {

  @NotNull(message = "A lista de categorias não pode ser nula")
  private List<Long> categoriasIDs;
}
