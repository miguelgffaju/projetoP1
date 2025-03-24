package br.com.boteco.comanda.rest.controller;


import br.com.boteco.comanda.model.ProdutoModel;
import br.com.boteco.comanda.rest.dto.ProdutoDTO;
import br.com.boteco.comanda.rest.dto.ProdutoVendidoDTO;
import br.com.boteco.comanda.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;


    @GetMapping()
    public ResponseEntity<List<ProdutoDTO>> obterTodos(){
        List<ProdutoDTO> produtoDTOS = produtoService.obterTodos();
        return ResponseEntity.ok(produtoDTOS);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> obterPorId(@PathVariable Long id) {
        ProdutoDTO produtoDTO = produtoService.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(produtoDTO);
    }


    @PostMapping
    public ResponseEntity<ProdutoDTO> salvar(@Valid @RequestBody ProdutoModel novoProduto){
        ProdutoDTO novoProdutoDTO = produtoService.salvar(novoProduto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProdutoDTO);
    }


    public ResponseEntity<ProdutoDTO> atualizar(@Valid @RequestBody ProdutoModel produtoExistente){
        ProdutoDTO produtoExistenteDTO = produtoService.atualizar(produtoExistente);
        return  ResponseEntity.status(HttpStatus.OK).body(produtoExistenteDTO);
    }


    @DeleteMapping
    public ResponseEntity<String> deletar(@Valid @RequestBody ProdutoModel produtoModel) {
        produtoService.deletar(produtoModel);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Produto excluído com sucesso.");
    }



    @GetMapping("/produto-mais-vendido")
    public ResponseEntity<ProdutoVendidoDTO> encontrarProdutoMaisVendido(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fim) {

        ProdutoVendidoDTO resultado = produtoService.encontrarProdutoMaisVendido(inicio, fim);

        if (resultado.getProduto() == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(resultado);
    }



}
