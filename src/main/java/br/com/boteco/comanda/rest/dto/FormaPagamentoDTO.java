package br.com.boteco.comanda.rest.dto;


import br.com.boteco.comanda.model.FormaPagamentoModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagamentoDTO {


    private Long idFormaPagamento;
    private String nome;
    private String descricao;


    public FormaPagamentoModel toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, FormaPagamentoModel.class);
    }


}
