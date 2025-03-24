package br.com.boteco.comanda.rest.controller;

import br.com.boteco.comanda.model.ComandaModel;
import br.com.boteco.comanda.rest.dto.ComandaDTO;
import br.com.boteco.comanda.service.ComandaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/comanda")
public class ComandaController {

    @Autowired
    private ComandaService comandaService;


    @GetMapping()
    public ResponseEntity<List<ComandaDTO>> obterTodos(){
        List<ComandaDTO> comandaDTOS = comandaService.obterTodos();
        return ResponseEntity.ok(comandaDTOS);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ComandaDTO> obterPorId(@PathVariable Long id) {
        ComandaDTO comandaDTO = comandaService.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(comandaDTO);
    }


    @PostMapping
    public ResponseEntity<ComandaDTO> salvar(@Valid @RequestBody ComandaModel novaComanda){
        ComandaDTO novaComandaDTO = comandaService.salvar(novaComanda);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaComandaDTO);
    }


    public ResponseEntity<ComandaDTO> atualizar(@Valid @RequestBody ComandaModel comandaExistente){
        ComandaDTO comandaExistenteDTO = comandaService.atualizar(comandaExistente);
        return  ResponseEntity.status(HttpStatus.OK).body(comandaExistenteDTO);
    }


    @DeleteMapping
    public ResponseEntity<String> deletar(@Valid @RequestBody ComandaModel comandaModel) {
        comandaService.deletar(comandaModel);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Comanda excluído com sucesso.");
    }

    @GetMapping("/Faturamento-Total")
    public ResponseEntity<BigDecimal> calcularFaturamentoTotal(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fim) {

        BigDecimal faturamento = comandaService.calcularFaturamentoTotal(inicio, fim);
        return ResponseEntity.ok(faturamento);
    }

    @GetMapping("/Comanda-de-maior-consumo")
    public ResponseEntity<List<ComandaDTO>> encontrarComandasComMaiorConsumo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {

        List<ComandaDTO> comandas = comandaService.encontrarComandasComMaiorConsumo(inicio, fim);

        if (comandas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comandas);
    }

    @GetMapping("/Media-de-tempo-de-permanencia")
    public ResponseEntity<Double> calcularMediaTempoPermanencia(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {

        double media = comandaService.calcularMediaTempoPermanencia(inicio, fim);
        return ResponseEntity.ok(media);
    }
}
