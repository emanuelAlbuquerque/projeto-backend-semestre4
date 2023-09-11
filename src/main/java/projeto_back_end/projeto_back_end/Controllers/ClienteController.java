package projeto_back_end.projeto_back_end.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.net.server.Client;
import jakarta.validation.Valid;
import projeto_back_end.projeto_back_end.DTO.CategoriasDTOs.CriarCategoriaRequest;
import projeto_back_end.projeto_back_end.DTO.CategoriasDTOs.PegarCategoriaResponse;
import projeto_back_end.projeto_back_end.DTO.ClientesDTOs.AtualizarClienteRequest;
import projeto_back_end.projeto_back_end.DTO.ClientesDTOs.AtualizarClienteResponse;
import projeto_back_end.projeto_back_end.DTO.ClientesDTOs.CriarClienteRequest;
import projeto_back_end.projeto_back_end.DTO.ClientesDTOs.CriarClienteResponse;
import projeto_back_end.projeto_back_end.DTO.ClientesDTOs.DeletarClienteResponse;
import projeto_back_end.projeto_back_end.DTO.ClientesDTOs.ListarClientesResponse;
import projeto_back_end.projeto_back_end.DTO.ClientesDTOs.PegarClienteResponse;
import projeto_back_end.projeto_back_end.DTO.ErrorDTO.ErrorResponse;
import projeto_back_end.projeto_back_end.Models.Cliente;
import projeto_back_end.projeto_back_end.Models.Produto;
import projeto_back_end.projeto_back_end.Repository.ClientesRepositorio;
import projeto_back_end.projeto_back_end.Repository.PedidosRepositorio;
import projeto_back_end.projeto_back_end.Repository.PedidosRepositorio;

@RestController
public class ClienteController {

	private static final List<Cliente> Clientes = null;

	@Autowired
	private ClientesRepositorio clienteRepositorio;

	@Autowired
	private PedidosRepositorio pedidosRepositorio;

