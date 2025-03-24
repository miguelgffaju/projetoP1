package br.com.boteco.comanda.repository;

import br.com.boteco.comanda.model.MesaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MesaRepository extends JpaRepository<MesaModel, Long> {
    boolean existsByNumero(int numero);
}
