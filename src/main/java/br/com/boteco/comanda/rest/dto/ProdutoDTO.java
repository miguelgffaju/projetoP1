package br.com.boteco.comanda.rest.dto;


import br.com.boteco.comanda.model.ProdutoModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {


    private Long idProduto;
    private String nome;
    private String descricao;
    private Float preco;
    private String status;


    public ProdutoModel toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ProdutoModel.class);
    }
}
