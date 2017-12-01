package br.uefs.ecomp.treeStock.util;

import java.util.Iterator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArvoreTest {
    
    Arvore arvore;
    
    @Before
    public void setUp() {
        arvore = new Arvore();
    }
    
    @Test
    public void testInserir() throws DadoDuplicadoException{
        Assert.assertTrue(arvore.isEmpty());
        arvore.inserir(5);
        arvore.inserir(3);
        arvore.inserir(8);
        Assert.assertFalse(arvore.isEmpty());
    }
    
    @Test(expected = DadoDuplicadoException.class)
    public void testInserirDuplicado() throws DadoDuplicadoException{
        arvore.inserir(5);
        arvore.inserir(5);
    }
    
    @Test
    public void testBuscar() throws DadoDuplicadoException, DadoNaoEncontradoException{
        arvore.inserir(5);
        arvore.inserir(3);
        arvore.inserir(8);
        arvore.inserir(6);
        arvore.inserir(9);
        arvore.inserir(1);
        
        Assert.assertEquals(5, (int)arvore.buscar(5));
    }
    
    @Test(expected = DadoNaoEncontradoException.class)
    public void testBuscarDadoNaoInserido() throws DadoNaoEncontradoException{
        Assert.assertEquals(5, (int)arvore.buscar(5));
    }
    
    @Test
    public void testRemover() throws DadoDuplicadoException, DadoNaoEncontradoException{
        arvore.inserir(5);
        Assert.assertFalse(arvore.isEmpty());
        arvore.remover(5);
        Assert.assertTrue(arvore.isEmpty());
    }
    
    @Test(expected = DadoNaoEncontradoException.class)
    public void testRemoverDadoNaoInserido() throws DadoNaoEncontradoException{
        arvore.remover(5);
    }
    
    @Test
    public void testIterator() throws DadoDuplicadoException{
        Iterator it;
        
        arvore.inserir(5);
        arvore.inserir(3);
        arvore.inserir(8);
        arvore.inserir(6);
        arvore.inserir(9);
        arvore.inserir(1);
        arvore.inserir(7);
        arvore.inserir(2);
        arvore.inserir(0);
        arvore.inserir(4);
        
        it = arvore.iterator();
        
        Assert.assertTrue(it.hasNext());
        Assert.assertEquals(0, (int)it.next());
        Assert.assertEquals(1, (int)it.next());
        Assert.assertEquals(2, (int)it.next());
        Assert.assertEquals(3, (int)it.next());
        Assert.assertEquals(4, (int)it.next());
        Assert.assertEquals(5, (int)it.next());
        Assert.assertEquals(6, (int)it.next());
        Assert.assertEquals(7, (int)it.next());
        Assert.assertEquals(8, (int)it.next());
        Assert.assertEquals(9, (int)it.next());
        Assert.assertFalse(it.hasNext());
    }
}
