package br.com.boteco.comanda.rest.dto;

import br.com.boteco.comanda.model.FormaPagamentoModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagamentoUtilizadaDTO {
    private Map<FormaPagamentoModel, Integer> formasPagamento;
}




