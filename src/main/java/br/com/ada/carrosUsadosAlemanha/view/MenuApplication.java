package br.com.ada.carrosUsadosAlemanha.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;

import br.com.ada.carrosUsadosAlemanha.controller.CarrosUsados;
import br.com.ada.carrosUsadosAlemanha.model.record.Carro;
import br.com.ada.carrosUsadosAlemanha.util.Constantes;

public class MenuApplication {

	private ScannerConsole leitor;
	
	CarrosUsados car = new CarrosUsados();

	public MenuApplication(ScannerConsole leitor) {
		this.leitor = leitor;
		iniciaApp();
	}

	private void iniciaApp() {
		carregaNomeApp();
		carregaArquivo();
	}

	private void carregaNomeApp() {
		System.out.println("******************************************");
		System.out.println("******* CADASTRO DE CARROS USADOS ********");
		System.out.println("******************************************");
	}
	
	private void carregaArquivo() {
		
		car.carregaCSV();
	}

	public void processar() {

		String opcaoDigitada = obterEntradaDoUsuario(leitor);

		while (!escolheuSair(opcaoDigitada)) {
			tratarOpcaoSelecionada(opcaoDigitada);
			opcaoDigitada = obterEntradaDoUsuario(leitor);
		}

		finalizaApp();

	}
	
	private String obterEntradaDoUsuario(ScannerConsole leitor){
        carregaMenu();
        System.out.print(Constantes.DIGITE_OPCAO_DESEJADA);
        return leitor.obterEntrada().toLowerCase();
    }
	
	private boolean escolheuSair(String opcaoDigitada){
        return opcaoDigitada.equals(Constantes.OPCAO_SAIR);
    }
	
	private void tratarOpcaoSelecionada(String opcaoDigitada) {
        switch (opcaoDigitada){
            case Constantes.OPCAO_SAIR:
                break;
            case Constantes.OPCAO_CADASTRAR_CARRO:
                this.cadastrarCarro();
                System.out.println("Cadastro realizado com sucesso!");
                pularLinha(2);
                break;
            case Constantes.OPCAO_LISTAR_CARROS:
                car.listarCarros();
                pularLinha(2);
                break;
            case Constantes.OPCAO_LISTAR_CARROS_N_DUPLICADOS:
                car.listarCarrosNaoDuplicados();
                pularLinha(2);
                break;
            case Constantes.OPCAO_ORDENAR_POR_MARCA_MODELO:
            	car.ordenarCarrosPorMarcaEModelo();
            	pularLinha(2);
            	break;
            case Constantes.OPCAO_BUSCA_POR_ID:
                this.buscaPorIdHashMap();
                break;
            case Constantes.OPCAO_BUSCA_POR_MODELO:
                this.buscaPorModeloHashMap();
                break;
            default:
                opcaoInvalida();
                break;
        }
    }
	
	private void cadastrarCarro() {
	    System.out.println("Cadastro de novo carro usado!");
	    System.out.print("Informe a marca do carro: ");
	    String marca = leitor.obterEntrada();
	    System.out.print("Informe o modelo do carro: ");
	    String modelo = leitor.obterEntrada();
	    System.out.print("Informe a cor do carro: ");
	    String cor = leitor.obterEntrada();
	    System.out.print("Informe a data de registro do carro (MM/yyyy): ");
	    String dataRegistoStr = leitor.obterEntrada();
	    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("MM/yyyy")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();
	    LocalDate dataRegisto = LocalDate.parse(dataRegistoStr, formatter);
	    System.out.print("Informe o valor em euros do carro: ");
	    double valorEuro = leitor.obterEntradaAsDouble();

	    Carro carro = new Carro(null, marca, modelo, cor, dataRegisto, valorEuro);
	    car.cadastrarCarro(carro);

	    System.out.println("Cadastro realizado com sucesso!");
	    pularLinha(2);
	}

	
	private void buscaPorModeloHashMap() {
		System.out.print("Digite o modelo do carro: ");
		String modelo = leitor.obterEntrada();

		List<Carro> carrosEncontrados = car.buscarCarrosPorModelo(modelo);
		// Exibir os carros encontrados
		if (carrosEncontrados == null || carrosEncontrados.isEmpty()) {
	        System.out.println("Nenhum carro encontrado com o modelo informado: " + modelo);
	    } else {
	    	for (Carro carro : carrosEncontrados) {
	    	    System.out.println(carro);
	    	}
	    }
		
	}

	private void buscaPorIdHashMap() {
		System.out.print("Digite o id do carro: ");
        Integer id = leitor.obterEntradaAsInt();
        Carro car = this.car.buscaCarroPorId(id);
        if(car != null){
            System.out.println("Carro localizado!");
            System.out.println(car);
        } else {
            System.out.println("Nenhum carro localizado para o id: " + id);
        }
	}

	private void carregaMenu() {
        System.out.println("********  DIGITE A OPÇÃO DESEJADA   ******");
        System.out.println("1 - CADASTRAR CARRO");
        System.out.println("2 - LISTAR CARROS");
        System.out.println("3 - LISTAR CARROS NÃO DUPLICADOS");
        System.out.println("4 - ORDENAR CARROS POR MARCA E MODELOS");
        System.out.println("5 - PESQUISAR POR ID");
        System.out.println("6 - PESQUISAR POR MODELO");
        System.out.println("X - SAIR");
    }
	
	private void opcaoInvalida(){
        System.out.println("Opção INVÁLIDA. Tente novamente.");
    }
	
	private void finalizaApp(){
        System.out.println("Até A Próxima!!");
    }
	
	public void pularLinha(int numeroDeLinhas){
        for (int i = 1; i <= numeroDeLinhas; i++) {
            System.out.println();
        }
    }

}
