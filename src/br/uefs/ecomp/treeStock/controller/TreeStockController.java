package br.uefs.ecomp.treeStock.controller;

import br.uefs.ecomp.treeStock.exceptions.AcaoEmCarteiraException;
import br.uefs.ecomp.treeStock.exceptions.NumeroClientesInsuficienteException;
import br.uefs.ecomp.treeStock.util.QuickSort;
import br.uefs.ecomp.treeStock.model.Acao;
import br.uefs.ecomp.treeStock.model.Carteira;
import br.uefs.ecomp.treeStock.model.Cliente;
import br.uefs.ecomp.treeStock.model.Lote;
import br.uefs.ecomp.treeStock.model.TipoAcao;
import br.uefs.ecomp.treeStock.exceptions.AcaoNaoEncontradaException;
import br.uefs.ecomp.treeStock.exceptions.ClienteNaoEncontradoException;
import br.uefs.ecomp.treeStock.util.Arvore;
import br.uefs.ecomp.treeStock.exceptions.DadoDuplicadoException;
import br.uefs.ecomp.treeStock.exceptions.DadoNaoEncontradoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Classe {@code TreeStockController} controla todos os dados do sistema, inserindo
 * removendo e atualizando os dados
 */
public class TreeStockController implements Serializable {
    private Arvore clientes = new Arvore();
    private LinkedList<Acao> acoes = new LinkedList();
    private LinkedList<Carteira> contas = new LinkedList();
    
    
    /**
     * M�todo retorna a existencia de um arquivo de salvamento do sistema
     * @return Se existe um arquivo de salvamento do sistema retorna True
     */
    public boolean temArquivoSistema(){
        File file = new File("Saves");
        int numeroArquivos = 0;
        
        File[] files = file.listFiles();
        
        for(File a: files){
            numeroArquivos++;
        }
        return numeroArquivos > 0;
    }
    
    /**
     * M�todo retorna o nome do mais novo arquivo de salvamento do sistema
     * @return Nome do arquivo de salvamento do sistema
     */
    public String nomeArquivoSistema(){
        File file = new File("Saves");
        File fileMaior;
        
        File[] files = file.listFiles();
        
        fileMaior = files[0];
        for (File file1 : files) {
            if (fileMaior.getName().compareTo(file1.getName()) < 0) {
                fileMaior = file1;
            }
        }
        
        return fileMaior.getName();
    }
    
    /**
     * M�todo cadastra um novo cliente
     * @param nome Nome do cliente
     * @param cpf CPF do cliente
     * @param endereco Endere�o do cliente
     * @return Cliente cadastrado 
     * @throws DadoDuplicadoException Se j� existe um cliente cadastrado com o CPF fornecido
     */
    public Cliente cadastrarCliente(String nome, String cpf, String endereco) throws DadoDuplicadoException {
        Cliente cliente = new Cliente(nome, cpf, endereco);
        Carteira carteira = new Carteira(cliente);
        
        cliente.setCarteira(carteira);
        clientes.inserir(cliente);
        contas.add(carteira);
        
        return cliente;
    }

    /**
     * M�todo remove cliente com o CPF fornecido
     * @param cpf CPF do cliente a ser removido
     * @return Cliente removido
     * @throws DadoNaoEncontradoException Se n�o for encontrado um cliente cadastrado com CPF fornecido
     */
    public Cliente removerCliente(String cpf) throws DadoNaoEncontradoException {
        Cliente clienteComp = new Cliente(null, cpf, null);     //instancia um cliente para que possa ser comparado na busca do cliente a ser removido
        Cliente cliente = (Cliente) clientes.buscar(clienteComp);
        Carteira carteira;
        
        clientes.remover(clienteComp);
        carteira = cliente.getCarteira();
        contas.remove(carteira);
        
        return cliente;
    }

