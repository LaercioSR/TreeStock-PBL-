package br.uefs.ecomp.treeStock.facade;

import br.uefs.ecomp.treeStock.model.Acao;
import br.uefs.ecomp.treeStock.model.Carteira;
import br.uefs.ecomp.treeStock.model.Cliente;
import br.uefs.ecomp.treeStock.model.TipoAcao;
import br.uefs.ecomp.treeStock.model.exception.AcaoNaoEncontradaException;
import br.uefs.ecomp.treeStock.model.exception.ClienteNaoEncontradoException;
import br.uefs.ecomp.treeStock.util.DadoDuplicadoException;
import br.uefs.ecomp.treeStock.util.DadoNaoEncontradoException;
import java.util.Iterator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.Test;

import org.junit.Before;

public class TestesAceitacao {

    private static final double EPSILON = 0.001;
    private TreeStockFacade facade;
    private Cliente maria, joao, pedro, marcos, suzana, tarcizio, priscila;
    private Acao petr, elet, bbas, embr;

    @Before
    public void setUp() throws Exception {
        facade = new TreeStockFacade();

        tarcizio = facade.cadastrarCliente("Tarcizio Nery", "33333333333", "Rua Pedro Suzart, 3456");
        maria = facade.cadastrarCliente("Maria dos Santos", "44444444444", "Rua Drummond, 23, Centro");
        joao = facade.cadastrarCliente("João dos Santos", "77777777777", "Rua Pessoa, 12, Centro");
        pedro = facade.cadastrarCliente("Pedro da Silva", "11111111111", "Rua Andrade, 45, Cidade Nova");
        marcos = facade.cadastrarCliente("Marcos Oliveira", "88888888888", "Rua Quintana, 45, Santa Monica");
        suzana = facade.cadastrarCliente("Suzana Abreu Lima", "99999999999", "Rua da ladeira, 23");
        priscila = facade.cadastrarCliente("Priscila Costa e Silva", "22222222222", "Rua E, 27");

        petr = facade.cadastrarAcao("PETR4", "Petrobrás", 21.10, TipoAcao.ON);
        elet = facade.cadastrarAcao("ELET3", "Eletrobrás", 9.80, TipoAcao.PN);
        bbas = facade.cadastrarAcao("BBAS3", "Banco do Brasil", 29.50, TipoAcao.ON);
        embr = facade.cadastrarAcao("EMBR3", "Embraer", 22.60, TipoAcao.PN);
    }

    @Test
    public void testCadastrarCliente() throws DadoDuplicadoException {
        assertFalse(facade.clienteCadastrado("66666666666"));

        Cliente cliente = facade.cadastrarCliente("Fulano de tal", "66666666666", "Rua da hora, 23, Centro");
        assertEquals("Fulano de tal", cliente.getNome());
        assertEquals("Rua da hora, 23, Centro", cliente.getEndereco());

        assertTrue(facade.clienteCadastrado("66666666666"));
    }

    @Test(expected = DadoDuplicadoException.class)
    public void testCadastrarClienteRepetido() throws DadoDuplicadoException {
        //deve lançar exceção
        facade.cadastrarCliente("Marcos Oliveira", "88888888888", "Independente do endereço");
    }

    @Test
    public void testRemoverCliente() throws DadoDuplicadoException, ClienteNaoEncontradoException {
        assertTrue(facade.clienteCadastrado("33333333333"));

        Cliente cliente = facade.removerCliente("33333333333");
        assertEquals(cliente, tarcizio);

        assertFalse(facade.clienteCadastrado("33333333333"));
    }

    @Test(expected = ClienteNaoEncontradoException.class)
    public void testRemoverClienteInexistente() throws ClienteNaoEncontradoException {
        //deve lançar exceção
        facade.removerCliente("Cliente nao cadastrado");
    }

    @Test
    public void testCadastrarAcao() throws DadoDuplicadoException {
        assertFalse(facade.acaoCadastrada("FAKE4"));

        Acao acao = facade.cadastrarAcao("FAKE4", "Ação Fake", 1.00, TipoAcao.ON);

        assertEquals("FAKE4", acao.getSigla());
        assertEquals("Ação Fake", acao.getNome());
        assertEquals(1.00, acao.getValor(), EPSILON);

        assertTrue(facade.acaoCadastrada("FAKE4"));
    }

