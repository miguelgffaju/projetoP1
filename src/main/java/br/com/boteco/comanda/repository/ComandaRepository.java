package br.com.boteco.comanda.repository;

import br.com.boteco.comanda.model.ComandaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ComandaRepository extends JpaRepository<ComandaModel, Long> {
    @Query("SELECT c.garcom, SUM(c.valorTotalComanda) " +
            "FROM ComandaModel c " +
            "JOIN c.garcom g " +
            "WHERE c.dataHoraFechamento IS NOT NULL " +
            "AND c.dataHoraFechamento BETWEEN :inicio AND :fim " +
            "AND c.status = 'Fechada' " +
            "GROUP BY c.garcom " +
            "ORDER BY SUM(c.valorTotalComanda) DESC")
    List<Object[]> encontrarFaturamentoPorGarcom(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT COALESCE(SUM(c.valorTotalComanda), 0) FROM ComandaModel c " +
            "WHERE (c.dataHoraFechamento IS NOT NULL AND c.dataHoraFechamento BETWEEN :inicio AND :fim) " +
            "AND c.status = 'Fechado'")
    BigDecimal calcularFaturamentoTotal(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT c.formaPagamento, COUNT(c.formaPagamento) FROM ComandaModel c " +
            "WHERE (c.dataHoraFechamento IS NOT NULL AND c.dataHoraFechamento BETWEEN :inicio AND :fim) " +
            "AND c.status = 'Fechado' " +
            "GROUP BY c.formaPagamento " +
            "ORDER BY COUNT(c.formaPagamento) DESC")
    List<Object[]> encontrarFormaPagamentoMaisUtilizada(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT c, c.valorTotalComanda, c.valorGorjeta, c.status " +
            "FROM ComandaModel c " +
            "WHERE (c.dataHoraFechamento IS NOT NULL AND c.dataHoraFechamento BETWEEN :inicio AND :fim) " +
            "AND c.status = 'Fechado' " +
            "ORDER BY c.valorTotalComanda DESC")
    List<Object[]> encontrarComandasComMaiorConsumo(@Param("inicio") LocalDateTime inicio,
                                                    @Param("fim") LocalDateTime fim);

    @Query("SELECT AVG(DATEDIFF(MINUTE, c.dataHoraAbertura, c.dataHoraFechamento)) " +
            "FROM ComandaModel c " +
            "WHERE c.status = 'Fechado' " +
            "AND c.dataHoraAbertura >= :inicio " +
            "AND c.dataHoraFechamento <= :fim")
    Double calcularMediaTempoPermanencia(@Param("inicio") LocalDateTime inicio,
                                         @Param("fim") LocalDateTime fim);


}


