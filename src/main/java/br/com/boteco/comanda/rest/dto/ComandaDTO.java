package br.com.boteco.comanda.rest.dto;


import br.com.boteco.comanda.model.ComandaModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;



import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComandaDTO {


    private Long idComanda;
    //private DateTimeException dataHoraAbertura;
    private LocalDateTime dataHoraAbertura;
    //private DateTimeException dataHoraFechamento;
    private LocalDateTime dataHoraFechamento;
    private float valorTotalComanda;
    private float valorGorjeta;
    private String status;


    public ComandaModel toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ComandaModel.class);
    }


}
