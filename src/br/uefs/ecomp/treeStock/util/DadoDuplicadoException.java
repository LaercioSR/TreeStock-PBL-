package br.uefs.ecomp.treeStock.util;

import java.io.Serializable;

public class DadoDuplicadoException extends Exception implements Serializable {
    public DadoDuplicadoException(){
        super();
    }
    
    public DadoDuplicadoException(String msg){
        super(msg);
    }
    
    public DadoDuplicadoException(Throwable t){
        super(t);
    }
}
