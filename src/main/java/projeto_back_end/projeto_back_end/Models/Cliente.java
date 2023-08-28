package projeto_back_end.projeto_back_end.Models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name = "clientes")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long clienteID;
	
	@Column(name = "nome", columnDefinition = "VARCHAR(60)", nullable = false)
	public String nome;
	
	@Column(name = "data_nascimento", columnDefinition = "VARCHAR(10)", nullable = false)
	public String data_nascimento;
	
	@Column(name = "cpf", columnDefinition = "VARCHAR(14)", nullable = false)
	public String cpf;
	
	@Column(name = "genero", columnDefinition = "VARCHAR(9)", nullable = false)
	public String genero;
	
	@Column(name = "email", columnDefinition = "VARCHAR(40)", nullable = false)
	public String email;
	
	@OneToMany(mappedBy="cliente", cascade = CascadeType.ALL,orphanRemoval = true)
    public List<Pedido> pedidos;
	

}
