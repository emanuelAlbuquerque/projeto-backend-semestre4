package projeto_back_end.projeto_back_end.DTO.PedidosDTOs;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import projeto_back_end.projeto_back_end.DTO.BasicDTO;
import projeto_back_end.projeto_back_end.DTO.ItensPedidoDTOs.ItemPedidosResponse;
import projeto_back_end.projeto_back_end.Models.ItemPedido;
import projeto_back_end.projeto_back_end.Models.Pedido;

@Data
public class CriarPedidoResponse extends BasicDTO {
  private PedidoResponse pedido;
  private List<ItemPedidosResponse> itensPedidos = new ArrayList<>();

  public void setPedido(Pedido pedido) {
    PedidoResponse pedidoResponse = new PedidoResponse(pedido);
    this.pedido = pedidoResponse;
  }

  public void setItensPedidos(List<ItemPedido> itensPedidos) {
    for (var i = 0; i < itensPedidos.size(); i++) {
      ItemPedidosResponse itemPedido = new ItemPedidosResponse(itensPedidos.get(i));
      this.itensPedidos.add(itemPedido);
    }
  }
}
