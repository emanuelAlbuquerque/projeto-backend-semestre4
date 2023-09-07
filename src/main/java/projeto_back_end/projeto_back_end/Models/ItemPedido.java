package projeto_back_end.projeto_back_end.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "item_pedido")
public class ItemPedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long Id;
	
	@ManyToOne
    @JoinColumn(name = "pedidoId")
    Pedido pedido;
	
	@ManyToOne
    @JoinColumn(name = "produtoId")
    Produto produto;
	
	@Column(name = "quantidade", columnDefinition = "int", nullable = false)
    public int quantidade;
	
    @Column(name = "preco_unitario", columnDefinition = "decimal", nullable = false)
    public float precoUnitario;

} 
