package projeto_back_end.projeto_back_end.DTO.ClientesDTOs;

import lombok.Data;
import projeto_back_end.projeto_back_end.DTO.BasicDTO;
import projeto_back_end.projeto_back_end.Models.Cliente;

@Data
public class AtualizarClienteResponse extends BasicDTO {
  private Cliente cliente;
}