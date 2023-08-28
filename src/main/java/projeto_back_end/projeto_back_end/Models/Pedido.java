package projeto_back_end.projeto_back_end.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "pedidos")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long pedidoID;
	
	@Column(name = "data_atualizada", columnDefinition = "VARCHAR(10)", nullable = false)
	public String data_atualizada;
	
	@Column(name = "metodo_pagamento", columnDefinition = "VARCHAR(15)", nullable = false)
	public String metodo_pagamento;
	
	@Column(name = "status", columnDefinition = "VARCHAR(60)", nullable = false)
	public String status;
	
	@Column(name = "data_pedido", columnDefinition = "VARCHAR(10)", nullable = false)
	public String data_pedido;
	
	@ManyToOne
    @JoinColumn(name="cliente_id", nullable=false)
    public Cliente cliente;

}
