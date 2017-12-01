package br.uefs.ecomp.treeStock.view;

import br.uefs.ecomp.treeStock.facade.TreeStockFacade;
import br.uefs.ecomp.treeStock.model.Acao;
import br.uefs.ecomp.treeStock.model.Cliente;
import br.uefs.ecomp.treeStock.model.TipoAcao;
import br.uefs.ecomp.treeStock.model.exception.AcaoNaoEncontradaException;
import br.uefs.ecomp.treeStock.model.exception.ClienteNaoEncontradoException;
import br.uefs.ecomp.treeStock.util.DadoDuplicadoException;

public class Teste {
    
    public static void main(String[] args) throws DadoDuplicadoException, ClienteNaoEncontradoException, AcaoNaoEncontradaException{
        TreeStockFacade facade = new TreeStockFacade();
        Cliente maria, joao, pedro, marcos, suzana, tarcizio, priscila;
        Acao petr, elet, bbas, embr;
        
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
        
        facade.setQuantidadeAcaoCliente(maria.getCpf(), petr.getSigla(), 501);
        facade.setQuantidadeAcaoCliente(maria.getCpf(), elet.getSigla(), 1150);
        System.out.println(facade.valorCarteira(maria.getCpf()));

        facade.setQuantidadeAcaoCliente(joao.getCpf(), petr.getSigla(), 1500);
        facade.setQuantidadeAcaoCliente(joao.getCpf(), elet.getSigla(), 100);
        System.out.println(facade.valorCarteira(joao.getCpf()));

        facade.setQuantidadeAcaoCliente(pedro.getCpf(), petr.getSigla(), 2500);
        facade.setQuantidadeAcaoCliente(pedro.getCpf(), elet.getSigla(), 50);
        System.out.println(facade.valorCarteira(pedro.getCpf()));

        facade.setQuantidadeAcaoCliente(marcos.getCpf(), petr.getSigla(), 400);
        System.out.println(facade.valorCarteira(marcos.getCpf()));
        facade.setQuantidadeAcaoCliente(suzana.getCpf(), petr.getSigla(), 200);
        System.out.println(facade.valorCarteira(suzana.getCpf()));
        facade.setQuantidadeAcaoCliente(tarcizio.getCpf(), petr.getSigla(), 300);
        System.out.println(facade.valorCarteira(tarcizio.getCpf()));
        facade.setQuantidadeAcaoCliente(priscila.getCpf(), petr.getSigla(), 500);
        System.out.println(facade.valorCarteira(priscila.getCpf()));
    }
}
