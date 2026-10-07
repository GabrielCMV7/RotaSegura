package br.com.rotasegura.model;

public class Sedan extends Veiculo {

    public Sedan(String placa, String modelo, int ano) {
        super(placa, modelo, ano);
    }

    @Override
    public double calcularDiaria() {
        return 170.00;
    }

    @Override
    public double calcularSeguro() {
        return 40.00;
    }

    @Override
    public double calcularManutencao() {
        return 25.00;
    }
}