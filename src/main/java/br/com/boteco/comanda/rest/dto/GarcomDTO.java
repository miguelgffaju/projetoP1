package br.com.boteco.comanda.rest.dto;

import br.com.boteco.comanda.model.GarcomModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) para encapsular dados do garçom em operações de entrada e saída na API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarcomDTO {

    private Long idGarcom;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private String telefone;
    private String email;
    private String sexo;

    /**
     * Converte o DTO (GarcomDTO) para sua entidade correspondente (GarcomModel).
     *
     * @return Uma instância de GarcomModel com os dados mapeados a partir do GarcomDTO.
     */
    public GarcomModel toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, GarcomModel.class);
    }
}