    /**
     * M�todo busca cliente com o CPF fornecido
     * @param cpf CPF do cliente a ser buscado
     * @return Cliente buscado
     * @throws DadoNaoEncontradoException Se n�o for encontrado um cliente cadastrado com CPF fornecido
     */
    public Cliente buscarCliente(String cpf) throws DadoNaoEncontradoException {
        Cliente cliente = new Cliente(null, cpf, null);     //instancia um cliente com CPF passado para que possa ser feito a compara��o
        
        return (Cliente) clientes.buscar(cliente);
    }

    /**
     * M�todo verifica se existe um cliente cadastrado com CPF fornecido
     * @param cpf CPF do cliente
     * @return True se o cliente est� cadastrado
     */
    public boolean clienteCadastrado(String cpf) {
        Cliente cliente = new Cliente(null, cpf, null);
        
        try{
            clientes.buscar(cliente);
            return true;
        } catch(DadoNaoEncontradoException e){
        }
        return false;
    }

    /**
     * M�todo cadastra uma a��o no sistema
     * @param sigla Sigla da a��o
     * @param nome Nome da empresa referente � a��o
     * @param valorInicial Valor inicial da cota��o da a��o
     * @param tipoAcao Tipo da a��o
     * @return A��o cadastrada
     * @throws DadoDuplicadoException Se existe a��o cadastrada com a sigla passada
     */
    public Acao cadastrarAcao(String sigla, String nome, double valorInicial, TipoAcao tipoAcao) throws DadoDuplicadoException {
        Acao acao = new Acao(sigla, nome, valorInicial, tipoAcao);
        Acao acaoComp = null;
        Iterator it = acoes.iterator();
        
        while(it.hasNext()){
            acaoComp = (Acao) it.next();
            if(acao.equals(acaoComp))
                break;
        }
        
        if(acaoComp != null && acao.equals(acaoComp)){
            throw new DadoDuplicadoException();
        }
        
        acoes.add(acao);
        
        return acao;
    }

    /**
     * M�todo remove do sistema a a��o com a sigla passada, que n�o pertence a 
     * nenhuma carteira
     * @param sigla Sigla da a��o
     * @return A��o removida
     * @throws DadoNaoEncontradoException Se n�o foi encontrada uma a��o com a sigla fornecida
     * @throws AcaoEmCarteiraException Se a a��o estiver em uma carteira
     */
    public Acao removerAcao(String sigla) throws DadoNaoEncontradoException, AcaoEmCarteiraException {
        Acao acaoComp = new Acao(sigla);
        Acao acao = null;
        Iterator itAcao = acoes.iterator();
        Iterator itContas = contas.iterator();
        
        while(itAcao.hasNext()){
            acao = (Acao) itAcao.next();
            if(acao.equals(acaoComp))
                break;
        }
        
        if(acao == null || !acao.equals(acaoComp)){
            throw new DadoNaoEncontradoException();
        }
        
        acoes.remove(acao);
        
        //busca a a��o nas carteiras
        while(itContas.hasNext()){
            Carteira carteira = (Carteira) itContas.next();
            Iterator itLotes = carteira.iteratorLotes();
            
            while(itLotes.hasNext()){
                Lote lote = (Lote) itLotes.next();
                
                if(lote.getAcao().getSigla().equals(acao.getSigla())){
                    throw new AcaoEmCarteiraException();
                }
            }
        }
        
        return acao;
    }
    
