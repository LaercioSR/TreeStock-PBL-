package br.uefs.ecomp.treeStock.util;

import br.uefs.ecomp.treeStock.exceptions.DadoDuplicadoException;
import br.uefs.ecomp.treeStock.exceptions.DadoNaoEncontradoException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Stack;

/**
 * Classe �rvore para armazenamento e manipula��o de dados com complexidade O(log n)
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
     * M�todo retorna se a �rvore est� vazia
     * @return True se a �rvore est� vazia
     */
    public boolean isEmpty(){
        return raiz == null;
    }
    
    /**
     * M�todo retorna o n�mero de dados na �rvore
     * @return N�mero de dados
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

    // M�todo retorna a altura da sub�rvore que possui o n� passado como raiz
    private int altura(Node no) {
        if (no == null) {
            return -1;
        } else {
            return no.getAltura();
        }
    }
    
    // M�todo calcula o fator de balanceamento de um n�
    private int fatorBalanceamento(Node no){
        return Math.abs(altura(no.getFilhoEsquerdo()) - altura(no.getFilhoDireito()));
    }
    
    /**
     * M�todo busca um item na �rvore
     * @param item Item igual ao que ser� buscado
     * @return Item buscado
     * @throws DadoNaoEncontradoException Se n�o encontrar o item na �rvore
     */
    @Override
    public Object buscar(Comparable item) throws DadoNaoEncontradoException {
        Node atual = raiz;
        
        if(this.isEmpty()){    //verifica no caso da �rvore estar vazia
            throw new DadoNaoEncontradoException();
        }
        
        while(atual.getData().compareTo(item) != 0){
            if(item.compareTo(atual.getData()) < 0){
                atual = atual.getFilhoEsquerdo();
            } else{
                atual = atual.getFilhoDireito();
            }
            
            if(atual == null){    //verifica se o n� atual � nulo, caso seja verdade, o item n�o encontrado na arvore
                throw new DadoNaoEncontradoException();
            }
        }
        return atual.getData();
    }

    /**
     * M�todo insere um item na �rvore
     * @param item Item a ser inserido
     * @throws DadoDuplicadoException Se j� existir um item igual na �rvore
     */
    @Override
    public void inserir(Comparable item) throws DadoDuplicadoException{
        raiz = inserir(raiz, item);
        size++;
    }
    
    // M�todo recursivo para inser��o
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
     * M�todo remove um item da �rvore
     * @param item Item a ser removido
     * @throws DadoNaoEncontradoException Se n�o encontrar o item na �rvore
     */
    @Override
    public void remover(Comparable item) throws DadoNaoEncontradoException{
        raiz = remover(raiz, item);
        size--;
    }

    // M�todo recursivo para remo��o
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
     * M�todo retorna <b>Iterator</b> da �rvore
     * @return Iterator da �rvore
     */
    @Override
    public Iterator iterator() {
        return new myIterator();
    }
    
    // Classe Iterator, que percorre a �rvore In Order
    private class myIterator implements Iterator{
        private Node atual = raiz;
        Stack pilha = new Stack();

        public myIterator(){
            if(raiz != null){    //verifica se raiz n�o � nula
                pilha.push(atual);     //empilha a raiz
                while(atual.getFilhoEsquerdo() != null){     //desce a �rvore do lado esquerdo 
                    atual = atual.getFilhoEsquerdo();         //at� n�o poder mais (Busca menor valor da �rvore)
                    pilha.push(atual);                          //empilhando o n�s
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
                temp = (Node) pilha.peek();     //pega o topo da pilha, que ser� retornado
                if(temp.getFilhoDireito() != null){    //verifica se possui filho direito 
                    atual = temp.getFilhoDireito();
                    pilha.push(atual);      //empilha o poss�vel filho direito
                    while(atual.getFilhoEsquerdo() != null){     //e desce � esquerda do filho direita at� n�o poder mais, empilhando-os
                        atual = atual.getFilhoEsquerdo();
                        pilha.push(atual);
                    }
                } else{      //caso o n� n�o possua filho direito
                    pilha.pop();      //desempilha-o
                    
                    if(pilha.size() > 0){     //verifica se existe mais n�s na pilha
                        atual = (Node) pilha.peek();
                        //verifica se o pr�ximo na pilha (atual) � menor que temp (isso ocorre quando atual j� foi iterado)
                        while(pilha.size() > 0 && temp.getData().compareTo(atual.getData()) > 0){    
                            pilha.pop();    //desempilha atual, que j� foi iterado
                            if(pilha.size() > 0){    //e verifica novamente se ainda existe n�s na pilha
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