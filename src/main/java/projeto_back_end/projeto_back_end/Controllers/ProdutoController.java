package projeto_back_end.projeto_back_end.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import projeto_back_end.projeto_back_end.DTO.ErrorDTO.ErrorResponse;
import projeto_back_end.projeto_back_end.DTO.ProdutosDTOs.AtualizarProdutoRequest;
import projeto_back_end.projeto_back_end.DTO.ProdutosDTOs.AtualizarProdutoResponse;
import projeto_back_end.projeto_back_end.DTO.ProdutosDTOs.CriarProdutoRequest;
import projeto_back_end.projeto_back_end.DTO.ProdutosDTOs.CriarProdutoResponse;
import projeto_back_end.projeto_back_end.DTO.ProdutosDTOs.DeletarProdutoResponse;
import projeto_back_end.projeto_back_end.DTO.ProdutosDTOs.ListarCategoriasDoProdutoResponse;
import projeto_back_end.projeto_back_end.DTO.ProdutosDTOs.ListarProdutosResponse;
import projeto_back_end.projeto_back_end.DTO.ProdutosDTOs.PegarProdutoResponse;
import projeto_back_end.projeto_back_end.Models.Produto;
import projeto_back_end.projeto_back_end.Repository.CategoriasRepositorio;
import projeto_back_end.projeto_back_end.Repository.ProdutosRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProdutoController {

  @Autowired
  private ProdutosRepositorio produtoRepositorio;
  @Autowired
  private CategoriasRepositorio categoriasRepositorio;

  // Listar todos os produtos
  @GetMapping(value = "/listarProdutos", produces = "application/json")
  public ResponseEntity<?> listarProdutos() {
    try {
      List<Produto> produtos = produtoRepositorio.findAll();
      ListarProdutosResponse listarProdutosResponse = new ListarProdutosResponse();

      listarProdutosResponse.setQuantidade(produtos.size());
      listarProdutosResponse.setStatus(HttpStatus.OK);
      listarProdutosResponse.setStatus_code(200);

      if (produtos.size() == 0) {
        listarProdutosResponse.getMessages().add("Não existe nenhum produto cadastrado.");
      }

      listarProdutosResponse.setProdutos(produtos);

      return new ResponseEntity<>(listarProdutosResponse, HttpStatus.OK);
    } catch (DataIntegrityViolationException ex) {
      ErrorResponse erro = new ErrorResponse();
      erro.getMessages().add(ex.getLocalizedMessage());
      erro.setStatus_code(500);
      erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

      return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // Obter um produto por ID
  @GetMapping("/produto/{id}")
  public ResponseEntity<?> produtoPorId(@PathVariable Long id) {
    try {
      Optional<Produto> produto = produtoRepositorio.findById(id);

      if (produto.isPresent()) {
        PegarProdutoResponse pegarProdutoResponse = new PegarProdutoResponse();

        pegarProdutoResponse.setProduto(produto.get());
        pegarProdutoResponse.getMessages().add("Produto encontrado.");
        pegarProdutoResponse.setStatus_code(200);
        pegarProdutoResponse.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(pegarProdutoResponse, HttpStatus.OK);
      } else {
        ErrorResponse erro = new ErrorResponse();
        erro.getMessages().add("Produto não encontrado");
        erro.setStatus_code(404);
        erro.setStatus(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
      }
    } catch (DataIntegrityViolationException ex) {
      ErrorResponse erro = new ErrorResponse();
      erro.getMessages().add(ex.getLocalizedMessage());
      erro.setStatus_code(500);
      erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

      return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // Criar um novo produto
  @PostMapping("/cadastrarProduto")
  public ResponseEntity<?> criarProduto(@Valid @RequestBody CriarProdutoRequest produto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      ErrorResponse error = new ErrorResponse();

      error.setStatus_code(428);
      error.setStatus(HttpStatus.PRECONDITION_REQUIRED);

      for (ObjectError obj : bindingResult.getAllErrors()) {
        error.getMessages().add(obj.getDefaultMessage());
      }

      return new ResponseEntity<>(error, HttpStatus.PRECONDITION_REQUIRED);
    } else {
      try {
        CriarProdutoResponse response = new CriarProdutoResponse();
        Produto novoProduto = new Produto();
        UUID codProduto = UUID.randomUUID();

        novoProduto.setNome(produto.getNome());
        novoProduto.setCodigo_produto(codProduto);
        novoProduto.setDescricao(produto.getDescricao());
        novoProduto.setPreco(produto.getPreco());
        novoProduto.setTamanho(produto.getTamanho());

        produtoRepositorio.save(novoProduto);

        response.setProduto(novoProduto);
        response.getMessages().add("Produto cadastrado com sucesso.");
        response.setStatus_code(201);
        response.setStatus(HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
      } catch (DataIntegrityViolationException ex) {
        ErrorResponse erro = new ErrorResponse();
        erro.getMessages().add(ex.getLocalizedMessage());
        erro.setStatus_code(500);
        erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
  }

  // Atualizar um produto existente
  @PutMapping("/atualizarProduto/{id}")
  public ResponseEntity<?> atualizarProduto(@PathVariable Long id,
      @Valid @RequestBody AtualizarProdutoRequest produto, BindingResult bindingResult) {
    try {
      if (bindingResult.hasErrors()) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus_code(428);
        error.setStatus(HttpStatus.PRECONDITION_REQUIRED);

        for (ObjectError obj : bindingResult.getAllErrors()) {
          error.getMessages().add(obj.getDefaultMessage());
        }

        return new ResponseEntity<>(error, HttpStatus.PRECONDITION_REQUIRED);
      } else {
        Optional<Produto> produtoExistente = produtoRepositorio.findById(id);

        if (produtoExistente.isPresent()) {
          produtoExistente.get().setNome(produto.getNome());
          produtoExistente.get().setDescricao(produto.getDescricao());
          produtoExistente.get().setPreco(produto.getPreco());
          produtoExistente.get().setTamanho(produto.getTamanho());

          Produto produtoAtualizado = produtoRepositorio.save(produtoExistente.get());

          AtualizarProdutoResponse response = new AtualizarProdutoResponse();

          response.setProduto(produtoAtualizado);
          response.setStatus_code(201);
          response.setStatus(HttpStatus.CREATED);
          response.getMessages().add("Produto Atualizado com sucesso.");

          return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
          ErrorResponse erro = new ErrorResponse();
          erro.getMessages().add("Produto não encontrado");
          erro.setStatus_code(404);
          erro.setStatus(HttpStatus.NOT_FOUND);

          return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
        }
      }
    } catch (DataIntegrityViolationException ex) {
      ErrorResponse erro = new ErrorResponse();
      erro.getMessages().add(ex.getLocalizedMessage());
      erro.setStatus_code(500);
      erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
      return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // Deletar um produto por ID
  @DeleteMapping("/deletarProduto/{id}")
  public ResponseEntity<?> deletarProduto(@PathVariable Long id) {
    try {
      Optional<Produto> produto = produtoRepositorio.findById(id);
      if (produto.isPresent()) {
        produtoRepositorio.deleteById(id);
        DeletarProdutoResponse deletarProdutoResponse = new DeletarProdutoResponse();

        deletarProdutoResponse.getMessages().add("Produto Deletado.");
        deletarProdutoResponse.setStatus_code(200);
        deletarProdutoResponse.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(deletarProdutoResponse, HttpStatus.OK);
      } else {
        ErrorResponse erro = new ErrorResponse();
        erro.getMessages().add("Produto não encontrado");
        erro.setStatus_code(404);
        erro.setStatus(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
      }
    } catch (DataIntegrityViolationException ex) {
      ErrorResponse erro = new ErrorResponse();
      erro.getMessages().add(ex.getLocalizedMessage());
      erro.setStatus_code(500);
      erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
      return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/listarCategoriasDoProduto/{id}")
  public ResponseEntity<?> listarCategoriasDoProduto(@PathVariable Long id) {
    try {
      Optional<Produto> produto = produtoRepositorio.findById(id);

      if (produto.isPresent()) {
        ListarCategoriasDoProdutoResponse response = new ListarCategoriasDoProdutoResponse();

        if (produto.get().getCategorias().size() == 0) {
          response.getMessages().add("Não existe nenhuma categoria vinculada a este produto");
        }

        response.setQuantidade(produto.get().getCategorias().size());
        response.setCategorias(produto.get().getCategorias());
        response.setStatus_code(200);
        response.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(response, HttpStatus.OK);
      } else {
        ErrorResponse erro = new ErrorResponse();
        erro.getMessages().add("Produto não encontrado");
        erro.setStatus_code(404);
        erro.setStatus(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
      }
    } catch (DataIntegrityViolationException ex) {
      ErrorResponse erro = new ErrorResponse();
      erro.getMessages().add(ex.getLocalizedMessage());
      erro.setStatus_code(500);
      erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

      return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
