package br.uefs.ecomp.treeStock.controller;

import br.uefs.ecomp.treeStock.exceptions.NumeroClientesInsuficienteException;
import br.uefs.ecomp.TreeStock.util.QuickSort;
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

public class TreeStockController implements Serializable {
    private Arvore clientes = new Arvore();
    private LinkedList<Acao> acoes = new LinkedList();
    private LinkedList<Carteira> contas = new LinkedList();
    
    
    public boolean temArquivoSistema(){
        File file = new File("Saves");
        int numeroArquivos = 0;
        
        File[] files = file.listFiles();
        
        for(File a: files){
            numeroArquivos++;
        }
        return numeroArquivos > 0;
    }
    
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
    
    public Cliente cadastrarCliente(String nome, String cpf, String endereco) throws DadoDuplicadoException {
        Cliente cliente = new Cliente(nome, cpf, endereco);
        Carteira carteira = new Carteira(cliente);
        
        cliente.setCarteira(carteira);
        clientes.inserir(cliente);
        contas.add(carteira);
        
        return cliente;
    }

    public Cliente removerCliente(String cpf) throws DadoNaoEncontradoException {
        Cliente clienteComp = new Cliente(null, cpf, null);     //instancia um cliente para que possa ser comparado na busca do cliente a ser removido
        Cliente cliente;
        Carteira carteira;
        
        cliente = (Cliente) clientes.remover(clienteComp);
        carteira = cliente.getCarteira();
        contas.remove(carteira);
        
        return cliente;
    }

    public Cliente buscarCliente(String cpf) throws DadoNaoEncontradoException {
        Cliente cliente = new Cliente(null, cpf, null);     //instancia um cliente com CPF passado para que possa ser feito a comparação
        
        return (Cliente) clientes.buscar(cliente);
    }

    public boolean clienteCadastrado(String cpf) {
        Cliente cliente = new Cliente(null, cpf, null);
        
        try{
            clientes.buscar(cliente);
            return true;
        } catch(DadoNaoEncontradoException e){
        }
        return false;
    }

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

    public Acao removerAcao(String sigla) throws DadoNaoEncontradoException {
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
        
        while(itContas.hasNext()){
            Carteira carteira = (Carteira) itContas.next();
            Iterator itLotes = carteira.iteratorLotes();
            
            while(itLotes.hasNext()){
                Lote lote = (Lote) itLotes.next();
                
                if(lote.getAcao().getSigla().equals(acao.getSigla())){
                    carteira.removerLote(lote);
                }
            }
        }
        
        return acao;
    }
    
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

    public boolean acaoCadastrada(String siglaAcao) {
        Acao acao = new Acao(siglaAcao);
        
        return acoes.contains(acao);
    }

    public void setValorAcao(String siglaAcao, double novoValor) {
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
        }
    }

    public double getValorAcao(String siglaAcao) {
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
        }
        return 0;
    }

    public Iterator iterator() {
        return clientes.iterator();
    }

    public void incluirAcaoCliente(String cpf, String siglaAcao, int quantidade) throws DadoNaoEncontradoException, AcaoNaoEncontradaException {
        Cliente clienteCmp = new Cliente(null, cpf, null);
        Cliente cliente = (Cliente) clientes.buscar(clienteCmp);
        Acao acaoCmp = new Acao(siglaAcao);
        Acao acao = null;
        Carteira carteira = cliente.getCarteira();
        Iterator it = acoes.iterator();
        
        while(it.hasNext()){
            acao = (Acao) it.next();
            if(acao.equals(acaoCmp))
                break;
        }
        
        if(acao == null || !acao.equals(acaoCmp))
            throw new AcaoNaoEncontradaException("\n    Inserção de lote mal sucedida, \n    Ação não encontrado");
        
        carteira.criarLote(acao, quantidade);
    }

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

    public double valorCarteira(String cpf) {
        Cliente clienteCmp = new Cliente(null, cpf, null);
        Cliente cliente = null;
        Iterator it = clientes.iterator();
        
        while(it.hasNext()){
            cliente = (Cliente) it.next();
            if(cliente.getCpf().equals(clienteCmp.getCpf())){
                System.out.println(cliente.getNome());
                break;
            }
        }
        if(cliente != null && cliente.getCpf().equals(clienteCmp.getCpf()))
            return cliente.getCarteira().atualizarMontante();
        
        return 0;
    }
    
    public void gerarArquivoSistema() throws IOException {
        Date dataAtual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy HH.mm");
        
        FileOutputStream f = new FileOutputStream(new File("Saves", "systemSave (" + formato.format(dataAtual) + ").data"));
        try (ObjectOutputStream objOutput = new ObjectOutputStream(f)) {
            objOutput.writeObject(this);
        }
    }
}
