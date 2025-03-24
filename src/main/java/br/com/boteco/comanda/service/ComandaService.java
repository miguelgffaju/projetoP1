package br.com.boteco.comanda.service;

import br.com.boteco.comanda.exception.*;
import br.com.boteco.comanda.model.ComandaModel;
import br.com.boteco.comanda.repository.ComandaRepository;
import br.com.boteco.comanda.rest.dto.ComandaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ComandaService {

    @Autowired
    private ComandaRepository comandaRepository;

    @Transactional(readOnly = true)
    public ComandaDTO obterPorId(Long id) {
        ComandaModel comanda = comandaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Comanda com ID " + id + " não encontrado."));
        return comanda.toDTO();
    }


    @Transactional(readOnly = true)
    public List<ComandaDTO> obterTodos(){
        List<ComandaModel> comandas = comandaRepository.findAll();

        return comandas.stream()
                .map(comanda -> comanda.toDTO())
                .collect(Collectors.toList());
    }





    @Transactional
    public ComandaDTO salvar(ComandaModel novaComanda) {

        try {

            if (!"Fechado".equals(novaComanda.getStatus())) {
                novaComanda.setDataHoraFechamento(null);
            }
            return comandaRepository.save(novaComanda).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar a comanda ");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar a comanda " );
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar a comanda " );
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar a comanda ");
        }
    }
    @Transactional
    public ComandaDTO atualizar(ComandaModel comandaExistente) {

        try {

            if ("Fechado".equals(comandaExistente.getStatus()) && comandaExistente.getDataHoraFechamento() == null) {
                comandaExistente.setDataHoraFechamento(LocalDateTime.now());
            } else if (!"Fechado".equals(comandaExistente.getStatus())) {
                comandaExistente.setDataHoraFechamento(null);
            }
            return comandaRepository.save(comandaExistente).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar a comanda " );
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar a comanda: Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar a comanda: Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar a comanda: Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível atualizar a comanda: Não encontrado no banco de dados!");
        }
    }


    @Transactional
    public void deletar(ComandaModel comandaExistente) {

        try {

            comandaRepository.delete(comandaExistente);

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar a comanda " );
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar a comanda: Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar a comanda: Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível deletar a comanda: Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível deletar a comanda: Não encontrado no banco de dados!");
        }
    }

    @Transactional
    public BigDecimal calcularFaturamentoTotal(LocalDateTime inicio, LocalDateTime fim) {
        return comandaRepository.calcularFaturamentoTotal(inicio, fim);
    }

    @Transactional(readOnly = true)
    public List<ComandaDTO> encontrarComandasComMaiorConsumo(LocalDateTime inicio, LocalDateTime fim) {
        List<Object[]> resultados = comandaRepository.encontrarComandasComMaiorConsumo(inicio, fim);

        if (resultados.isEmpty()) {
            return Collections.emptyList();
        }

        float maiorConsumo = (float) resultados.get(0)[1];

        return resultados.stream()
                .filter(obj -> ((float) obj[1]) == maiorConsumo)
                .map(obj -> {
                    ComandaModel comanda = (ComandaModel) obj[0];
                    float total = (float) obj[1];
                    float gorjeta = (float) obj[2];  // ✅ Recupera o valor da gorjeta
                    String status = (String) obj[3]; // ✅ Recupera o status da comanda

                    return new ComandaDTO(comanda.getIdComanda(), comanda.getDataHoraAbertura(),
                            comanda.getDataHoraFechamento(), total, gorjeta, status);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public double calcularMediaTempoPermanencia(LocalDateTime inicio, LocalDateTime fim) {
        Double media = comandaRepository.calcularMediaTempoPermanencia(inicio, fim);
        return (media != null) ? media : 0.0;
    }



}