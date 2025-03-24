package br.com.boteco.comanda.model;

import br.com.boteco.comanda.rest.dto.GarcomDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

/**
 * Representa a entidade Garçom, responsável por armazenar os dados de um garçom
 * no sistema e mapeá-los para a base de dados.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "garcom")
public class GarcomModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Garcom")
    private Long idGarcom;

    @NotNull(message = "O nome não pode ser nulo.")
    @NotBlank(message = "O nome é obrigatório.")
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @NotNull(message = "A data de nascimento não pode ser nula.")
    @Past(message = "A data de nascimento informada deve ser anterior ao dia atual.")
    @Column(name = "data_Nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotNull(message = "O CPF não pode ser nulo.")
    @NotBlank(message = "O CPF é obrigatório.")
    @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 caracteres.")
    @CPF(message = "CPF inválido. Verifique o valor informado.")
    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @NotNull(message = "O telefone não pode ser nulo.")
    @NotBlank(message = "O telefone é obrigatório.")
    @Size(min = 11, max = 11, message = "O telefone deve conter exatamente 11 caracteres.")
    @Column(name = "telefone", length = 11, nullable = false, unique = true)
    private String telefone;

    @NotNull(message = "O e-mail não pode ser nulo.")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido. Verifique o valor informado.")
    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @NotNull(message = "O sexo não pode ser nulo.")
    @NotBlank(message = "O sexo é obrigatório.")
    @Pattern(regexp = "^[MF]$", message = "O sexo deve ser 'M' para masculino ou 'F' para feminino.")
    @Size(min = 1, max = 1, message = "O sexo deve conter apenas 1 caracter (M ou F).")
    @Column(name = "sexo", length = 1, nullable = false)
    private String sexo;

    /**
     * Converte a entidade GarcomModel para seu correspondente DTO (GarcomDTO).
     *
     * @return Uma instância de GarcomDTO com os dados mapeados a partir do GarcomModel.
     */
    public GarcomDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, GarcomDTO.class);
    }
}