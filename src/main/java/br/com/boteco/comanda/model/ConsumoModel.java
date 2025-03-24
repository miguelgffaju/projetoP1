package br.com.boteco.comanda.model;


import br.com.boteco.comanda.rest.dto.ConsumoDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "consumo")
public class ConsumoModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Consumo")
    private Long idConsumo;

    //@NotBlank(message = "Não admite valor vazio.")  @NotBlank não pode ser usada em Float, Double ou Integer.
    @Column(name = "quantidade")
    private int quantidade;

    @Column(name = "data_Hora_Consumo", nullable = false)
    private LocalDateTime dataHoraConsumo;

    @NotNull(message = "Não admite valor nulo.")
    //@NotBlank(message = "Não admite valor vazio.")  @NotBlank não pode ser usada em Float, Double ou Integer.
    @Column(name = "preco_Unitario_Vendido", nullable = false)
    private BigDecimal precoUnitarioVendido;

    @NotNull(message = "Não admite valor nulo.")
    //@NotBlank(message = "Não admite valor vazio.")  @NotBlank não pode ser usada em Float, Double ou Integer.
    @Column(name = "preco_Total", nullable = false)
    private BigDecimal precoTotal;

    @ManyToOne
    @JoinColumn(name = "id_Produto", nullable = false)
    private ProdutoModel produto;

    @ManyToOne
    @JoinColumn(name = "id_Comanda", nullable = false)
    private ComandaModel comanda;

    public ConsumoDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ConsumoDTO.class);
    }


}
