package projeto_back_end.projeto_back_end.DTO.PedidosDTOs;

import java.time.LocalDate;

import lombok.Data;
import projeto_back_end.projeto_back_end.Models.Cliente;
import projeto_back_end.projeto_back_end.Models.Pedido;

@Data
public class PedidoResponse {
  private Long pedidoID;
  private String metodoPagamento;
  private String status;
  private LocalDate dataPedido;

  public PedidoResponse(Pedido pedido) {
    this.status = pedido.getStatus();
    this.dataPedido = pedido.getDataPedido();
    this.metodoPagamento = pedido.getMetodoPagamento();
    this.pedidoID = pedido.getPedidoID();
  }
}
