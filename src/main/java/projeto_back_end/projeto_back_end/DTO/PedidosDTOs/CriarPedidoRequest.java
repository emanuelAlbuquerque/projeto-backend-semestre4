package projeto_back_end.projeto_back_end.DTO.PedidosDTOs;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import projeto_back_end.projeto_back_end.DTO.ItensPedidoDTOs.CriarItemPedidoRequest;
import projeto_back_end.projeto_back_end.Models.Cliente;

@Data
public class CriarPedidoRequest {
  @NotBlank(message = "O método de Pagamento não pode ser vazio nem nulo")
  @Pattern(regexp = "^(Boleto|Pix|Cartão de Crédito)$", message = "Método de pagamento inválido.")
  private String metodoPagamento;

  @NotNull(message = "O ID do cliente não pode ser vazio.")
  @Positive(message = "O ID do cliente precisa ser positivo.")
  private Long clienteID;

  @NotBlank(message = "O endereço não pode ser vazio nem nulo")
  @Length(message = "O endereço não pode ultrapassar 255 caracteres")
  private String endereco;

  @Valid
  @NotNull(message = "Os itens pedidos não pode ser nulos")
  private List<CriarItemPedidoRequest> itensPedidos;
}