    @Test(expected = DadoDuplicadoException.class)
    public void testCadastrarAcaoRepetida() throws DadoDuplicadoException {
        //deve lançar exceção
        facade.cadastrarAcao("PETR4", "Nao importa o nome  ou o valor, apenas a sigla", 123, TipoAcao.ON);
    }

    @Test
    public void testRemoverAcao() throws DadoDuplicadoException, AcaoNaoEncontradaException {
        assertTrue(facade.acaoCadastrada("EMBR3"));

        Acao acao = facade.removerAcao("EMBR3");
        assertEquals("EMBR3", acao.getSigla());

        assertFalse(facade.acaoCadastrada("EMBR3"));
    }

    @Test(expected = AcaoNaoEncontradaException.class)
    public void testRemoverAcaoInexistente() throws AcaoNaoEncontradaException {
        //deve lançar exceção
        facade.removerAcao("UEFS3");
    }

    public void testValorAcao() throws AcaoNaoEncontradaException {
        assertEquals(21.10, facade.getValorAcao("PETR4"), EPSILON);

        facade.setValorAcao("PETR4", 40.80);

        assertEquals(40.80, facade.getValorAcao("PETR4"), EPSILON);
    }

    @Test
    public void testListarClientes() throws DadoDuplicadoException {
        Iterator clientes = facade.iterator();

        assertTrue(clientes.hasNext());
        assertEquals(pedro, clientes.next());
        
        assertTrue(clientes.hasNext());
        assertEquals(priscila, clientes.next());
        
        assertTrue(clientes.hasNext());
        assertEquals(tarcizio, clientes.next());
        
        assertTrue(clientes.hasNext());
        assertEquals(maria, clientes.next());
        
        assertTrue(clientes.hasNext());
        assertEquals(joao, clientes.next());

        assertTrue(clientes.hasNext());
        assertEquals(marcos, clientes.next());

        assertTrue(clientes.hasNext());
        assertEquals(suzana, clientes.next());

        assertFalse(clientes.hasNext());
    }

    @Test
    public void testIncluirAcaoCliente() throws ClienteNaoEncontradoException, AcaoNaoEncontradaException, DadoDuplicadoException {
        try {
            facade.getQuantidadeAcaoCliente("77777777777", "PETR4");
        } catch (AcaoNaoEncontradaException e) {
            //indica que a carteira do cliente não possui esta ação
            facade.incluirAcaoCliente("77777777777", "PETR4", 1000);
            assertEquals(1000, facade.getQuantidadeAcaoCliente("77777777777", "PETR4"));
        }
    }

    @Test(expected = ClienteNaoEncontradoException.class)
    public void testIncluirAcaoClienteNaoCadastradaNaBolsa() throws ClienteNaoEncontradoException, AcaoNaoEncontradaException, DadoDuplicadoException {
        facade.incluirAcaoCliente("Cliente não Cadastrado", "PERT2", 1000);
    }
    
    @Test(expected = AcaoNaoEncontradaException.class)
    public void testSetQuantidadeAcaoNaoCadastradaNaBolsa() throws ClienteNaoEncontradoException, AcaoNaoEncontradaException, DadoDuplicadoException {
        facade.setQuantidadeAcaoCliente("77777777777", "MICO2", 1000);        
    }

