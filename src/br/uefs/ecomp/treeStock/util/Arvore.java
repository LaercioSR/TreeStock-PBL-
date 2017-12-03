package br.uefs.ecomp.treeStock.util;

import br.uefs.ecomp.treeStock.exceptions.DadoDuplicadoException;
import br.uefs.ecomp.treeStock.exceptions.DadoNaoEncontradoException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Stack;

//Classe Arvore e seus métodos foram adaptados dos encontrados no livro "Estrutura de Dados..." de Lafore
public class Arvore implements IArvore, Serializable{
    private Node raiz;
    private int size = 0;
    
    private class Node implements Serializable{
        private Comparable data;
        private Node filhoEsquerda;
        private Node filhoDireita;
        
        public Node(Comparable data){
            this.data = data;
        }

        public Comparable getData() {
            return data;
        }

        public Node getFilhoEsquerda() {
            return filhoEsquerda;
        }

        public void setFilhoEsquerda(Node filhoEsquerda) {
            this.filhoEsquerda = filhoEsquerda;
        }

        public Node getFilhoDireita() {
            return filhoDireita;
        }

        public void setFilhoDireita(Node filhoDireita) {
            this.filhoDireita = filhoDireita;
        }

    }

    public boolean isEmpty(){
        return raiz == null;
    }
    
    public int size(){
        return size;
    }
    
    @Override
    public Object buscar(Comparable item) throws DadoNaoEncontradoException {
        Node atual = raiz;
        
        if(this.isEmpty()){    //verifica no caso da árvore estar vazia
            throw new DadoNaoEncontradoException();
        }
        
        while(atual.getData().compareTo(item) != 0){
            if(item.compareTo(atual.getData()) < 0){
                atual = atual.getFilhoEsquerda();
            } else{
                atual = atual.getFilhoDireita();
            }
            
            if(atual == null){    //verifica se o nó atual é nulo, caso seja verdade, o item não encontrado na arvore
                throw new DadoNaoEncontradoException();
            }
        }
        return atual.getData();
    }

    @Override
    public void inserir(Comparable item) throws DadoDuplicadoException {
        Node novo = new Node(item);
        
        if(raiz == null){
            raiz = novo;
        } else{
            Node atual = raiz;
            Node pai;
            
            while(true){
                pai = atual;
                if(item.compareTo(atual.getData()) < 0){
                    atual = atual.getFilhoEsquerda();
                    if(atual == null){
                        pai.setFilhoEsquerda(novo);
                        size++;
                        return;
                    }
                }else if(item.compareTo(atual.getData()) > 0){
                    atual = atual.getFilhoDireita();
                    if(atual == null){
                        pai.setFilhoDireita(novo);
                        size++;
                        return;
                    }
                }else{    //Condição só ocorrerá caso seja encontrado um elemento com dado igual ao novo elemento
                    throw new DadoDuplicadoException();
                }
            }
        }
    }

    @Override
    public Comparable remover(Comparable item) throws DadoNaoEncontradoException {
        Node atual = raiz;
        Node pai = raiz;
        boolean eFilhoEsquerdo = true;
        
        if(this.isEmpty()){    //verifica no caso da árvore estar vazia
            throw new DadoNaoEncontradoException();
        }
        
        while(atual.getData().compareTo(item) != 0){
            pai = atual;
            
            if(item.compareTo(atual.getData()) < 0){
                eFilhoEsquerdo = true;
                atual = atual.getFilhoEsquerda();
            } else{
                eFilhoEsquerdo = false;
                atual = atual.getFilhoDireita();
            }
            
            if(atual == null){
                throw new DadoNaoEncontradoException();
            }
        }
        
        //verifica se o item a ser removido não possui filhos
        if(atual.getFilhoEsquerda() == null && atual.getFilhoDireita() == null){
            if(atual == raiz)
                raiz = null;
            else if(eFilhoEsquerdo)
                pai.setFilhoEsquerda(null);
            else
                pai.setFilhoDireita(null);
        }
        //verifica se não possui filho a direita
        else if(atual.getFilhoDireita() == null){
            if(atual == raiz)
                raiz = atual.getFilhoEsquerda();
            else if(eFilhoEsquerdo)
                pai.setFilhoEsquerda(atual.getFilhoEsquerda());
            else
                pai.setFilhoDireita(atual.getFilhoEsquerda());
        }
        //verifica se não possui filho a esquerda
        else if(atual.getFilhoEsquerda() == null){
            if(atual == raiz)
                raiz = atual.getFilhoDireita();
            else if(eFilhoEsquerdo)
                pai.setFilhoEsquerda(atual.getFilhoDireita());
            else
                pai.setFilhoDireita(atual.getFilhoDireita());
        }
        //dois filhos, procura sucessor antes de remover
        else{
            Node sucessor = getSucessor(atual);
            
            if(atual == raiz)
                raiz = sucessor;
            else if(eFilhoEsquerdo)
                pai.setFilhoEsquerda(sucessor);
            else
                pai.setFilhoDireita(sucessor);
            
            sucessor.setFilhoEsquerda(atual.getFilhoEsquerda());
        }
        
        size--;
        return atual.getData();
    }
    
    //procura e retorna o nó com maior valor mais alto depois de delNode
    //vai para filho à direita e depois para os descendentes à esquerda
    private Node getSucessor(Node delNode){
        Node paiSucessor = delNode;
        Node sucessor = delNode;
        Node atual = delNode.getFilhoDireita();
        
        while(atual != null){    //vai para o filho à esquerda até não que mais
            paiSucessor = sucessor;
            sucessor = atual;
            atual = atual.getFilhoEsquerda();
        }
        
        //se sucessor não é filho a esquerda de delNode será feito algumas conexões
        if(sucessor != delNode.getFilhoDireita()){
            paiSucessor.setFilhoEsquerda(sucessor.getFilhoDireita());
            sucessor.setFilhoDireita(delNode.getFilhoDireita());
        }
        
        return sucessor;
    }

    @Override
    public Iterator iterator() {
        return new myIterator();
    }
    
    private class myIterator implements Iterator, Serializable{
        private Node atual = raiz;
        Stack pilha = new Stack();

        public myIterator(){
            if(raiz != null){    //verifica se raiz não é nula
                pilha.push(atual);     //empilha a raiz
                while(atual.getFilhoEsquerda() != null){     //desce a árvore do lado esquerdo 
                    atual = atual.getFilhoEsquerda();         //até não poder mais (Busca menor valor da árvore)
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
                if(temp.getFilhoDireita() != null){    //verifica se possui filho direito 
                    atual = temp.getFilhoDireita();
                    pilha.push(atual);      //empilha o possível filho direito
                    while(atual.getFilhoEsquerda() != null){     //e desce à esquerda do filho direita até não poder mais, empilhando-os
                        atual = atual.getFilhoEsquerda();
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