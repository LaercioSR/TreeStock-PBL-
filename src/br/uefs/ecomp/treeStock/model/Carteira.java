package br.uefs.ecomp.treeStock.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A classe {@code Carteira} simula a conta de um cliente,
 * armazenando tanto seu valor do saldo banc�rio, quanto a seus lote 
 * de a��es na bolsa de vaolres
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
     * M�todo para retornar o nome do dono da carteira
     * @return Nome do cliente dono da conta
     */
    public String getNomeDonoConta() {
        return donoConta.getNome();
    }

    /**
     * M�todo para retornar o valor do saldo da conta do cliente
     * @return Valor do saldo do cliente
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * M�todo atualizar o valor da conta do cliente pelo valor de dividendo das a��es dele
     * @param valorDividendo Valor de dividendo de uma das a��es do cliente
     */
    public void setSaldo(double valorDividendo) {
        this.saldo += valorDividendo;
    }
    
    /**
     * M�todo cria e adiciona lotes na carteira
     * @param acao Acao refente ao novo lote
     * @param quantidadeLotes Quantidade de lotes
     */
    public void criarLote(Acao acao, int quantidadeLotes){
        Lote lote = new Lote(acao, quantidadeLotes);
        lotes.add(lote);
    }
    
    /**
     * M�todo remove um lote da carteira
     * @param lote Lote igual ao lote a ser removido
     */
    public void removerLote(Lote lote){
        lotes.remove(lote);
    }
    
    /**
     * M�todo atualiza o montante da carteira do cliente pelo saldo
     * da conta e pelo valor de a��es da carteira
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
     * M�todo retorna o iterador da lista de lotes do cliente
     * @return Iretador de lotes
     */
    public Iterator iteratorLotes(){
        return lotes.iterator();
    }
    
    /**
     * M�todo retorna texto de impress�o na tela da classe {@code Carteira}
     * @return Texto refente � {@code Carteira}
     */
    @Override
    public String toString(){
        return "    Nome do Cliente: " + donoConta.getNome() +
               "\n    CPF: " + donoConta.getCpf() +
               "\n    Endere�o: " + donoConta.getEndereco() +
               "\n    Montante: " + atualizarMontante() +
               "\n" ;
    }

    /**
     * M�todo compara carteira para saber qual tem maior valor
     * @param o Carteira que ser� comparada
     * @return Diferen�a (em inteiro) de valores entre as carteiras
     */
    @Override
    public int compareTo(Object o) {
        return Double.compare(atualizarMontante(), ((Carteira) o).atualizarMontante());
    }
}
