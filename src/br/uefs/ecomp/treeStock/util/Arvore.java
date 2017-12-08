package br.uefs.ecomp.treeStock.util;

import br.uefs.ecomp.treeStock.exceptions.DadoDuplicadoException;
import br.uefs.ecomp.treeStock.exceptions.DadoNaoEncontradoException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Stack;

/**
 * Classe árvore para armazenamento e manipulação de dados com complexidade O(log n)
 */     
public class Arvore implements IArvore, Serializable {
    private Node raiz;
    private int size = 0;
    
    private class Node implements Serializable{
        private Comparable data;
        private Node filhoEsquerdo;
        private Node filhoDireito;
        private int altura;
        
        public Node(Comparable data){
            this.data = data;
        }

        public Comparable getData() {
            return data;
        }

        public Node getFilhoEsquerdo() {
            return filhoEsquerdo;
        }

        public void setFilhoEsquerdo(Node filhoEsquerdo) {
            this.filhoEsquerdo = filhoEsquerdo;
        }

        public Node getFilhoDireito() {
            return filhoDireito;
        }

        public void setFilhoDireito(Node filhoDireito) {
            this.filhoDireito = filhoDireito;
        }

        public int getAltura() {
            return altura;
        }

        public void setAltura(int altura) {
            this.altura = altura;
        }
        
        private void setData(Comparable data) {
            this.data = data;
        }
    }

    /**
     * Método retorna se a árvore está vazia
     * @return True se a árvore está vazia
     */
    public boolean isEmpty(){
        return raiz == null;
    }
    
    /**
     * Método retorna o número de dados na árvore
     * @return Número de dados
     */
    public int size(){
        return size;
    }
    
    private Node rotacaoDireita(Node no) {
        if(no.getFilhoEsquerdo() != null){
            Node aux = no.getFilhoEsquerdo();
            no.setFilhoEsquerdo(aux.getFilhoDireito());
            aux.setFilhoDireito(no);

            no.setAltura(Math.max(altura(no.getFilhoEsquerdo()), altura(no.getFilhoDireito())) + 1);
            aux.setAltura(Math.max(altura(aux.getFilhoEsquerdo()), altura(aux.getFilhoDireito())) + 1);

            return (aux);
        }
        return no;
    }

    private Node rotacaoEsquerda(Node no) {
        if(no.getFilhoDireito() != null){
            Node aux = no.getFilhoDireito();
            no.setFilhoDireito(aux.getFilhoEsquerdo());
            aux.setFilhoEsquerdo(no);

            no.setAltura(Math.max(altura(no.getFilhoEsquerdo()), altura(no.getFilhoDireito())) + 1);
            aux.setAltura(Math.max(altura(aux.getFilhoEsquerdo()), altura(aux.getFilhoDireito())) + 1);

            return (aux);
        }
        return no;
    }

    private Node rotacaoEsquerdaDireita(Node no) {
        if(no.getFilhoEsquerdo() != null){
            Node aux = rotacaoEsquerda(no.getFilhoEsquerdo());
            no.setFilhoEsquerdo(aux);
            return rotacaoDireita(no);
        }
        return no;
    }

    private Node rotacaoDireitaEsquerda(Node no) {
        if(no.getFilhoDireito() != null){
            Node aux = rotacaoDireita(no.getFilhoDireito());
            no.setFilhoDireito(aux);
            return rotacaoEsquerda(no);
        }
        return no;
    }

    // Método retorna a altura da subárvore que possui o nó passado como raiz
    private int altura(Node no) {
        if (no == null) {
            return -1;
        } else {
            return no.getAltura();
        }
    }
    
    // Método calcula o fator de balanceamento de um nó
    private int fatorBalanceamento(Node no){
        return Math.abs(altura(no.getFilhoEsquerdo()) - altura(no.getFilhoDireito()));
    }
    
