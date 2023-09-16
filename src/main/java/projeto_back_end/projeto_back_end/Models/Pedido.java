package projeto_back_end.projeto_back_end.Models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pedido_id")
	private Long pedidoID;

	@Column(name = "metodo_pagamento", columnDefinition = "VARCHAR(15)", nullable = false)
	private String metodoPagamento;

	@Column(name = "status", columnDefinition = "VARCHAR(60)", nullable = false)
	private String status;

	@Column(name = "data_pedido", columnDefinition = "DATE", nullable = false)
	private LocalDate dataPedido;

	@Column(name = "endereco", columnDefinition = "VARCHAR(255)", nullable = false)
	private String endereco;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

	@OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER)
	private List<ItemPedido> itensPedidos;
}
