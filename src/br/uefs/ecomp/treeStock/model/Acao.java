package br.uefs.ecomp.treeStock.model;

import java.io.Serializable;

/**
 * A clsse {@code Acao} mantem os dados referentes a acão da bolsa 
 * de valores de uma empresa
 * @see java.util.LinkedList
 */
public class Acao implements Serializable {
    private final String sigla;
    private String nome;
    private double valor;
    private TipoAcao tipoAcao;

    /**
     * Constroi uma ação com os dados passados
     * @param sigla Sigla da ação
     * @param nome Nome da empresa refente a ação
     * @param valorInicial Cotação inial da ação
     * @param tipoAcao - Define o se a ação é Ordinária ou Preferencial
     */
    public Acao(String sigla, String nome, double valorInicial, TipoAcao tipoAcao) {
        this.sigla = sigla;
        this.nome = nome;
        this.valor = valorInicial;
        this.tipoAcao = tipoAcao;
    }
    
    /**
     * Constroi uma ação "falsa" apenas com a sigla, usada para 
     * ser usada na busca da ação no {@linkplain java.util.LinkedList LinkedList}
     * @param sigla Sigla da ação que será buscada
     */
    public Acao(String sigla) {
        this.sigla = sigla;
    }

    /**
     * Método para retornar a sigla da ação
     * @return Sigla da ação
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * Método para retornar o nome da empresa referente a ação
     * @return Nome da empresa
     */
    public String getNome() {
        return nome;
    }

    /**
     * Método para retornar o valor da cotação da ação
     * @return Valor da cotação
     */
    public double getValor() {
        return valor;
    }

    /**
     * Método para retornar o tipo da ação
     * @return Tipo da ação
     */
    public TipoAcao getTipoAcao() {
        return tipoAcao;
    }

    /**
     * Método para modificar o valor da cotação da ação
     * @param valor Novo valor da cotação
     */
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    /**
     * Método compara se duas ações são iguais pelo nome
     * @param obj Ação que será comparada
     * @return True se as duas ações são iguais
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Acao)
            return sigla.equals(((Acao) obj).getSigla());
        return false;
    }
}