    /**
     * Método busca um item na árvore
     * @param item Item igual ao que será buscado
     * @return Item buscado
     * @throws DadoNaoEncontradoException Se não encontrar o item na árvore
     */
    @Override
    public Object buscar(Comparable item) throws DadoNaoEncontradoException {
        Node atual = raiz;
        
        if(this.isEmpty()){    //verifica no caso da árvore estar vazia
            throw new DadoNaoEncontradoException();
        }
        
        while(atual.getData().compareTo(item) != 0){
            if(item.compareTo(atual.getData()) < 0){
                atual = atual.getFilhoEsquerdo();
            } else{
                atual = atual.getFilhoDireito();
            }
            
            if(atual == null){    //verifica se o nó atual é nulo, caso seja verdade, o item não encontrado na arvore
                throw new DadoNaoEncontradoException();
            }
        }
        return atual.getData();
    }

    /**
     * Método insere um item na árvore
     * @param item Item a ser inserido
     * @throws DadoDuplicadoException Se já existir um item igual na árvore
     */
    @Override
    public void inserir(Comparable item) throws DadoDuplicadoException{
        raiz = inserir(raiz, item);
        size++;
    }
    
    // Método recursivo para inserção
    private Node inserir(Node raiz, Comparable item) throws DadoDuplicadoException{
        if(raiz == null){
            return new Node(item);
        }
        
        if(item.compareTo(raiz.getData()) < 0){
            raiz.setFilhoEsquerdo(inserir(raiz.getFilhoEsquerdo(), item));
            if(fatorBalanceamento(raiz) == 2){
                if(item.compareTo(raiz.getFilhoEsquerdo().getData()) < 0)
                    raiz = rotacaoDireita(raiz);
                else
                    raiz = rotacaoEsquerdaDireita(raiz);
            }
        } else if(item.compareTo(raiz.getData()) > 0){
            raiz.setFilhoDireito(inserir(raiz.getFilhoDireito(), item));
            if(fatorBalanceamento(raiz) == 2){
                if(item.compareTo(raiz.getFilhoDireito().getData()) > 0)
                    raiz = rotacaoEsquerda(raiz);
                else
                    raiz = rotacaoDireitaEsquerda(raiz);
            }
        } else{
            throw new DadoDuplicadoException();
        }
        
        raiz.setAltura(Math.max(altura(raiz.getFilhoEsquerdo()), altura(raiz.getFilhoDireito())) + 1);
        return raiz;
    }

    /**
     * Método remove um item da árvore
     * @param item Item a ser removido
     * @throws DadoNaoEncontradoException Se não encontrar o item na árvore
     */
    @Override
    public void remover(Comparable item) throws DadoNaoEncontradoException{
        raiz = remover(raiz, item);
        size--;
    }

