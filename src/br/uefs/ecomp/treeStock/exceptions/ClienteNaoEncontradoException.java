package br.uefs.ecomp.treeStock.exceptions;

import java.io.Serializable;

/**
 * Exceção lençada quando um cliente não é encontrado em uma estrutura de dados
 */
public class ClienteNaoEncontradoException extends Exception implements Serializable {

    /**
     * Controi a exceção
     */
    public ClienteNaoEncontradoException() {
        super();
    }

    /**
     * Constroi a exceção com uma mensagem que poderá ser exibida
     * @param msg Mensagem que será exibida na tela
     */
    public ClienteNaoEncontradoException(String msg) {
        super(msg);
    }

    public ClienteNaoEncontradoException(Throwable exception) {
        super(exception);
    }
}
