package br.com.boteco.comanda.model;


import br.com.boteco.comanda.rest.dto.FormaPagamentoDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "forma_Pagamento")
public class FormaPagamentoModel {


    @Id // define como Chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_FormaPagamento")
    private Long idFormaPagamento;

    @NotNull(message = "Não admite valor nulo.")
    @NotBlank(message = "Não admite valor vazio.")
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @NotNull(message = "Não admite valor nulo.")
    @NotBlank(message = "Não admite valor vazio.")
    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    public FormaPagamentoDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, FormaPagamentoDTO.class);
    }


}
