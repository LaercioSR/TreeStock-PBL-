package br.uefs.ecomp.treeStock.exceptions;

/**
 * Exceção lançada quando o usuário tentar remover uma ação que pertence a uma carteira
 * @see br.uefs.ecomp.treeStock.model.Acao
 * @see br.uefs.ecomp.treeStock.model.Carteira
 */
public class AcaoEmCarteiraException extends Exception {
    
    /**
     * Controi a exceção
     */
    public AcaoEmCarteiraException() {
        super();
    }

    /**
     * Constroi a exceção com uma mensagem que poderá ser exibida
     * @param msg Mensagem que será exibida na tela
     */
    public AcaoEmCarteiraException(String msg) {
        super(msg);
    }

    public AcaoEmCarteiraException(Throwable exception) {
        super(exception);
    }
}
