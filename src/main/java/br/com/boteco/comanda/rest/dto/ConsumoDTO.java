package br.com.boteco.comanda.rest.dto;


import br.com.boteco.comanda.model.ComandaModel;
import br.com.boteco.comanda.model.ConsumoModel;
import br.com.boteco.comanda.model.ProdutoModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumoDTO {


    private Long idConsumo;
    private int quantidade;
    private LocalDateTime dataHoraConsumo;
    private BigDecimal precoUnitarioVendido;
    private BigDecimal precoTotal;
    private ProdutoModel produto;
    private ComandaModel comanda;


    public ConsumoModel toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ConsumoModel.class);
    }


}
