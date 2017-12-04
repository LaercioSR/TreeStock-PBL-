package br.uefs.ecomp.treeStock.model;

import java.io.Serializable;

/**
 * Classe {@code Cliente} armazena dados de um cliente, como uma carteira
 */
public class Cliente implements Serializable, Comparable {
    private String nome;
    private String endereco;
    private final String cpf;
    private Carteira carteira;

    /**
     * Constroi um cliente com seus dados pessoais
     * @param nome Nome do cliente
     * @param cpf CPF do cliente
     * @param endereco Endereço do cliente
     */
    public Cliente(String nome, String cpf, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    /**
     * Método retorna o nome do cliente
     * @return Nome do cliente
     */
    public String getNome() {
        return nome;
    }

    /**
     * Método retorna o endereço do cliente
     * @return Endereço do cliente
     */
    public String getEndereco() {
        return endereco;
    }
    
    /**
     * Método retorna CPF do cliente
     * @return CPF do cliente
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Método retorna {@code Carteira} do cliente
     * @return Carteira do cliente
     */
    public Carteira getCarteira() {
        return carteira;
    }

    /**
     * Método adiciona uma carteira ao cliente
     * @param carteira Carteira do cliente
     */
    public void setCarteira(Carteira carteira) {
        this.carteira = carteira;
    }
    
    /**
     * Método compara o CPF do cliente com outro, para saber qual é
     * maior
     * @param o Cliente que será usado na comparação
     * @return Se o cliente tem CPF maior que o do passado como parametro, retorna 
     * um número positivo, se for menor, um número negativo e se igual, retorna 0
     */
    @Override
    public int compareTo(Object o) {
        return getCpf().compareTo(((Cliente)o).getCpf());
    }
    
    /**
     * Método retorna texto de impressão na tela da classe {@code Cliente}
     * @return Texto refente á {@code Cliente}
     */
    @Override
    public String toString(){
        return "    Nome: " + nome +
               "\n    CPF: " + cpf +
               "\n    Endereço: " + endereco +
               "\n";
    }
}
