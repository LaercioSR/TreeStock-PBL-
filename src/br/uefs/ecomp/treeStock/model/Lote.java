package br.uefs.ecomp.treeStock.model;

import java.io.Serializable;

public class Lote implements Serializable {
    private Acao acao;     //define a empresa do lote
    private int quantidadeLotes;     //quantidade de lotes da empresa

    public Lote(Acao acao, int quantidadeLotes) {
        this.acao = acao;
        this.quantidadeLotes = quantidadeLotes;
    }

    public Acao getAcao() {
        return acao;
    }

    public int getQuantidadeLotes() {
        return quantidadeLotes;
    }

    public void setQuantidadeLotes(int quantidadeLotes) {
        this.quantidadeLotes = quantidadeLotes;
    }
}
