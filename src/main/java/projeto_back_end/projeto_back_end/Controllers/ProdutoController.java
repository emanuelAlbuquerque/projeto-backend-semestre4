package projeto_back_end.projeto_back_end.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto_back_end.projeto_back_end.Models.Produto;
import projeto_back_end.projeto_back_end.Repository.ProdutosRepositorio;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

  @Autowired
  private ProdutosRepositorio produtoRepositorio;

  // Listar todos os produtos
  @GetMapping("/")
  public List<Produto> listarProdutos() {
    List<Produto> produtos = produtoRepositorio.findAll();
    return produtos;
  }

  // Obter um produto por ID
  @GetMapping("/{id}")
  public Produto obterProdutoPorId(@PathVariable Long id) {
    Produto produto = produtoRepositorio.findById(id).orElse(null);
    return produto;
  }

  // Criar um novo produto
  @PostMapping("/")
  public Produto criarProduto(@RequestBody Produto produto) {
    Produto novoProduto = produtoRepositorio.save(produto);
    return novoProduto;
  }

  // Atualizar um produto existente
  @PutMapping("/{id}")
  public Produto atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
    Produto produtoExistente = produtoRepositorio.findById(id).orElse(null);
    if (produtoExistente != null) {
      produtoExistente.nome = produtoAtualizado.nome;
      produtoExistente.descricao = produtoAtualizado.descricao;
      produtoExistente.codigo_produto = produtoExistente.codigo_produto;
      produtoExistente.preco = produtoAtualizado.preco;
      produtoExistente.tamanho = produtoAtualizado.tamanho;

      Produto novoProduto = produtoRepositorio.save(produtoExistente);
      return novoProduto;
    }
    return null;
  }

  // Deletar um produto por ID
  @DeleteMapping("/{id}")
  public void deletarProduto(@PathVariable Long id) {
    produtoRepositorio.deleteById(id);
  }
}
