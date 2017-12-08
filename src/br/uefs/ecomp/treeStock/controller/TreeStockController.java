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
     * Método retorna a existencia de um arquivo de salvamento do sistema
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
     * Método retorna o nome do mais novo arquivo de salvamento do sistema
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
     * Método cadastra um novo cliente
     * @param nome Nome do cliente
     * @param cpf CPF do cliente
     * @param endereco Endereço do cliente
     * @return Cliente cadastrado 
     * @throws DadoDuplicadoException Se já existe um cliente cadastrado com o CPF fornecido
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
     * Método remove cliente com o CPF fornecido
     * @param cpf CPF do cliente a ser removido
     * @return Cliente removido
     * @throws DadoNaoEncontradoException Se não for encontrado um cliente cadastrado com CPF fornecido
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
     * Método busca cliente com o CPF fornecido
     * @param cpf CPF do cliente a ser buscado
     * @return Cliente buscado
     * @throws DadoNaoEncontradoException Se não for encontrado um cliente cadastrado com CPF fornecido
     */
    public Cliente buscarCliente(String cpf) throws DadoNaoEncontradoException {
        Cliente cliente = new Cliente(null, cpf, null);     //instancia um cliente com CPF passado para que possa ser feito a comparação
        
        return (Cliente) clientes.buscar(cliente);
    }

    /**
     * Método verifica se existe um cliente cadastrado com CPF fornecido
     * @param cpf CPF do cliente
     * @return True se o cliente está cadastrado
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
     * Método cadastra uma ação no sistema
     * @param sigla Sigla da ação
     * @param nome Nome da empresa referente à ação
     * @param valorInicial Valor inicial da cotação da ação
     * @param tipoAcao Tipo da ação
     * @return Ação cadastrada
     * @throws DadoDuplicadoException Se existe ação cadastrada com a sigla passada
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
     * Método remove do sistema a ação com a sigla passada, que não pertence a 
     * nenhuma carteira
     * @param sigla Sigla da ação
     * @return Ação removida
     * @throws DadoNaoEncontradoException Se não foi encontrada uma ação com a sigla fornecida
     * @throws AcaoEmCarteiraException Se a ação estiver em uma carteira
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
        
        //busca a ação nas carteiras
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
     * Método atualiza as cotações das ações
     * @param nomeArq Nome do arquivo com as cotações
     * @throws FileNotFoundException Se o arquivo não foi encontrado
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
            
            //verifica se a ação é do tipo Preferencial, para que as carteiras sejam atualizadas
            if(acao != null && acao.equals(acaoComp) && tipoAcao.equals("PN")){
                for (Carteira carteira : contas) {
                    Iterator itLotes = carteira.iteratorLotes();
                    
                    while(itLotes.hasNext()){
                        Lote lote = (Lote) itLotes.next();
                        
                        //verifica se ação é a mesma que está sendo atualizada
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
     * Método verifica se existe ação cadastrada com a sigla passada
     * @param siglaAcao Sigla da ação
     * @return True se existe ação cadastrada com a sigla fornecida
     */
    public boolean acaoCadastrada(String siglaAcao) {
        Acao acao = new Acao(siglaAcao);
        
        return acoes.contains(acao);
    }

    /**
     * Método modifica o valor da cotação de uma ação
     * @param siglaAcao Sigla da ação 
     * @param novoValor Novo valor de cotaçã
     * @throws DadoNaoEncontradoException Se não foi encontrada uma ação com a sigla fornecida
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
     * Método retorna o valor de cotação de uma ação
     * @param siglaAcao Sigla da ação 
     * @return Valor da cotação
     * @throws DadoNaoEncontradoException Se não foi encontrada uma ação com a sigla fornecida
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
     * Método inclui lotes (100 ações) na carteira de um cliente
     * @param cpf CPF do cliente
     * @param siglaAcao Sigla da Ação
     * @param quantidade Quantidade lotes
     * @throws DadoNaoEncontradoException Se não for encontrado um cliente cadastrado com CPF fornecido
     * @throws AcaoNaoEncontradaException Se não foi encontrada uma ação com a sigla fornecida
     * @throws AcaoEmCarteiraException Se a ação já está na carteira do cliente
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
            throw new AcaoNaoEncontradaException("\n    Inserção de lote mal sucedida, \n    Ação não encontrado");
        
        carteira.criarLote(acao, quantidade);
    }

    /**
     * Método remove lotes (100 ações) da carteira de um cliente
     * @param cpf CPF do cliente
     * @param siglaAcao Sigla da ação
     * @throws DadoNaoEncontradoException Se não for encontrado um cliente cadastrado com CPF fornecido
     * @throws AcaoNaoEncontradaException Se não foi encontrada uma ação com a sigla fornecida
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
            throw new AcaoNaoEncontradaException("\n    Remoção de lote mal sucedida, \n    Lote não encontrado");
        
        carteira.removerLote(lote);
    }

    /**
     * Método retorna a quantidade de lotes de uma certa ação que um cliente possui
     * @param cpf CPF do cliente
     * @param siglaAcao Sigla da ação
     * @return Quantidade de lotes
     * @throws DadoNaoEncontradoException Se não for encontrado um cliente cadastrado com CPF fornecido
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
     * Método modifica a quantidade de lotes de uma certa ação na carteira de um cliente
     * @param cpfCliente CPF do cliente
     * @param siglaAcao Sigla da ação
     * @param quantidade Nova quantidade de lotes
     * @throws ClienteNaoEncontradoException Se não for encontrado um cliente cadastrado com CPF fornecido
     * @throws AcaoNaoEncontradaException Se não foi encontrada uma ação com a sigla fornecida
     * @throws DadoNaoEncontradoException Se não existe lote de ação passada na carteira do cliente
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
     * Método retorna a {@link br.uefs.ecomp.treeStock.model.Carteira carteira} de um cliente
     * @param cpfCliente CPF do cliente
     * @return {@link br.uefs.ecomp.treeStock.model.Carteira Carteira} do cliente
     * @throws ClienteNaoEncontradoException Se não for encontrado um cliente cadastrado com CPF fornecido
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
     * Método retorna o montante da carteira de um cliente
     * @param cpfCliente CPF do cliente
     * @return Montante da carteira
     * @throws ClienteNaoEncontradoException Se não for encontrado um cliente cadastrado com CPF fornecido
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
     * @throws ClienteNaoEncontradoException Se não existir clientes cadastrados
     * @throws NumeroClientesInsuficienteException Se o número K passado for menor que o número de clientes cadastrados
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
     * Método retorna o valor atualizado da carteira do cliente
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
     * Método gera arquivo de salvamento do sistema com data e hora do salvamento no nome do arquivo
     * @throws IOException Caso aconteça algum erro na geração do arquivo
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
