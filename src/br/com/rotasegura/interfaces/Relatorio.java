package br.com.rotasegura.interfaces;

public interface Relatorio {

    String gerarRelatorio();

    default void imprimirRelatorio() {
        System.out.println(gerarRelatorio());
    }
}