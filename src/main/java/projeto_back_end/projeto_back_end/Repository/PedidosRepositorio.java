package projeto_back_end.projeto_back_end.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projeto_back_end.projeto_back_end.Models.Pedido;

@Repository
public interface PedidosRepositorio extends JpaRepository<Pedido, Long>{

}
