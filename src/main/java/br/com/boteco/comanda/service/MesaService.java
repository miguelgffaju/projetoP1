package br.com.boteco.comanda.service;


import br.com.boteco.comanda.exception.*;
import br.com.boteco.comanda.model.MesaModel;
import br.com.boteco.comanda.repository.MesaRepository;
import br.com.boteco.comanda.rest.dto.MesaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;


    @Transactional(readOnly = true)
    public MesaDTO obterPorId(Long id) {
        MesaModel mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Mesa com ID " + id + " não encontrado."));
        return mesa.toDTO();
    }

    @Transactional(readOnly = true)
    public List<MesaDTO> obterTodos() {
        List<MesaModel> mesas = mesaRepository.findAll();
        return mesas.stream()
                .map(g -> g.toDTO())
                .collect(Collectors.toList());
    }

    @Transactional
    public MesaDTO salvar(MesaModel novaMesa) {

        try {
            //Caso ocorra uma tentativa de salvar um novo garçom com um cpf já existente.
            if (mesaRepository.existsByNumero(novaMesa.getNumero())) {
                throw new ConstraintException("Já existe uma mesa com esse número " + novaMesa.getNumero() + " na base de dados!");
            }
            return mesaRepository.save(novaMesa).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar a mesa !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar a mesa !");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar a mesa !\". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar a mesa !\". Falha na conexão com o banco de dados!");
        }
    }

    @Transactional
    public MesaDTO atualizar(MesaModel mesaExistente) {

        try {
            //Caso ocorra uma tentativa de salvar um garçom que não existe utilizando um cpf.
            if (!mesaRepository.existsByNumero(mesaExistente.getNumero())) {
                throw new ConstraintException("O garçom com esse CPF " + mesaExistente.getNumero() + " não existe na base de dados!");
            }

            //Atualiza o garçom na base de dados.
            return mesaRepository.save(mesaExistente).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar a mesa!");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar a mesa: Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar a mesa. Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar a mesa. Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível atualizar a mesa. Não encontrado no banco de dados!");
        }
    }

    @Transactional
    public void deletar(MesaModel mesaExistente) {

        try {

            if (!mesaRepository.existsByNumero(mesaExistente.getNumero())) {
                throw new ConstraintException("A mesa com esse numero " + mesaExistente.getNumero() + " não existe na base de dados!");
            }

            //Deletar o garçom na base de dados.
            mesaRepository.delete(mesaExistente);

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar a mesa! ");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar a mesa! " );
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar a mesa! ");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar oa mesa! " );
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível deletar a mesa!" );
        }
    }











}