    // Método recursivo para remoção
    private Node remover(Node no, Comparable item) throws DadoNaoEncontradoException {
        if (no == null) {
            throw new DadoNaoEncontradoException();
        }
        if (item.compareTo(no.getData()) < 0) {
            no.setFilhoEsquerdo(remover(no.getFilhoEsquerdo(), item));
            if (fatorBalanceamento(no) >= 2){ //Verifica se houve desbalanceamento ap?s a remo??o
                Node netoEsquerdo = no.getFilhoDireito().getFilhoEsquerdo();
                Node netoDireito = no.getFilhoDireito().getFilhoDireito();
                if (altura(netoEsquerdo) <= altura(netoDireito)) {
                    no = rotacaoDireita(no);
                } else {
                    no = rotacaoDireitaEsquerda(no);
                }
            }
            return no;
        } else if (item.compareTo(no.getData()) > 0) {
            no.setFilhoDireito(remover(no.getFilhoDireito(), item));
            if (fatorBalanceamento(no) >= 2) { //Verifica se houve desbalanceamento ap?s a remo??o
                Node netoEsquerdo = no.getFilhoEsquerdo().getFilhoEsquerdo();
                Node netoDireito = no.getFilhoEsquerdo().getFilhoDireito();
                if (altura(netoDireito) <= altura(netoEsquerdo)) {
                    no = rotacaoEsquerda(no);
                } else {
                    no = rotacaoEsquerdaDireita(no);
                }
            }
            return no;
        } else {
            if (no.getFilhoEsquerdo() == null || no.getFilhoDireito() == null) { //Se o n? tem 1 ou nenhum filho
                if (no.getFilhoEsquerdo() != null) {
                    no = no.getFilhoEsquerdo();
                } else {
                    no = no.getFilhoDireito();
                }
            } else { 	//Caso o n? tenha 2 filhos
                Node temp = procuraMenor(no.getFilhoDireito());
                no.setData(temp.getData());  //Substitui o dado do n? a ser removido pelo dado do menor filho do filho da direita 
                no.setFilhoDireito(remover(no.getFilhoDireito(), no.getData())); //Remove o n? que cont?m o dado que foi colocado no n? removido
                if ((altura(no.getFilhoEsquerdo()) - altura(no.getFilhoDireito())) >= 2) {  //Verifica se houve desbalanceamento
                    Node netoEsquerdo = no.getFilhoEsquerdo().getFilhoDireito();
                    Node netoDireito = no.getFilhoEsquerdo().getFilhoDireito();
                    if (altura(netoDireito) <= altura(netoEsquerdo)) {
                        no = rotacaoEsquerda(no);
                    } else {
                        no = rotacaoEsquerdaDireita(no);
                    }
                }
            }
            return no;
        }       
    }
    
    /*Retorna o menor dos filhos do n? passado.*/
    private Node procuraMenor(Node atual) {
        Node aux = atual;
        if (aux != null) {
            while (aux.getFilhoEsquerdo() != null) {
                aux = aux.getFilhoEsquerdo();
            }
        }
        return aux;
    }

    /**
     * Método retorna <b>Iterator</b> da árvore
     * @return Iterator da árvore
     */
    @Override
    public Iterator iterator() {
        return new myIterator();
    }
    
    // Classe Iterator, que percorre a árvore In Order
    private class myIterator implements Iterator{
        private Node atual = raiz;
        Stack pilha = new Stack();

        public myIterator(){
            if(raiz != null){    //verifica se raiz não é nula
                pilha.push(atual);     //empilha a raiz
                while(atual.getFilhoEsquerdo() != null){     //desce a árvore do lado esquerdo 
                    atual = atual.getFilhoEsquerdo();         //até não poder mais (Busca menor valor da árvore)
                    pilha.push(atual);                          //empilhando o nós
                }
            }
        }
        
        @Override
        public boolean hasNext() {
            return !pilha.empty();
        }

        @Override
        public Object next() {
            Node temp = null;
            
            if(hasNext()){
                temp = (Node) pilha.peek();     //pega o topo da pilha, que será retornado
                if(temp.getFilhoDireito() != null){    //verifica se possui filho direito 
                    atual = temp.getFilhoDireito();
                    pilha.push(atual);      //empilha o possível filho direito
                    while(atual.getFilhoEsquerdo() != null){     //e desce à esquerda do filho direita até não poder mais, empilhando-os
                        atual = atual.getFilhoEsquerdo();
                        pilha.push(atual);
                    }
                } else{      //caso o nó não possua filho direito
                    pilha.pop();      //desempilha-o
                    
                    if(pilha.size() > 0){     //verifica se existe mais nós na pilha
                        atual = (Node) pilha.peek();
                        //verifica se o próximo na pilha (atual) é menor que temp (isso ocorre quando atual já foi iterado)
                        while(pilha.size() > 0 && temp.getData().compareTo(atual.getData()) > 0){    
                            pilha.pop();    //desempilha atual, que já foi iterado
                            if(pilha.size() > 0){    //e verifica novamente se ainda existe nós na pilha
                                atual = (Node) pilha.peek();
                            }
                        }
                    }
                }
            }
            if(temp != null){
                return temp.getData();
            }
            return null;
        }
    }
}