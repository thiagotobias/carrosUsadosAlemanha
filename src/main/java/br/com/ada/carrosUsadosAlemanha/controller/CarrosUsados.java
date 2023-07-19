package br.com.ada.carrosUsadosAlemanha.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.com.ada.carrosUsadosAlemanha.model.record.Carro;
import br.com.ada.carrosUsadosAlemanha.util.CSVReader;

public class CarrosUsados {
	private List<Carro> carList = new ArrayList<>();
	private List<Carro> removeDuplicadoCarList = new ArrayList<>();
	private List<Carro> carrosOrdenados = new ArrayList<>();
	private List<Carro> carLinkedList = new LinkedList<>();
	private Map<Integer, Carro> mapIdCarro = new HashMap<>();
	private Map<String, List<Carro>> mapCarroModelo = new HashMap<>();
	
	private Integer maiorIdCarro = 0; // Acompanha o maior ID atual dos carros


	public void carregaCSV() {
		CSVReader csvReader = new CSVReader();
		carList = csvReader.readCSVFile();
		this.maiorIdCarro = csvReader.getMaiorIdCarro();
		removeDuplicadoCarList = removerDuplicados(carList);
		inserirCarroUsados(removeDuplicadoCarList);
		
	}
	
	private List<Carro> removerDuplicados(List<Carro> lista){
        return new ArrayList<>(new HashSet<>(lista));
    }
	
	private void inserirCarroUsados(List<Carro> cars){
        for (Carro car: cars){
        	inserirCarro(car);
        }
    }
	
	private void inserirCarro(Carro car){
        this.carLinkedList.add(car);
        this.mapIdCarro.put(car.id(), car);
        String[] modelos = car.modelo().split(" ");

        for (String modelo : modelos){
            modelo = modelo.toLowerCase();
            List<Carro> cars = mapCarroModelo.get(modelo);

            if(cars != null){
                cars.add(car);
            } else {
                List<Carro> carsAux = new ArrayList<>();
                carsAux.add(car);
                mapCarroModelo.put(modelo, carsAux);
            }
        }
    }
	
	public void listarCarros(){
		StringBuilder sb = new StringBuilder();

        if (carList.isEmpty()) {
            sb.append("[]");
        } else {
            sb.append("[\n");
            for (Carro car : carList) {
                sb.append("\t").append(car).append(",\n");
            }
            sb.setLength(sb.length() - 2); // Remover a vírgula extra após o último carro
            sb.append("\n]");
        }

        System.out.println(sb);
	}

	public void listarCarrosNaoDuplicados() {
		StringBuilder sb = new StringBuilder();

		if (removeDuplicadoCarList.isEmpty()) {
            sb.append("[]");
        } else {
            sb.append("[\n");
            for (Carro car : removeDuplicadoCarList) {
                sb.append("\t").append(car).append(",\n");
            }
            sb.setLength(sb.length() - 2); // Remover a vírgula extra após o último carro
            sb.append("\n]");
        }

        System.out.println(sb);
		
	}
	
	public Carro buscaCarroPorId(Integer id){
        Carro car = this.mapIdCarro.get(id);
        return car;
    }
	
	public void ordenarCarrosPorMarcaEModelo() {
		carrosOrdenados = new ArrayList<>(removeDuplicadoCarList); // Copia a lista original para a nova lista
        Collections.sort(carrosOrdenados); // Ordena a nova lista de carros usando o compareTo()
        listarCarrosOrdenados();
    }
	
	private void listarCarrosOrdenados() {
	    StringBuilder sb = new StringBuilder();

	    if (carrosOrdenados.isEmpty()) {
	        sb.append("Lista vazia.");
	    } else {
	        sb.append("Carros Ordenados:\n");
	        for (Carro car : carrosOrdenados) {
	            sb.append("\t").append(car).append(",\n");
	        }
	        sb.setLength(sb.length() - 2); // Remover a vírgula extra após o último carro
	    }

	    System.out.println(sb);
	}
	
	public List<Carro> buscarCarrosPorModelo(String modelo) {
	    List<Carro> carrosEncontrados = mapCarroModelo.get(modelo);
	    
	    if (carrosEncontrados == null) {
	        return null; // Retorna uma lista vazia quando não encontrar o modelo
	    } else {
	        return new ArrayList<>(carrosEncontrados);
	    }
	}
	
	private int obterProximoIdCarro() {
        // Verifica o próximo ID disponível com base no maior ID atual
        return maiorIdCarro + 1;
    }

    public void cadastrarCarro(Carro car) {
        int novoId = obterProximoIdCarro();

        // Criar um novo carro com o ID correto
        Carro novoCarro = new Carro(novoId, car.marca(), car.modelo(), car.cor(), car.dataRegisto(), car.valorEuro());

        // Adicionar o novo carro nas listas carList e removeDuplicadoCarList
        carList.add(novoCarro);
        removeDuplicadoCarList.add(novoCarro);

        // Adicionar o novo carro na lista carLinkedList
        carLinkedList.add(novoCarro);

        // Adicionar o novo carro no mapa mapIdCarro
        mapIdCarro.put(novoId, novoCarro);

        // Atualizar o valor de maiorIdCarro
        maiorIdCarro = novoId;

        // Atualizar o mapa mapCarroModelo
        String[] modelos = novoCarro.modelo().split(" ");
        for (String modelo : modelos) {
            modelo = modelo.toLowerCase();
            List<Carro> cars = mapCarroModelo.get(modelo);

            if (cars != null) {
                cars.add(novoCarro);
            } else {
                List<Carro> carsAux = new ArrayList<>();
                carsAux.add(novoCarro);
                mapCarroModelo.put(modelo, carsAux);
            }
        }
    }
	
}
