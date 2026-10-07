package br.com.rotasegura.model;

import br.com.rotasegura.interfaces.Relatorio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Contrato implements Relatorio {

    private Cliente cliente;
    private Veiculo veiculo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private double valorTotal;
    private boolean finalizado;

    public Contrato(Cliente cliente, Veiculo veiculo,
                    LocalDate dataInicio, LocalDate dataFim) {

        if (cliente == null) {
            throw new IllegalArgumentException("O cliente não pode ser nulo.");
        }

        if (veiculo == null) {
            throw new IllegalArgumentException("O veículo não pode ser nulo.");
        }

        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException(
                    "As datas não podem ser nulas."
            );
        }

        if (dataInicio.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "A data de retirada não pode ser anterior à data atual."
            );
        }

        if (dataFim.isBefore(dataInicio)) {
            throw new IllegalArgumentException(
                    "A data de devolução não pode ser anterior à data de retirada."
            );
        }

        if (!veiculo.isDisponivel()) {
            throw new IllegalStateException(
                    "O veículo está ocupado ou indisponível."
            );
        }

        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.finalizado = false;

        calcularValorTotal();

        veiculo.ocupar();
    }

    private void calcularValorTotal() {

        long quantidadeDias = ChronoUnit.DAYS.between(dataInicio, dataFim);

        // Uma locação de apenas um dia deve cobrar uma diária.
        if (quantidadeDias == 0) {
            quantidadeDias = 1;
        }

        double valorDiarias =
                quantidadeDias * veiculo.calcularDiaria();

        double valorSeguro =
                quantidadeDias * veiculo.calcularSeguro();

        double valorManutencao =
                quantidadeDias * veiculo.calcularManutencao();

        this.valorTotal =
                valorDiarias + valorSeguro + valorManutencao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void finalizar() {

        if (finalizado) {
            throw new IllegalStateException(
                    "Este contrato já foi finalizado."
            );
        }

        veiculo.liberar();
        finalizado = true;
    }


    @Override
    public String gerarRelatorio() {

        return "===== CONTRATO DE LOCAÇÃO =====\n" +
                "Cliente: " + cliente.getNome() + "\n" +
                "Veículo: " + veiculo.getModelo() + "\n" +
                "Placa: " + veiculo.getPlaca() + "\n" +
                "Data de início: " + dataInicio + "\n" +
                "Data de fim: " + dataFim + "\n" +
                "Valor total: R$ " + valorTotal + "\n" +
                "================================";
    }
}