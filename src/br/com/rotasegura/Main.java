package br.com.rotasegura;

import br.com.rotasegura.model.Cliente;
import br.com.rotasegura.model.Contrato;
import br.com.rotasegura.model.Popular;
import br.com.rotasegura.model.SUV;
import br.com.rotasegura.model.Sedan;
import br.com.rotasegura.model.Veiculo;
import br.com.rotasegura.service.Locadora;
import br.com.rotasegura.service.PersistenciaService;
import br.com.rotasegura.service.RelatorioFechamento;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {

        Locadora locadora = new Locadora();

        PersistenciaService persistencia =
                new PersistenciaService();

        persistencia.carregar(locadora);

        boolean executando = true;

        while (executando) {

            exibirMenu();

            int opcao = lerInteiro("Escolha uma opção: ");

            System.out.println();

            try {

                switch (opcao) {

                    case 1:
                        cadastrarCliente(locadora);
                        break;

                    case 2:
                        cadastrarVeiculo(locadora);
                        break;

                    case 3:
                        realizarLocacao(locadora);
                        break;

                    case 4:
                        devolverVeiculo(locadora);
                        break;

                    case 5:
                        listarClientes(locadora);
                        break;

                    case 6:
                        listarVeiculos(locadora);
                        break;

                    case 7:
                        gerarRelatorioFechamento(locadora);
                        break;

                    case 8:
                        salvarDados(locadora);
                        break;

                    case 9:
                        locadora.exibirResumo();
                        break;

                    case 0:
                        salvarDados(locadora);
                        System.out.println("Sistema encerrado.");
                        executando = false;
                        break;

                    default:
                        System.out.println(
                                "Opção inválida. Escolha uma opção do menu."
                        );
                }

            } catch (IllegalArgumentException | IllegalStateException e) {

                System.out.println();
                System.out.println("ERRO: " + e.getMessage());
            }

            if (executando) {
                System.out.println();
                System.out.println("Pressione ENTER para continuar...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }


    // =========================================================
    // MENU PRINCIPAL
    // =========================================================

    private static void exibirMenu() {

        System.out.println();
        System.out.println("======================================");
        System.out.println("          ROTA SEGURA");
        System.out.println("       SISTEMA DE LOCAÇÃO");
        System.out.println("======================================");
        System.out.println("1 - Cadastrar cliente");
        System.out.println("2 - Cadastrar veículo");
        System.out.println("3 - Realizar locação");
        System.out.println("4 - Devolver veículo");
        System.out.println("5 - Listar clientes");
        System.out.println("6 - Listar veículos");
        System.out.println("7 - Relatório de fechamento");
        System.out.println("8 - Salvar dados");
        System.out.println("9 - Resumo da locadora");
        System.out.println("0 - Sair");
        System.out.println("======================================");
    }


    // =========================================================
    // CADASTRAR CLIENTE
    // =========================================================

    private static void cadastrarCliente(Locadora locadora) {

        System.out.println("===== CADASTRO DE CLIENTE =====");

        String nome = lerTexto("Nome: ");
        String cpf = lerTexto("CPF: ");
        String telefone = lerTexto("Telefone: ");
        String email = lerTexto("E-mail: ");

        Cliente cliente = new Cliente(
                nome,
                cpf,
                telefone,
                email
        );

        locadora.cadastrarCliente(cliente);

        System.out.println();
        System.out.println("Cliente cadastrado com sucesso!");
    }


    // =========================================================
    // CADASTRAR VEÍCULO
    // =========================================================

    private static void cadastrarVeiculo(Locadora locadora) {

        System.out.println("===== CADASTRO DE VEÍCULO =====");

        System.out.println();
        System.out.println("Escolha a categoria:");
        System.out.println("1 - Popular");
        System.out.println("2 - Sedan");
        System.out.println("3 - SUV");

        int tipo = lerInteiro("Categoria: ");

        String placa = lerTexto("Placa: ");
        String modelo = lerTexto("Modelo: ");
        int ano = lerInteiro("Ano: ");

        Veiculo veiculo;

        switch (tipo) {

            case 1:
                veiculo = new Popular(
                        placa,
                        modelo,
                        ano
                );
                break;

            case 2:
                veiculo = new Sedan(
                        placa,
                        modelo,
                        ano
                );
                break;

            case 3:
                veiculo = new SUV(
                        placa,
                        modelo,
                        ano
                );
                break;

            default:
                throw new IllegalArgumentException(
                        "Categoria de veículo inválida."
                );
        }

        locadora.cadastrarVeiculo(veiculo);

        System.out.println();
        System.out.println("Veículo cadastrado com sucesso!");
    }


    // =========================================================
    // REALIZAR LOCAÇÃO
    // =========================================================

    private static void realizarLocacao(Locadora locadora) {

        System.out.println("===== REALIZAR LOCAÇÃO =====");

        String cpf = lerTexto("CPF do cliente: ");
        String placa = lerTexto("Placa do veículo: ");

        LocalDate dataInicio =
                lerData("Data de retirada (dd/MM/yyyy): ");

        LocalDate dataFim =
                lerData("Data de devolução (dd/MM/yyyy): ");

        Contrato contrato = locadora.realizarLocacao(
                cpf,
                placa,
                dataInicio,
                dataFim
        );

        System.out.println();
        System.out.println("Locação realizada com sucesso!");
        System.out.println();

        contrato.imprimirRelatorio();
    }


    // =========================================================
    // DEVOLVER VEÍCULO
    // =========================================================

    private static void devolverVeiculo(Locadora locadora) {

        System.out.println("===== DEVOLUÇÃO DE VEÍCULO =====");

        String placa = lerTexto("Placa do veículo: ");

        locadora.devolverVeiculo(placa);

        System.out.println();
        System.out.println("Veículo devolvido com sucesso!");
    }


    // =========================================================
    // LISTAR CLIENTES
    // =========================================================

    private static void listarClientes(Locadora locadora) {

        System.out.println("===== CLIENTES CADASTRADOS =====");

        List<Cliente> clientes = locadora.listarClientes();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        for (Cliente cliente : clientes) {

            System.out.println();
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("CPF: " + cliente.getCpf());
            System.out.println("Telefone: " + cliente.getTelefone());
            System.out.println("E-mail: " + cliente.getEmail());
        }
    }


    // =========================================================
    // LISTAR VEÍCULOS
    // =========================================================

    private static void listarVeiculos(Locadora locadora) {

        System.out.println("===== VEÍCULOS CADASTRADOS =====");

        List<Veiculo> veiculos = locadora.listarVeiculos();

        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
            return;
        }

        for (Veiculo veiculo : veiculos) {

            System.out.println();
            System.out.println(
                    "Categoria: "
                            + veiculo.getClass().getSimpleName()
            );

            System.out.println(
                    "Modelo: "
                            + veiculo.getModelo()
            );

            System.out.println(
                    "Placa: "
                            + veiculo.getPlaca()
            );

            System.out.println(
                    "Ano: "
                            + veiculo.getAno()
            );

            System.out.println(
                    "Diária: R$ "
                            + veiculo.calcularDiaria()
            );

            System.out.println(
                    "Seguro: R$ "
                            + veiculo.calcularSeguro()
            );

            System.out.println(
                    "Manutenção: R$ "
                            + veiculo.calcularManutencao()
            );

            System.out.println(
                    "Disponível: "
                            + (veiculo.isDisponivel()
                            ? "SIM"
                            : "NÃO")
            );
        }
    }


    // =========================================================
    // RELATÓRIO DE FECHAMENTO
    // =========================================================

    private static void gerarRelatorioFechamento(
            Locadora locadora) {

        System.out.println(
                "===== RELATÓRIO DE FECHAMENTO ====="
        );

        RelatorioFechamento fechamento =
                new RelatorioFechamento(
                        locadora.listarContratos(),
                        locadora.listarVeiculos()
                );

        fechamento.imprimirRelatorio();
    }


    // =========================================================
    // SALVAR DADOS
    // =========================================================

    private static void salvarDados(Locadora locadora) {

        PersistenciaService persistencia =
                new PersistenciaService();

        persistencia.salvar(locadora);
    }


    // =========================================================
    // LER TEXTO
    // =========================================================

    private static String lerTexto(String mensagem) {

        System.out.print(mensagem);

        String valor = scanner.nextLine().trim();

        if (valor.isBlank()) {
            throw new IllegalArgumentException(
                    "O campo não pode ficar vazio."
            );
        }

        return valor;
    }


    // =========================================================
    // LER NÚMERO INTEIRO
    // =========================================================

    private static int lerInteiro(String mensagem) {

        while (true) {

            System.out.print(mensagem);

            String valor = scanner.nextLine().trim();

            try {

                return Integer.parseInt(valor);

            } catch (NumberFormatException e) {

                System.out.println();
                System.out.println(
                        "Entrada inválida. Digite apenas um número."
                );
            }
        }
    }


    // =========================================================
    // LER DATA
    // =========================================================

    private static LocalDate lerData(String mensagem) {

        System.out.print(mensagem);

        String valor = scanner.nextLine().trim();

        try {

            return LocalDate.parse(
                    valor,
                    FORMATO_DATA
            );

        } catch (DateTimeParseException e) {

            throw new IllegalArgumentException(
                    "Data inválida. Use o formato dd/MM/yyyy."
            );
        }
    }
}