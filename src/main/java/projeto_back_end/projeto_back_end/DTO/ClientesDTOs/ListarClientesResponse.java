package projeto_back_end.projeto_back_end.DTO.ClientesDTOs;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import projeto_back_end.projeto_back_end.DTO.BasicDTO;
import projeto_back_end.projeto_back_end.Models.Cliente;

@Data
public class ListarClientesResponse extends BasicDTO {
  private List<ClienteResponse> clientes = new ArrayList<>();;

  private int quantidade;

  public void setClientes(List<Cliente> clientes) {
    for (var i = 0; i < clientes.size(); i++) {
      ClienteResponse clienteResponse = new ClienteResponse(clientes.get(i));
      this.clientes.add(i, clienteResponse);
    }
  }
}
