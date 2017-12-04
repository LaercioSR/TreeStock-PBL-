package br.uefs.ecomp.treeStock.exceptions;

import java.io.Serializable;

/**
 * Exceção lançada quando uma ação não foi encontrada na estrutura de dado
 */
public class AcaoNaoEncontradaException extends Exception implements Serializable {
    
    /**
     * Controi a exceção
     */
    public AcaoNaoEncontradaException() {
        super();
    }

    /**
     * Constroi a exceção com uma mensagem que poderá ser exibida
     * @param msg Mensagem que será exibida na tela
     */
    public AcaoNaoEncontradaException(String msg) {
        super(msg);
    }

    public AcaoNaoEncontradaException(Throwable exception) {
        super(exception);
    }
}
