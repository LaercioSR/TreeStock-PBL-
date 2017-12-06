package br.uefs.ecomp.treeStock.model;

import java.io.Serializable;

/**
 * Classe {@code Lote} guarda dados ({@code Acao} e quantidade de lotes 
 * dessa a��o) referentes a a��es de uma empresa na carteira de um cliente
 */
public class Lote implements Serializable {
    private Acao acao;
    private int quantidadeLotes;

    /**
     * Constroi um lote de uma a��o para uma carteira, com a quantidade
     * de lotes desse tipo
     * @param acao A��o refente ao lote
     * @param quantidadeLotes Quantidade de lotes da a��o passada
     */
    public Lote(Acao acao, int quantidadeLotes) {
        this.acao = acao;
        this.quantidadeLotes = quantidadeLotes;
    }

    /**
     * M�todo retorna a a��o do lote
     * @return A��o do lote
     */
    public Acao getAcao() {
        return acao;
    }

    /**
     * M�todo retorna quantidade de lotes
     * @return Quantidade de lotes
     */
    public int getQuantidadeLotes() {
        return quantidadeLotes;
    }

    /**
     * M�todo modifica a quantidade de lotes 
     * @param quantidadeLotes Nova qauntidade de lotes
     */
    public void setQuantidadeLotes(int quantidadeLotes) {
        this.quantidadeLotes = quantidadeLotes;
    }
    
    @Override
    public String toString(){
        return "A��o: " +
                "\n  Nome: " + acao.getNome() +
                "\n  Sigla: " + acao.getSigla() +
               "\nQuantidade de Lotes: " + quantidadeLotes;
    }
}
