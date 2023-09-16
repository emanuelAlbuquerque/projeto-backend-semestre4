package projeto_back_end.projeto_back_end.DTO.PedidosDTOs;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import projeto_back_end.projeto_back_end.DTO.ItensPedidoDTOs.ItemPedidosResponse;
import projeto_back_end.projeto_back_end.Models.ItemPedido;

@Data
class ListarPedidos {
  private PedidoResponse pedido;
  private List<ItemPedidosResponse> itensPedidos = new ArrayList<>();

  public void setItensPedidos(List<ItemPedido> itensPedidos) {
    for (var i = 0; i < itensPedidos.size(); i++) {
      this.itensPedidos.add(new ItemPedidosResponse(itensPedidos.get(i)));
    }
  }
}
