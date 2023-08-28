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

import projeto_back_end.projeto_back_end.Models.Cliente;
import projeto_back_end.projeto_back_end.Repository.ClientesRepositorio;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClientesRepositorio clienteRepositorio;
	
	// Listar todos os clientes
	  @GetMapping("/")
	  public List<Cliente> listarClientes() {
	    List<Cliente> clientes = clienteRepositorio.findAll();
	    return clientes;
	  }

	  // Obter um cliente por ID
	  @GetMapping("/{id}")
	  public Cliente obterClientePorId(@PathVariable Long id) {
	    Cliente cliente = clienteRepositorio.findById(id).orElse(null);
	    return cliente;
	  }

	  // Criar um novo cliente
	  @PostMapping("/")
	  public Cliente criarCliente(@RequestBody Cliente cliente) {
	    Cliente novoCliente = clienteRepositorio.save(cliente);
	    return novoCliente;
	  }

	  // Atualizar um cliente existente
	  @PutMapping("/{id}")
	  public Cliente atualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
	    Cliente clienteExistente = clienteRepositorio.findById(id).orElse(null);
	    if (clienteExistente != null) {
	      clienteExistente.nome = clienteAtualizado.nome;
	      clienteExistente.data_nascimento = clienteAtualizado.data_nascimento;
	      clienteExistente.cpf = clienteExistente.cpf;
	      clienteExistente.genero = clienteAtualizado.genero;
	      clienteExistente.email = clienteAtualizado.email;

	      Cliente novoCliente = clienteRepositorio.save(clienteExistente);
	      return novoCliente;
	    }
	    return null;
	  }

	  // Deletar um cliente por ID
	  @DeleteMapping("/{id}")
	  public void deletarCliente(@PathVariable Long id) {
	    clienteRepositorio.deleteById(id);
	  }

}
