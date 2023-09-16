package projeto_back_end.projeto_back_end.DTO.ItensPedidoDTOs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Data;
import projeto_back_end.projeto_back_end.DTO.ProdutosDTOs.ProdutoResponse;
import projeto_back_end.projeto_back_end.Models.ItemPedido;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ItemPedidosResponse {
  private Long id;
  private ProdutoResponse produto;
  private int quantidade;
  private float precoUnitario;

  public ItemPedidosResponse(ItemPedido itemPedido) {
    this.id = itemPedido.getId();
    this.precoUnitario = itemPedido.getPrecoUnitario();
    this.produto = new ProdutoResponse(itemPedido.getProduto());
    this.quantidade = itemPedido.getQuantidade();
  }
}
