package br.uefs.ecomp.treeStock.util;

import br.uefs.ecomp.treeStock.exceptions.DadoDuplicadoException;
import br.uefs.ecomp.treeStock.exceptions.DadoNaoEncontradoException;

/**
 * Interface para árvores. Define métodos que serão 
 * necessários nas árvores
 */
public interface IArvore extends Iterable{
    /**
     * Busca um item na árvore, usando um item igual para achá-lo
     * @param item Item igual ao item a ser buscado
     * @return Item buscado
     * @throws DadoNaoEncontradoException Se o item buscado não existe na árvore
     */
    public Object buscar(Comparable item) throws DadoNaoEncontradoException;

    /**
     * Insere um item na árvore
     * @param item Item a ser inserido
     * @throws DadoDuplicadoException Se o já existe um item igual ao que foi passado 
     * ser inserido
     */
    public void inserir(Comparable item) throws DadoDuplicadoException;

    /**
     * Remove um item da árvore usando um item parecido para achá-lo
     * @param item Item parecidao para acha e remover o item desejado
     * @throws DadoNaoEncontradoException Se não achar item a ser removido
     */
    public void remover(Comparable item) throws DadoNaoEncontradoException;

}