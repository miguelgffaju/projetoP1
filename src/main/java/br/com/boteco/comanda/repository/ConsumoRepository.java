package br.com.boteco.comanda.repository;

import br.com.boteco.comanda.model.ConsumoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsumoRepository extends JpaRepository<ConsumoModel, Long> {

    @Query("SELECT c.produto, SUM(c.quantidade) " +
            "FROM ConsumoModel c " +
            "JOIN c.produto p " +
            "WHERE c.dataHoraConsumo BETWEEN :inicio AND :fim " +
            "AND c.comanda.status = 'Fechada' " +
            "GROUP BY p.idProduto " +
            "ORDER BY SUM(c.quantidade) DESC")
    List<Object[]> encontrarProdutoMaisVendido(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}
