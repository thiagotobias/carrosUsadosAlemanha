package br.com.ada.carrosUsadosAlemanha.view;

import java.util.Locale;
import java.util.Scanner;

public class ScannerConsole implements AutoCloseable {

    private Scanner scanner;

    public ScannerConsole(){
        scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);
    }

    public String obterEntrada(){
        return scanner.nextLine();
    }

    public Double obterEntradaAsDouble(){
        double retorno = scanner.nextDouble();
        scanner.nextLine(); // limpar buffer
        return retorno;
    }

    public Integer obterEntradaAsInt(){
        Integer retorno = scanner.nextInt();
        scanner.nextLine(); // limpar buffer
        return retorno;
    }

    @Override
    public void close() {
        scanner.close();
    }
}
