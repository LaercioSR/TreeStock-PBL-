package br.uefs.ecomp.treeStock.exceptions;

/**
 * Exce��o lan�ada quando o usu�rio tentar remover uma a��o que pertence a uma carteira
 * @see br.uefs.ecomp.treeStock.model.Acao
 * @see br.uefs.ecomp.treeStock.model.Carteira
 */
public class AcaoEmCarteiraException extends Exception {
    
    /**
     * Controi a exce��o
     */
    public AcaoEmCarteiraException() {
        super();
    }

    /**
     * Constroi a exce��o com uma mensagem que poder� ser exibida
     * @param msg Mensagem que ser� exibida na tela
     */
    public AcaoEmCarteiraException(String msg) {
        super(msg);
    }

    public AcaoEmCarteiraException(Throwable exception) {
        super(exception);
    }
}
