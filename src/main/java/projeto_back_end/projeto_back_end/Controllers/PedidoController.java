package projeto_back_end.projeto_back_end.Controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import projeto_back_end.projeto_back_end.DTO.ErrorDTO.ErrorResponse;
import projeto_back_end.projeto_back_end.DTO.ItensPedidoDTOs.CriarItemPedidoRequest;
import projeto_back_end.projeto_back_end.DTO.PedidosDTOs.CancelarPedidoResponse;
import projeto_back_end.projeto_back_end.DTO.PedidosDTOs.CriarPedidoRequest;
import projeto_back_end.projeto_back_end.DTO.PedidosDTOs.CriarPedidoResponse;
import projeto_back_end.projeto_back_end.DTO.PedidosDTOs.EnviarPedidoResponse;
import projeto_back_end.projeto_back_end.DTO.PedidosDTOs.FinalizarPedidoResponse;
import projeto_back_end.projeto_back_end.DTO.PedidosDTOs.ListarPedidosCanceladosResponse;
import projeto_back_end.projeto_back_end.DTO.PedidosDTOs.ListarPedidosEnviadosClientesResponse;
import projeto_back_end.projeto_back_end.DTO.PedidosDTOs.ListarPedidosResponse;
import projeto_back_end.projeto_back_end.DTO.PedidosDTOs.PegarPedidoResponse;
import projeto_back_end.projeto_back_end.DTO.ProdutosDTOs.ListarProdutosResponse;
import projeto_back_end.projeto_back_end.Models.Cliente;
import projeto_back_end.projeto_back_end.Models.ItemPedido;
import projeto_back_end.projeto_back_end.Models.Pedido;
import projeto_back_end.projeto_back_end.Models.Produto;
import projeto_back_end.projeto_back_end.Repository.ClientesRepositorio;
import projeto_back_end.projeto_back_end.Repository.ItemPedidoRepositorio;
import projeto_back_end.projeto_back_end.Repository.PedidosRepositorio;
import projeto_back_end.projeto_back_end.Repository.ProdutosRepositorio;

@RestController
@RequestMapping()
public class PedidoController {

	@Autowired
	private PedidosRepositorio pedidoRepositorio;

	@Autowired
	private ClientesRepositorio clienteRepositorio;

	@Autowired
	private ItemPedidoRepositorio itemPedidoRepositorio;

	@Autowired
	private ProdutosRepositorio produtoRepositorio;

