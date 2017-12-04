package br.uefs.ecomp.treeStock.exceptions;

import java.io.Serializable;

/**
 * Exce��o lan�ada quando uma a��o n�o foi encontrada na estrutura de dado
 */
public class AcaoNaoEncontradaException extends Exception implements Serializable {
    
    /**
     * Controi a exce��o
     */
    public AcaoNaoEncontradaException() {
        super();
    }

    /**
     * Constroi a exce��o com uma mensagem que poder� ser exibida
     * @param msg Mensagem que ser� exibida na tela
     */
    public AcaoNaoEncontradaException(String msg) {
        super(msg);
    }

    public AcaoNaoEncontradaException(Throwable exception) {
        super(exception);
    }
}
