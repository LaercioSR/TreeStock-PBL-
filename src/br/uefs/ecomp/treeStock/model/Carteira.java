package br.uefs.ecomp.treeStock.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A classe {@code Carteira} simula a conta de um cliente,
 * armazenando tanto seu valor do saldo bancário, quanto a seus lote 
 * de ações na bolsa de vaolres
 * @see br.uefs.ecomp.treeStock.model.Lote
 * @see java.util.Iterator
 * @see java.util.LinkedList
 */
public class Carteira implements Serializable, Comparable {
    private Cliente donoConta;
    private double saldo = 0;
    private double montante = 0;
    private LinkedList<Lote> lotes = new LinkedList();
    
    /**
     * Constroi uma carteira para um cliente
     * @param donoConta Cliente dono da carteira
     */
    public Carteira(Cliente donoConta){
        this.donoConta = donoConta;
    }

    /**
     * Método para retornar o nome do dono da carteira
     * @return Nome do cliente dono da conta
     */
    public String getNomeDonoConta() {
        return donoConta.getNome();
    }

    /**
     * Método para retornar o valor do saldo da conta do cliente
     * @return Valor do saldo do cliente
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Método atualizar o valor da conta do cliente pelo valor de dividendo das ações dele
     * @param valorDividendo Valor de dividendo de uma das ações do cliente
     */
    public void setSaldo(double valorDividendo) {
        this.saldo += valorDividendo;
    }
    
    /**
     * Método cria e adiciona lotes na carteira
     * @param acao Acao refente ao novo lote
     * @param quantidadeLotes Quantidade de lotes
     */
    public void criarLote(Acao acao, int quantidadeLotes){
        Lote lote = new Lote(acao, quantidadeLotes);
        lotes.add(lote);
    }
    
    /**
     * Método remove um lote da carteira
     * @param lote Lote igual ao lote a ser removido
     */
    public void removerLote(Lote lote){
        lotes.remove(lote);
    }
    
    /**
     * Método atualiza o montante da carteira do cliente pelo saldo
     * da conta e pelo valor de ações da carteira
     * @return Valor atualizado do montante
     */
    public double atualizarMontante(){
        double valorAcoes = 0;
        Iterator it = iteratorLotes();
        
        while(it.hasNext()){
            Lote lote = (Lote) it.next();
            
            valorAcoes += lote.getAcao().getValor() * lote.getQuantidadeLotes() * 100;
        }
        
        montante = saldo + valorAcoes;
        
        return montante;
    }
    
    /**
     * Método retorna o iterador da lista de lotes do cliente
     * @return Iretador de lotes
     */
    public Iterator iteratorLotes(){
        return lotes.iterator();
    }
    
    /**
     * Método retorna texto de impressão na tela da classe {@code Carteira}
     * @return Texto refente á {@code Carteira}
     */
    @Override
    public String toString(){
        return "    Nome do Cliente: " + donoConta.getNome() +
               "\n    CPF: " + donoConta.getCpf() +
               "\n    Endereço: " + donoConta.getEndereco() +
               "\n    Montante: " + atualizarMontante() +
               "\n" ;
    }

    /**
     * Método compara carteira para saber qual tem maior valor
     * @param o Carteira que será comparada
     * @return Diferença (em inteiro) de valores entre as carteiras
     */
    @Override
    public int compareTo(Object o) {
        return Double.compare(atualizarMontante(), ((Carteira) o).atualizarMontante());
    }
}
