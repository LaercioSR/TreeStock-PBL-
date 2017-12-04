package br.uefs.ecomp.treeStock.view;

import br.uefs.ecomp.treeStock.facade.TreeStockFacade;
import br.uefs.ecomp.treeStock.model.Acao;
import br.uefs.ecomp.treeStock.model.Carteira;
import br.uefs.ecomp.treeStock.model.Cliente;
import br.uefs.ecomp.treeStock.model.Lote;
import br.uefs.ecomp.treeStock.model.TipoAcao;
import br.uefs.ecomp.treeStock.exceptions.AcaoNaoEncontradaException;
import br.uefs.ecomp.treeStock.exceptions.ClienteNaoEncontradoException;
import br.uefs.ecomp.treeStock.exceptions.DadoDuplicadoException;
import br.uefs.ecomp.treeStock.exceptions.NumeroClientesInsuficienteException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

/**
 * A classe {@code TreeStockView} é responsável por toda parte de
 * visualização dos dados do projeto. Classe encarregada de interagir
 * com o usuário
 */
public class TreeStockView {
    
    /**
     * Método responsável por exibir o menu principal na tela
     */
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
    
    /**
     * Método exibe o menu de clientes, sendo encarregado por chamar os métodos
     * referente a escolha do usuário
     * @param facade Façade do programa
     */
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
    
    /**
     * Método responsável por receber do usuário os dados de cadastro de um 
     * novo cliente, além de chamar o método referente ao cadastro de cliente do
     * façade
     * @param facade Façade do programa
     */
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
    
    /**
     * Método responsável por receber do usuário o CPF cliente que deverá ser
     * excluido do sistema, além de chamar o método referente à remoção de cliente 
     * do façade
     * @param facade Façade do programa
     */
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
    
    /**
     * Método responsável por receber do usuário o CPF cliente que deverá ser
     * buscado no sistema, além de chamar o método referente à busca de cliente 
     * do façade
     * @param facade Façade do programa
     */
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
    
    /**
     * Método exibe o menu de carteiras, sendo encarregado por chamar os métodos
     * referente a escolha do usuário
     * @param facade Façade do programa
     */
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
    
    /**
     * Método responsável por receber do usuário os dados para inserção de Lotes (100 ações) 
     * na carteira de um cliente, além de chamar o método referente à inserção de ações na 
     * carteira de um cliente do façade
     * @param facade Façade do programa
     */
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
    
    /**
     * Método responsável por receber do usuário os dados para alteração de Lotes (100 ações) 
     * na carteira de um cliente, além de chamar o método referente à alteração de ações na 
     * carteira de um cliente do façade
     * @param facade Façade do programa
     */
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
            facade.setQuantidadeAcaoCliente(cpf, siglaAcao, novaQuantidade);
            System.out.println("\n    Alteração de lote(s) na carteira do cliente foi bem sucedida!");
        } catch (ClienteNaoEncontradoException | AcaoNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Método responsável por receber do usuário os dados para remoção de Lotes (100 ações) 
     * na carteira de um cliente, além de chamar o método referente à remmoção de ações na 
     * carteira de um cliente do façade
     * @param facade Façade do programa
     */
    public static void removerAcoesCarteira(TreeStockFacade facade){
        String cpf;
        String siglaAcao;
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
    
    /**
     * Método exibe o menu de ações, sendo encarregado por chamar os métodos
     * referente a escolha do usuário
     * @param facade Façade do programa
     */
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
    
    /**
     * Método responsável por receber do usuário os dados para o cadastro de uma ação
     * no sistema, além de chamar o método refente ao cadastro de ações do façade
     * @param facade Façade do programa
     */
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
    
    /**
     * Método responsável por receber do usuário a sigla de uma ação para remover
     * do sistema, além de chamar o método refente à remoção de ações do façade
     * @param facade Façade do programa
     */
    public static void removerAcao(TreeStockFacade facade){
        String sigla;
        Scanner in = new Scanner(System.in);
        
        System.out.println("\n\n  Remover Cliente");
        System.out.print("   Digite a sigla da empresa: ");
        sigla = in.next();
        
        try{
            Acao acao;
            
            acao = facade.removerAcao(sigla);
            System.out.println("\n    Ação da empresa " + acao.getNome() + " removido com sucesso!");
        } catch(AcaoNaoEncontradaException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Método responsável por receber do usuário o nome (e/ou dirétorio) do arquivo com
     * as cotações das ações, além de chamar o método refente ao carregamento de cotações
     * do façade
     * @param facade Façade do programa
     */
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
    
    /**
     * Método exibe o menu de listagem e exição, sendo encarregado por chamar 
     * os métodos referente a escolha do usuário
     * @param facade Façade do programa
     */
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
    
    
    /**
     * Método responsável por receber do usuário o número de clientes para o TOP-K
     * (k maiores carteiras)
     * @param facade Façade do programa
     */
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
        } catch (ClienteNaoEncontradoException e) {
            System.out.println("\n   Top-k deu erro,\n    Não existe clientes cadastrados");
        } catch (NumeroClientesInsuficienteException e) {
            System.out.println("\n   Top-k deu erro,\n    Número de clientes é insuficiente");
        }
    }
    
    /**
     * Método responsável por exibir em ordem crescente de CPF todos os clientes
     * cadastrados no sistema
     * @param facade Façade do programa
     */
    public static void listarClientes(TreeStockFacade facade){
        Iterator it = facade.iterator();
        
        System.out.println("\n\n  Listar Clientes\n");
        while(it.hasNext()){
            Cliente cliente = (Cliente) it.next();
            
            System.out.println(cliente);
        }
    }
    
    /**
     * Método responsável por receber do usuário o CPF do cliente que deseja ver
     * os dados da carteira
     * @param facade Façade do programa
     */
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
    
    /**
     * Método chama o método do façade referente à gerar arquivo do sistema e
     * exibe se o processo deu certo ou não
     * @param facade Façade do programa
     */
    public static void gerarArquivoSistema(TreeStockFacade facade){
        System.out.println("\n\n  Salvar Arquivo do Sistema");
        
        try {
            facade.gerarArquivoSistema();
            System.out.println("\n   Arquivo salvo com sucesso");
        } catch (IOException e) {
            System.out.println("\n   Erro no momento de salvar arquivo");
        }
    }
    
    /**
     * Método chama métodos do façade para buscar se existe arquivo de salvamento
     * so sitema, a data e o horário do salvamento desse possivél arquivo e pergunta
     * ao usuário se ele deseja carregar o sistema salvo, caso exista
     * @param facade Façade do programa
     */
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
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("\n   Erro no carregamento do arquivo do sistema");
                }
            }
        }
    }
    
    /**
     * Classe principal do projeto, responsável por iniciar a cascata de chamadas
     * de outros métodos
     * @param args Paramêtro padrão do java
     */
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
