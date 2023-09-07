package projeto_back_end.projeto_back_end.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projeto_back_end.projeto_back_end.Models.Cliente;

@Repository
public interface ClientesRepositorio extends JpaRepository<Cliente, Long> {

}
