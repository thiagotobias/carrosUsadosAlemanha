package br.com.ada.carrosUsadosAlemanha.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import br.com.ada.carrosUsadosAlemanha.model.record.Carro;

public class CSVReader {
	
	private Integer maiorIdCarro = 0;

    public List<Carro> readCSVFile() {
    	List<Carro> cars = new ArrayList<>();
        
    	try (BufferedReader br = new BufferedReader(new FileReader(Constantes.CSV_FILE_PATH))) {
        	br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
            	Carro car = parseCarFromCSVLine(line);
            	if(car != null) {
            		cars.add(car);
            	}
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }
        
        return cars;
    }

    private Carro parseCarFromCSVLine(String line) {
    	try {
    		String[] tokens = line.split(Constantes.CSV_DELIMITER);
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("MM/yyyy")
                    .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                    .toFormatter();
            
            Integer id = Integer.parseInt(removeQuotes(tokens[0]));
            
            String marca = removeQuotes(tokens[1]);
            String modelo = removeQuotes(tokens[2]);
            String cor = removeQuotes(tokens[3]);
            LocalDate dataRegisto = LocalDate.parse(removeQuotes(tokens[4]), formatter);
            Double valorEuro = Double.parseDouble(removeQuotes(tokens[6]));

            // Atualizar o maiorIdCarro, se necessário
            if (id > maiorIdCarro) {
                maiorIdCarro = id;
            }
            return new Carro(id, marca, modelo, cor, dataRegisto, valorEuro);
		} catch (Exception e) {
			e.getStackTrace();
		} 
        return null;

    }
    
    private String removeQuotes(String value) {
        if (value.startsWith(Constantes.CSV_QUOTE) && value.endsWith(Constantes.CSV_QUOTE)) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }
    
 // Getter para obter o maiorIdCarro
    public Integer getMaiorIdCarro() {
        return maiorIdCarro;
    }
}
