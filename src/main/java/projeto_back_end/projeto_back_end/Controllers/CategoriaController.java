package projeto_back_end.projeto_back_end.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto_back_end.projeto_back_end.Models.Categoria;
import projeto_back_end.projeto_back_end.Repository.CategoriasRepositorio;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriasRepositorio categoriaRepositorio;
	
	// Listar todas as categorias
	  @GetMapping("/")
	  public List<Categoria> listarCategorias() {
	    List<Categoria> categorias = categoriaRepositorio.findAll();
	    return categorias;
	  }
	  
	// Obter uma categoria por ID
	  @GetMapping("/{id}")
	  public Categoria obterCategoriaPorId(@PathVariable Long id) {
	    Categoria categoria = categoriaRepositorio.findById(id).orElse(null);
	    return categoria;
	  }
	  
	// Criar uma nova categoria
	  @PostMapping("/")
	  public Categoria criarCategoria(@RequestBody Categoria categoria) {
	    Categoria novaCategoria = categoriaRepositorio.save(categoria);
	    return novaCategoria;
	  }
	  
	// Atualizar uma categoria existente
	  @PutMapping("/{id}")
	  public Categoria atualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoriaAtualizado) {
	    Categoria categoriaExistente = categoriaRepositorio.findById(id).orElse(null);
	    if (categoriaExistente != null) {
	      categoriaExistente.nome = categoriaAtualizado.nome;

	      Categoria novaCategoria = categoriaRepositorio.save(categoriaExistente);
	      return novaCategoria;
	    }
	    return null;
	  }
	  
	  // Deletar uma categoria por ID
	  @DeleteMapping("/{id}")
	  public void deletarCategoria(@PathVariable Long id) {
	    categoriaRepositorio.deleteById(id);
	  }

}
