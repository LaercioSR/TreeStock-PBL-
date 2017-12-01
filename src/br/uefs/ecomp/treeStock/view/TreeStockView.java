package br.uefs.ecomp.treeStock.view;

import br.uefs.ecomp.treeStock.facade.TreeStockFacade;
import br.uefs.ecomp.treeStock.model.Acao;
import br.uefs.ecomp.treeStock.model.Carteira;
import br.uefs.ecomp.treeStock.model.Cliente;
import br.uefs.ecomp.treeStock.model.Lote;
import br.uefs.ecomp.treeStock.model.TipoAcao;
import br.uefs.ecomp.treeStock.model.exception.AcaoNaoEncontradaException;
import br.uefs.ecomp.treeStock.model.exception.ClienteNaoEncontradoException;
import br.uefs.ecomp.treeStock.util.DadoDuplicadoException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TreeStockView {
    
    public static void menu(){
        
        System.out.println();
        System.out.println("\t==========================================");
        System.out.println("\t||              TreeStock               ||");
        System.out.println("\t||   Gerenciador de Carteira de Ações   ||");
        System.out.println("\t==========================================");
        System.out.println("");
        
        System.out.println("  DIGITE A OPÇÃO DESEJADA:");
        System.out.println(" ");
        System.out.println(" 1 - Gerenciar Clientes");
        System.out.println(" 2 - Gerenciar Carteiras");
        System.out.println(" 3 - Gerenciar Ações");
        System.out.println(" 4 - Listar ou exibir");
        System.out.println(" 5 - Salvar dados");
        System.out.println(" 6 - Sair do Programa");
        System.out.print(">>>>>>   ");
    }
    
    public static void menuClientes(TreeStockFacade facade){
        int opcao;
        Scanner in = new Scanner(System.in);
        
        do{
            System.out.println("\n  DIGITE A OPÇÃO DESEJADA:");
            System.out.println(" 1 - Registrar Cliente");
            System.out.println(" 2 - Remover Cliente");
            System.out.println(" 3 - Buscar Cliente");
            System.out.println(" 4 - Voltar");
            System.out.print(">>>>>>   ");
            opcao = in.nextInt();
            
            switch(opcao){
                case 1:
                    cadastrarCliente(facade);
                    break;
                case 2:
                    removerCliente(facade);
                    break;
                case 3:
                    buscarCliente(facade);
                    break;
                case 4:
                    break;
                default:
                    System.out.println();
                    System.out.println("Valor Digitado Invalido");
            }
        }while (opcao < 1 || opcao > 4);
    }
    
    public static void cadastrarCliente(TreeStockFacade facade){
        String nome;
        String endereco;
        String cpf;
        Scanner in = new Scanner(System.in);
        
        System.out.println("\n\n  Cadastrar Cliente");
        System.out.print("   Digite o nome do cliente: ");
        nome = in.nextLine();
        System.out.print("   Digite o CPF do cliente: ");
        cpf = in.next();
        System.out.print("   Digite o endereço do cliente: ");
        in.nextLine();    //esvazia buffer do teclado
        endereco = in.nextLine();
        
        try{
            facade.cadastrarCliente(nome, cpf, endereco);
            System.out.println("\n    Cadastro de cliente bem sucedido!");
        } catch(DadoDuplicadoException e){
            System.out.println("\n    Cadastro de cliente mal sucedido, cpf já cadastrado!");
        }
    }
    
    public static void removerCliente(TreeStockFacade facade){
        String cpf;
        Scanner in = new Scanner(System.in);
        
        System.out.println("\n\n  Remover Cliente");
        System.out.print("   Digite o CPF do cliente: ");
        cpf = in.next();
        
        try{
            Cliente cliente = facade.removerCliente(cpf);
            System.out.println("\n    Cliente " + cliente.getNome() + " removido com sucesso!");
        } catch(ClienteNaoEncontradoException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void buscarCliente(TreeStockFacade facade){
        String cpf;
        Scanner in = new Scanner(System.in);
        
        System.out.println("\n\n  Buscar Cliente");
        System.out.print("   Digite o CPF do cliente: ");
        cpf = in.next();
        
        try{
            Cliente cliente = facade.buscarCliente(cpf);
            System.out.println("\n" + cliente);
        } catch(ClienteNaoEncontradoException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void menuCarteiras(TreeStockFacade facade){
        int opcao;
        Scanner in = new Scanner(System.in);
        
        do{
            System.out.println("\n  DIGITE A OPÇÃO DESEJADA:");
            System.out.println(" 1 - Inserir Ações na Carteira");
            System.out.println(" 2 - Alterar Ações na Carteira");
            System.out.println(" 3 - Remover Ações na Carteira");
            System.out.println(" 4 - Voltar");
            System.out.print(">>>>>>   ");
            opcao = in.nextInt();
            
            switch(opcao){
                case 1:
                    inserirAcoesCarteira(facade);
                    break;
                case 2:
                    alterarAcoesCarteira(facade);
                    break;
                case 3:
                    removerAcoesCarteira(facade);
                    break;
                case 4:
                    break;
                default:
                    System.out.println();
                    System.out.println("Valor Digitado Invalido");
            }
        }while (opcao < 1 || opcao > 4);
    }
    
    public static void inserirAcoesCarteira(TreeStockFacade facade){
        String cpf;
        String siglaAcao;
        int quantidade;
        Scanner in = new Scanner(System.in);
        
        System.out.println("\n\n  Inserir Ações na Carteira");
        System.out.print("   Digite o cpf do cliente: ");
        cpf = in.next();
        System.out.print("   Digite a sigla da ação: ");
        siglaAcao = in.next();
        System.out.print("   Digite a quantidade de lotes (100 ações): ");
        quantidade = in.nextInt();
        
        try{
            facade.incluirAcaoCliente(cpf, siglaAcao, quantidade);
            System.out.println("\n    Inserção de lote(s) na carteira do cliente foi bem sucedida!");
        } catch (ClienteNaoEncontradoException | AcaoNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void alterarAcoesCarteira(TreeStockFacade facade){
        String cpf;
        String siglaAcao;
        int novaQuantidade;
        Scanner in = new Scanner(System.in);
        
        System.out.println("\n\n  Alterar Ações na Carteira");
        System.out.print("   Digite o cpf do cliente: ");
        cpf = in.next();
        System.out.print("   Digite a sigla da ação: ");
        siglaAcao = in.next();
        System.out.print("   Digite a nova quantidade de lotes (100 ações): ");
        novaQuantidade = in.nextInt();
        
        try{
            facade.alterarAcaoCliente(cpf, siglaAcao, novaQuantidade);
            System.out.println("\n    Alteração de lote(s) na carteira do cliente foi bem sucedida!");
        } catch (ClienteNaoEncontradoException | AcaoNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void removerAcoesCarteira(TreeStockFacade facade){
        String cpf;
        String siglaAcao;
        int quantidade;
        Scanner in = new Scanner(System.in);
        
        System.out.println("\n\n  Remover Ações na Carteira");
        System.out.print("   Digite o cpf do cliente: ");
        cpf = in.next();
        System.out.print("   Digite a sigla da ação: ");
        siglaAcao = in.next();
        
        try{
            facade.removerAcaoCliente(cpf, siglaAcao);
            System.out.println("\n    Remoção de lote(s) na carteira do cliente foi bem sucedida!");
        } catch (ClienteNaoEncontradoException | AcaoNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void menuAcoes(TreeStockFacade facade){
        int opcao;
        Scanner in = new Scanner(System.in);
        
        do{
            System.out.println("\n  DIGITE A OPÇÃO DESEJADA:");
            System.out.println(" 1 - Cadastrar Ação");
            System.out.println(" 2 - Remover Ação");
            System.out.println(" 3 - Carregar Arquivo de Cotações");
            System.out.println(" 4 - Voltar");
            System.out.print(">>>>>>   ");
            opcao = in.nextInt();
            
            switch(opcao){
                case 1:
                    cadastrarAcao(facade);
                    break;
                case 2:
                    removerAcao(facade);
                    break;
                case 3:
                    carregarCotacao(facade);
                    break;
                case 4:
                    break;
                default:
                    System.out.println();
                    System.out.println("Valor Digitado Invalido");
            }
        }while (opcao < 1 || opcao > 4);
    }
    
    public static void cadastrarAcao(TreeStockFacade facade){
        String sigla;
        String nome;
        double valorInicial;
        int opcaoTipoAcao;
        Scanner in = new Scanner(System.in);
        
        System.out.println("\n\n  Cadastrar Acao");
        System.out.print("   Digite a sigla da empresa: ");
        sigla = in.next();
        System.out.print("   Digite o nome da empresa: ");
        in.nextLine();    //esvaziar buffer do teclado
        nome = in.nextLine();
        System.out.print("   Digite o inicial da cotação da ação: ");
        valorInicial = in.nextDouble();
        do{
            System.out.println("   Digite a opção desejada, referente ao tipo de Ação: ");
            System.out.println("    1 - Ordinária \n    2 - Preferêncial");
            System.out.print("   ");
            opcaoTipoAcao = in.nextInt();
        }while(opcaoTipoAcao != 1 && opcaoTipoAcao != 2);
        
        try{
            if(opcaoTipoAcao == 1)
                facade.cadastrarAcao(sigla, nome, valorInicial, TipoAcao.ON);
            else
                facade.cadastrarAcao(sigla, nome, valorInicial, TipoAcao.PN);
            System.out.println("\n    Cadastro de ação bem sucedido!");
        } catch(DadoDuplicadoException e){
            System.out.println("\n    Cadastro de ação mal sucedido,\n     Sigla já cadastrado!");
        }
    }
    
    public static void removerAcao(TreeStockFacade facade){
        String sigla;
        int opcaoTipoAcao;
        Scanner in = new Scanner(System.in);
        
        System.out.println("\n\n  Remover Cliente");
        System.out.print("   Digite a sigla da empresa: ");
        sigla = in.next();
        do{
            System.out.println("   Digite a opção desejada, referente ao tipo de Ação: ");
            System.out.println("    1 - Ordinária \n    2 - Preferêncial");
            System.out.print("   ");
            opcaoTipoAcao = in.nextInt();
        }while(opcaoTipoAcao != 1 && opcaoTipoAcao != 2);
        
        try{
            Acao acao;
            
            acao = facade.removerAcao(sigla);
            System.out.println("\n    Ação da empresa " + acao.getNome() + " removido com sucesso!");
        } catch(AcaoNaoEncontradaException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void carregarCotacao(TreeStockFacade facade){
        String nomeArq;
        Scanner in = new Scanner(System.in);
        
        System.out.println("\n\n  Carregar Cotação");
        System.out.print("   Digite o nome e/ou diretório do arquivo: ");
        nomeArq = in.next();
        
        try{
            facade.atualizarCotacoes(nomeArq);
            System.out.println("\n    Cotações atualizadas com sucesso!");
        }catch(FileNotFoundException e){
            System.out.println("\n    Erro no carregamento,\n     Arquivo não encontrado");
        }
    }
    
    public static void menuListar(TreeStockFacade facade){
        int opcao;
        Scanner in = new Scanner(System.in);
        
        do{
            System.out.println("\n  DIGITE A OPÇÃO DESEJADA:");
            System.out.println(" 1 - Listar TOP-K de Contas");
            System.out.println(" 2 - Listar Clientes");
            System.out.println(" 3 - Listar Carteira de Cliente");
            System.out.println(" 4 - Voltar");
            System.out.print(">>>>>>   ");
            opcao = in.nextInt();
            
            switch(opcao){
                case 1:
                    listarTopK(facade);
                    break;
                case 2:
                    listarClientes(facade);
                    break;
                case 3:
                    listarCarteira(facade);
                    break;
                case 4:
                    break;
                default:
                    System.out.println();
                    System.out.println("Valor Digitado Invalido");
            }
        }while (opcao < 1 || opcao > 4);
    }
    
    public static void listarTopK(TreeStockFacade facade){
        int k;
        Scanner in = new Scanner(System.in);
        
        System.out.println("\n\n  Listar Top-K Contas");
        do{
            System.out.print("   Digite a quantidade de posições da lista: ");
            k = in.nextInt();
            
            if(k < 0)
                System.out.println("Valor Digitado Invalido");
        }while(k < 0);
        
        try {
            Iterator it = facade.melhoresClientes(k);
            while(it.hasNext())
                System.out.println(((Carteira)it.next()));
        } catch (ClienteNaoEncontradoException | AcaoNaoEncontradaException e) {
            System.out.println("\n   Top-k deu erro,\n    Clientes e/ou Ações não encontrado");
        }
    }
    
    public static void listarClientes(TreeStockFacade facade){
        Iterator it = facade.iterator();
        
        System.out.println("\n\n  Listar Clientes\n");
        while(it.hasNext()){
            Cliente cliente = (Cliente) it.next();
            
            System.out.println(cliente);
        }
    }
    
    public static void listarCarteira(TreeStockFacade facade){
        Iterator it;
        Carteira carteira;
        String cpfCliente;
        Scanner in = new Scanner(System.in);
        
        System.out.println("\n\n  Listar Carteira de Clientes");
        System.out.print("   Digite o CPF do cliente: ");
        cpfCliente = in.next();
        
        try {
            carteira = facade.getCarteiraCliente(cpfCliente);
            it = carteira.iteratorLotes();
            System.out.println(carteira);
            
            while(it.hasNext()){
                Lote lote = (Lote) it.next();
                
                System.out.println(lote);
            }
        } catch (ClienteNaoEncontradoException e) {
            System.out.println("\n   Erro na Exibição da carteira do cliente,\n    Cliente não encontrado");
        }
    }
    
    public static void gerarArquivoSistema(TreeStockFacade facade){
        System.out.println("\n\n  Salvar Arquivo do Sistema");
        
        try {
            facade.gerarArquivoSistema();
            System.out.println("\n   Arquivo salvo com sucesso");
        } catch (IOException e) {
            System.out.println("\n   Erro no momento de salvar arquivo");
        }
    }
    
    public static void carregarArquivoSistema(TreeStockFacade facade) {
        Scanner in = new Scanner(System.in);
        int opcao;
        
        if(facade.temArquivoSistema()){
            String nomeArq = facade.nomeArquivoSistema();
            
            nomeArq = nomeArq.replace("-", "/");
            nomeArq = nomeArq.replace(".", ":");
            
            char[] dia = Arrays.copyOfRange(nomeArq.toCharArray(), 12, 21);
            char[] horario = Arrays.copyOfRange(nomeArq.toCharArray(), 21, 26);
            
            System.out.print(" Deseja Carregar o Arquivo do Sistema do dia ");
            System.out.print(dia);
            System.out.print("e do horario ");
            System.out.println(horario);
            System.out.println("  1 - Sim \n  2 - Não");
            do{
                System.out.print(" >>> ");
                opcao = in.nextInt();
                if(opcao != 1 && opcao != 2)
                    System.out.println("\n Valor inválido, digite novamente");
            } while(opcao != 1 && opcao != 2);
            
            if(opcao == 1){
                try {
                    facade.carregarArquivoSistema();
                } catch (IOException ex) {
                    Logger.getLogger(TreeStockView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(TreeStockView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public static void main(String[] args){
        int opcaoMenu;
        TreeStockFacade facade = new TreeStockFacade();
        Scanner in = new Scanner(System.in);
        
        carregarArquivoSistema(facade);
        
        do{
            menu();
            opcaoMenu = in.nextInt();
            System.out.println("");
            
            switch(opcaoMenu){
                case 1:
                    System.out.println(" MENU de CLIENTES:");
                    menuClientes(facade);
                    break;
                case 2:
                    System.out.println(" MENU de CARTEIRAS:");
                    menuCarteiras(facade);
                    break;
                case 3:
                    System.out.println(" MENU de AÇÕES:");
                    menuAcoes(facade);
                    break;
                case 4:
                    System.out.println(" MENU de EXIBIÇÃO:");
                    menuListar(facade);
                    break;
                case 5:
                    System.out.println(" SALVAR DADOS do SISTEMA:");
                    gerarArquivoSistema(facade);
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Valor Digitado Invalido");
                    System.out.println();
            }
            
        } while(opcaoMenu != 6);
    }
}
