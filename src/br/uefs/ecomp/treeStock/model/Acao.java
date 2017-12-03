package br.uefs.ecomp.treeStock.model;

import java.io.Serializable;

/**
 * A clsse {@code Acao} mantem os dados referentes a ac�o da bolsa 
 * de valores de uma empresa
 * @see java.util.LinkedList
 */
public class Acao implements Serializable {
    private final String sigla;
    private String nome;
    private double valor;
    private TipoAcao tipoAcao;

    /**
     * Constroi uma a��o com os dados passados
     * @param sigla Sigla da a��o
     * @param nome Nome da empresa refente a a��o
     * @param valorInicial Cota��o inial da a��o
     * @param tipoAcao - Define o se a a��o � Ordin�ria ou Preferencial
     */
    public Acao(String sigla, String nome, double valorInicial, TipoAcao tipoAcao) {
        this.sigla = sigla;
        this.nome = nome;
        this.valor = valorInicial;
        this.tipoAcao = tipoAcao;
    }
    
    /**
     * Constroi uma a��o "falsa" apenas com a sigla, usada para 
     * ser usada na busca da a��o no {@linkplain java.util.LinkedList LinkedList}
     * @param sigla Sigla da a��o que ser� buscada
     */
    public Acao(String sigla) {
        this.sigla = sigla;
    }

    /**
     * M�todo para retornar a sigla da a��o
     * @return Sigla da a��o
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * M�todo para retornar o nome da empresa referente a a��o
     * @return Nome da empresa
     */
    public String getNome() {
        return nome;
    }

    /**
     * M�todo para retornar o valor da cota��o da a��o
     * @return Valor da cota��o
     */
    public double getValor() {
        return valor;
    }

    /**
     * M�todo para retornar o tipo da a��o
     * @return Tipo da a��o
     */
    public TipoAcao getTipoAcao() {
        return tipoAcao;
    }

    /**
     * M�todo para modificar o valor da cota��o da a��o
     * @param valor Novo valor da cota��o
     */
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    /**
     * M�todo compara se duas a��es s�o iguais pelo nome
     * @param obj A��o que ser� comparada
     * @return True se as duas a��es s�o iguais
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Acao)
            return sigla.equals(((Acao) obj).getSigla());
        return false;
    }
}
