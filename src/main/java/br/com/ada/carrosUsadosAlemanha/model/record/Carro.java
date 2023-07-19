package br.com.ada.carrosUsadosAlemanha.model.record;

import java.time.LocalDate;
import java.util.Objects;

public record Carro(Integer id,String marca,String modelo,String cor,LocalDate dataRegisto,Double valorEuro) implements Comparable<Carro> {

	@Override
	public int hashCode() {
		return Objects.hash(cor, marca, modelo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carro other = (Carro) obj;
		return Objects.equals(cor, other.cor)
				&& Objects.equals(marca, other.marca)
				&& Objects.equals(modelo, other.modelo);
	}

	@Override
    public int compareTo(Carro car) {
        // Primeiro, compara pela marca
        int marcaComparison = this.marca.compareTo(car.marca);
        if (marcaComparison != 0) {
            return marcaComparison;
        }

        // Se a marca for igual, compara pelo modelo
        return this.modelo.compareTo(car.modelo);
    }

}
