package br.com.boteco.comanda.repository;

import br.com.boteco.comanda.model.FormaPagamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamentoModel, Long> {
}
