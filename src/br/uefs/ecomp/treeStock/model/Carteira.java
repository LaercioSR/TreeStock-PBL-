package br.uefs.ecomp.treeStock.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

//Classe carteira fará papel de conta do Cliente
public class Carteira implements Serializable, Comparable {
    private Cliente donoConta;
    private double saldo = 0;
    private double montante = 0;
    private LinkedList<Lote> lotes = new LinkedList();
    
    public Carteira(Cliente donoConta){
        this.donoConta = donoConta;
    }

    public String getNomeDonoConta() {
        return donoConta.getNome();
    }

    public void setSaldo(double saldo) {
        this.saldo += saldo;
    }

    public double getSaldo() {
        return saldo;
    }
    
    public void criarLote(Acao acao, int quantidadeLotes){
        Lote lote = new Lote(acao, quantidadeLotes);
        lotes.add(lote);
    }
    
    public void removerLote(Lote lote){
        lotes.remove(lote);
    }
    
    public double atualizarMontante(){
        double valorAcoes = 0;
        Iterator it = iteratorLotes();
        
        while(it.hasNext()){
            Lote lote = (Lote) it.next();
            
            valorAcoes += lote.getAcao().getValor() * lote.getQuantidadeLotes() * 100;
        }
        //System.out.println();
        
        montante = saldo + valorAcoes;
        
        return montante;
    }
    
    public Iterator iteratorLotes(){
        return lotes.iterator();
    }
    
    @Override
    public String toString(){
        return "    Nome do Cliente: " + donoConta.getNome() +
               "\n    CPF: " + donoConta.getCpf() +
               "\n    Endereço: " + donoConta.getEndereco() +
               "\n    Montante: " + atualizarMontante() +
               "\n" ;
    }

    @Override
    public int compareTo(Object o) {
        return Double.compare(atualizarMontante(), ((Carteira) o).atualizarMontante());
    }
}
