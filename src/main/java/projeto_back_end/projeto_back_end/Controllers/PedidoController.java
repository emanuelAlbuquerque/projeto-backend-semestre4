package projeto_back_end.projeto_back_end.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto_back_end.projeto_back_end.Models.Pedido;
import projeto_back_end.projeto_back_end.Repository.PedidosRepositorio;



@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidosRepositorio pedidoRepositorio;
	
	// Listar todos os pedidos
	  @GetMapping("/")
	  public List<Pedido> listarPedidos() {
	    List<Pedido> pedidos = pedidoRepositorio.findAll();
	    return pedidos;
	  }
	  
	// Obter um pedido por ID
	  @GetMapping("/{id}")
	  public Pedido obterPedidoPorId(@PathVariable Long id) {
	    Pedido pedido = pedidoRepositorio.findById(id).orElse(null);
	    return pedido;
	  }
	  
	// Criar um novo pedido
	  @PostMapping("/")
	  public Pedido criarPedido(@RequestBody Pedido pedido) {
	    Pedido novoPedido = pedidoRepositorio.save(pedido);
	    return novoPedido;
	  }
	  
	// Atualizar um pedido existente
	  @PutMapping("/{id}")
	  public Pedido atualizarPedido(@PathVariable Long id, @RequestBody Pedido pedidoAtualizado) {
	    Pedido pedidoExistente = pedidoRepositorio.findById(id).orElse(null);
	    if (pedidoExistente != null) {
	      pedidoExistente.data_atualizada = pedidoAtualizado.data_atualizada;
	      pedidoExistente.metodo_pagamento = pedidoAtualizado.metodo_pagamento;
	      pedidoExistente.status = pedidoExistente.status;
	      pedidoExistente.data_pedido = pedidoAtualizado.data_pedido;
	     
	      Pedido novoPedido = pedidoRepositorio.save(pedidoExistente);
	      return novoPedido;
	    }
	    return null;
	  }
	  
	// Deletar um pedido por ID
	  @DeleteMapping("/{id}")
	  public void deletarPedido(@PathVariable Long id) {
	    pedidoRepositorio.deleteById(id);
	  }

}
