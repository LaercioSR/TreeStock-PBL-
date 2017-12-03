package br.uefs.ecomp.treeStock.util;

//Adaptado da Ordenação QuickSort do livro de Estrutura de dados de Lafore

import java.util.Iterator;
import java.util.LinkedList;


/*
    * Os métodos são estáticos para que não seja necessário instanciar um objeto da classe QuickSort.
    * O algoritmo está adaptado para ordenar em ordem descrecente (na partiçãoo os valores menores que o pivô 
    ficam na sua direita e os valores maiores ficam a sua esquerda.
    * Ápos a ordenação é retornado o iterador de uma lista ordenada.
*/

public class QuickSort {
    
    //método público responsável por iniciar a ordenação e por retornar o iterator da nova lista
    public static Iterator ordenar(LinkedList lista, int k){
        int nElementos = lista.size();
        Comparable[] array = new Comparable[nElementos];
        copiarLista(lista, array);
        recQuickSort(array, 0, nElementos-1);
        return copiaArray(array, k).iterator();
    }
    
    //método copia a lista para um array
    private static void copiarLista(LinkedList lista, Comparable[] array){
        Iterator it = lista.iterator();
        
        for(int i = 0; it.hasNext(); i++){
            array[i] = (Comparable) it.next();
        }
    }
    
    //método copia o array ordenado para uma nova lista
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
            Comparable pivo = array[direita];   //pega o pivô
            
            int particao = partir(array, esquerda, direita, pivo);
            recQuickSort(array, esquerda, particao-1);
            recQuickSort(array, particao+1, direita);
        }
    }

    private static int partir(Comparable[] array, int esquerda, int direita, Comparable pivo) {
        int esquerdaPtr = esquerda - 1;
        int direitaPtr = direita;
        
        while(true){
            while(array[++esquerdaPtr].compareTo(pivo) > 0){    //percorre até achar um valor no array menor que o pivô
                
            }
            while(direitaPtr > 0 && array[--direitaPtr].compareTo(pivo) < 0){    //percorre até achar um valor no array maior que o pivô
                
            }
            if(esquerdaPtr >= direitaPtr){    //verifica para saber se os indices se cruzaram, caso seja verdade não 
                break;                         //será necessário a troca e a partição está quase pronta
            }
            else{
                swap(array, esquerdaPtr, direitaPtr);     //troca o valor maior e menor, que o pivô, de posiçãoo
            }
        }
        swap(array,esquerdaPtr, direita);     //troca o valor pivô para que ele fique na posição certa (valores menores 
        return esquerdaPtr;                    //que ele na sua esquerda e valores maiores direita)
    }
    
    //método para trocar posições no array
    private static void swap(Comparable[] array, int pos1, int pos2) {
        Comparable temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }
}
