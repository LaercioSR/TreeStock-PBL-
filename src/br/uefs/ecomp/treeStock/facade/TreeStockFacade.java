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
 * Classe {@code TreeStockFacade} respons�vel por intermediar a {@link br.uefs.ecomp.treeStock.view.TreeStockView view}
 * e o {@link br.uefs.ecomp.treeStock.controller.TreeStockController controller}, em que cada m�todo chama o m�todo equivalente
 * do controller
 */
public class TreeStockFacade {

    private TreeStockController controller;

    /**
     * Constroi um Fa�ade e instancia um {@link br.uefs.ecomp.treeStock.controller.TreeStockController controller}
     */
    public TreeStockFacade() {
        controller = new TreeStockController();
    }

    /**
     * M�todo retorna a existencia de um arquivo de salvamento do sistema
     * @return Se existe um arquivo de salvamento do sistema retorna True
     */
    public boolean temArquivoSistema(){
        return controller.temArquivoSistema();
    }
    
    /**
     * M�todo retorna o nome do mais novo arquivo de salvamento do sistema
     * @return Nome do arquivo de salvamento do sistema
     */
    public String nomeArquivoSistema(){
        return controller.nomeArquivoSistema();
    }
    
    /**
     * M�todo carrega um arquivo de salvamento do sistema
     * @throws FileNotFoundException Se o arquivo n�o foi encontrado
     * @throws IOException Se ocorreu algum problema no carregamento do arquivo
     * @throws ClassNotFoundException Se no arquivo cont�m uma classe n�o encontrada no software
     */
    public void carregarArquivoSistema() throws FileNotFoundException, IOException, ClassNotFoundException{
        String nomeArquivo = nomeArquivoSistema();
        try (FileInputStream arquivoLeitura = new FileInputStream("Saves/" + nomeArquivo); 
        ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura)) {
            
            controller = (TreeStockController) objLeitura.readObject();
            
        }
    }
    
    /**
     * M�todo cadastra um novo cliente
     * @param nome Nome do cliente
     * @param cpf CPF do cliente
     * @param endereco Endere�o do cliente
     * @return Cliente cadastrado 
     * @throws DadoDuplicadoException Se j� exista um cliente cadastrado com o CPF fornecido
     */
    public Cliente cadastrarCliente(String nome, String cpf, String endereco) throws DadoDuplicadoException {
        return controller.cadastrarCliente(nome, cpf, endereco);
    }

    /**
     * M�todo remove cliente com o CPF fornecido
     * @param cpf CPF do cliente a ser removido
     * @return Cliente removido
     * @throws ClienteNaoEncontradoException Se n�o for encontrado um cliente cadastrado com CPF fornecido
     */
    public Cliente removerCliente(String cpf) throws ClienteNaoEncontradoException {
        try{
            Cliente cliente = controller.removerCliente(cpf);
            return cliente;
        }catch(DadoNaoEncontradoException e){
            throw new ClienteNaoEncontradoException("\n    Remo��o de cliente mal sucedida,\n     Cliente n�o encontrado!");
        }
    }

    /**
     * M�todo busca cliente com o CPF fornecido
     * @param cpf CPF do cliente a ser buscado
     * @return Cliente buscado
     * @throws ClienteNaoEncontradoException Se n�o for encontrado um cliente cadastrado com CPF fornecido
     */
    public Cliente buscarCliente(String cpf) throws ClienteNaoEncontradoException {
        try{
            Cliente cliente = controller.buscarCliente(cpf);
            return cliente;
        }catch(DadoNaoEncontradoException e){
            throw new ClienteNaoEncontradoException("\n    Busca de cliente mal sucedida,\n     Cliente n�o encontrado!");
        }
    }
    
    /**
     * M�todo verifica se existe um cliente cadastrado com CPF fornecido
     * @param cpf CPF do cliente
     * @return True se o cliente est� cadastrado
     */
    public boolean clienteCadastrado(String cpf) {
        return controller.clienteCadastrado(cpf);
    }

    /**
     * M�todo cadastra uma a��o no sistema
     * @param siglaAcao Sigla da a��o
     * @param nomeAcao Nome da empresa referente � a��o
     * @param valorInicial Valor inicial da cota��o da a��o
     * @param tipoAcao Tipo da a��o
     * @return A��o cadastrada
     * @throws DadoDuplicadoException Se existe a��o cadastrada com a sigla passada
     */
    public Acao cadastrarAcao(String siglaAcao, String nomeAcao, double valorInicial, TipoAcao tipoAcao) throws DadoDuplicadoException {
        return controller.cadastrarAcao(siglaAcao, nomeAcao, valorInicial, tipoAcao);
    }

    /**
     * M�todo remove do sistema a a��o com a sigla passada
     * @param siglaAcao Sigla da a��o
     * @return A��o removida
     * @throws AcaoNaoEncontradaException Se n�o foi encontrada uma a��o com a sigla fornecida
     * @throws AcaoEmCarteiraException Se a a��o estiver em uma carteira
     */
    public Acao removerAcao(String siglaAcao) throws AcaoNaoEncontradaException, AcaoEmCarteiraException {
        try{
            Acao acao = controller.removerAcao(siglaAcao);
            return acao;
        } catch(DadoNaoEncontradoException e){
            throw new AcaoNaoEncontradaException("\n    Remo��o de acao mal sucedida,\n     Acao n�o encontrado!");
        }
    }
    
    /**
     * M�todo atualiza as cota��es das a��es
     * @param nomeArq Nome do arquivo com as cota��es
     * @throws FileNotFoundException Se o arquivo n�o foi encontrado
     */
    public void atualizarCotacoes(String nomeArq) throws FileNotFoundException{
        controller.atualizarCotacoes(nomeArq);
    }

    /**
     * M�todo verifica se existe a��o cadastrada com a sigla passada
     * @param siglaAcao Sigla da a��o
     * @return True se existe a��o cadastrada com a sigla fornecida
     */
    public boolean acaoCadastrada(String siglaAcao) {
        return controller.acaoCadastrada(siglaAcao);
    }

    /**
     * M�todo modifica o valor da cota��o de uma a��o
     * @param siglaAcao Sigla da a��o 
     * @param novoValor Novo valor de cota��
     * @throws AcaoNaoEncontradaException Se n�o foi encontrada uma a��o com a sigla fornecida
     */
    public void setValorAcao(String siglaAcao, double novoValor) throws AcaoNaoEncontradaException {
        try {
            controller.setValorAcao(siglaAcao, novoValor);
        } catch (DadoNaoEncontradoException e) {
            throw new AcaoNaoEncontradaException();
        }
    }
    
    /**
     * M�todo retorna o valor de cota��o de uma a��o
     * @param siglaAcao Sigla da a��o 
     * @return Valor da cota��o
     * @throws AcaoNaoEncontradaException Se n�o foi encontrada uma a��o com a sigla fornecida
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
     * M�todo inclui lotes (100 a��es) na carteira de um cliente
     * @param cpf CPF do cliente
     * @param siglaAcao Sigla da A��o
     * @param quantidade Quantidade lotes
     * @throws ClienteNaoEncontradoException Se n�o for encontrado um cliente cadastrado com CPF fornecido
     * @throws AcaoNaoEncontradaException Se n�o foi encontrada uma a��o com a sigla fornecida
     */
    public void incluirAcaoCliente(String cpf, String siglaAcao, int quantidade) throws ClienteNaoEncontradoException, AcaoNaoEncontradaException {
        try {
            controller.incluirAcaoCliente(cpf, siglaAcao, quantidade);
        } catch (DadoNaoEncontradoException ex) {
            throw new ClienteNaoEncontradoException("\n    Inser��o de lote mal sucedida, \n    Cliente n�o encontrado");
        } catch (AcaoEmCarteiraException ex) {
            try {
                controller.setQuantidadeAcaoCliente(cpf, siglaAcao, quantidade);
            } catch (DadoNaoEncontradoException ex1) { }
        }
    }

    /**
     * M�todo remove lotes (100 a��es) da carteira de um cliente
     * @param cpf CPF do cliente
     * @param siglaAcao Sigla da a��o
     * @throws ClienteNaoEncontradoException Se n�o for encontrado um cliente cadastrado com CPF fornecido
     * @throws AcaoNaoEncontradaException Se n�o foi encontrada uma a��o com a sigla fornecida
     */
    public void removerAcaoCliente(String cpf, String siglaAcao) throws ClienteNaoEncontradoException, AcaoNaoEncontradaException {
        try {
            controller.removerAcaoCliente(cpf, siglaAcao);
        } catch (DadoNaoEncontradoException e) {
            throw new ClienteNaoEncontradoException("\n    Remo��o de lote mal sucedida, \n    Cliente n�o encontrado");
        }
    }
    
    /**
     * M�todo retorna a quantidade de lotes de uma certa a��o que um cliente possui
     * @param cpf CPF do cliente
     * @param siglaAcao Sigla da a��o
     * @return Quantidade de lotes
     * @throws ClienteNaoEncontradoException Se n�o for encontrado um cliente cadastrado com CPF fornecido
     */
    public int getQuantidadeAcaoCliente(String cpf, String siglaAcao) throws ClienteNaoEncontradoException {
        try {
            return  controller.getQuantidadeAcaoCliente(cpf, siglaAcao);
        } catch (DadoNaoEncontradoException ex) {
            throw new ClienteNaoEncontradoException();
        }
    }
    
    /**
     * M�todo modifica a quantidade de lotes de uma certa a��o na carteira de um cliente
     * @param cpfCliente CPF do cliente
     * @param siglaAcao Sigla da a��o
     * @param quantidade Nova quantidade de lotes
     * @throws ClienteNaoEncontradoException Se n�o for encontrado um cliente cadastrado com CPF fornecido
     * @throws AcaoNaoEncontradaException Se n�o foi encontrada uma a��o com a sigla fornecida
     */
    public void setQuantidadeAcaoCliente(String cpfCliente, String siglaAcao, int quantidade) throws ClienteNaoEncontradoException, AcaoNaoEncontradaException {
        try {
            controller.setQuantidadeAcaoCliente(cpfCliente, siglaAcao, quantidade);
        } catch (DadoNaoEncontradoException e) {
            incluirAcaoCliente(cpfCliente, siglaAcao, quantidade);
        }
    }

    /**
     * M�todo retorna a {@link br.uefs.ecomp.treeStock.model.Carteira carteira} de um cliente
     * @param cpfCliente CPF do cliente
     * @return {@link br.uefs.ecomp.treeStock.model.Carteira Carteira} do cliente
     * @throws ClienteNaoEncontradoException Se n�o for encontrado um cliente cadastrado com CPF fornecido
     */
    public Carteira getCarteiraCliente(String cpfCliente) throws ClienteNaoEncontradoException {
        return controller.getCarteiraCliente(cpfCliente);
    }

    /**
     * M�todo retorna o montante da carteira de um cliente
     * @param cpfCliente CPF do cliente
     * @return Montante da carteira
     * @throws ClienteNaoEncontradoException Se n�o for encontrado um cliente cadastrado com CPF fornecido
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
     * @throws ClienteNaoEncontradoException Se n�o existir clientes cadastrados
     * @throws NumeroClientesInsuficienteException Se o n�mero K passado for menor que o n�mero de clientes cadastrados
     */
    public Iterator melhoresClientes(int k) throws ClienteNaoEncontradoException, NumeroClientesInsuficienteException {
        return controller.melhoresClientes(k);
    }

    /**
     * M�todo retorna o valor atualizado da carteira do cliente
     * @param cpf CPF do cliente
     * @return Valor da carteira
     */
    public double valorCarteira(String cpf) {
        return controller.valorCarteira(cpf);
    }

    /**
     * M�todo gera arquivo de salvamento do sistema
     * @throws IOException Caso aconte�a algum erro na gera��o do arquivo
     */
    public void gerarArquivoSistema() throws IOException {
        controller.gerarArquivoSistema();
    }
}