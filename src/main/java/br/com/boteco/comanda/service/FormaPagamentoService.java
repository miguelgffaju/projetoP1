package br.com.boteco.comanda.service;

import br.com.boteco.comanda.exception.*;
import br.com.boteco.comanda.model.FormaPagamentoModel;
import br.com.boteco.comanda.repository.ComandaRepository;
import br.com.boteco.comanda.repository.FormaPagamentoRepository;
import br.com.boteco.comanda.rest.dto.FormaPagamentoDTO;
import br.com.boteco.comanda.rest.dto.FormaPagamentoUtilizadaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class FormaPagamentoService {
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Transactional(readOnly = true)
    public FormaPagamentoDTO obterPorId(Long id) {
        FormaPagamentoModel formaPagamento = formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Forma de Pagamento com ID " + id + " não encontrado."));
        return formaPagamento.toDTO();
    }


    @Transactional(readOnly = true)
    public List<FormaPagamentoDTO> obterTodos(){
        List<FormaPagamentoModel> formasPagamento = formaPagamentoRepository.findAll();

        return formasPagamento.stream()
                .map(f -> f.toDTO())
                .collect(Collectors.toList());
    }



    @Transactional
    public FormaPagamentoDTO salvar(FormaPagamentoModel novaformaPagamento) {

        try {

            return formaPagamentoRepository.save(novaformaPagamento).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar a Forma de Pagamento ");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar a Forma de Pagamento " );
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar a Forma de Pagamento " );
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar a Forma de Pagamento ");
        }
    }
    @Transactional
    public FormaPagamentoDTO atualizar(FormaPagamentoModel formaPagamentoExistente) {

        try {

            return formaPagamentoRepository.save(formaPagamentoExistente).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar a Forma de Pagamento " );
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar a Forma de Pagamento: Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar a Forma de Pagamento: Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar a Forma de Pagamento: Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível atualizar a Forma de Pagamento: Não encontrado no banco de dados!");
        }
    }


    @Transactional
    public void deletar(FormaPagamentoModel formaPagamentoExistente) {

        try {

            formaPagamentoRepository.delete(formaPagamentoExistente);

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar a Forma de Pagamento " );
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar a Forma de Pagamento: Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar a Forma de Pagamento: Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível deletar a Forma de Pagamento: Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível deletar a Forma de Pagamento: Não encontrado no banco de dados!");
        }
    }
        @Autowired
        private ComandaRepository comandaRepository;
        @Transactional
        public FormaPagamentoUtilizadaDTO encontrarFormaPagamentoMaisUtilizada(LocalDateTime inicio, LocalDateTime fim) {
            List<Object[]> resultados = comandaRepository.encontrarFormaPagamentoMaisUtilizada(inicio, fim);

            if (resultados.isEmpty()) {
                return new FormaPagamentoUtilizadaDTO(Collections.emptyMap());
            }

            Map<FormaPagamentoModel, Integer> formasMaisUtilizadas = new LinkedHashMap<>();
            Integer maiorQuantidade = ((Number) resultados.get(0)[1]).intValue();

            for (Object[] resultado : resultados) {
                Integer quantidade = ((Number) resultado[1]).intValue();
                if (!quantidade.equals(maiorQuantidade)) {
                    break;
                }
                formasMaisUtilizadas.put((FormaPagamentoModel) resultado[0], quantidade);
            }

            return new FormaPagamentoUtilizadaDTO(formasMaisUtilizadas);
        }


}
