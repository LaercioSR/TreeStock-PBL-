package br.uefs.ecomp.treeStock.util;

import br.uefs.ecomp.treeStock.exceptions.DadoDuplicadoException;
import br.uefs.ecomp.treeStock.exceptions.DadoNaoEncontradoException;

/**
 * Interface para �rvores. Define m�todos que ser�o 
 * necess�rios nas �rvores
 */
public interface IArvore extends Iterable{
    /**
     * Busca um item na �rvore, usando um item igual para ach�-lo
     * @param item Item igual ao item a ser buscado
     * @return Item buscado
     * @throws DadoNaoEncontradoException Se o item buscado n�o existe na �rvore
     */
    public Object buscar(Comparable item) throws DadoNaoEncontradoException;

    /**
     * Insere um item na �rvore
     * @param item Item a ser inserido
     * @throws DadoDuplicadoException Se o j� existe um item igual ao que foi passado 
     * ser inserido
     */
    public void inserir(Comparable item) throws DadoDuplicadoException;

    /**
     * Remove um item da �rvore usando um item parecido para ach�-lo
     * @param item Item parecidao para acha e remover o item desejado
     * @throws DadoNaoEncontradoException Se n�o achar item a ser removido
     */
    public void remover(Comparable item) throws DadoNaoEncontradoException;

}