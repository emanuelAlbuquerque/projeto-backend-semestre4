package projeto_back_end.projeto_back_end.DTO.PedidosDTOs;

import lombok.Data;
import projeto_back_end.projeto_back_end.DTO.BasicDTO;
import projeto_back_end.projeto_back_end.Models.Pedido;

@Data
public class PegarPedidoResponse extends BasicDTO {
  private PedidoResponse pedido;

  public void setPedido(Pedido pedido) {
    this.pedido = new PedidoResponse(pedido);
  }
}
