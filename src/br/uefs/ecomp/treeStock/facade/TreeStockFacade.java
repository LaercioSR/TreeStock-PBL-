package br.uefs.ecomp.treeStock.facade;

import br.uefs.ecomp.treeStock.model.Acao;
import br.uefs.ecomp.treeStock.model.Cliente;
import br.uefs.ecomp.treeStock.exceptions.AcaoNaoEncontradaException;
import br.uefs.ecomp.treeStock.exceptions.ClienteNaoEncontradoException;
import br.uefs.ecomp.treeStock.exceptions.DadoDuplicadoException;
import br.uefs.ecomp.treeStock.controller.TreeStockController;
import br.uefs.ecomp.treeStock.exceptions.AcaoEmCarteiraException;
import br.uefs.ecomp.treeStock.model.Carteira;
import br.uefs.ecomp.treeStock.model.TipoAcao;
import br.uefs.ecomp.treeStock.exceptions.DadoNaoEncontradoException;
import br.uefs.ecomp.treeStock.exceptions.NumeroClientesInsuficienteException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;

/**
 * Classe {@code TreeStockFacade} responsável por intermediar a {@link br.uefs.ecomp.treeStock.view.TreeStockView view}
 * e o {@link br.uefs.ecomp.treeStock.controller.TreeStockController controller}, em que cada método chama o método equivalente
 * do controller
 */
public class TreeStockFacade {

    private TreeStockController controller;

    /**
     * Constroi um Façade e instancia um {@link br.uefs.ecomp.treeStock.controller.TreeStockController controller}
     */
    public TreeStockFacade() {
        controller = new TreeStockController();
    }

    /**
     * Método retorna a existencia de um arquivo de salvamento do sistema
     * @return Se existe um arquivo de salvamento do sistema retorna True
     */
    public boolean temArquivoSistema(){
        return controller.temArquivoSistema();
    }
    
    /**
     * Método retorna o nome do mais novo arquivo de salvamento do sistema
     * @return Nome do arquivo de salvamento do sistema
     */
    public String nomeArquivoSistema(){
        return controller.nomeArquivoSistema();
    }
    
