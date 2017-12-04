package br.uefs.ecomp.treeStock.exceptions;

import java.io.Serializable;

/**
 * Exce��o len�ada quando um cliente n�o � encontrado em uma estrutura de dados
 */
public class ClienteNaoEncontradoException extends Exception implements Serializable {

    /**
     * Controi a exce��o
     */
    public ClienteNaoEncontradoException() {
        super();
    }

    /**
     * Constroi a exce��o com uma mensagem que poder� ser exibida
     * @param msg Mensagem que ser� exibida na tela
     */
    public ClienteNaoEncontradoException(String msg) {
        super(msg);
    }

    public ClienteNaoEncontradoException(Throwable exception) {
        super(exception);
    }
}
