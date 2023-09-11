package projeto_back_end.projeto_back_end.DTO.ClientesDTOs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import projeto_back_end.projeto_back_end.DTO.BasicDTO;
import projeto_back_end.projeto_back_end.Models.Cliente;

@Data
@EqualsAndHashCode(callSuper = false)
public class CriarClienteResponse extends BasicDTO {
  private Cliente cliente;
}