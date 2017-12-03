package br.uefs.ecomp.treeStock.exceptions;

import java.io.Serializable;

public class ClienteNaoEncontradoException extends Exception implements Serializable {

    public ClienteNaoEncontradoException() {
        super();
    }

    public ClienteNaoEncontradoException(String msg) {
        super(msg);
    }

    public ClienteNaoEncontradoException(Throwable exception) {
        super(exception);
    }
}
