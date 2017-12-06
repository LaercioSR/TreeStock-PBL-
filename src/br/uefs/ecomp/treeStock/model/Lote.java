package br.uefs.ecomp.treeStock.model;

import java.io.Serializable;

/**
 * Classe {@code Lote} guarda dados ({@code Acao} e quantidade de lotes 
 * dessa ação) referentes a ações de uma empresa na carteira de um cliente
 */
public class Lote implements Serializable {
    private Acao acao;
    private int quantidadeLotes;

    /**
     * Constroi um lote de uma ação para uma carteira, com a quantidade
     * de lotes desse tipo
     * @param acao Ação refente ao lote
     * @param quantidadeLotes Quantidade de lotes da ação passada
     */
    public Lote(Acao acao, int quantidadeLotes) {
        this.acao = acao;
        this.quantidadeLotes = quantidadeLotes;
    }

    /**
     * Método retorna a ação do lote
     * @return Ação do lote
     */
    public Acao getAcao() {
        return acao;
    }

    /**
     * Método retorna quantidade de lotes
     * @return Quantidade de lotes
     */
    public int getQuantidadeLotes() {
        return quantidadeLotes;
    }

    /**
     * Método modifica a quantidade de lotes 
     * @param quantidadeLotes Nova qauntidade de lotes
     */
    public void setQuantidadeLotes(int quantidadeLotes) {
        this.quantidadeLotes = quantidadeLotes;
    }
    
    @Override
    public String toString(){
        return "Ação: " +
                "\n  Nome: " + acao.getNome() +
                "\n  Sigla: " + acao.getSigla() +
               "\nQuantidade de Lotes: " + quantidadeLotes;
    }
}
