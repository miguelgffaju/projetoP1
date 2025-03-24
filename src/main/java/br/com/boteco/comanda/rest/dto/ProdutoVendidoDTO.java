package br.com.boteco.comanda.rest.dto;

import br.com.boteco.comanda.model.ProdutoModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoVendidoDTO {
    private ProdutoModel produto;
    private Integer quantidadeVendida;


}
