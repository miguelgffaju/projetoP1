package br.com.boteco.comanda.model;


import br.com.boteco.comanda.rest.dto.ComandaDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comanda")
public class ComandaModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Comanda")
    private Long idComanda;

    @Column(name = "data_Hora_Abertura", nullable = false)
    private LocalDateTime dataHoraAbertura;

    @Column(name = "data_Hora_Fechamento")
    private LocalDateTime dataHoraFechamento;



    @NotNull(message = "Não admite valor nulo.")
    //@NotBlank(message = "Não admite valor vazio.")  @NotBlank não pode ser usada em Float, Double ou Integer.
    @Column(name = "valor_Total_Comanda", nullable = false)
    private float valorTotalComanda;

    //@NotBlank(message = "Não admite valor vazio.")  @NotBlank não pode ser usada em Float, Double ou Integer.
    @Column(name = "valor_Gorjeta")
    private float valorGorjeta;

    //@NotBlank(message = "Não admite valor vazio.")  @NotBlank não pode ser usada em Float, Double ou Integer.
    @NotBlank(message = "Não admite valor vazio.")
    @Column(name = "status", length = 255, nullable = false)
    private String status;

    public ComandaDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ComandaDTO.class);
    }

    @ManyToOne
    @JoinColumn(name = "id_Garcom", nullable = false)
    private GarcomModel garcom;

    @ManyToOne
    @JoinColumn(name = "id_Mesa", nullable = false) // Define o nome da FK no banco
    private MesaModel mesa;

    @ManyToOne
    @JoinColumn(name = "id_Forma_Pagamento", nullable = false) // Define o nome da FK no banco
    private FormaPagamentoModel formaPagamento;

}










