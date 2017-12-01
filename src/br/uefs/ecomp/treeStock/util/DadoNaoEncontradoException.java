
package br.uefs.ecomp.treeStock.util;

import java.io.Serializable;

public class DadoNaoEncontradoException extends Exception implements Serializable {
    public DadoNaoEncontradoException(){
        super();
    }
    
    public DadoNaoEncontradoException(String msg){
        super(msg);
    }
    
    public DadoNaoEncontradoException(Throwable t){
        super(t);
    }
}
