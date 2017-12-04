
package br.uefs.ecomp.treeStock.exceptions;

import java.io.Serializable;

/**
 * Exce��o lan�ada quando uma item n�o foi encontrado na estrutura de dado
 */
public class DadoNaoEncontradoException extends Exception implements Serializable {
    
    /**
     * Controi a exce��o
     */
    public DadoNaoEncontradoException(){
        super();
    }
    
    /**
     * Constroi a exce��o com uma mensagem que poder� ser exibida
     * @param msg Mensagem que ser� exibida na tela
     */
    public DadoNaoEncontradoException(String msg){
        super(msg);
    }
    
    public DadoNaoEncontradoException(Throwable t){
        super(t);
    }
}
