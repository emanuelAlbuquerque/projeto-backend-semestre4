package projeto_back_end.projeto_back_end.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projeto_back_end.projeto_back_end.Models.ItemPedido;

@Repository
public interface ItemPedidoRepositorio extends JpaRepository<ItemPedido, Long> {

}