	// Listar todos os pedidos
	@GetMapping(value = "/listarPedidosCliente/{id}", produces = "application/json")
	public ResponseEntity<?> listarPedidosCliente(@PathVariable Long id) {
		try {
			Optional<Cliente> cliente = clienteRepositorio.findById(id);

			if (cliente.isPresent()) {
				ListarPedidosResponse listarPedidosResponse = new ListarPedidosResponse();

				listarPedidosResponse.setQuantidade(cliente.get().getPedidos().size());
				listarPedidosResponse.setStatus(HttpStatus.OK);
				listarPedidosResponse.setStatus_code(200);

				if (cliente.get().getPedidos().size() == 0) {
					listarPedidosResponse.getMessages().add("Não existe nenhum pedido cadastrado.");
				}

				listarPedidosResponse.setPedidos(cliente.get().getPedidos());

				return new ResponseEntity<>(listarPedidosResponse, HttpStatus.OK);
			} else {
				PegarPedidoResponse pegarPedidoResponse = new PegarPedidoResponse();
				pegarPedidoResponse.setStatus(HttpStatus.NOT_FOUND);
				pegarPedidoResponse.setStatus_code(404);
				pegarPedidoResponse.getMessages().add("Cliente não encontrado");

				return new ResponseEntity<>(pegarPedidoResponse, HttpStatus.NOT_FOUND);
			}
		} catch (DataIntegrityViolationException ex) {
			ErrorResponse erro = new ErrorResponse();
			erro.getMessages().add(ex.getLocalizedMessage());
			erro.setStatus_code(500);
			erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

			return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/listarPedidosCanceladosCliente/{id}", produces = "application/json")
	public ResponseEntity<?> listarPedidosCanceladosCliente(@PathVariable Long id) {
		try {
			Optional<Cliente> cliente = clienteRepositorio.findById(id);

			if (cliente.isPresent()) {
				ListarPedidosCanceladosResponse listarPedidosCanceladosResponse = new ListarPedidosCanceladosResponse();
				List<Pedido> pedidosCancelados = new ArrayList<>();

				for (Pedido pedido : cliente.get().getPedidos()) {
					if (pedido.getStatus().equals("Cancelado")) {
						pedidosCancelados.add(pedido);
					}
				}

				listarPedidosCanceladosResponse.setQuantidade(pedidosCancelados.size());
				listarPedidosCanceladosResponse.setStatus(HttpStatus.OK);
				listarPedidosCanceladosResponse.setStatus_code(200);

				if (pedidosCancelados.size() == 0) {
					listarPedidosCanceladosResponse.getMessages().add("Não existe nenhum pedido cancelado.");
				}

				listarPedidosCanceladosResponse.setPedidos(pedidosCancelados);

				return new ResponseEntity<>(listarPedidosCanceladosResponse, HttpStatus.OK);
			} else {
				PegarPedidoResponse pegarPedidoResponse = new PegarPedidoResponse();
				pegarPedidoResponse.setStatus(HttpStatus.NOT_FOUND);
				pegarPedidoResponse.setStatus_code(404);
				pegarPedidoResponse.getMessages().add("Cliente não encontrado");

				return new ResponseEntity<>(pegarPedidoResponse, HttpStatus.NOT_FOUND);
			}
		} catch (DataIntegrityViolationException ex) {
			ErrorResponse erro = new ErrorResponse();
			erro.getMessages().add(ex.getLocalizedMessage());
			erro.setStatus_code(500);
			erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

			return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/listarPedidosEnviadosCliente/{id}", produces = "application/json")
	public ResponseEntity<?> listarPedidosEnviadosCliente(@PathVariable Long id) {
		try {
			Optional<Cliente> cliente = clienteRepositorio.findById(id);

			if (cliente.isPresent()) {
				ListarPedidosEnviadosClientesResponse listarPedidosEnviadosClientesResponse = new ListarPedidosEnviadosClientesResponse();
				List<Pedido> pedidosEnviados = new ArrayList<>();

				for (Pedido pedido : cliente.get().getPedidos()) {
					if (pedido.getStatus().equals("Enviado")) {
						pedidosEnviados.add(pedido);
					}
				}

				listarPedidosEnviadosClientesResponse.setQuantidade(pedidosEnviados.size());
				listarPedidosEnviadosClientesResponse.setStatus(HttpStatus.OK);
				listarPedidosEnviadosClientesResponse.setStatus_code(200);

				if (pedidosEnviados.size() == 0) {
					listarPedidosEnviadosClientesResponse.getMessages().add("Não existe nenhum pedido enviado.");
				}

				listarPedidosEnviadosClientesResponse.setPedidos(pedidosEnviados);

				return new ResponseEntity<>(listarPedidosEnviadosClientesResponse, HttpStatus.OK);
			} else {
				PegarPedidoResponse pegarPedidoResponse = new PegarPedidoResponse();
				pegarPedidoResponse.setStatus(HttpStatus.NOT_FOUND);
				pegarPedidoResponse.setStatus_code(404);
				pegarPedidoResponse.getMessages().add("Cliente não encontrado");

				return new ResponseEntity<>(pegarPedidoResponse, HttpStatus.NOT_FOUND);
			}
		} catch (DataIntegrityViolationException ex) {
			ErrorResponse erro = new ErrorResponse();
			erro.getMessages().add(ex.getLocalizedMessage());
			erro.setStatus_code(500);
			erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

			return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Obter um pedido por ID
	@GetMapping("/pedido/{id}")
	public ResponseEntity<?> obterPedidoPorId(@PathVariable Long id) {
		try {
			Optional<Pedido> pedido = pedidoRepositorio.findById(id);

			if (pedido.isPresent()) {
				PegarPedidoResponse pegarPedidoResponse = new PegarPedidoResponse();
				pegarPedidoResponse.setPedido(pedido.get());
				pegarPedidoResponse.setStatus(HttpStatus.OK);
				pegarPedidoResponse.setStatus_code(200);

				return new ResponseEntity<>(pegarPedidoResponse, HttpStatus.OK);
			} else {
				PegarPedidoResponse pegarPedidoResponse = new PegarPedidoResponse();
				pegarPedidoResponse.setStatus(HttpStatus.NOT_FOUND);
				pegarPedidoResponse.setStatus_code(404);
				pegarPedidoResponse.getMessages().add("Pedido não encontrado");

				return new ResponseEntity<>(pegarPedidoResponse, HttpStatus.NOT_FOUND);
			}
		} catch (DataIntegrityViolationException ex) {
			ErrorResponse erro = new ErrorResponse();
			erro.getMessages().add(ex.getLocalizedMessage());
			erro.setStatus_code(500);
			erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Criar um novo pedido
	@PostMapping("/criarPedido")
	public ResponseEntity<?> criarPedido(@Valid @RequestBody CriarPedidoRequest pedidoRequest,
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
				System.out.println(pedidoRequest.getItensPedidos().isEmpty());
				if (!pedidoRequest.getItensPedidos().isEmpty() && pedidoRequest.getItensPedidos().size() > 0) {
					Pedido pedido = new Pedido();
					LocalDate dataPedido = LocalDate.now();
					Optional<Cliente> cliente = clienteRepositorio.findById(pedidoRequest.getClienteID());

					if (cliente.isPresent()) {
						pedido.setDataPedido(dataPedido);
						pedido.setStatus("Preparando");
						pedido.setCliente(cliente.get());
						pedido.setMetodoPagamento(pedidoRequest.getMetodoPagamento());
						pedido.setEndereco(pedidoRequest.getEndereco());

						Pedido pedidoCriado = pedidoRepositorio.save(pedido);
						List<ItemPedido> itensPedidos = new ArrayList<>();

						for (CriarItemPedidoRequest itemPedidoRequest : pedidoRequest.getItensPedidos()) {
							ItemPedido itemPedido = new ItemPedido();
							Optional<Produto> produto = produtoRepositorio.findById(itemPedidoRequest.getProdutoID());

							if (produto.isPresent()) {
								itemPedido.setPedido(pedidoCriado);
								itemPedido.setProduto(produto.get());
								itemPedido.setPrecoUnitario(itemPedidoRequest.getPrecoUnitario());
								itemPedido.setQuantidade(itemPedidoRequest.getQuantidade());

								ItemPedido novoItemPedido = itemPedidoRepositorio.save(itemPedido);

								itensPedidos.add(novoItemPedido);
							} else {
								ErrorResponse erro = new ErrorResponse();
								erro.getMessages().add("O produto, " + itemPedidoRequest.getProdutoID()
										+ " que foi especificado como um item do pedido não existe.");
								erro.setStatus_code(404);
								erro.setStatus(HttpStatus.NOT_FOUND);

								return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
							}
						}

						pedidoCriado.setItensPedidos(itensPedidos);

						CriarPedidoResponse response = new CriarPedidoResponse();
						response.setItensPedidos(itensPedidos);
						response.setPedido(pedidoCriado);
						response.setStatus(HttpStatus.CREATED);
						response.setStatus_code(201);

						return new ResponseEntity<>(response, HttpStatus.CREATED);
					} else {
						ErrorResponse erro = new ErrorResponse();
						erro.getMessages().add("O Cliente não existe");
						erro.setStatus_code(404);
						erro.setStatus(HttpStatus.NOT_FOUND);

						return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
					}
				} else {
					ErrorResponse erro = new ErrorResponse();
					erro.getMessages()
							.add("Você precisa especificar pelo menos um item pedido para poder realizar o seu pedido!");
					erro.setStatus_code(428);
					erro.setStatus(HttpStatus.PRECONDITION_REQUIRED);

					return new ResponseEntity<>(erro, HttpStatus.PRECONDITION_REQUIRED);
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

	@PutMapping(path = "/cancelarPedido")
	public ResponseEntity<?> cancelarPedido(@PathVariable Long id) {
		try {
			Optional<Pedido> pedido = pedidoRepositorio.findById(id);

			if (pedido.isPresent()) {

				if (pedido.get().getStatus() == "Preparando") {
					pedido.get().setStatus("Cancelado");
					pedidoRepositorio.save(pedido.get());

					CancelarPedidoResponse cancelarPedidoResponse = new CancelarPedidoResponse();
					cancelarPedidoResponse.setStatus(HttpStatus.OK);
					cancelarPedidoResponse.setStatus_code(200);
					cancelarPedidoResponse.getMessages().add("Pedido cancelado com sucesso");

					return new ResponseEntity<>(cancelarPedidoResponse, HttpStatus.OK);
				} else {
					CancelarPedidoResponse cancelarPedidoResponse = new CancelarPedidoResponse();
					cancelarPedidoResponse.setStatus(HttpStatus.PRECONDITION_REQUIRED);
					cancelarPedidoResponse.setStatus_code(428);
					cancelarPedidoResponse.getMessages().add("O pedido só pode ser cancelado se ainda estiver sendo Preparado");

					return new ResponseEntity<>(cancelarPedidoResponse, HttpStatus.PRECONDITION_REQUIRED);
				}
			} else {
				CancelarPedidoResponse cancelarPedidoResponse = new CancelarPedidoResponse();
				cancelarPedidoResponse.setStatus(HttpStatus.NOT_FOUND);
				cancelarPedidoResponse.setStatus_code(404);
				cancelarPedidoResponse.getMessages().add("Pedido não encontrado");

				return new ResponseEntity<>(cancelarPedidoResponse, HttpStatus.NOT_FOUND);
			}
		} catch (DataIntegrityViolationException ex) {
			ErrorResponse erro = new ErrorResponse();
			erro.getMessages().add(ex.getLocalizedMessage());
			erro.setStatus_code(500);
			erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "/enviarPedido")
	public ResponseEntity<?> enviarPedido(@PathVariable Long id) {
		try {
			Optional<Pedido> pedido = pedidoRepositorio.findById(id);

			if (pedido.isPresent()) {

				if (pedido.get().getStatus() == "Preparando") {
					pedido.get().setStatus("Enviado");
					pedidoRepositorio.save(pedido.get());

					EnviarPedidoResponse enviarPedidoResponse = new EnviarPedidoResponse();
					enviarPedidoResponse.setStatus(HttpStatus.OK);
					enviarPedidoResponse.setStatus_code(200);
					enviarPedidoResponse.getMessages().add("Pedido Enviado.");

					return new ResponseEntity<>(enviarPedidoResponse, HttpStatus.OK);
				} else {
					EnviarPedidoResponse enviarPedidoResponse = new EnviarPedidoResponse();
					enviarPedidoResponse.setStatus(HttpStatus.PRECONDITION_REQUIRED);
					enviarPedidoResponse.setStatus_code(428);
					enviarPedidoResponse.getMessages().add("O pedido só pode ser enviado se ainda estiver sendo preparado.");

					return new ResponseEntity<>(enviarPedidoResponse, HttpStatus.PRECONDITION_REQUIRED);
				}
			} else {
				EnviarPedidoResponse enviarPedidoResponse = new EnviarPedidoResponse();
				enviarPedidoResponse.setStatus(HttpStatus.NOT_FOUND);
				enviarPedidoResponse.setStatus_code(404);
				enviarPedidoResponse.getMessages().add("Pedido não encontrado");

				return new ResponseEntity<>(enviarPedidoResponse, HttpStatus.NOT_FOUND);
			}
		} catch (DataIntegrityViolationException ex) {
			ErrorResponse erro = new ErrorResponse();
			erro.getMessages().add(ex.getLocalizedMessage());
			erro.setStatus_code(500);
			erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "/finalizarPedido")
	public ResponseEntity<?> finalizarPedido(@PathVariable Long id) {
		try {
			Optional<Pedido> pedido = pedidoRepositorio.findById(id);

			if (pedido.isPresent()) {

				if (pedido.get().getStatus() == "Enviado") {
					pedido.get().setStatus("Finalizado");
					pedidoRepositorio.save(pedido.get());

					FinalizarPedidoResponse finalizarPedidoResponse = new FinalizarPedidoResponse();
					finalizarPedidoResponse.setStatus(HttpStatus.OK);
					finalizarPedidoResponse.setStatus_code(200);
					finalizarPedidoResponse.getMessages().add("Pedido Finalizado.");

					return new ResponseEntity<>(finalizarPedidoResponse, HttpStatus.OK);
				} else {
					FinalizarPedidoResponse finalizarPedidoResponse = new FinalizarPedidoResponse();
					finalizarPedidoResponse.setStatus(HttpStatus.PRECONDITION_REQUIRED);
					finalizarPedidoResponse.setStatus_code(428);
					finalizarPedidoResponse.getMessages().add("O pedido só pode ser finalizado se já estiver sido enviado.");

					return new ResponseEntity<>(finalizarPedidoResponse, HttpStatus.PRECONDITION_REQUIRED);
				}
			} else {
				FinalizarPedidoResponse finalizarPedidoResponse = new FinalizarPedidoResponse();
				finalizarPedidoResponse.setStatus(HttpStatus.NOT_FOUND);
				finalizarPedidoResponse.setStatus_code(404);
				finalizarPedidoResponse.getMessages().add("Pedido não encontrado");

				return new ResponseEntity<>(finalizarPedidoResponse, HttpStatus.NOT_FOUND);
			}
		} catch (DataIntegrityViolationException ex) {
			ErrorResponse erro = new ErrorResponse();
			erro.getMessages().add(ex.getLocalizedMessage());
			erro.setStatus_code(500);
			erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
