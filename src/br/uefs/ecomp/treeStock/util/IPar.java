package br.uefs.ecomp.treeStock.util;

/**
 * Inteface que representa pares <chave, valor>. O método iterator() de IPares
 * deve retornar um iterador de pares do tipo IPar.
 * <br>
 * @see br.uefs.ecomp.treeStock.util.IPares
 * @author Robos
 */
public interface IPar {
    /**
     * Retorna a chave do par.
     * @return a chave do par.
     */
    public Object getChave();
    
    /**
     * Retorna o valor do par.
     * @return o valor do par.
     */
    public Object getValor();
}