	// Listar todas as Clientes
	@GetMapping(value = "/listarClientes", produces = "application/json")
	public ResponseEntity<?> listarClientes() {
		try {
			ListCrudRepository<Cliente, Long> cRieetepositorio;
			List<Cliente> clientes = clienteRepositorio.findAll(
			ListarClientesResponse listarClientesResponse = new ListarClientesResponse();

			listarClientesResponse.setQuantidade(Clientes.size());
			listarClientesResponse.setStatus(HttpStatus.OK);
			listarClientesResponse.setStatus_code(200);

			if (Clientes.size() == 0) {
				listarClientesResponse.getMessages().add("Não existe nenhum pedido no carrinho.");
			}

			llllistarClientesResponse.setClientes(Clientes);

			return new ResponseEntity<>(listarClientesResponse, HttpStatus.OK);
		} catch (DataIntegrityViolationException ex) {
			ErrorResponse erro = new ErrorResponse();
			erro.getMessages().add(ex.getLocalizedMessage());
			erro.setStatus_code(500);
			erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

			return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Obter uma cliente por ID
	@GetMapping(value = "/cliente/{id}", produces = "application/json")
	public ResponseEntity<?> obterclientePorId(@PathVariable Long id) {
		try {
			Optional<Cliente> cliente = clienteRepositorio.findById(id);

			if (cliente.isPresent()) {
				PegarCategoriaResponse pegarclienteResponse = new PegarCategoriaResponse();

				pegarclienteResponse.setcliente(cliente.get());
				pegarclienteResponse.getMessages().add("cliente encontrada.");
				pegarclienteResponse.setStatus_code(200);
				pegarclienteResponse.setStatus(HttpStatus.OK);

				return new ResponseEntity<>(pegarclienteResponse, HttpStatus.OK);
			} else {
				ErrorResponse erro = new ErrorResponse();
				erro.getMessages().add("cliente não encontrada");
				erro.setStatus_code(404);
				erro.setStatus(HttpStatus.NOT_FOUND);

				return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
			}
		} catch (DataIntegrityViolationException ex) {
			ErrorResponse erro = new ErrorResponse();
			erro.getMessages().add(ex.getLocalizedMessage());
			erro.setStatus_code(500);
			erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

			return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Criar uma nova cliente
	@PostMapping("/criarcliente")
	public ResponseEntity<?> criarcliente(@Valid @RequestBody CriarCategoriaRequest clienteRequest,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				ErrorResponse error = new ErrorResponse();

				error.setStatus_code(428);
				error.setStatus(HttpStatus.PRECONDITION_REQUIRED);

				for (ObjectError obj : bindingResult.getAllErrors()) {
					error.getMessages().add(obj.getDefaultMessage());
				}

				return new ResponseEntity<>(error, HttpStatus.PRECONDITION_REQUIRED);
			} else {
				Cliente cliente = new Cliente();

				if (clienteRequest.getPedidosIDs() != null) {
					List<Produto> pedidos = pedidosRepositorio.findAllById(clienteRequest.getPedidosIDs());
					cliente.setPedidos(pedidos);
				}

				cliente.setNome(clienteRequest.getNome());

				clienteRepositorio.save(cliente);

				CriarclienteResponse response = new CriarclienteResponse();
				response.setcliente(cliente);
				response.getMessages().add("Cliente cadastrado com sucesso.");
				response.setStatus_code(201);
				response.setStatus(HttpStatus.CREATED);
				return new ResponseEntity<>(response, HttpStatus.CREATED);
			}
		} catch (DataIntegrityViolationException ex) {
			ErrorResponse erro = new ErrorResponse();
			erro.getMessages().add(ex.getLocalizedMessage());
			erro.setStatus_code(500);
			erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

			return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Atualizar uma cliente existente
	@PutMapping("/atualizarcliente/{id}")
	public ResponseEntity<?> atualizarcliente(@PathVariable Long id,
			@Valid @RequestBody AtualizarclienteRequest cliente, BindingResult bindingResult) {

		try {
			if (bindingResult.hasErrors()) {
				ErrorResponse error = new ErrorResponse();

				error.setStatus_code(428);
				error.setStatus(HttpStatus.PRECONDITION_REQUIRED);

				for (ObjectError obj : bindingResult.getAllErrors()) {
					error.getMessages().add(obj.getDefaultMessage());
				}

				return new ResponseEntity<>(error, HttpStatus.PRECONDITION_REQUIRED);
			} else {
				Optional<cliente> clienteExistente = clienteRepositorio.findById(id);

				if (clienteExistente.isPresent()) {

					if (cliente.getPedidosIDs() != null) {
						List<Produto> pedidos = pedidosRepositorio.findAllById(cliente.getPedidosIDs());
						clienteExistente.get().setPedidos(pedidos);
					}
					clienteExistente.get().setNome(cliente.getNome());

					clienteRepositorio.save(clienteExistente.get());

					AtualizarclienteResponse response = new AtualizarclienteResponse();
					response.setcliente(clienteExistente.get());
					response.getMessages().add("Categria atualizada com sucesso.");
					response.setStatus_code(201);
					response.setStatus(HttpStatus.CREATED);
					return new ResponseEntity<>(response, HttpStatus.CREATED);
				} else {
					ErrorResponse erro = new ErrorResponse();
					erro.getMessages().add("cliente não encontrada");
					erro.setStatus_code(404);
					erro.setStatus(HttpStatus.NOT_FOUND);

					return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
				}
			}
		} catch (DataIntegrityViolationException ex) {
			ErrorResponse erro = new ErrorResponse();
			erro.getMessages().add(ex.getLocalizedMessage());
			erro.setStatus_code(500);
			erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

			return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Deletar uma cliente por ID
	@DeleteMapping("/deletarcliente/{id}")
	public ResponseEntity<?> deletarcliente(@PathVariable Long id) {
		try {
			Optional<Cliente> cliente = clienteRepositorio.findById(id);
			if (cliente.isPresent()) {
				clienteRepositorio.deleteById(id);
				DeletarclienteResponse deletarclienteResponse = new DeletarclienteResponse();

				deletarclienteResponse.getMessages().add("cliente deletado com sucesso.");
				deletarclienteResponse.setStatus_code(200);
				deletarclienteResponse.setStatus(HttpStatus.OK);
				return new ResponseEntity<>(deletarclienteResponse, HttpStatus.OK);
			} else {
				ErrorResponse erro = new ErrorResponse();
				erro.getMessages().add("cliente não encontrado");
				erro.setStatus_code(404);
				erro.setStatus(HttpStatus.NOT_FOUND);

				return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			ErrorResponse erro = new ErrorResponse();
			erro.getMessages().add(ex.getLocalizedMessage());
			erro.setStatus_code(500);
			erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
