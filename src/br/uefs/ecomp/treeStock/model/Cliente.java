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
     * @param endereco Endere�o do cliente
     */
    public Cliente(String nome, String cpf, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    /**
     * M�todo retorna o nome do cliente
     * @return Nome do cliente
     */
    public String getNome() {
        return nome;
    }

    /**
     * M�todo retorna o endere�o do cliente
     * @return Endere�o do cliente
     */
    public String getEndereco() {
        return endereco;
    }
    
    /**
     * M�todo retorna CPF do cliente
     * @return CPF do cliente
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * M�todo retorna {@code Carteira} do cliente
     * @return Carteira do cliente
     */
    public Carteira getCarteira() {
        return carteira;
    }

    /**
     * M�todo adiciona uma carteira ao cliente
     * @param carteira Carteira do cliente
     */
    public void setCarteira(Carteira carteira) {
        this.carteira = carteira;
    }
    
    /**
     * M�todo compara o CPF do cliente com outro, para saber qual �
     * maior
     * @param o Cliente que ser� usado na compara��o
     * @return Se o cliente tem CPF maior que o do passado como parametro, retorna 
     * um n�mero positivo, se for menor, um n�mero negativo e se igual, retorna 0
     */
    @Override
    public int compareTo(Object o) {
        return getCpf().compareTo(((Cliente)o).getCpf());
    }
    
    /**
     * M�todo retorna texto de impress�o na tela da classe {@code Cliente}
     * @return Texto refente � {@code Cliente}
     */
    @Override
    public String toString(){
        return "    Nome: " + nome +
               "\n    CPF: " + cpf +
               "\n    Endere�o: " + endereco +
               "\n";
    }
}
