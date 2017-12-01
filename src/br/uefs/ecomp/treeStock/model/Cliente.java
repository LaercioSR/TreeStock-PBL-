package br.uefs.ecomp.treeStock.model;

import java.io.Serializable;

public class Cliente implements Serializable, Comparable {
    private String nome;
    private String endereco;
    private String cpf;
    private Carteira carteira;

    public Cliente(String nome, String cpf, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }
    
    public String getCpf() {
        return cpf;
    }

    public Carteira getCarteira() {
        return carteira;
    }

    public void setCarteira(Carteira carteira) {
        this.carteira = carteira;
    }
    
    @Override
    public int compareTo(Object o) {
        return getCpf().compareTo(((Cliente)o).getCpf());
    }
    
    @Override
    public String toString(){
        return "    Nome: " + nome +
               "\n    CPF: " + cpf +
               "\n    Endereço: " + endereco +
               "\n";
    }
}
