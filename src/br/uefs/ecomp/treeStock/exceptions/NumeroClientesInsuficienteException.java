package br.uefs.ecomp.treeStock.exceptions;

/**
 * Exceção lançada quando é necissatado um número de cliente superior 
 * ao número de cadastrados
 * @see br.uefs.ecomp.treeStock.model.Cliente
 */
public class NumeroClientesInsuficienteException extends Exception {

    /**
     * Controi a exceção
     */
    public NumeroClientesInsuficienteException() {
        super();
    }
    
    /**
     * Constroi a exceção com uma mensagem que poderá ser exibida
     * @param msg Mensagem que será exibida na tela
     */
    public NumeroClientesInsuficienteException(String msg) {
        super(msg);
    }
}
