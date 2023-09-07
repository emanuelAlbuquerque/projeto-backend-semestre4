package projeto_back_end.projeto_back_end.Controllers;

import java.util.List;
import java.util.Optional;

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
import projeto_back_end.projeto_back_end.DTO.CategoriasDTOs.AtualizarCategoriaRequest;
import projeto_back_end.projeto_back_end.DTO.CategoriasDTOs.AtualizarCategoriaResponse;
import projeto_back_end.projeto_back_end.DTO.CategoriasDTOs.CriarCategoriaRequest;
import projeto_back_end.projeto_back_end.DTO.CategoriasDTOs.CriarCategoriaResponse;
import projeto_back_end.projeto_back_end.DTO.CategoriasDTOs.DeletarCategoriaResponse;
import projeto_back_end.projeto_back_end.DTO.CategoriasDTOs.ListarCategoriasResponse;
import projeto_back_end.projeto_back_end.DTO.CategoriasDTOs.PegarCategoriaResponse;
import projeto_back_end.projeto_back_end.DTO.ErrorDTO.ErrorResponse;
import projeto_back_end.projeto_back_end.Models.Categoria;
import projeto_back_end.projeto_back_end.Models.Produto;
import projeto_back_end.projeto_back_end.Repository.CategoriasRepositorio;
import projeto_back_end.projeto_back_end.Repository.ProdutosRepositorio;

@RestController
public class CategoriaController {

  @Autowired
  private CategoriasRepositorio categoriaRepositorio;

  @Autowired
  private ProdutosRepositorio produtosRepositorio;

  // Listar todas as categorias
  @GetMapping(value = "/listarCategorias", produces = "application/json")
  public ResponseEntity<?> listarCategorias() {
    try {
      List<Categoria> categorias = categoriaRepositorio.findAll();
      ListarCategoriasResponse listarCategoriasResponse = new ListarCategoriasResponse();

      listarCategoriasResponse.setQuantidade(categorias.size());
      listarCategoriasResponse.setStatus(HttpStatus.OK);
      listarCategoriasResponse.setStatus_code(200);

      if (categorias.size() == 0) {
        listarCategoriasResponse.getMessages().add("Não existe nenhum produto cadastrado.");
      }

      listarCategoriasResponse.setCategorias(categorias);

      return new ResponseEntity<>(listarCategoriasResponse, HttpStatus.OK);
    } catch (DataIntegrityViolationException ex) {
      ErrorResponse erro = new ErrorResponse();
      erro.getMessages().add(ex.getLocalizedMessage());
      erro.setStatus_code(500);
      erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

      return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // Obter uma categoria por ID
  @GetMapping(value = "/categoria/{id}", produces = "application/json")
  public ResponseEntity<?> obterCategoriaPorId(@PathVariable Long id) {
    try {
      Optional<Categoria> categoria = categoriaRepositorio.findById(id);

      if (categoria.isPresent()) {
        PegarCategoriaResponse pegarCategoriaResponse = new PegarCategoriaResponse();

        pegarCategoriaResponse.setCategoria(categoria.get());
        pegarCategoriaResponse.getMessages().add("Categoria encontrada.");
        pegarCategoriaResponse.setStatus_code(200);
        pegarCategoriaResponse.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(pegarCategoriaResponse, HttpStatus.OK);
      } else {
        ErrorResponse erro = new ErrorResponse();
        erro.getMessages().add("Categoria não encontrada");
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

  // Criar uma nova categoria
  @PostMapping("/criarCategoria")
  public ResponseEntity<?> criarCategoria(@Valid @RequestBody CriarCategoriaRequest categoriaRequest,
      BindingResult bindingResult) {
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
        Categoria categoria = new Categoria();

        if (categoriaRequest.getProdutosIDs() != null) {
          List<Produto> produtos = produtosRepositorio.findAllById(categoriaRequest.getProdutosIDs());
          categoria.setProdutos(produtos);
        }

        categoria.setNome(categoriaRequest.getNome());

        categoriaRepositorio.save(categoria);

        CriarCategoriaResponse response = new CriarCategoriaResponse();
        response.setCategoria(categoria);
        response.getMessages().add("Categria cadastrada com sucesso.");
        response.setStatus_code(201);
        response.setStatus(HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
      }
    } catch (DataIntegrityViolationException ex) {
      ErrorResponse erro = new ErrorResponse();
      erro.getMessages().add(ex.getLocalizedMessage());
      erro.setStatus_code(500);
      erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

      return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // Atualizar uma categoria existente
  @PutMapping("/atualizarCategoria/{id}")
  public ResponseEntity<?> atualizarCategoria(@PathVariable Long id,
      @Valid @RequestBody AtualizarCategoriaRequest categoria, BindingResult bindingResult) {

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
        Optional<Categoria> categoriaExistente = categoriaRepositorio.findById(id);

        if (categoriaExistente.isPresent()) {

          if (categoria.getProdutosIDs() != null) {
            List<Produto> produtos = produtosRepositorio.findAllById(categoria.getProdutosIDs());
            categoriaExistente.get().setProdutos(produtos);
          }
          categoriaExistente.get().setNome(categoria.getNome());

          categoriaRepositorio.save(categoriaExistente.get());

          AtualizarCategoriaResponse response = new AtualizarCategoriaResponse();
          response.setCategoria(categoriaExistente.get());
          response.getMessages().add("Categria atualizada com sucesso.");
          response.setStatus_code(201);
          response.setStatus(HttpStatus.CREATED);
          return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
          ErrorResponse erro = new ErrorResponse();
          erro.getMessages().add("Categoria não encontrada");
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

  // Deletar uma categoria por ID
  @DeleteMapping("/deletarCategoria/{id}")
  public ResponseEntity<?> deletarCategoria(@PathVariable Long id) {
    try {
      Optional<Categoria> categoria = categoriaRepositorio.findById(id);
      if (categoria.isPresent()) {
        categoriaRepositorio.deleteById(id);
        DeletarCategoriaResponse deletarCategoriaResponse = new DeletarCategoriaResponse();

        deletarCategoriaResponse.getMessages().add("Categoria deletada com sucesso.");
        deletarCategoriaResponse.setStatus_code(200);
        deletarCategoriaResponse.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(deletarCategoriaResponse, HttpStatus.OK);
      } else {
        ErrorResponse erro = new ErrorResponse();
        erro.getMessages().add("Categoria não encontrada");
        erro.setStatus_code(404);
        erro.setStatus(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
      }
    } catch (Exception ex) {
      ErrorResponse erro = new ErrorResponse();
      erro.getMessages().add(ex.getLocalizedMessage());
      erro.setStatus_code(500);
      erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
      return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
