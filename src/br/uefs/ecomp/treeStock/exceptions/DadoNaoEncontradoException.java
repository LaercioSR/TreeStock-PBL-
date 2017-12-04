
package br.uefs.ecomp.treeStock.exceptions;

import java.io.Serializable;

/**
 * Exceção lançada quando uma item não foi encontrado na estrutura de dado
 */
public class DadoNaoEncontradoException extends Exception implements Serializable {
    
    /**
     * Controi a exceção
     */
    public DadoNaoEncontradoException(){
        super();
    }
    
    /**
     * Constroi a exceção com uma mensagem que poderá ser exibida
     * @param msg Mensagem que será exibida na tela
     */
    public DadoNaoEncontradoException(String msg){
        super(msg);
    }
    
    public DadoNaoEncontradoException(Throwable t){
        super(t);
    }
}
