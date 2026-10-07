package br.com.rotasegura.model;

public class SUV extends Veiculo {

    public SUV(String placa, String modelo, int ano) {
        super(placa, modelo, ano);
    }

    @Override
    public double calcularDiaria() {
        return 250.00;
    }

    @Override
    public double calcularSeguro() {
        return 55.00;
    }

    @Override
    public double calcularManutencao() {
        return 40.00;
    }
}