package projeto_back_end.projeto_back_end.DTO.ProdutosDTOs;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AtualizarProdutoRequest {
  @Length(max = 80, message = "O nome não pode ultrapassar 60 caracteres")
  @NotBlank(message = "O nome não pode ser vazio")
  private String nome;

  @Length(max = 80, message = "O descrição não pode ultrapassar 255 caracteres")
  @NotBlank(message = "A descrição não pode ser vazia")
  private String descricao;

  @Positive(message = "O preço deve ser maior que 0")
  @NotNull(message = "O preço não pode ser vazio")
  private Double preco;

  @NotBlank(message = "O tamanho não pode ser vazio")
  private String tamanho;
}