    /**
     * M�todo atualiza as cota��es das a��es
     * @param nomeArq Nome do arquivo com as cota��es
     * @throws FileNotFoundException Se o arquivo n�o foi encontrado
     */
    public void atualizarCotacoes(String nomeArq) throws FileNotFoundException{
        Scanner in = new Scanner(new File(nomeArq));
        String siglaEmp;
        String tipoAcao;
        double cotacao;
        double valorDividendo;
        
        in.nextLine();
        while(in.hasNext()){
            Iterator it = acoes.iterator();
            Acao acao = null;
            
            siglaEmp = in.next();
            Acao acaoComp = new Acao(siglaEmp);
            tipoAcao = in.next();
            cotacao = in.nextDouble()/100;
            valorDividendo = in.nextDouble()/100;
            
            while(it.hasNext()){
                acao = (Acao) it.next();
                if(acao.equals(acaoComp))
                    break;
            }
            if(acao != null && acao.equals(acaoComp)){
                acao.setValor(cotacao);
            }
            
            //verifica se a a��o � do tipo Preferencial, para que as carteiras sejam atualizadas
            if(acao != null && acao.equals(acaoComp) && tipoAcao.equals("PN")){
                for (Carteira carteira : contas) {
                    Iterator itLotes = carteira.iteratorLotes();
                    
                    while(itLotes.hasNext()){
                        Lote lote = (Lote) itLotes.next();
                        
                        //verifica se a��o � a mesma que est� sendo atualizada
                        if(lote.getAcao().equals(acao)){
                            double atualizarValorDividendo = lote.getQuantidadeLotes() * 100 * valorDividendo;
                            carteira.setSaldo(atualizarValorDividendo);
                            
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * M�todo verifica se existe a��o cadastrada com a sigla passada
     * @param siglaAcao Sigla da a��o
     * @return True se existe a��o cadastrada com a sigla fornecida
     */
    public boolean acaoCadastrada(String siglaAcao) {
        Acao acao = new Acao(siglaAcao);
        
        return acoes.contains(acao);
    }

    /**
     * M�todo modifica o valor da cota��o de uma a��o
     * @param siglaAcao Sigla da a��o 
     * @param novoValor Novo valor de cota��
     * @throws DadoNaoEncontradoException Se n�o foi encontrada uma a��o com a sigla fornecida
     */
    public void setValorAcao(String siglaAcao, double novoValor) throws DadoNaoEncontradoException {
        Acao acaoCmp = new Acao(siglaAcao);
        Acao acao = null;
        Iterator it = acoes.iterator();
        
        while(it.hasNext()){
            acao = (Acao) it.next();
            if(acao.equals(acaoCmp)){
                break;
            }
        }
        
        if(acao != null && acao.equals(acaoCmp)){
            acao.setValor(novoValor);
        } else{
            throw new DadoNaoEncontradoException();
        }
    }

    /**
     * M�todo retorna o valor de cota��o de uma a��o
     * @param siglaAcao Sigla da a��o 
     * @return Valor da cota��o
     * @throws DadoNaoEncontradoException Se n�o foi encontrada uma a��o com a sigla fornecida
     */
    public double getValorAcao(String siglaAcao) throws DadoNaoEncontradoException {
        Acao acaoCmp = new Acao(siglaAcao);
        Acao acao = null;
        Iterator it = acoes.iterator();
        
        while(it.hasNext()){
            acao = (Acao) it.next();
            if(acao.equals(acaoCmp)){
                break;
            }
        }
        
        if(acao != null && acao.equals(acaoCmp)){
            return acao.getValor();
        } else{
            throw new DadoNaoEncontradoException();
        }
    }

    /**
     * Retorna os clientes ordenados pelo nome
     * @return <b>Iterator</b> dos clientes ordenados
     */
    public Iterator iterator() {
        return clientes.iterator();
    }

    /**
     * M�todo inclui lotes (100 a��es) na carteira de um cliente
     * @param cpf CPF do cliente
     * @param siglaAcao Sigla da A��o
     * @param quantidade Quantidade lotes
     * @throws DadoNaoEncontradoException Se n�o for encontrado um cliente cadastrado com CPF fornecido
     * @throws AcaoNaoEncontradaException Se n�o foi encontrada uma a��o com a sigla fornecida
     * @throws AcaoEmCarteiraException Se a a��o j� est� na carteira do cliente
     */
    public void incluirAcaoCliente(String cpf, String siglaAcao, int quantidade) throws DadoNaoEncontradoException, AcaoNaoEncontradaException, AcaoEmCarteiraException {
        Cliente clienteCmp = new Cliente(null, cpf, null);
        Cliente cliente = (Cliente) clientes.buscar(clienteCmp);
        Acao acaoCmp = new Acao(siglaAcao);
        Acao acao = null;
        Carteira carteira = cliente.getCarteira();
        Iterator it = acoes.iterator();
        Iterator itLotes = carteira.iteratorLotes();
        
        while(itLotes.hasNext()){
            Lote lote = (Lote) itLotes.next();
            if(lote.getAcao().equals(acaoCmp)){
                throw new AcaoEmCarteiraException();
            }
        }
        
        while(it.hasNext()){
            acao = (Acao) it.next();
            if(acao.equals(acaoCmp))
                break;
        }
        
        if(acao == null || !acao.equals(acaoCmp))
            throw new AcaoNaoEncontradaException("\n    Inser��o de lote mal sucedida, \n    A��o n�o encontrado");
        
        carteira.criarLote(acao, quantidade);
    }

    /**
     * M�todo remove lotes (100 a��es) da carteira de um cliente
     * @param cpf CPF do cliente
     * @param siglaAcao Sigla da a��o
     * @throws DadoNaoEncontradoException Se n�o for encontrado um cliente cadastrado com CPF fornecido
     * @throws AcaoNaoEncontradaException Se n�o foi encontrada uma a��o com a sigla fornecida
     */
    public void removerAcaoCliente(String cpf, String siglaAcao) throws DadoNaoEncontradoException, AcaoNaoEncontradaException {
        Cliente clienteCmp = new Cliente(null, cpf, null);
        Cliente cliente = (Cliente) clientes.buscar(clienteCmp);
        Carteira carteira = cliente.getCarteira();
        Acao acaoCmp = new Acao(siglaAcao);
        Lote lote = null;
        Iterator it = carteira.iteratorLotes();
        
        while(it.hasNext()){
            lote = (Lote) it.next();
            if(lote.getAcao().equals(acaoCmp))
                break;
        }
        
        if(lote == null || !lote.getAcao().equals(acaoCmp))
            throw new AcaoNaoEncontradaException("\n    Remo��o de lote mal sucedida, \n    Lote n�o encontrado");
        
        carteira.removerLote(lote);
    }

    /**
     * M�todo retorna a quantidade de lotes de uma certa a��o que um cliente possui
     * @param cpf CPF do cliente
     * @param siglaAcao Sigla da a��o
     * @return Quantidade de lotes
     * @throws DadoNaoEncontradoException Se n�o for encontrado um cliente cadastrado com CPF fornecido
     */
    public int getQuantidadeAcaoCliente(String cpf, String siglaAcao) throws DadoNaoEncontradoException {
        Cliente clienteCmp = new Cliente(null, cpf, null);
        Cliente cliente;
        Acao acaoCmp = new Acao(siglaAcao);
        Lote lote = null;
        Iterator it;
                
        cliente = (Cliente) clientes.buscar(clienteCmp);
        it = cliente.getCarteira().iteratorLotes();
        
        while(it.hasNext()){
            lote = (Lote) it.next();
            if(lote.getAcao().equals(acaoCmp)){
                break;
            }
        }
        
        if(lote == null || !lote.getAcao().equals(acaoCmp)){
            return 0;
        } else{
            return lote.getQuantidadeLotes();
        }
    }

    /**
     * M�todo modifica a quantidade de lotes de uma certa a��o na carteira de um cliente
     * @param cpfCliente CPF do cliente
     * @param siglaAcao Sigla da a��o
     * @param quantidade Nova quantidade de lotes
     * @throws ClienteNaoEncontradoException Se n�o for encontrado um cliente cadastrado com CPF fornecido
     * @throws AcaoNaoEncontradaException Se n�o foi encontrada uma a��o com a sigla fornecida
     * @throws DadoNaoEncontradoException Se n�o existe lote de a��o passada na carteira do cliente
     */
    public void setQuantidadeAcaoCliente(String cpfCliente, String siglaAcao, int quantidade) throws AcaoNaoEncontradaException, ClienteNaoEncontradoException, DadoNaoEncontradoException {
        Cliente clienteCmp = new Cliente(null, cpfCliente, null);
        Cliente cliente;
        Acao acaoCmp = new Acao(siglaAcao);
        Lote lote = null;
        Iterator itLote;
        Iterator itAcao = acoes.iterator();
        Acao acao = null;
        
        try {
            cliente = (Cliente) clientes.buscar(clienteCmp);
        } catch (DadoNaoEncontradoException e) {
            throw new ClienteNaoEncontradoException();
        }
        
        itLote = cliente.getCarteira().iteratorLotes();
        
        while(itAcao.hasNext()){
            acao = (Acao) itAcao.next();
            if(acao.equals(acaoCmp)){
                break;
            }
        }
        
        if(acao == null || !acao.equals(acaoCmp)){
            throw new AcaoNaoEncontradaException();
        }
        
        while(itLote.hasNext()){
            lote = (Lote) itLote.next();
            if(lote.getAcao().equals(acaoCmp)){
                break;
            }
        }
        
        if(lote != null && lote.getAcao().equals(acaoCmp)){
            lote.setQuantidadeLotes(quantidade);
        } else{
            throw new DadoNaoEncontradoException();
        }
    }

    /**
     * M�todo retorna a {@link br.uefs.ecomp.treeStock.model.Carteira carteira} de um cliente
     * @param cpfCliente CPF do cliente
     * @return {@link br.uefs.ecomp.treeStock.model.Carteira Carteira} do cliente
     * @throws ClienteNaoEncontradoException Se n�o for encontrado um cliente cadastrado com CPF fornecido
     */
    public Carteira getCarteiraCliente(String cpfCliente) throws ClienteNaoEncontradoException {
        Cliente clienteCmp = new Cliente(null, cpfCliente, null);
        Cliente cliente;
        
        try {
            cliente = (Cliente) clientes.buscar(clienteCmp);
            return cliente.getCarteira();
        } catch (DadoNaoEncontradoException e) {
            throw new ClienteNaoEncontradoException();
        }
    }

    /**
     * M�todo retorna o montante da carteira de um cliente
     * @param cpfCliente CPF do cliente
     * @return Montante da carteira
     * @throws ClienteNaoEncontradoException Se n�o for encontrado um cliente cadastrado com CPF fornecido
     */
    public double getValorCarteiraCliente(String cpfCliente) throws ClienteNaoEncontradoException {
        Cliente clienteCmp = new Cliente(null, cpfCliente, null);
        Cliente cliente;
        
        try {
            cliente = (Cliente) clientes.buscar(clienteCmp);
            return cliente.getCarteira().atualizarMontante();
        } catch (DadoNaoEncontradoException e) {
            throw new ClienteNaoEncontradoException();
        }
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
        Iterator it = contas.iterator();
        
        if(contas.isEmpty())
            throw new ClienteNaoEncontradoException();
        else if(contas.size() < k)
            throw new NumeroClientesInsuficienteException();
        
        while(it.hasNext()){
            Carteira carteira = (Carteira) it.next();
            
            carteira.atualizarMontante();
        }
        
        return QuickSort.ordenar(contas, k);
    }

    /**
     * M�todo retorna o valor atualizado da carteira do cliente
     * @param cpf CPF do cliente
     * @return Valor da carteira
     */
    public double valorCarteira(String cpf) {
        Cliente clienteCmp = new Cliente(null, cpf, null);
        Cliente cliente = null;
        Iterator it = clientes.iterator();
        
        while(it.hasNext()){
            cliente = (Cliente) it.next();
            if(cliente.getCpf().equals(clienteCmp.getCpf())){
                break;
            }
        }
        if(cliente != null && cliente.getCpf().equals(clienteCmp.getCpf()))
            return cliente.getCarteira().atualizarMontante();
        
        return 0;
    }
    
    /**
     * M�todo gera arquivo de salvamento do sistema com data e hora do salvamento no nome do arquivo
     * @throws IOException Caso aconte�a algum erro na gera��o do arquivo
     */
    public void gerarArquivoSistema() throws IOException {
        Date dataAtual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy HH.mm");
        
        FileOutputStream f = new FileOutputStream(new File("Saves", "systemSave (" + formato.format(dataAtual) + ").data"));
        try (ObjectOutputStream objOutput = new ObjectOutputStream(f)) {
            objOutput.writeObject(this);
        }
    }
}
