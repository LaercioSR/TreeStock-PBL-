package br.uefs.ecomp.treeStock.exceptions;

/**
 * Exce��o len�ada quando um cliente n�o � encontrado em uma estrutura de dados
 * @see br.uefs.ecomp.treeStock.model.Cliente
 */
public class ClienteNaoEncontradoException extends Exception {

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
