package projeto_back_end.projeto_back_end.DTO.ClientesDTOs;

import lombok.Data;
import projeto_back_end.projeto_back_end.Models.Cliente;

@Data
class ClienteResponse {
  private Long ID;
  private String nome;
  private String cpf;
  private String genero;
  private String dataNascimento;
  private String email;

  public ClienteResponse(Cliente cliente) {
    this.ID = cliente.getClienteID();
    this.nome = cliente.getNome();
    this.cpf = cliente.getCpf();
    this.genero = cliente.getGenero();
    this.dataNascimento = cliente.getDataNascimento();
    this.email = cliente.getEmail();
  }
}