    /**
     * Método carrega um arquivo de salvamento do sistema
     * @throws FileNotFoundException Se o arquivo não foi encontrado
     * @throws IOException Se ocorreu algum problema no carregamento do arquivo
     * @throws ClassNotFoundException Se no arquivo contém uma classe não encontrada no software
     */
    public void carregarArquivoSistema() throws FileNotFoundException, IOException, ClassNotFoundException{
        String nomeArquivo = nomeArquivoSistema();
        try (FileInputStream arquivoLeitura = new FileInputStream("Saves/" + nomeArquivo); 
        ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura)) {
            
            controller = (TreeStockController) objLeitura.readObject();
            
        }
    }
    
    /**
     * Método cadastra um novo cliente
     * @param nome Nome do cliente
     * @param cpf CPF do cliente
     * @param endereco Endereço do cliente
     * @return Cliente cadastrado 
     * @throws DadoDuplicadoException Se já exista um cliente cadastrado com o CPF fornecido
     */
    public Cliente cadastrarCliente(String nome, String cpf, String endereco) throws DadoDuplicadoException {
        return controller.cadastrarCliente(nome, cpf, endereco);
    }

    /**
     * Método remove cliente com o CPF fornecido
     * @param cpf CPF do cliente a ser removido
     * @return Cliente removido
     * @throws ClienteNaoEncontradoException Se não for encontrado um cliente cadastrado com CPF fornecido
     */
    public Cliente removerCliente(String cpf) throws ClienteNaoEncontradoException {
        try{
            Cliente cliente = controller.removerCliente(cpf);
            return cliente;
        }catch(DadoNaoEncontradoException e){
            throw new ClienteNaoEncontradoException("\n    Remoção de cliente mal sucedida,\n     Cliente não encontrado!");
        }
    }

    /**
     * Método busca cliente com o CPF fornecido
     * @param cpf CPF do cliente a ser buscado
     * @return Cliente buscado
     * @throws ClienteNaoEncontradoException Se não for encontrado um cliente cadastrado com CPF fornecido
     */
    public Cliente buscarCliente(String cpf) throws ClienteNaoEncontradoException {
        try{
            Cliente cliente = controller.buscarCliente(cpf);
            return cliente;
        }catch(DadoNaoEncontradoException e){
            throw new ClienteNaoEncontradoException("\n    Busca de cliente mal sucedida,\n     Cliente não encontrado!");
        }
    }
    
    /**
     * Método verifica se existe um cliente cadastrado com CPF fornecido
     * @param cpf CPF do cliente
     * @return True se o cliente está cadastrado
     */
    public boolean clienteCadastrado(String cpf) {
        return controller.clienteCadastrado(cpf);
    }

    /**
     * Método cadastra uma ação no sistema
     * @param siglaAcao Sigla da ação
     * @param nomeAcao Nome da empresa referente à ação
     * @param valorInicial Valor inicial da cotação da ação
     * @param tipoAcao Tipo da ação
     * @return Ação cadastrada
     * @throws DadoDuplicadoException Se existe ação cadastrada com a sigla passada
     */
    public Acao cadastrarAcao(String siglaAcao, String nomeAcao, double valorInicial, TipoAcao tipoAcao) throws DadoDuplicadoException {
        return controller.cadastrarAcao(siglaAcao, nomeAcao, valorInicial, tipoAcao);
    }

    /**
     * Método remove do sistema a ação com a sigla passada
     * @param siglaAcao Sigla da ação
     * @return Ação removida
     * @throws AcaoNaoEncontradaException Se não foi encontrada uma ação com a sigla fornecida
     * @throws AcaoEmCarteiraException Se a ação estiver em uma carteira
     */
    public Acao removerAcao(String siglaAcao) throws AcaoNaoEncontradaException, AcaoEmCarteiraException {
        try{
            Acao acao = controller.removerAcao(siglaAcao);
            return acao;
        } catch(DadoNaoEncontradoException e){
            throw new AcaoNaoEncontradaException("\n    Remoção de acao mal sucedida,\n     Acao não encontrado!");
        }
    }
    
    /**
     * Método atualiza as cotações das ações
     * @param nomeArq Nome do arquivo com as cotações
     * @throws FileNotFoundException Se o arquivo não foi encontrado
     */
    public void atualizarCotacoes(String nomeArq) throws FileNotFoundException{
        controller.atualizarCotacoes(nomeArq);
    }

    /**
     * Método verifica se existe ação cadastrada com a sigla passada
     * @param siglaAcao Sigla da ação
     * @return True se existe ação cadastrada com a sigla fornecida
     */
    public boolean acaoCadastrada(String siglaAcao) {
        return controller.acaoCadastrada(siglaAcao);
    }

    /**
     * Método modifica o valor da cotação de uma ação
     * @param siglaAcao Sigla da ação 
     * @param novoValor Novo valor de cotaçã
     * @throws AcaoNaoEncontradaException Se não foi encontrada uma ação com a sigla fornecida
     */
    public void setValorAcao(String siglaAcao, double novoValor) throws AcaoNaoEncontradaException {
        try {
            controller.setValorAcao(siglaAcao, novoValor);
        } catch (DadoNaoEncontradoException e) {
            throw new AcaoNaoEncontradaException();
        }
    }
    
    /**
     * Método retorna o valor de cotação de uma ação
     * @param siglaAcao Sigla da ação 
     * @return Valor da cotação
     * @throws AcaoNaoEncontradaException Se não foi encontrada uma ação com a sigla fornecida
     */
    public double getValorAcao(String siglaAcao) throws AcaoNaoEncontradaException {
        try{
            return controller.getValorAcao(siglaAcao);
        } catch (DadoNaoEncontradoException e) {
            throw new AcaoNaoEncontradaException();
        }
    }

    /**
     * Retorna os clientes ordenados pelo nome
     * @return <b>Iterator</b> dos clientes ordenados
     */
    public Iterator iterator() {
        return controller.iterator();
    }

    /**
     * Método inclui lotes (100 ações) na carteira de um cliente
     * @param cpf CPF do cliente
     * @param siglaAcao Sigla da Ação
     * @param quantidade Quantidade lotes
     * @throws ClienteNaoEncontradoException Se não for encontrado um cliente cadastrado com CPF fornecido
     * @throws AcaoNaoEncontradaException Se não foi encontrada uma ação com a sigla fornecida
     */
    public void incluirAcaoCliente(String cpf, String siglaAcao, int quantidade) throws ClienteNaoEncontradoException, AcaoNaoEncontradaException {
        try {
            controller.incluirAcaoCliente(cpf, siglaAcao, quantidade);
        } catch (DadoNaoEncontradoException ex) {
            throw new ClienteNaoEncontradoException("\n    Inserção de lote mal sucedida, \n    Cliente não encontrado");
        } catch (AcaoEmCarteiraException ex) {
            try {
                controller.setQuantidadeAcaoCliente(cpf, siglaAcao, quantidade);
            } catch (DadoNaoEncontradoException ex1) { }
        }
    }

    /**
     * Método remove lotes (100 ações) da carteira de um cliente
     * @param cpf CPF do cliente
     * @param siglaAcao Sigla da ação
     * @throws ClienteNaoEncontradoException Se não for encontrado um cliente cadastrado com CPF fornecido
     * @throws AcaoNaoEncontradaException Se não foi encontrada uma ação com a sigla fornecida
     */
    public void removerAcaoCliente(String cpf, String siglaAcao) throws ClienteNaoEncontradoException, AcaoNaoEncontradaException {
        try {
            controller.removerAcaoCliente(cpf, siglaAcao);
        } catch (DadoNaoEncontradoException e) {
            throw new ClienteNaoEncontradoException("\n    Remoção de lote mal sucedida, \n    Cliente não encontrado");
        }
    }
    
    /**
     * Método retorna a quantidade de lotes de uma certa ação que um cliente possui
     * @param cpf CPF do cliente
     * @param siglaAcao Sigla da ação
     * @return Quantidade de lotes
     * @throws ClienteNaoEncontradoException Se não for encontrado um cliente cadastrado com CPF fornecido
     */
    public int getQuantidadeAcaoCliente(String cpf, String siglaAcao) throws ClienteNaoEncontradoException {
        try {
            return  controller.getQuantidadeAcaoCliente(cpf, siglaAcao);
        } catch (DadoNaoEncontradoException ex) {
            throw new ClienteNaoEncontradoException();
        }
    }
    
    /**
     * Método modifica a quantidade de lotes de uma certa ação na carteira de um cliente
     * @param cpfCliente CPF do cliente
     * @param siglaAcao Sigla da ação
     * @param quantidade Nova quantidade de lotes
     * @throws ClienteNaoEncontradoException Se não for encontrado um cliente cadastrado com CPF fornecido
     * @throws AcaoNaoEncontradaException Se não foi encontrada uma ação com a sigla fornecida
     */
    public void setQuantidadeAcaoCliente(String cpfCliente, String siglaAcao, int quantidade) throws ClienteNaoEncontradoException, AcaoNaoEncontradaException {
        try {
            controller.setQuantidadeAcaoCliente(cpfCliente, siglaAcao, quantidade);
        } catch (DadoNaoEncontradoException e) {
            incluirAcaoCliente(cpfCliente, siglaAcao, quantidade);
        }
    }

    /**
     * Método retorna a {@link br.uefs.ecomp.treeStock.model.Carteira carteira} de um cliente
     * @param cpfCliente CPF do cliente
     * @return {@link br.uefs.ecomp.treeStock.model.Carteira Carteira} do cliente
     * @throws ClienteNaoEncontradoException Se não for encontrado um cliente cadastrado com CPF fornecido
     */
    public Carteira getCarteiraCliente(String cpfCliente) throws ClienteNaoEncontradoException {
        return controller.getCarteiraCliente(cpfCliente);
    }

    /**
     * Método retorna o montante da carteira de um cliente
     * @param cpfCliente CPF do cliente
     * @return Montante da carteira
     * @throws ClienteNaoEncontradoException Se não for encontrado um cliente cadastrado com CPF fornecido
     */
    public double getValorCarteiraCliente(String cpfCliente) throws ClienteNaoEncontradoException {
        return controller.getValorCarteiraCliente(cpfCliente);
    }

    /**
     * Retorna os k melhores clientes em ordem descrescente de valor da
     * carteira.
     * @param k Os k melhores clientes
     * @return Iterator - Iterador dos k melhores clientes em ordem descrescente de valor da
     * carteira.
     * @throws ClienteNaoEncontradoException Se não existir clientes cadastrados
     * @throws NumeroClientesInsuficienteException Se o número K passado for menor que o número de clientes cadastrados
     */
    public Iterator melhoresClientes(int k) throws ClienteNaoEncontradoException, NumeroClientesInsuficienteException {
        return controller.melhoresClientes(k);
    }

    /**
     * Método retorna o valor atualizado da carteira do cliente
     * @param cpf CPF do cliente
     * @return Valor da carteira
     */
    public double valorCarteira(String cpf) {
        return controller.valorCarteira(cpf);
    }

    /**
     * Método gera arquivo de salvamento do sistema
     * @throws IOException Caso aconteça algum erro na geração do arquivo
     */
    public void gerarArquivoSistema() throws IOException {
        controller.gerarArquivoSistema();
    }
}