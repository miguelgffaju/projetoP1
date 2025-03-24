package br.com.boteco.comanda.rest.dto;


import br.com.boteco.comanda.model.MesaModel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import lombok.Data;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MesaDTO {


    private Long idMesa;
    private int numero;
    private String status;


    public MesaModel toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, MesaModel.class);
    }
}
