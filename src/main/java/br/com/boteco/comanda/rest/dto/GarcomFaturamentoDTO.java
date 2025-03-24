package br.com.boteco.comanda.rest.dto;

import br.com.boteco.comanda.model.GarcomModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import br.com.boteco.comanda.model.GarcomModel;
import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarcomFaturamentoDTO {
    private Map<GarcomModel, BigDecimal> garconsFaturamento;
}
