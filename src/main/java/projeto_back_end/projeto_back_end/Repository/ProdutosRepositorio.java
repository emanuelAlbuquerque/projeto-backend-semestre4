package projeto_back_end.projeto_back_end.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projeto_back_end.projeto_back_end.Models.Categoria;
import projeto_back_end.projeto_back_end.Models.Produto;

@Repository
public interface ProdutosRepositorio extends JpaRepository<Produto, Long> {
  List<Produto> findByIdIn(List<Long> ids);
}
