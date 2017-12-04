package br.uefs.ecomp.treeStock.exceptions;

import java.io.Serializable;

/**
 * Exce��o lan�ada quando tentam inserir um item duplicado em uma
 * estrutura de dados
 */
public class DadoDuplicadoException extends Exception implements Serializable {
    
    /**
     * Controi a exce��o
     */
    public DadoDuplicadoException(){
        super();
    }
    
    /**
     * Constroi a exce��o com uma mensagem que poder� ser exibida
     * @param msg Mensagem que ser� exibida na tela
     */
    public DadoDuplicadoException(String msg){
        super(msg);
    }
    
    public DadoDuplicadoException(Throwable t){
        super(t);
    }
}
