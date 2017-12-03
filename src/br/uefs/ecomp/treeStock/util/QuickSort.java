package br.uefs.ecomp.treeStock.util;

//Adaptado da Ordena��o QuickSort do livro de Estrutura de dados de Lafore

import java.util.Iterator;
import java.util.LinkedList;


/*
    * Os m�todos s�o est�ticos para que n�o seja necess�rio instanciar um objeto da classe QuickSort.
    * O algoritmo est� adaptado para ordenar em ordem descrecente (na parti��oo os valores menores que o piv� 
    ficam na sua direita e os valores maiores ficam a sua esquerda.
    * �pos a ordena��o � retornado o iterador de uma lista ordenada.
*/

public class QuickSort {
    
    //m�todo p�blico respons�vel por iniciar a ordena��o e por retornar o iterator da nova lista
    public static Iterator ordenar(LinkedList lista, int k){
        int nElementos = lista.size();
        Comparable[] array = new Comparable[nElementos];
        copiarLista(lista, array);
        recQuickSort(array, 0, nElementos-1);
        return copiaArray(array, k).iterator();
    }
    
    //m�todo copia a lista para um array
    private static void copiarLista(LinkedList lista, Comparable[] array){
        Iterator it = lista.iterator();
        
        for(int i = 0; it.hasNext(); i++){
            array[i] = (Comparable) it.next();
        }
    }
    
    //m�todo copia o array ordenado para uma nova lista
    private static LinkedList copiaArray(Comparable[] array, int k){
        LinkedList listaOrdenada = new LinkedList();
        
        for(int i = 0; i < k; i++){
            listaOrdenada.addLast(array[i]);
        }
        
        return listaOrdenada;
    }
    
    private static void recQuickSort(Comparable[] array, int esquerda, int direita){
        if(direita - esquerda <= 0){
            
        }
        else{
            Comparable pivo = array[direita];   //pega o piv�
            
            int particao = partir(array, esquerda, direita, pivo);
            recQuickSort(array, esquerda, particao-1);
            recQuickSort(array, particao+1, direita);
        }
    }

    private static int partir(Comparable[] array, int esquerda, int direita, Comparable pivo) {
        int esquerdaPtr = esquerda - 1;
        int direitaPtr = direita;
        
        while(true){
            while(array[++esquerdaPtr].compareTo(pivo) > 0){    //percorre at� achar um valor no array menor que o piv�
                
            }
            while(direitaPtr > 0 && array[--direitaPtr].compareTo(pivo) < 0){    //percorre at� achar um valor no array maior que o piv�
                
            }
            if(esquerdaPtr >= direitaPtr){    //verifica para saber se os indices se cruzaram, caso seja verdade n�o 
                break;                         //ser� necess�rio a troca e a parti��o est� quase pronta
            }
            else{
                swap(array, esquerdaPtr, direitaPtr);     //troca o valor maior e menor, que o piv�, de posi��oo
            }
        }
        swap(array,esquerdaPtr, direita);     //troca o valor piv� para que ele fique na posi��o certa (valores menores 
        return esquerdaPtr;                    //que ele na sua esquerda e valores maiores direita)
    }
    
    //m�todo para trocar posi��es no array
    private static void swap(Comparable[] array, int pos1, int pos2) {
        Comparable temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }
}
