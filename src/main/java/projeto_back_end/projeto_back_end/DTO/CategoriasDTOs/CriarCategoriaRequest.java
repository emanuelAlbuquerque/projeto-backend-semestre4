package projeto_back_end.projeto_back_end.DTO.CategoriasDTOs;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CriarCategoriaRequest {
  @NotBlank(message = "O nome não pode ser nulo nem vazio")
  @Length(max = 40, message = "O nome não pode ultrapassar 40 caracteres")
  private String nome;
}
