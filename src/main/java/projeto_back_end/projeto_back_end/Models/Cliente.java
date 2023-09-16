package projeto_back_end.projeto_back_end.Models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity(name = "clientes")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long clienteID;

	@Column(name = "nome", columnDefinition = "VARCHAR(255)", nullable = false)
	private String nome;

	@Column(name = "idade", columnDefinition = "int", nullable = false)
	private int idade;

	@Column(name = "cpf", columnDefinition = "VARCHAR(14)", nullable = false)
	private String cpf;

	@Column(name = "genero", columnDefinition = "VARCHAR(9)", nullable = false)
	private String genero;

	@Column(name = "email", columnDefinition = "VARCHAR(40)", nullable = false)
	private String email;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Pedido> pedidos;

}
