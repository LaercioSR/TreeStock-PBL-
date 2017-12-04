package br.uefs.ecomp.treeStock.model;

import java.io.Serializable;

/**
 * Enumera��o dos tipos de a��es poss�veis, podendo ser
 * Ordin�ria <b>(ON)</b> ou Preferencial <b>(PN)</b>
 */
public enum TipoAcao implements Serializable{
    /**
     * Ordin�ria
     */
    ON, 
    /**
     * Preferencial
     */
    PN;
}
