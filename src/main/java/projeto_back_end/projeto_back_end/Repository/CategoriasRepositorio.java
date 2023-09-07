package projeto_back_end.projeto_back_end.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projeto_back_end.projeto_back_end.Models.Categoria;

@Repository
public interface CategoriasRepositorio extends JpaRepository<Categoria, Long> {
  List<Categoria> findByIdIn(List<Long> ids);
}
