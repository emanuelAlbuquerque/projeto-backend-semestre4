package projeto_back_end.projeto_back_end.DTO.PedidosDTOs;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import projeto_back_end.projeto_back_end.DTO.BasicDTO;
import projeto_back_end.projeto_back_end.Models.Pedido;

@Data
public class ListarPedidosCanceladosResponse extends BasicDTO {
  private List<ListarPedidos> pedidos = new ArrayList<>();
  private int quantidade;

  public void setPedidos(List<Pedido> pedidos) {
    for (var i = 0; i < pedidos.size(); i++) {
      ListarPedidos listarPedidos = new ListarPedidos();
      listarPedidos.setPedido(new PedidoResponse(pedidos.get(i)));
      listarPedidos.setItensPedidos(pedidos.get(i).getItensPedidos());

      this.pedidos.add(listarPedidos);
    }
  }

  public void setQuantidade(int quantidade) {
    this.quantidade = quantidade;
  }
}
