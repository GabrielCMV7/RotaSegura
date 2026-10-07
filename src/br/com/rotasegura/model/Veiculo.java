package br.com.rotasegura.model;

public abstract class Veiculo {

    private String placa;
    private String modelo;
    private int ano;
    private boolean disponivel;

    public Veiculo(String placa, String modelo, int ano) {
        setPlaca(placa);
        setModelo(modelo);
        setAno(ano);
        this.disponivel = true;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {

        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException(
                    "A placa não pode ser vazia."
            );
        }

        String placaLimpa =
                placa.trim()
                        .toUpperCase()
                        .replaceAll("[^A-Z0-9]", "");

        if (!placaLimpa.matches("[A-Z]{3}[0-9][A-Z0-9][0-9]{2}")) {

            throw new IllegalArgumentException(
                    "Placa inválida. Use o formato ABC1D23."
            );
        }

        this.placa = placaLimpa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException("O modelo não pode ser vazio.");
        }

        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {

        int anoAtual = java.time.Year.now().getValue();

        if (ano < 2000 || ano > anoAtual + 1) {
            throw new IllegalArgumentException(
                    "Ano do veículo inválido. "
                            + "Informe um ano entre 2000 e "
                            + (anoAtual + 1) + "."
            );
        }

        this.ano = ano;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void ocupar() {
        if (!disponivel) {
            throw new IllegalStateException(
                    "O veículo já está indisponível."
            );
        }

        disponivel = false;
    }

    public void liberar() {
        if (disponivel) {
            throw new IllegalStateException(
                    "O veículo já está disponível."
            );
        }

        disponivel = true;
    }

    public abstract double calcularDiaria();

    public abstract double calcularSeguro();

    public abstract double calcularManutencao();
}
