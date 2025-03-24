
package br.com.boteco.comanda.rest.controller;
import br.com.boteco.comanda.model.ConsumoModel;
import br.com.boteco.comanda.rest.dto.ConsumoDTO;
import br.com.boteco.comanda.service.ConsumoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumo")
public class ConsumoController {

    @Autowired
    private ConsumoService consumoService;


    @GetMapping()
    public ResponseEntity<List<ConsumoDTO>> obterTodos(){
        List<ConsumoDTO> consumoDTOS = consumoService.obterTodos();
        return ResponseEntity.ok(consumoDTOS);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ConsumoDTO> obterPorId(@PathVariable Long id) {
        ConsumoDTO consumoDTO = consumoService.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(consumoDTO);
    }


    @PostMapping
    public ResponseEntity<ConsumoDTO> salvar(@Valid @RequestBody ConsumoModel novoConsumo){
        ConsumoDTO novoConsumoDTO = consumoService.salvar(novoConsumo);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoConsumoDTO);
    }


    public ResponseEntity<ConsumoDTO> atualizar(@Valid @RequestBody ConsumoModel consumoExistente){
        ConsumoDTO consumoExistenteDTO = consumoService.atualizar(consumoExistente);
        return  ResponseEntity.status(HttpStatus.OK).body(consumoExistenteDTO);
    }


    @DeleteMapping
    public ResponseEntity<String> deletar(@Valid @RequestBody ConsumoModel consumoModel) {
        consumoService.deletar(consumoModel);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Consumo excluído com sucesso.");
    }
}

