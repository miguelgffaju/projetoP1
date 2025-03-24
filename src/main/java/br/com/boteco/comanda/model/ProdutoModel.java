package br.com.boteco.comanda.model;


import br.com.boteco.comanda.rest.dto.ProdutoDTO;
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
@Table(name = "produto")
public class ProdutoModel {

    @Id // define como Chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Produto")
    private Long idProduto;

    @NotNull(message = "Não admite valor nulo.")
    @NotBlank(message = "Não admite valor vazio.")
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @NotNull(message = "Não admite valor nulo.")
    @NotBlank(message = "Não admite valor vazio.")
    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    @NotNull(message = "Não admite valor nulo.")
    //@NotBlank(message = "Não admite valor vazio.")  @NotBlank não pode ser usada em Float, Double ou Integer.
    @Column(name = "preco", nullable = false)
    private float preco;

    @NotNull(message = "Não admite valor nulo.")
    @NotBlank(message = "Não admite valor vazio.")
    @Column(name = "status", length = 255, nullable = false)
    private String status;

    public ProdutoDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ProdutoDTO.class);
    }

}
