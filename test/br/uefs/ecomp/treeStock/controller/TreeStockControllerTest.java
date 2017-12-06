package br.uefs.ecomp.treeStock.controller;

import br.uefs.ecomp.treeStock.model.Cliente;
import br.uefs.ecomp.treeStock.model.TipoAcao;
import br.uefs.ecomp.treeStock.exceptions.AcaoEmCarteiraException;
import br.uefs.ecomp.treeStock.exceptions.AcaoNaoEncontradaException;
import br.uefs.ecomp.treeStock.exceptions.ClienteNaoEncontradoException;
import br.uefs.ecomp.treeStock.exceptions.DadoDuplicadoException;
import br.uefs.ecomp.treeStock.exceptions.DadoNaoEncontradoException;
import br.uefs.ecomp.treeStock.exceptions.NumeroClientesInsuficienteException;
import br.uefs.ecomp.treeStock.model.Carteira;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class TreeStockControllerTest {
    
    private static final double EPSILON = 0.001;
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
    public void testRemoverAcao() throws DadoDuplicadoException, DadoNaoEncontradoException, AcaoEmCarteiraException{
        controller.cadastrarAcao("FACE", "facebook", 11.50, TipoAcao.ON);
        Assert.assertTrue(controller.acaoCadastrada("FACE"));
        controller.removerAcao("FACE");
        Assert.assertFalse(controller.acaoCadastrada("FACE"));
    }
    
    @Test (expected = DadoNaoEncontradoException.class)
    public void testRemoverAcaoNaoCadastrada() throws DadoNaoEncontradoException, AcaoEmCarteiraException{
        controller.removerAcao("FACE");
    }
    
    @Test (expected = AcaoEmCarteiraException.class)
    public void testRemoverAcaoPertencenteAUmaCarteira() throws DadoDuplicadoException, DadoNaoEncontradoException, AcaoNaoEncontradaException, AcaoEmCarteiraException{
        controller.cadastrarCliente("João da Silva", "55555555555", "Feira de Santana-BA");
        controller.cadastrarAcao("VR14", "vr14.com.br", 21.05, TipoAcao.ON);
        controller.incluirAcaoCliente("55555555555", "VR14", 5);
        controller.removerAcao("VR14");
    }
    
    @Test
    public void testAtualizarCotacoes() throws DadoDuplicadoException, FileNotFoundException, DadoNaoEncontradoException {
        controller.cadastrarAcao("VR14", "vr14.com.br", 21.05, TipoAcao.ON);
        Assert.assertEquals(21.05, controller.getValorAcao("VR14"), EPSILON);
        controller.atualizarCotacoes("ArquivosTesteCotacoes/CotacoesHistoricas1.txt");
        Assert.assertEquals(32.81, controller.getValorAcao("VR14"), EPSILON);
    }
    
    @Test
    public void testIncluirAcaoEmCarteira() throws DadoDuplicadoException, DadoNaoEncontradoException, AcaoNaoEncontradaException, AcaoEmCarteiraException{
        controller.cadastrarCliente("João da Silva", "55555555555", "Feira de Santana-BA");
        controller.cadastrarAcao("VR14", "vr14.com.br", 21.05, TipoAcao.ON);
        Assert.assertEquals( 0, controller.getQuantidadeAcaoCliente("55555555555", "VR14"));
        controller.incluirAcaoCliente("55555555555", "VR14", 10);
        Assert.assertEquals(10, controller.getQuantidadeAcaoCliente("55555555555", "VR14"));
    }
    
    @Test (expected = AcaoNaoEncontradaException.class)
    public void testIncluirAcaoNaoExistenteEmCarteira() throws DadoDuplicadoException, DadoNaoEncontradoException, AcaoNaoEncontradaException, AcaoEmCarteiraException{
        controller.cadastrarCliente("João da Silva", "55555555555", "Feira de Santana-BA");
        controller.incluirAcaoCliente("55555555555", "VR14", 10);
    }
    
    @Test (expected = DadoNaoEncontradoException.class)
    public void testIncluirAcaoEmCarteiraInexistente() throws DadoDuplicadoException, DadoNaoEncontradoException, AcaoNaoEncontradaException, AcaoEmCarteiraException{
        controller.cadastrarAcao("VR14", "vr14.com.br", 21.05, TipoAcao.ON);
        controller.incluirAcaoCliente("55555555555", "VR14", 10);
    }
    
    @Test (expected = AcaoEmCarteiraException.class)
    public void testIncluirAcaoJaExistenteNaCarteira() throws DadoDuplicadoException, DadoNaoEncontradoException, AcaoNaoEncontradaException, AcaoEmCarteiraException{
        controller.cadastrarCliente("João da Silva", "55555555555", "Feira de Santana-BA");
        controller.cadastrarAcao("VR14", "vr14.com.br", 21.05, TipoAcao.ON);
        controller.incluirAcaoCliente("55555555555", "VR14", 10);
        controller.incluirAcaoCliente("55555555555", "VR14", 5);
    }
    
    @Test
    public void testRemoverAcaoEmCarteira() throws DadoDuplicadoException, DadoNaoEncontradoException, AcaoNaoEncontradaException, AcaoEmCarteiraException{
        controller.cadastrarCliente("João da Silva", "55555555555", "Feira de Santana-BA");
        controller.cadastrarAcao("VR14", "vr14.com.br", 21.05, TipoAcao.ON);
        controller.incluirAcaoCliente("55555555555", "VR14", 10);
        Assert.assertEquals(10, controller.getQuantidadeAcaoCliente("55555555555", "VR14"));
        controller.removerAcaoCliente("55555555555", "VR14");
        Assert.assertEquals( 0, controller.getQuantidadeAcaoCliente("55555555555", "VR14"));
    }
    
    @Test (expected = AcaoNaoEncontradaException.class)
    public void testRemoverAcaoNaoExistenteEmCarteira() throws DadoDuplicadoException, DadoNaoEncontradoException, AcaoNaoEncontradaException{
        controller.cadastrarCliente("João da Silva", "55555555555", "Feira de Santana-BA");
        controller.removerAcaoCliente("55555555555", "VR14");
    }
    
    @Test (expected = DadoNaoEncontradoException.class)
    public void testRemoverAcaoEmCarteiraInexistente() throws DadoDuplicadoException, DadoNaoEncontradoException, AcaoNaoEncontradaException{
        controller.cadastrarAcao("VR14", "vr14.com.br", 21.05, TipoAcao.ON);
        controller.removerAcaoCliente("55555555555", "VR14");
    }
    
    @Test
    public void testAlterarAcaoEmCarteira() throws DadoDuplicadoException, DadoNaoEncontradoException, AcaoNaoEncontradaException, ClienteNaoEncontradoException, AcaoEmCarteiraException{
        controller.cadastrarCliente("João da Silva", "55555555555", "Feira de Santana-BA");
        controller.cadastrarAcao("VR14", "vr14.com.br", 21.05, TipoAcao.ON);
        controller.incluirAcaoCliente("55555555555", "VR14", 10);
        Assert.assertEquals(10, controller.getQuantidadeAcaoCliente("55555555555", "VR14"));
        controller.setQuantidadeAcaoCliente("55555555555", "VR14", 20);
        Assert.assertEquals( 20, controller.getQuantidadeAcaoCliente("55555555555", "VR14"));
    }
    
    
    @Test (expected = AcaoNaoEncontradaException.class)
    public void testAlterarAcaoNaoExistenteNaCarteira() throws DadoDuplicadoException, DadoNaoEncontradoException, AcaoNaoEncontradaException, ClienteNaoEncontradoException{
        controller.cadastrarCliente("João da Silva", "55555555555", "Feira de Santana-BA");
        controller.setQuantidadeAcaoCliente("55555555555", "VR14", 20);
    }
    
    @Test (expected = ClienteNaoEncontradoException.class)
    public void testAlterarAcaoEmCarteiraInexistente() throws DadoDuplicadoException, DadoNaoEncontradoException, AcaoNaoEncontradaException, ClienteNaoEncontradoException{
        controller.cadastrarAcao("VR14", "vr14.com.br", 21.05, TipoAcao.ON);
        controller.setQuantidadeAcaoCliente("55555555555", "VR14", 20);
    }
    
    @Test (expected = DadoNaoEncontradoException.class)
    public void testAlterarAcaoNaoEncontradaNaCarteira() throws DadoDuplicadoException, DadoNaoEncontradoException, AcaoNaoEncontradaException, ClienteNaoEncontradoException{
        controller.cadastrarCliente("João da Silva", "55555555555", "Feira de Santana-BA");
        controller.cadastrarAcao("VR14", "vr14.com.br", 21.05, TipoAcao.ON);
        controller.setQuantidadeAcaoCliente("55555555555", "VR14", 20);
    }
    
    @Test
    public void testNomeArquivoSistema() throws IOException{
        Date dataAtual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy HH.mm");
        controller.gerarArquivoSistema();
        String nomeArq = "systemSave (" + formato.format(dataAtual) + ").data";
        Assert.assertEquals(nomeArq, controller.nomeArquivoSistema());
        File arq = new File("Saves/", nomeArq);
        arq.delete();
    }
    
    @Test
    public void testIteratorClientes() throws DadoDuplicadoException{
        controller.cadastrarCliente("João da Nica", "55555555555", "Sabe onde eu tô?");
        controller.cadastrarCliente("Zé Ninguém", "33333333333", "Pintadas-BA");
        controller.cadastrarCliente("Irineu, você não, nem eu", "88888888888", "Vitória-ES");
        controller.cadastrarCliente("Rolezera", "77777777777", "Parque Ibiapoera, São Paulo-SP");
        controller.cadastrarCliente("Galo Cego", "11111111111", "Maringá-PR");
        Iterator it = controller.iterator();
        Assert.assertEquals("Galo Cego", ((Cliente) it.next()).getNome());
        Assert.assertEquals("Zé Ninguém", ((Cliente) it.next()).getNome());
        Assert.assertEquals("João da Nica", ((Cliente) it.next()).getNome());
        Assert.assertEquals("Rolezera", ((Cliente) it.next()).getNome());
        Assert.assertEquals("Irineu, você não, nem eu", ((Cliente) it.next()).getNome());
    }
    
    @Test (expected = ClienteNaoEncontradoException.class)
    public void testMelhoresClientesSemClientesCadastrados() throws ClienteNaoEncontradoException, NumeroClientesInsuficienteException{
        controller.melhoresClientes(10);
    }
    
    @Test (expected = NumeroClientesInsuficienteException.class)
    public void testMelhoresClientesNumeroClientesInsuficiente() throws ClienteNaoEncontradoException, DadoDuplicadoException, NumeroClientesInsuficienteException{
        controller.cadastrarCliente("João da Nica", "55555555555", "Sabe onde eu tô?");
        controller.cadastrarCliente("Zé Ninguém", "33333333333", "Pintadas-BA");
        controller.cadastrarCliente("Irineu, você não, nem eu", "88888888888", "Vitória-ES");
        controller.melhoresClientes(10);
    }
    
    @Test 
    public void testMelhoresClientes() throws ClienteNaoEncontradoException, DadoDuplicadoException, NumeroClientesInsuficienteException, AcaoNaoEncontradaException, DadoNaoEncontradoException, AcaoEmCarteiraException{
        controller.cadastrarCliente("João da Nica", "55555555555", "Sabe onde eu tô?");
        controller.cadastrarCliente("Zé Ninguém", "33333333333", "Pintadas-BA");
        controller.cadastrarCliente("Irineu, você não, nem eu", "88888888888", "Vitória-ES");
        controller.cadastrarCliente("Rolezera", "77777777777", "Parque Ibiapoera, São Paulo-SP");
        controller.cadastrarCliente("Galo Cego", "11111111111", "Maringá-PR");
        controller.cadastrarAcao("FACE", "facebook", 11.50, TipoAcao.ON);
        controller.cadastrarAcao("VR14", "vr14.com.br", 21.05, TipoAcao.ON);
        controller.incluirAcaoCliente("55555555555", "VR14", 10);
        controller.incluirAcaoCliente("55555555555", "FACE", 5);
        controller.incluirAcaoCliente("33333333333", "VR14", 9);
        controller.incluirAcaoCliente("33333333333", "FACE", 11);
        controller.incluirAcaoCliente("88888888888", "VR14", 15);
        controller.incluirAcaoCliente("88888888888", "FACE", 25);
        controller.incluirAcaoCliente("77777777777", "VR14", 25);
        controller.incluirAcaoCliente("11111111111", "VR14", 5);
        Iterator it = controller.melhoresClientes(5);
        Assert.assertEquals("Irineu, você não, nem eu", ((Carteira) it.next()).getNomeDonoConta());
        Assert.assertEquals("Rolezera", ((Carteira) it.next()).getNomeDonoConta());
        Assert.assertEquals("Zé Ninguém", ((Carteira) it.next()).getNomeDonoConta());
        Assert.assertEquals("João da Nica", ((Carteira) it.next()).getNomeDonoConta());
        Assert.assertEquals("Galo Cego", ((Carteira) it.next()).getNomeDonoConta());
    }
    
    @Test
    public void testSaldoCliente() throws DadoDuplicadoException, DadoNaoEncontradoException, AcaoNaoEncontradaException, ClienteNaoEncontradoException, FileNotFoundException, AcaoEmCarteiraException{
        controller.cadastrarCliente("João da Silva", "55555555555", "Feira de Santana-BA");
        controller.cadastrarCliente("Maria", "44444444444", "Araraquara-SP");
        controller.cadastrarAcao("FACE", "facebook", 11.50, TipoAcao.PN);
        controller.cadastrarAcao("VR14", "vr14.com.br", 21.00, TipoAcao.ON);
        controller.incluirAcaoCliente("55555555555", "FACE", 5);
        Assert.assertEquals(11.50 * 5 * 100, controller.getValorCarteiraCliente("55555555555"), EPSILON);
        controller.atualizarCotacoes("ArquivosTesteCotacoes/CotacoesHistoricas1.txt");
        Assert.assertEquals(1 * 5 * 100, controller.getCarteiraCliente("55555555555").getSaldo(), EPSILON);
        Assert.assertEquals((30.56 * 5 * 100) + (1 * 5 * 100), controller.getValorCarteiraCliente("55555555555"), EPSILON);
        controller.incluirAcaoCliente("44444444444", "FACE", 2);
        controller.incluirAcaoCliente("55555555555", "VR14", 4);
        Assert.assertEquals(30.56 * 2 * 100, controller.getValorCarteiraCliente("44444444444"), EPSILON);
        Assert.assertEquals((30.56 * 5 * 100) + (1 * 5 * 100) + (32.81 * 4 * 100), controller.getValorCarteiraCliente("55555555555"), EPSILON);
        controller.atualizarCotacoes("ArquivosTesteCotacoes/CotacoesHistoricas2.txt");
        Assert.assertEquals(1.7 * 5 * 100, controller.getCarteiraCliente("55555555555").getSaldo(), EPSILON);
        Assert.assertEquals(0.7 * 2 * 100, controller.getCarteiraCliente("44444444444").getSaldo(), EPSILON);
        Assert.assertEquals((31.96 * 2 * 100) + (0.7 * 2 * 100), controller.getValorCarteiraCliente("44444444444"), EPSILON);
        Assert.assertEquals((31.96 * 5 * 100) + (1.7 * 5 * 100) + (35.72 * 4 * 100), controller.getValorCarteiraCliente("55555555555"), EPSILON);
    }
}