    @Test
    public void testExcluirAcaoCliente() throws ClienteNaoEncontradoException, AcaoNaoEncontradaException, DadoDuplicadoException {
        facade.setQuantidadeAcaoCliente("77777777777", "PETR4", 500);

        assertEquals(500, facade.getQuantidadeAcaoCliente("77777777777", "PETR4"));

        facade.removerAcaoCliente("77777777777", "PETR4");

        try {
            //deve lançar exceção: AcaoNaoEncontradaException
            facade.getQuantidadeAcaoCliente("77777777777", "PETR4");
            assertTrue(false);
        } catch (AcaoNaoEncontradaException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetCarteiraCliente() throws ClienteNaoEncontradoException, AcaoNaoEncontradaException, DadoNaoEncontradoException {
        /*
        IPares carteira = facade.getCarteiraCliente("Suzana Abreu Lima");
        assertTrue(carteira.estaVazia());

        double valor = facade.getValorCarteiraCliente("Suzana Abreu Lima");
        assertEquals(0.0, valor, EPSILON);

        facade.setQuantidadeAcaoCliente("Suzana Abreu Lima", "PETR4", 500);
        carteira = facade.getCarteiraCliente("Suzana Abreu Lima");
        assertFalse(carteira.estaVazia());
        assertEquals(500, carteira.get("PETR4"));
        assertEquals(500, facade.getQuantidadeAcaoCliente("Suzana Abreu Lima", "PETR4"));

        valor = facade.getValorCarteiraCliente("Suzana Abreu Lima");
        assertEquals(petr.getValor() * 500, valor, EPSILON);

        facade.setQuantidadeAcaoCliente("Suzana Abreu Lima", "EMBR3", 1000);
        facade.setQuantidadeAcaoCliente("Suzana Abreu Lima", "BBAS3", 100);
        carteira = facade.getCarteiraCliente("Suzana Abreu Lima");
        assertFalse(carteira.estaVazia());
        assertEquals(500, carteira.get("PETR4"));
        assertEquals(1000, carteira.get("EMBR3"));
        assertEquals(100, carteira.get("BBAS3"));

        valor = facade.getValorCarteiraCliente("Suzana Abreu Lima");
        assertEquals(petr.getValor() * 500 + embr.getValor() * 1000 + bbas.getValor() * 100, valor, EPSILON);

        facade.removerAcaoCliente("Suzana Abreu Lima", "PETR4");
        facade.removerAcaoCliente("Suzana Abreu Lima", "EMBR3");
        facade.removerAcaoCliente("Suzana Abreu Lima", "BBAS3");

        carteira = facade.getCarteiraCliente("Suzana Abreu Lima");
        assertTrue(carteira.estaVazia());
        */
    }

    @Test
    public void testMelhoresClientes() throws ClienteNaoEncontradoException, AcaoNaoEncontradaException, DadoNaoEncontradoException {
        Iterator melhores = facade.melhoresClientes(0);
        assertFalse(melhores.hasNext());

        facade.setQuantidadeAcaoCliente(maria.getCpf(), petr.getSigla(), 501);
        facade.setQuantidadeAcaoCliente(maria.getCpf(), elet.getSigla(), 1150);

        facade.setQuantidadeAcaoCliente(joao.getCpf(), petr.getSigla(), 1500);
        facade.setQuantidadeAcaoCliente(joao.getCpf(), elet.getSigla(), 100);

        facade.setQuantidadeAcaoCliente(pedro.getCpf(), petr.getSigla(), 2500);
        facade.setQuantidadeAcaoCliente(pedro.getCpf(), elet.getSigla(), 50);

        facade.setQuantidadeAcaoCliente(marcos.getCpf(), petr.getSigla(), 400);
        facade.setQuantidadeAcaoCliente(suzana.getCpf(), petr.getSigla(), 200);
        facade.setQuantidadeAcaoCliente(tarcizio.getCpf(), petr.getSigla(), 300);
        facade.setQuantidadeAcaoCliente(priscila.getCpf(), petr.getSigla(), 500);

        melhores = facade.melhoresClientes(1);
        assertTrue(melhores.hasNext());
        assertEquals(pedro.getCarteira(), melhores.next());
        assertFalse(melhores.hasNext());


        melhores = facade.melhoresClientes(3);
        assertTrue(melhores.hasNext());
        assertEquals(pedro.getCarteira(), melhores.next());
        assertEquals(joao.getCarteira(), melhores.next());
        assertEquals(maria.getCarteira(), melhores.next());
        assertFalse(melhores.hasNext());

        facade.setValorAcao(elet.getSigla(), 40.00);

        melhores = facade.melhoresClientes(1);
        assertTrue(melhores.hasNext());
        assertEquals(maria.getCarteira(), melhores.next());
        assertFalse(melhores.hasNext());

        facade.setQuantidadeAcaoCliente(suzana.getCpf(), bbas.getSigla(), 2000);

        melhores = facade.melhoresClientes(1);
        assertTrue(melhores.hasNext());
        assertEquals(suzana.getCarteira(), melhores.next());
        assertFalse(melhores.hasNext());
    }
}
