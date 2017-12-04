package br.uefs.ecomp.treeStock.exceptions;

import java.io.Serializable;

/**
 * Exceção lançada quando tentam inserir um item duplicado em uma
 * estrutura de dados
 */
public class DadoDuplicadoException extends Exception implements Serializable {
    
    /**
     * Controi a exceção
     */
    public DadoDuplicadoException(){
        super();
    }
    
    /**
     * Constroi a exceção com uma mensagem que poderá ser exibida
     * @param msg Mensagem que será exibida na tela
     */
    public DadoDuplicadoException(String msg){
        super(msg);
    }
    
    public DadoDuplicadoException(Throwable t){
        super(t);
    }
}
