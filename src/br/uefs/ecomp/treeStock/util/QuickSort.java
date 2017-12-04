package br.uefs.ecomp.treeStock.util;

//Adaptado da Ordenação QuickSort do livro de Estrutura de dados de Lafore

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Classe {@code QuickSort} ordena um <b>LinkedList</b> e retorna o <b>Iterator</b> de
 * uma nova LinkedList com os k maoires valores. Apenas um método é publico, sendo responsável 
 * por receber o LinkedList e o números de maiores valores (k) do novo LinkedList. Métodos
 * são estáticos para não haver necessidade de instanciar um objeto da classe
 */
public class QuickSort {
    
    /**
     * Método publico responsável por chamar os outros métodos da classe
     * @param lista LinkedList há ser ordenado
     * @param k Número de elementos do <b>LinkedList</b> de maiores valores
     * @return Iterator do LinkedList de maiores valores
     */
    public static Iterator ordenar(LinkedList lista, int k){
        int nElementos = lista.size();
        Comparable[] array = new Comparable[nElementos];
        copiarLista(lista, array);
        recQuickSort(array, 0, nElementos-1);
        return copiaArray(array, k).iterator();
    }
    
    /**
     * Método copia o lista passada para classe em um array
     * @param lista LinkedList dos elementos a ser ordenados
     * @param array Array que será ordenado
     */
    private static void copiarLista(LinkedList lista, Comparable[] array){
        Iterator it = lista.iterator();
        
        for(int i = 0; it.hasNext(); i++){
            array[i] = (Comparable) it.next();
        }
    }
    
    //método copia o array ordenado para uma nova lista
    /**
     * Método copia o array ordenado para um novo LinkedList com k elementos
     * @param array Array ordenado
     * @param k Número de elementos do LinkedList
     * @return LinkedList dos elementos ordenados
     */
    private static LinkedList copiaArray(Comparable[] array, int k){
        LinkedList listaOrdenada = new LinkedList();
        
        for(int i = 0; i < k; i++){
            listaOrdenada.addLast(array[i]);
        }
        
        return listaOrdenada;
    }
    
    /**
     * Método responsável pela ordenação do Array
     * @param array Array há ser ordenado
     * @param esquerda Posição inicial da partição do array
     * @param direita Posição final da partição do array
     */
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

    /**
     * Método particiona o Array em duas partes em relação ao pivô, elementos menores à 
     * esquerda do pivô, e o maiores à direita
     * @param array Array há ser particionado
     * @param esquerda Posição inical da parte há ser particionado do array
     * @param direita Posição final da parte há ser particionado do array
     * @param pivo Elemento pivô
     * @return Posição final do particionamento do array
     */
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
    /**
     * Método para trocar posições no array 
     * @param array Array que terá troca de posições entre 2 elementos
     * @param pos1 Posição do elemento 1
     * @param pos2 Posição do elemento 2
     */
    private static void swap(Comparable[] array, int pos1, int pos2) {
        Comparable temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }
}
