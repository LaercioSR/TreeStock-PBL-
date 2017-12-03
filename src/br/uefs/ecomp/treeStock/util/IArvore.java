package br.uefs.ecomp.treeStock.util;

import br.uefs.ecomp.treeStock.exceptions.DadoDuplicadoException;
import br.uefs.ecomp.treeStock.exceptions.DadoNaoEncontradoException;

public interface IArvore extends Iterable{
    
    public Object buscar(Comparable item) throws DadoNaoEncontradoException;

    public void inserir(Comparable item) throws DadoDuplicadoException;

    public Comparable remover(Comparable item) throws DadoNaoEncontradoException;

}