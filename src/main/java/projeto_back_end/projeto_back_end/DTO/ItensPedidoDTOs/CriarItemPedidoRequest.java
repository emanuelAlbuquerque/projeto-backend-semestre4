package projeto_back_end.projeto_back_end.DTO.ItensPedidoDTOs;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CriarItemPedidoRequest {
  @NotNull(message = "O ID do produto não pode ser vazio.")
  @Positive(message = "O ID do produto precisa ser um valor positivo.")
  private Long produtoID;

  @NotNull(message = "A quantidade não pode ser vazia.")
  @Min(value = 1, message = "A quantidade precisa ser maior que zero.")
  private int quantidade;

  @NotNull(message = "O preço unitário do produto não pode ser vazio.")
  @DecimalMin(value = "0.1", message = "O preço unitário do produto precisa ser maior que zero.")
  private float precoUnitario;
}
