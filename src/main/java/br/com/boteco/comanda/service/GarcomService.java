package br.com.boteco.comanda.service;

import br.com.boteco.comanda.exception.*;
import br.com.boteco.comanda.model.GarcomModel;
import br.com.boteco.comanda.repository.ComandaRepository;
import br.com.boteco.comanda.repository.GarcomRepository;
import br.com.boteco.comanda.rest.dto.GarcomDTO;
import br.com.boteco.comanda.rest.dto.GarcomFaturamentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelas operações relacionadas aos garçons.
 */
@Service
public class GarcomService {

    /**
     * Instância do repositório de garçons, responsável por realizar operações de
     * persistência e consulta diretamente no banco de dados.
     */
    @Autowired
    private GarcomRepository garcomRepository;


    /**
     * Obtém um garçom pelo ID.
     *
     * @param id ID do garçom.
     * @return GarcomDTO representando o garçom encontrado.
     * @throws ObjectNotFoundException Se o garçom não for encontrado.
     */
    @Transactional(readOnly = true)
    public GarcomDTO obterPorId(Long id) {
        GarcomModel garcom = garcomRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Garçom com ID " + id + " não encontrado."));
        return garcom.toDTO();
    }

    /**
     * Obtém a lista de todos os garçons cadastrados.
     *
     * @return Lista de GarcomDTO representando os garçons cadastrados.
     */
    @Transactional(readOnly = true)
    public List<GarcomDTO> obterTodos() {
        List<GarcomModel> garcons = garcomRepository.findAll();
        return garcons.stream()
                .map(g -> g.toDTO())
                .collect(Collectors.toList());
    }

    /**
     * Salva um novo garçom na base de dados.
     *
     * @param novoGarcom GarcomModel contendo os dados do novo garçom.
     * @return GarcomDTO representando o garçom salvo.
     * @throws ConstraintException       Se o CPF ou e-mail já existirem.
     * @throws DataIntegrityException    Se ocorrer violação de integridade.
     * @throws BusinessRuleException     Se houver violação de regra de negócio.
     * @throws SQLException              Se ocorrer falha de conexão com o banco de dados.
     */
    @Transactional
    public GarcomDTO salvar(GarcomModel novoGarcom) {

        try {
            //Caso ocorra uma tentativa de salvar um novo garçom com um cpf já existente.
            if (garcomRepository.existsByCpf(novoGarcom.getCpf())) {
                throw new ConstraintException("Já existe um garçom com esse CPF " + novoGarcom.getCpf() + " na base de dados!");
            }

            //Caso ocorra uma tentativa de salvar um novo garçom com um e-mail já existente.
            if (garcomRepository.existsByEmail(novoGarcom.getEmail())) {
                throw new ConstraintException("Já existe um garçom com esse E-MAIL " + novoGarcom.getEmail() + " na base de dados!");
            }

            //Salva o novo garçom na base de dados.
            return garcomRepository.save(novoGarcom).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar o garçom " + novoGarcom.getNome() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar o garçom " + novoGarcom.getNome() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar o garçom " + novoGarcom.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar o garçom " + novoGarcom.getNome() + ". Falha na conexão com o banco de dados!");
        }
    }

    /**
     * Atualiza os dados de um garçom existente.
     *
     * @param garcomExistente GarcomModel contendo os dados atualizados do garçom.
     * @return GarcomDTO representando o garçom atualizado.
     * @throws ConstraintException       Se o CPF não existir.
     * @throws DataIntegrityException    Se ocorrer violação de integridade.
     * @throws BusinessRuleException     Se houver violação de regra de negócio.
     * @throws SQLException              Se ocorrer falha de conexão com o banco de dados.
     */
    @Transactional
    public GarcomDTO atualizar(GarcomModel garcomExistente) {

        try {
            //Caso ocorra uma tentativa de salvar um garçom que não existe utilizando um cpf.
            if (!garcomRepository.existsByCpf(garcomExistente.getCpf())) {
                throw new ConstraintException("O garçom com esse CPF " + garcomExistente.getCpf() + " não existe na base de dados!");
            }

            //Atualiza o garçom na base de dados.
            return garcomRepository.save(garcomExistente).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar o garçom " + garcomExistente.getNome() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar o garçom " + garcomExistente.getNome() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar o garçom " + garcomExistente.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o garçom " + garcomExistente.getNome() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível atualizar o garçom" + garcomExistente.getNome() + ". Não encontrado no banco de dados!");
        }
    }

    /**
     * Deleta um garçom da base de dados.
     *
     * @param garcomExistente GarcomModel contendo os dados do garçom a ser deletado.
     * @throws ConstraintException       Se o CPF não existir.
     * @throws DataIntegrityException    Se ocorrer violação de integridade.
     * @throws BusinessRuleException     Se houver violação de regra de negócio.
     * @throws SQLException              Se ocorrer falha de conexão com o banco de dados.
     */
    @Transactional
    public void deletar(GarcomModel garcomExistente) {

        try {
            //Caso ocorra uma tentativa de salvar um garçom que não existe utilizando um cpf.
            if (!garcomRepository.existsByCpf(garcomExistente.getCpf())) {
                throw new ConstraintException("O garçom com esse CPF " + garcomExistente.getCpf() + " não existe na base de dados!");
            }

            //Deletar o garçom na base de dados.
            garcomRepository.delete(garcomExistente);

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar o garçom " + garcomExistente.getNome() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar o garçom " + garcomExistente.getNome() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar o garçom " + garcomExistente.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o deletar " + garcomExistente.getNome() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível deletar o garçom" + garcomExistente.getNome() + ". Não encontrado no banco de dados!");
        }
    }
    @Autowired
    private ComandaRepository comandaRepository;

    @Transactional
    public GarcomFaturamentoDTO encontrarGarcomComMaiorFaturamento(LocalDateTime inicio, LocalDateTime fim) {
        List<Object[]> resultados = comandaRepository.encontrarFaturamentoPorGarcom(inicio, fim);

        if (resultados.isEmpty()) {
            return new GarcomFaturamentoDTO(Collections.emptyMap());
        }

        Map<GarcomModel, BigDecimal> garconsMaisFaturados = new LinkedHashMap<>();
        BigDecimal maiorFaturamento = BigDecimal.valueOf((Double) resultados.get(0)[1]);

        for (Object[] resultado : resultados) {
            BigDecimal faturamento = BigDecimal.valueOf((Double) resultado[1]);
            if (!faturamento.equals(maiorFaturamento)) {
                break; // Sai do loop assim que encontrar um valor menor
            }
            garconsMaisFaturados.put((GarcomModel) resultado[0], faturamento);
        }

        return new GarcomFaturamentoDTO(garconsMaisFaturados);
    }

}