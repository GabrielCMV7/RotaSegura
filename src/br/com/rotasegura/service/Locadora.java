package br.com.rotasegura.service;

import br.com.rotasegura.model.Cliente;
import br.com.rotasegura.model.Contrato;
import br.com.rotasegura.model.Veiculo;

import java.time.LocalDate;
import java.util.List;

public class Locadora {

    private final Repositorio<Cliente> clientes;
    private final Repositorio<Veiculo> veiculos;
    private final Repositorio<Contrato> contratos;

    public Locadora() {
        clientes = new Repositorio<>();
        veiculos = new Repositorio<>();
        contratos = new Repositorio<>();
    }

    public void cadastrarCliente(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "O cliente não pode ser nulo."
            );
        }

        if (buscarClientePorCpf(cliente.getCpf()) != null) {
            throw new IllegalArgumentException(
                    "Já existe um cliente cadastrado com este CPF."
            );
        }

        clientes.adicionar(cliente);
    }

    public void cadastrarVeiculo(Veiculo veiculo) {

        if (veiculo == null) {
            throw new IllegalArgumentException(
                    "O veículo não pode ser nulo."
            );
        }

        if (buscarVeiculoPorPlaca(veiculo.getPlaca()) != null) {
            throw new IllegalArgumentException(
                    "Já existe um veículo cadastrado com esta placa."
            );
        }

        veiculos.adicionar(veiculo);
    }

    public Contrato realizarLocacao(
            String cpf,
            String placa,
            LocalDate dataInicio,
            LocalDate dataFim) {

        Cliente cliente = buscarClientePorCpf(cpf);

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "Cliente não encontrado."
            );
        }

        Veiculo veiculo = buscarVeiculoPorPlaca(placa);

        if (veiculo == null) {
            throw new IllegalArgumentException(
                    "Veículo não encontrado."
            );
        }

        Contrato contrato = new Contrato(
                cliente,
                veiculo,
                dataInicio,
                dataFim
        );

        contratos.adicionar(contrato);

        return contrato;
    }

    public void adicionarContratoRestaurado(
            Contrato contrato) {

        if (contrato == null) {
            throw new IllegalArgumentException(
                    "O contrato não pode ser nulo."
            );
        }

        contratos.adicionar(contrato);
    }

    public void devolverVeiculo(String placa) {

        Veiculo veiculo = buscarVeiculoPorPlaca(placa);

        if (veiculo == null) {
            throw new IllegalArgumentException(
                    "Veículo não encontrado."
            );
        }

        if (veiculo.isDisponivel()) {
            throw new IllegalStateException(
                    "Este veículo já está disponível."
            );
        }

        for (Contrato contrato : contratos.listar()) {

            if (!contrato.isFinalizado()
                    && contrato.getVeiculo().getPlaca()
                    .equalsIgnoreCase(placa)) {

                contrato.finalizar();
                return;
            }
        }

        throw new IllegalStateException(
                "Não foi encontrado contrato ativo para este veículo."
        );
    }

    public Cliente buscarClientePorCpf(String cpf) {

        if (cpf == null) {
            return null;
        }

        for (Cliente cliente : clientes.listar()) {

            if (cliente.getCpf().equals(
                    cpf.replaceAll("\\D", ""))) {

                return cliente;
            }
        }

        return null;
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) {

        if (placa == null) {
            return null;
        }

        String placaLimpa =
                placa.trim()
                        .toUpperCase()
                        .replaceAll("[^A-Z0-9]", "");

        for (Veiculo veiculo : veiculos.listar()) {

            if (veiculo.getPlaca().equalsIgnoreCase(placaLimpa)) {
                return veiculo;
            }
        }

        return null;
    }

    public List<Cliente> listarClientes() {
        return clientes.listar();
    }

    public List<Veiculo> listarVeiculos() {
        return veiculos.listar();
    }

    public List<Contrato> listarContratos() {
        return contratos.listar();
    }

    public void exibirResumo() {

        System.out.println();
        System.out.println("===== RESUMO DA LOCADORA =====");
        System.out.println(
                "Clientes cadastrados: "
                        + clientes.tamanho()
        );
        System.out.println(
                "Veículos cadastrados: "
                        + veiculos.tamanho()
        );
        System.out.println(
                "Contratos registrados: "
                        + contratos.tamanho()
        );
        System.out.println("==============================");
    }
}