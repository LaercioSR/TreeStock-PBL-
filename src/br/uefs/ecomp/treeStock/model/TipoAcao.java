package br.uefs.ecomp.treeStock.model;

import java.io.Serializable;

/**
 * Enumeração dos tipos de ações possíveis, podendo ser
 * Ordinária <b>(ON)</b> ou Preferencial <b>(PN)</b>
 */
public enum TipoAcao implements Serializable{
    /**
     * Ordinária
     */
    ON, 
    /**
     * Preferencial
     */
    PN;
}
