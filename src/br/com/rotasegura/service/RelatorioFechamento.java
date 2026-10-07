package br.com.rotasegura.service;

import br.com.rotasegura.interfaces.Relatorio;
import br.com.rotasegura.model.Contrato;
import br.com.rotasegura.model.Veiculo;

import java.util.List;

public class RelatorioFechamento implements Relatorio {

    private List<Contrato> contratos;
    private List<Veiculo> veiculos;

    public RelatorioFechamento(
            List<Contrato> contratos,
            List<Veiculo> veiculos) {

        this.contratos = contratos;
        this.veiculos = veiculos;
    }

    @Override
    public String gerarRelatorio() {

        double faturamentoTotal = 0;

        for (Contrato contrato : contratos) {
            faturamentoTotal += contrato.getValorTotal();
        }

        int veiculosDisponiveis = 0;
        int veiculosIndisponiveis = 0;

        for (Veiculo veiculo : veiculos) {

            if (veiculo.isDisponivel()) {
                veiculosDisponiveis++;
            } else {
                veiculosIndisponiveis++;
            }
        }

        return "===== RELATÓRIO DE FECHAMENTO =====\n" +
                "Total de contratos: " + contratos.size() + "\n" +
                "Faturamento total: R$ " + faturamentoTotal + "\n" +
                "Veículos disponíveis: " + veiculosDisponiveis + "\n" +
                "Veículos indisponíveis: " + veiculosIndisponiveis + "\n" +
                "===================================";
    }
}