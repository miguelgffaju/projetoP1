package br.com.boteco.comanda.rest.controller;


import br.com.boteco.comanda.model.FormaPagamentoModel;
import br.com.boteco.comanda.rest.dto.FormaPagamentoDTO;
import br.com.boteco.comanda.rest.dto.FormaPagamentoUtilizadaDTO;
import br.com.boteco.comanda.service.FormaPagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/formaPagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;


    @GetMapping()
    public ResponseEntity<List<FormaPagamentoDTO>> obterTodos(){
        List<FormaPagamentoDTO> formaPagamentoDTOS = formaPagamentoService.obterTodos();
        return ResponseEntity.ok(formaPagamentoDTOS);
    }


    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoDTO> obterPorId(@PathVariable Long id) {
        FormaPagamentoDTO formaPagamentoDTO = formaPagamentoService.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(formaPagamentoDTO);
    }


    @PostMapping
    public ResponseEntity<FormaPagamentoDTO> salvar(@Valid @RequestBody FormaPagamentoModel novaFormaPagamento){
        FormaPagamentoDTO novaFormaPagamentoDTO = formaPagamentoService.salvar(novaFormaPagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaFormaPagamentoDTO);
    }


    public ResponseEntity<FormaPagamentoDTO> atualizar(@Valid @RequestBody FormaPagamentoModel formaPagamentoExistente){
        FormaPagamentoDTO formaPagamentoExistenteDTO = formaPagamentoService.atualizar(formaPagamentoExistente);
        return  ResponseEntity.status(HttpStatus.OK).body(formaPagamentoExistenteDTO);
    }


    @DeleteMapping
    public ResponseEntity<String> deletar(@Valid @RequestBody FormaPagamentoModel formaPagamentoModel) {
        formaPagamentoService.deletar(formaPagamentoModel);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Forma de PagamentoDTO excluído com sucesso.");
    }

    @GetMapping("/forma-pagamento-mais-utilizada")
    public ResponseEntity<FormaPagamentoUtilizadaDTO> encontrarFormaPagamentoMaisUtilizada(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fim) {

        FormaPagamentoUtilizadaDTO resultado = formaPagamentoService.encontrarFormaPagamentoMaisUtilizada(inicio, fim);
        return ResponseEntity.ok(resultado);
    }

}
