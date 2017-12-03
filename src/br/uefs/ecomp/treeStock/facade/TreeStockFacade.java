package br.uefs.ecomp.treeStock.facade;

import br.uefs.ecomp.treeStock.model.Acao;
import br.uefs.ecomp.treeStock.model.Cliente;
import br.uefs.ecomp.treeStock.exceptions.AcaoNaoEncontradaException;
import br.uefs.ecomp.treeStock.exceptions.ClienteNaoEncontradoException;
import br.uefs.ecomp.treeStock.exceptions.DadoDuplicadoException;
import br.uefs.ecomp.treeStock.controller.TreeStockController;
import br.uefs.ecomp.treeStock.model.Carteira;
import br.uefs.ecomp.treeStock.model.TipoAcao;
import br.uefs.ecomp.treeStock.exceptions.DadoNaoEncontradoException;
import br.uefs.ecomp.treeStock.exceptions.NumeroClientesInsuficienteException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;

public class TreeStockFacade {

    private TreeStockController controller;

    public TreeStockFacade() {
        controller = new TreeStockController();
    }

    public boolean temArquivoSistema(){
        return controller.temArquivoSistema();
    }
    
    public String nomeArquivoSistema(){
        return controller.nomeArquivoSistema();
    }
    
    public void carregarArquivoSistema() throws FileNotFoundException, IOException, ClassNotFoundException{
        String nomeArquivo = nomeArquivoSistema();
        try (FileInputStream arquivoLeitura = new FileInputStream("Saves/" + nomeArquivo); 
        ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura)) {
            
            controller = (TreeStockController) objLeitura.readObject();
            
        }
    }
    
    public Cliente cadastrarCliente(String nome, String cpf, String endereco) throws DadoDuplicadoException {
        return controller.cadastrarCliente(nome, cpf, endereco);
    }

    public Cliente removerCliente(String cpf) throws ClienteNaoEncontradoException {
        try{
            Cliente cliente = controller.removerCliente(cpf);
            return cliente;
        }catch(DadoNaoEncontradoException e){
            throw new ClienteNaoEncontradoException("\n    Remoção de cliente mal sucedida,\n     Cliente não encontrado!");
        }
    }

    public Cliente buscarCliente(String cpf) throws ClienteNaoEncontradoException {
        try{
            Cliente cliente = controller.buscarCliente(cpf);
            return cliente;
        }catch(DadoNaoEncontradoException e){
            throw new ClienteNaoEncontradoException("\n    Busca de cliente mal sucedida,\n     Cliente não encontrado!");
        }
    }
    
    public boolean clienteCadastrado(String cpf) {
        return controller.clienteCadastrado(cpf);
    }

    public Acao cadastrarAcao(String siglaAcao, String nomeAcao, double valorInicial, TipoAcao tipoAcao) throws DadoDuplicadoException {
        return controller.cadastrarAcao(siglaAcao, nomeAcao, valorInicial, tipoAcao);
    }

    public Acao removerAcao(String siglaAcao) throws AcaoNaoEncontradaException {
        try{
            Acao acao = controller.removerAcao(siglaAcao);
            return acao;
        } catch(DadoNaoEncontradoException e){
            throw new AcaoNaoEncontradaException("\n    Remoção de acao mal sucedida,\n     Acao não encontrado!");
        }
    }
    
    public void atualizarCotacoes(String nomeArq) throws FileNotFoundException{
        controller.atualizarCotacoes(nomeArq);
    }

    public boolean acaoCadastrada(String siglaAcao) {
        return controller.acaoCadastrada(siglaAcao);
    }

    public void setValorAcao(String siglaAcao, double novoValor) throws AcaoNaoEncontradaException {
        controller.setValorAcao(siglaAcao, novoValor);
    }
    
    public double getValorAcao(String siglaAcao) throws AcaoNaoEncontradaException {
        return controller.getValorAcao(siglaAcao);
    }

    /**
     * Retorna os clientes ordenados pelo nome.
     *
     * @return os clientes ordenados pelo nome.
     */
    public Iterator iterator() {
        return controller.iterator();
    }

    public void incluirAcaoCliente(String cpf, String siglaAcao, int quantidade) throws ClienteNaoEncontradoException, AcaoNaoEncontradaException{
        try {
            controller.incluirAcaoCliente(cpf, siglaAcao, quantidade);
        } catch (DadoNaoEncontradoException ex) {
            throw new ClienteNaoEncontradoException("\n    Inserção de lote mal sucedida, \n    Cliente não encontrado");
        }
    }

    public void removerAcaoCliente(String cpf, String siglaAcao) throws ClienteNaoEncontradoException, AcaoNaoEncontradaException {
        try {
            controller.removerAcaoCliente(cpf, siglaAcao);
        } catch (DadoNaoEncontradoException e) {
            throw new ClienteNaoEncontradoException("\n    Remoção de lote mal sucedida, \n    Cliente não encontrado");
        }
    }
    
    public int getQuantidadeAcaoCliente(String cpf, String siglaAcao) throws ClienteNaoEncontradoException, AcaoNaoEncontradaException {
        try {
            return  controller.getQuantidadeAcaoCliente(cpf, siglaAcao);
        } catch (DadoNaoEncontradoException ex) {
            throw new ClienteNaoEncontradoException();
        }
    }
    
    public void setQuantidadeAcaoCliente(String cpfCliente, String siglaAcao, int quantidade) throws ClienteNaoEncontradoException, AcaoNaoEncontradaException {
        try {
            controller.setQuantidadeAcaoCliente(cpfCliente, siglaAcao, quantidade);
        } catch (DadoNaoEncontradoException e) {
            incluirAcaoCliente(cpfCliente, siglaAcao, quantidade);
        }
    }

    public Carteira getCarteiraCliente(String cpfCliente) throws ClienteNaoEncontradoException {
        return controller.getCarteiraCliente(cpfCliente);
    }

    public double getValorCarteiraCliente(String cpfCliente) throws ClienteNaoEncontradoException, AcaoNaoEncontradaException {
        return controller.getValorCarteiraCliente(cpfCliente);
    }

    /**
     * Retorna os k melhores clientes em ordem descrescente de valor da
     * carteira.
     *
     * @param k os k melhores clientes
     * @return os k melhores clientes em ordem descrescente de valor da
     * carteira.
     */
    public Iterator melhoresClientes(int k) throws ClienteNaoEncontradoException, NumeroClientesInsuficienteException {
        return controller.melhoresClientes(k);
    }

    public double valorCarteira(String cpf) {
        return controller.valorCarteira(cpf);
    }

    public void gerarArquivoSistema() throws IOException {
        controller.gerarArquivoSistema();
    }
}