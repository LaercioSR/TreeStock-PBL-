package br.uefs.ecomp.treeStock.model;

import java.io.Serializable;

public class Acao implements Serializable {
    private String sigla;
    private String nome;
    private double valor;
    private TipoAcao tipoAcao;

    public Acao(String sigla, String nome, double valorInicial, TipoAcao tipoAcao) {
        this.sigla = sigla;
        this.nome = nome;
        this.valor = valorInicial;
        this.tipoAcao = tipoAcao;
    }

    public Acao(String sigla) {
        this.sigla = sigla;
    }

    public String getSigla() {
        return sigla;
    }

    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }

    public TipoAcao getTipoAcao() {
        return tipoAcao;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    @Override
    public boolean equals(Object obj){
        return sigla.equals(((Acao) obj).getSigla());
    }
}
