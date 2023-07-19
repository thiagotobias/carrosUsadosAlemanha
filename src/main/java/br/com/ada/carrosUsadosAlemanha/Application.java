package br.com.ada.carrosUsadosAlemanha;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.ada.carrosUsadosAlemanha.view.MenuApplication;
import br.com.ada.carrosUsadosAlemanha.view.ScannerConsole;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		
		try(ScannerConsole leitor = new ScannerConsole()) {
            new MenuApplication(leitor).processar();
        }
	}

}
