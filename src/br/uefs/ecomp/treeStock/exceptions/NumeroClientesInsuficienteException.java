package br.uefs.ecomp.treeStock.exceptions;

/**
 * Exce��o lan�ada quando � necissatado um n�mero de cliente superior 
 * ao n�mero de cadastrados
 * @see br.uefs.ecomp.treeStock.model.Cliente
 */
public class NumeroClientesInsuficienteException extends Exception {

    /**
     * Controi a exce��o
     */
    public NumeroClientesInsuficienteException() {
        super();
    }
    
    /**
     * Constroi a exce��o com uma mensagem que poder� ser exibida
     * @param msg Mensagem que ser� exibida na tela
     */
    public NumeroClientesInsuficienteException(String msg) {
        super(msg);
    }
}
