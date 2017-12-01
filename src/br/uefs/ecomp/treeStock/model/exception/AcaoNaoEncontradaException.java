package br.uefs.ecomp.treeStock.model.exception;

import java.io.Serializable;


public class AcaoNaoEncontradaException extends Exception implements Serializable {
    public AcaoNaoEncontradaException() {
        super();
    }

    public AcaoNaoEncontradaException(String msg) {
        super(msg);
    }

    public AcaoNaoEncontradaException(Throwable exception) {
        super(exception);
    }
}
