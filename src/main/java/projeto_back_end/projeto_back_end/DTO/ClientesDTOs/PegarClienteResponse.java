package projeto_back_end.projeto_back_end.DTO.ClientesDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import projeto_back_end.projeto_back_end.DTO.BasicDTO;
import projeto_back_end.projeto_back_end.Models.Cliente;

public class PegarClienteResponse extends BasicDTO {
  @JsonProperty("cliente")
  private ClienteResponse cliente;

  public void setCliente(Cliente cliente) {
    this.cliente = new ClienteResponse(cliente);
  }
}
