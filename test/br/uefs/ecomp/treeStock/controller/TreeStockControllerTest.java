package br.uefs.ecomp.treeStock.controller;

import br.uefs.ecomp.treeStock.model.Cliente;
import br.uefs.ecomp.treeStock.model.TipoAcao;
import br.uefs.ecomp.treeStock.util.DadoDuplicadoException;
import br.uefs.ecomp.treeStock.util.DadoNaoEncontradoException;
import java.io.File;
//java.io.PrintStream;
import java.io.FileNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class TreeStockControllerTest {
    
    private TreeStockController controller;
    
    @Before
    public void setUp() {
        controller = new TreeStockController();
    }
    
    @Test
    public void testCadastrarCliente() throws DadoDuplicadoException {
        Assert.assertFalse(controller.clienteCadastrado("55555555555"));
        controller.cadastrarCliente("João da Silva", "55555555555", "Feira de Santana-BA");
        Assert.assertTrue(controller.clienteCadastrado("55555555555"));
    }
    
    @Test (expected = DadoDuplicadoException.class)
    public void testCadastrarClienteDuplicado() throws DadoDuplicadoException {
        controller.cadastrarCliente("João da Silva", "55555555555", "Feira de Santana-BA");
        controller.cadastrarCliente("João da Silva", "55555555555", "Feira de Santana-BA");
    }
    
    @Test
    public void testBuscarCliente() throws DadoDuplicadoException, DadoNaoEncontradoException{
        controller.cadastrarCliente("João da Silva", "55555555555", "Feira de Santana-BA");
        Cliente cliente = controller.buscarCliente("55555555555");
        Assert.assertEquals("João da Silva", cliente.getNome());
    }
    
    @Test (expected = DadoNaoEncontradoException.class)
    public void testBuscarClienteNaoCadastrado() throws DadoNaoEncontradoException{
        controller.buscarCliente("66666666666");
    }
    
    @Test
    public void testRemoverCliente() throws DadoDuplicadoException, DadoNaoEncontradoException{
        controller.cadastrarCliente("João da Silva", "55555555555", "Feira de Santana-BA");
        Assert.assertTrue(controller.clienteCadastrado("55555555555"));
        controller.removerCliente("55555555555");
        Assert.assertFalse(controller.clienteCadastrado("55555555555"));
    }
    
    @Test (expected = DadoNaoEncontradoException.class)
    public void testRemoverClienteNaoCadastrado() throws DadoNaoEncontradoException{
        controller.removerCliente("55555555555");
    }
    
    @Test
    public void testCasdastrarAcao() throws DadoDuplicadoException{
        Assert.assertFalse(controller.acaoCadastrada("FACE"));
        controller.cadastrarAcao("FACE", "facebook", 11.50, TipoAcao.ON);
        Assert.assertTrue(controller.acaoCadastrada("FACE"));
    }
    
    @Test (expected = DadoDuplicadoException.class)
    public void testCadastrarAcaoRepetida() throws DadoDuplicadoException{
        controller.cadastrarAcao("FACE", "facebook", 11.50, TipoAcao.ON);
        controller.cadastrarAcao("FACE", "facebook", 11.50, TipoAcao.ON);
    }
    
    @Test
    public void testRemoverAcao() throws DadoDuplicadoException, DadoNaoEncontradoException{
        controller.cadastrarAcao("FACE", "facebook", 11.50, TipoAcao.ON);
        Assert.assertTrue(controller.acaoCadastrada("FACE"));
        controller.removerAcao("FACE");
        Assert.assertFalse(controller.acaoCadastrada("FACE"));
    }
    
    @Test (expected = DadoNaoEncontradoException.class)
    public void testRemoverAcaoNaoCadastrada() throws DadoNaoEncontradoException{
        controller.removerAcao("FACE");
    }
    
    @Test
    public void testAtualizarCotacoes() throws FileNotFoundException{
        //Scanner arq = new Scanner(new File("CotacosTest.txt"));
        //PrintStream arquivo = PrintStream("Cotacoes.txt");
    }
}
