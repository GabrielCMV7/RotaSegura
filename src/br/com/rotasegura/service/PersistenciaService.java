package br.com.rotasegura.service;

import br.com.rotasegura.model.Cliente;
import br.com.rotasegura.model.Contrato;
import br.com.rotasegura.model.Popular;
import br.com.rotasegura.model.SUV;
import br.com.rotasegura.model.Sedan;
import br.com.rotasegura.model.Veiculo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class PersistenciaService {

    private static final String ARQUIVO = "dados_rotasegura.txt";


    // =========================================================
    // SALVAR
    // =========================================================

    public void salvar(Locadora locadora) {

        try {

            StringBuilder dados = new StringBuilder();

            dados.append("ROTASEGURA_V1\n");

            // CLIENTES
            dados.append("[CLIENTES]\n");

            for (Cliente cliente : locadora.listarClientes()) {

                dados.append(cliente.getNome())
                        .append("|")
                        .append(cliente.getCpf())
                        .append("|")
                        .append(cliente.getTelefone())
                        .append("|")
                        .append(cliente.getEmail())
                        .append("\n");
            }

            // VEÍCULOS
            dados.append("[VEICULOS]\n");

            for (Veiculo veiculo : locadora.listarVeiculos()) {

                dados.append(
                                veiculo.getClass().getSimpleName()
                        )
                        .append("|")
                        .append(veiculo.getPlaca())
                        .append("|")
                        .append(veiculo.getModelo())
                        .append("|")
                        .append(veiculo.getAno())
                        .append("|")
                        .append(veiculo.isDisponivel())
                        .append("\n");
            }

            // CONTRATOS
            dados.append("[CONTRATOS]\n");

            for (Contrato contrato : locadora.listarContratos()) {

                dados.append(contrato.getCliente().getCpf())
                        .append("|")
                        .append(contrato.getVeiculo().getPlaca())
                        .append("|")
                        .append(contrato.getDataInicio())
                        .append("|")
                        .append(contrato.getDataFim())
                        .append("|")
                        .append(contrato.isFinalizado())
                        .append("\n");
            }

            Files.writeString(
                    Path.of(ARQUIVO),
                    dados.toString()
            );

            System.out.println(
                    "Dados salvos com sucesso em: " + ARQUIVO
            );

        } catch (IOException e) {

            System.out.println(
                    "Erro ao salvar os dados: "
                            + e.getMessage()
            );
        }
    }


    // =========================================================
    // CARREGAR
    // =========================================================

    public void carregar(Locadora locadora) {

        Path caminho = Path.of(ARQUIVO);

        if (!Files.exists(caminho)) {
            System.out.println(
                    "Nenhum arquivo de dados encontrado."
            );
            return;
        }

        try {

            List<String> linhas =
                    Files.readAllLines(caminho);

            String secao = "";

            // Primeiro carregamos clientes e veículos.
            for (String linha : linhas) {

                linha = linha.trim();

                if (linha.isEmpty()) {
                    continue;
                }

                if (linha.equals("[CLIENTES]")) {
                    secao = "CLIENTES";
                    continue;
                }

                if (linha.equals("[VEICULOS]")) {
                    secao = "VEICULOS";
                    continue;
                }

                if (linha.equals("[CONTRATOS]")) {
                    secao = "CONTRATOS";
                    continue;
                }

                if (linha.equals("ROTASEGURA_V1")) {
                    continue;
                }

                String[] dados = linha.split("\\|");

                if (secao.equals("CLIENTES")) {

                    carregarCliente(
                            locadora,
                            dados
                    );

                } else if (secao.equals("VEICULOS")) {

                    carregarVeiculo(
                            locadora,
                            dados
                    );
                }
            }

            // Depois carregamos os contratos.
            for (String linha : linhas) {

                linha = linha.trim();

                if (linha.isEmpty()
                        || linha.equals("ROTASEGURA_V1")
                        || linha.equals("[CLIENTES]")
                        || linha.equals("[VEICULOS]")
                        || linha.equals("[CONTRATOS]")) {

                    continue;
                }

                // Descobre se estamos na seção de contratos
                // verificando o marcador antes da linha.
            }

            carregarContratos(locadora, linhas);

            System.out.println(
                    "Dados carregados com sucesso."
            );

        } catch (IOException e) {

            System.out.println(
                    "Erro ao carregar os dados: "
                            + e.getMessage()
            );

        } catch (RuntimeException e) {

            System.out.println(
                    "Erro nos dados salvos: "
                            + e.getMessage()
            );
        }
    }


    // =========================================================
    // CARREGAR CLIENTE
    // =========================================================

    private void carregarCliente(
            Locadora locadora,
            String[] dados) {

        if (dados.length < 4) {
            return;
        }

        Cliente cliente = new Cliente(
                dados[0],
                dados[1],
                dados[2],
                dados[3]
        );

        if (locadora.buscarClientePorCpf(
                cliente.getCpf()) == null) {

            locadora.cadastrarCliente(cliente);
        }
    }


    // =========================================================
    // CARREGAR VEÍCULO
    // =========================================================

    private void carregarVeiculo(
            Locadora locadora,
            String[] dados) {

        if (dados.length < 5) {
            return;
        }

        String tipo = dados[0];
        String placa = dados[1];
        String modelo = dados[2];
        int ano = Integer.parseInt(dados[3]);

        Veiculo veiculo;

        switch (tipo) {

            case "Popular":
                veiculo = new Popular(
                        placa,
                        modelo,
                        ano
                );
                break;

            case "Sedan":
                veiculo = new Sedan(
                        placa,
                        modelo,
                        ano
                );
                break;

            case "SUV":
                veiculo = new SUV(
                        placa,
                        modelo,
                        ano
                );
                break;

            default:
                throw new IllegalArgumentException(
                        "Tipo de veículo desconhecido: "
                                + tipo
                );
        }

        locadora.cadastrarVeiculo(veiculo);
    }


    // =========================================================
    // CARREGAR CONTRATOS
    // =========================================================

    private void carregarContratos(
            Locadora locadora,
            List<String> linhas) {

        boolean lendoContratos = false;

        for (String linha : linhas) {

            linha = linha.trim();

            if (linha.equals("[CONTRATOS]")) {
                lendoContratos = true;
                continue;
            }

            if (!lendoContratos
                    || linha.isEmpty()
                    || linha.equals("ROTASEGURA_V1")) {

                continue;
            }

            String[] dados = linha.split("\\|");

            if (dados.length < 5) {
                continue;
            }

            String cpf = dados[0];
            String placa = dados[1];

            LocalDate dataInicio =
                    LocalDate.parse(dados[2]);

            LocalDate dataFim =
                    LocalDate.parse(dados[3]);

            boolean finalizado =
                    Boolean.parseBoolean(dados[4]);

            Contrato contrato =
                    locadora.realizarLocacao(
                            cpf,
                            placa,
                            dataInicio,
                            dataFim
                    );

            if (finalizado) {
                locadora.devolverVeiculo(placa);
            }
        }
    }
}