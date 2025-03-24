package br.com.boteco.comanda.repository;

import br.com.boteco.comanda.model.GarcomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório responsável por gerenciar as operações de persistência
 * relacionadas à entidade GarcomModel.
 */
@Repository
public interface GarcomRepository extends JpaRepository<GarcomModel, Long> {

    /**
     * Verifica se existe um garçom cadastrado com o CPF especificado.
     *
     * @param cpf O CPF a ser verificado.
     * @return {@code true} se existir um garçom com o CPF fornecido, {@code false} caso contrário.
     */
    boolean existsByCpf(String cpf);

    /**
     * Verifica se existe um garçom cadastrado com o e-mail especificado.
     *
     * @param email O e-mail a ser verificado.
     * @return {@code true} se existir um garçom com o e-mail fornecido, {@code false} caso contrário.
     */
    boolean existsByEmail(String email);
}