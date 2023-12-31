package projeto_back_end.projeto_back_end.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
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

import jakarta.validation.Valid;
import projeto_back_end.projeto_back_end.DTO.ClientesDTOs.CriarClienteRequest;
import projeto_back_end.projeto_back_end.DTO.ClientesDTOs.PegarClienteResponse;
import projeto_back_end.projeto_back_end.DTO.ClientesDTOs.AtualizarClienteRequest;
import projeto_back_end.projeto_back_end.DTO.ClientesDTOs.AtualizarClienteResponse;
import projeto_back_end.projeto_back_end.DTO.ClientesDTOs.CriarClienteResponse;
import projeto_back_end.projeto_back_end.DTO.ClientesDTOs.DeletarClienteResponse;
import projeto_back_end.projeto_back_end.DTO.ClientesDTOs.ListarClientesResponse;
import projeto_back_end.projeto_back_end.DTO.ErrorDTO.ErrorResponse;
import projeto_back_end.projeto_back_end.Models.Cliente;
import projeto_back_end.projeto_back_end.Repository.ClientesRepositorio;
import projeto_back_end.projeto_back_end.Repository.PedidosRepositorio;

@RestController
public class ClienteController {

	private static final List<Cliente> Clientes = null;

	private static final Sort ListarClientesResponse = null;

	@Autowired
	private ClientesRepositorio clienteRepositorio;

	@Autowired
	private PedidosRepositorio pedidosRepositorio;

	// Listar todos os Clientes
	@GetMapping(value = "/listarClientes", produces = "application/json")
	public ResponseEntity<?> listarClientes() {
		try {
			List<Cliente> clientes = clienteRepositorio.findAll();
			ListarClientesResponse listarClientesResponse = new ListarClientesResponse();

			listarClientesResponse.setQuantidade(clientes.size());
			listarClientesResponse.setStatus(HttpStatus.OK);
			listarClientesResponse.setStatus_code(200);

			if (Clientes.size() == 0) {
				listarClientesResponse.getMessages().add("Não existe nenhum cliente cadastrado.");
			}

			listarClientesResponse.setClientes(Clientes);

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
				PegarClienteResponse pegarClienteResponse = new PegarClienteResponse();

				pegarClienteResponse.setCliente(cliente.get());
				pegarClienteResponse.getMessages().add("cliente encontrado.");
				pegarClienteResponse.setStatus_code(200);
				pegarClienteResponse.setStatus(HttpStatus.OK);

				return new ResponseEntity<>(pegarClienteResponse, HttpStatus.OK);
			} else {
				ErrorResponse erro = new ErrorResponse();
				erro.getMessages().add("cliente não encontrado");
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
	@PostMapping("/criarCliente")
	public ResponseEntity<?> criarcliente(@Valid @RequestBody CriarClienteRequest clienteRequest,
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
				cliente.setNome(clienteRequest.getNome());
				cliente.setCpf(clienteRequest.getCpf());
				cliente.setIdade(clienteRequest.getIdade());
				cliente.setGenero(cliente.getGenero());
				cliente.setEmail(clienteRequest.getEmail());

				clienteRepositorio.save(cliente);

				CriarClienteResponse response = new CriarClienteResponse();
				response.setCliente(cliente);
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
			@Valid @RequestBody AtualizarClienteRequest cliente, BindingResult bindingResult) {

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
				Optional<Cliente> clienteExistente = clienteRepositorio.findById(id);

				if (clienteExistente.isPresent()) {
					clienteExistente.get().setNome(cliente.getNome());

					clienteExistente.get().setNome(cliente.getNome());
					clienteExistente.get().setCpf(cliente.getCpf());
					clienteExistente.get().setIdade(cliente.getIdade());
					clienteExistente.get().setGenero(cliente.getGenero());
					clienteExistente.get().setEmail(cliente.getEmail());

					clienteRepositorio.save(clienteExistente.get());

					AtualizarClienteResponse response = new AtualizarClienteResponse();
					response.setCliente(clienteExistente.get());
					response.getMessages().add("Cadastro atualizado com sucesso.");
					response.setStatus_code(201);
					response.setStatus(HttpStatus.CREATED);
					return new ResponseEntity<>(response, HttpStatus.CREATED);
				} else {
					ErrorResponse erro = new ErrorResponse();
					erro.getMessages().add("cliente não encontrado");
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
	@DeleteMapping("/deletarCliente/{id}")
	public ResponseEntity<?> deletarcliente(@PathVariable Long id) {
		try {
			Optional<Cliente> cliente = clienteRepositorio.findById(id);
			if (cliente.isPresent()) {
				DeletarClienteResponse deletarClienteResponse = new DeletarClienteResponse();

				if (cliente.get().getPedidos().size() > 0) {
					deletarClienteResponse.getMessages()
							.add("Não é possível deletar o Cliente pois ele está vinculado a um pedido.");
					deletarClienteResponse.setStatus_code(200);
					deletarClienteResponse.setStatus(HttpStatus.OK);
					return new ResponseEntity<>(deletarClienteResponse, HttpStatus.OK);
				} else {
					clienteRepositorio.deleteById(id);

					deletarClienteResponse.getMessages().add("Cliente deletado com sucesso.");
					deletarClienteResponse.setStatus_code(200);
					deletarClienteResponse.setStatus(HttpStatus.OK);
					return new ResponseEntity<>(deletarClienteResponse, HttpStatus.OK);
				}
			} else {
				ErrorResponse erro = new ErrorResponse();
				erro.getMessages().add("Cliente não encontrado. ");
				erro.setStatus_code(404);
				erro.setStatus(HttpStatus.NOT_FOUND);

				return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
			}
		} catch (

		DataIntegrityViolationException ex) {
			ErrorResponse erro = new ErrorResponse();
			erro.getMessages().add(ex.getLocalizedMessage());
			erro.setStatus_code(500);
			erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
