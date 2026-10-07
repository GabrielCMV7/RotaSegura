package br.com.rotasegura.service;

import java.util.ArrayList;
import java.util.List;

public class Repositorio<T> {

    private List<T> itens;

    public Repositorio() {
        this.itens = new ArrayList<>();
    }

    public void adicionar(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Não é possível adicionar um item nulo.");
        }

        itens.add(item);
    }

    public List<T> listar() {
        return new ArrayList<>(itens);
    }

    public int tamanho() {
        return itens.size();
    }

    public boolean estaVazio() {
        return itens.isEmpty();
    }

    public void remover(T item) {
        itens.remove(item);
    }

    public void limpar() {
        itens.clear();
    }
}