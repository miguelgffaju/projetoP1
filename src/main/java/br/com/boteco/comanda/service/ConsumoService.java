package br.com.boteco.comanda.service;


import br.com.boteco.comanda.exception.*;
import br.com.boteco.comanda.model.ConsumoModel;
import br.com.boteco.comanda.repository.ConsumoRepository;
import br.com.boteco.comanda.rest.dto.ConsumoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsumoService {
    @Autowired
    private ConsumoRepository consumoRepository;

    @Transactional(readOnly = true)
    public ConsumoDTO obterPorId(Long id) {
        ConsumoModel consumo = consumoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Consumo com ID " + id + " não encontrado."));
        return consumo.toDTO();
    }


    @Transactional(readOnly = true)
    public List<ConsumoDTO> obterTodos(){
        List<ConsumoModel> consumos = consumoRepository.findAll();

        return consumos.stream()
                .map(consumo -> consumo.toDTO())
                .collect(Collectors.toList());
    }



    @Transactional
    public ConsumoDTO salvar(ConsumoModel novoConsumo) {

        try {

            if (novoConsumo.getPrecoTotal() == null) {
                BigDecimal precoUnitario = BigDecimal.valueOf(novoConsumo.getProduto().getPreco());
                novoConsumo.setPrecoTotal(precoUnitario.multiply(BigDecimal.valueOf(novoConsumo.getQuantidade())));
            }
            return consumoRepository.save(novoConsumo).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar o consumo ");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar o consumo " );
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar o consumo " );
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar o consumo ");
        }
    }
    @Transactional
    public ConsumoDTO atualizar(ConsumoModel consumoExistente) {

        try {

            return consumoRepository.save(consumoExistente).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar o consumo " );
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar o consumo: Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar o consumo: Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o consumo: Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível atualizar o consumo: Não encontrado no banco de dados!");
        }
    }


    @Transactional
    public void deletar(ConsumoModel consumoExistente) {

        try {

            consumoRepository.delete(consumoExistente);

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar o consumo " );
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar o consumo: Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar o consumo: Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível deletar o consumo: Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível deletar o consumo: Não encontrado no banco de dados!");
        }
    }

}
