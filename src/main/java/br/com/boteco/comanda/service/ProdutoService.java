package br.com.boteco.comanda.service;

import br.com.boteco.comanda.exception.*;
import br.com.boteco.comanda.model.ProdutoModel;
import br.com.boteco.comanda.repository.ConsumoRepository;
import br.com.boteco.comanda.repository.ProdutoRepository;
import br.com.boteco.comanda.rest.dto.ProdutoDTO;
import br.com.boteco.comanda.rest.dto.ProdutoVendidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional(readOnly = true)
    public ProdutoDTO obterPorId(Long id) {
        ProdutoModel produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Produto com ID " + id + " não encontrado."));
        return produto.toDTO();
    }


    @Transactional(readOnly = true)
    public List<ProdutoDTO> obterTodos(){
        List<ProdutoModel> produtos = produtoRepository.findAll();

        return produtos.stream()
                .map(produto -> produto.toDTO())
                .collect(Collectors.toList());
    }



    @Transactional
    public ProdutoDTO salvar(ProdutoModel novoProduto) {

        try {

            return produtoRepository.save(novoProduto).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar o produto ");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar o produto " );
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar o produto " );
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar o produto ");
        }
    }
    @Transactional
    public ProdutoDTO atualizar(ProdutoModel produtoExistente) {

        try {

            return produtoRepository.save(produtoExistente).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar o produto " );
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar o produto: Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar o produto: Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o produto: Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível atualizar o produto: Não encontrado no banco de dados!");
        }
    }


    @Transactional
    public void deletar(ProdutoModel produtoExistente) {

        try {

            produtoRepository.delete(produtoExistente);

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar o produto " );
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar o produto: Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar o produto: Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível deletar o produto: Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível deletar o produto: Não encontrado no banco de dados!");
        }
    }
    @Autowired
    private ConsumoRepository consumoRepository;


    @Transactional
    public ProdutoVendidoDTO encontrarProdutoMaisVendido(LocalDateTime inicio, LocalDateTime fim) {
        // Obtenha a lista de resultados
        List<Object[]> resultados = consumoRepository.encontrarProdutoMaisVendido(inicio, fim);

        // Criar o Map para armazenar os produtos e suas quantidades
        Map<ProdutoModel, Integer> produtosQuantidades = new HashMap<>();

        // Preencher o Map com os dados
        for (Object[] resultado : resultados) {
            ProdutoModel produto = (ProdutoModel) resultado[0];
            Integer quantidade = ((Long) resultado[1]).intValue();
            produtosQuantidades.put(produto, quantidade);
        }

        // Encontrando o produto com a maior quantidade
        ProdutoModel produtoMaisVendido = null;
        int maiorQuantidade = 0;

        for (Map.Entry<ProdutoModel, Integer> entry : produtosQuantidades.entrySet()) {
            if (entry.getValue() > maiorQuantidade) {
                maiorQuantidade = entry.getValue();
                produtoMaisVendido = entry.getKey();
            }
        }

        return new ProdutoVendidoDTO(produtoMaisVendido, maiorQuantidade);
    }



}
