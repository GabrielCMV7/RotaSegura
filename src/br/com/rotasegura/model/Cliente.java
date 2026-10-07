package br.com.rotasegura.model;

public class Cliente {

    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public Cliente(String nome, String cpf, String telefone, String email) {
        setNome(nome);
        setCpf(cpf);
        setTelefone(telefone);
        setEmail(email);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome não pode ser vazio.");
        }

        if (nome.trim().length() < 3) {
            throw new IllegalArgumentException("O nome deve possuir pelo menos 3 caracteres.");
        }

        this.nome = nome.trim();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {

        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException(
                    "O CPF não pode ser vazio."
            );
        }

        String cpfLimpo = cpf.replaceAll("\\D", "");

        if (cpfLimpo.length() != 11) {
            throw new IllegalArgumentException(
                    "O CPF deve possuir 11 dígitos."
            );
        }

        // Rejeita CPFs formados pelo mesmo número
        if (cpfLimpo.matches("(\\d)\\1{10}")) {
            throw new IllegalArgumentException(
                    "CPF inválido."
            );
        }

        // Cálculo do primeiro dígito verificador
        int soma = 0;

        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpfLimpo.charAt(i))
                    * (10 - i);
        }

        int resto = soma % 11;

        int primeiroDigito;

        if (resto < 2) {
            primeiroDigito = 0;
        } else {
            primeiroDigito = 11 - resto;
        }

        if (primeiroDigito
                != Character.getNumericValue(cpfLimpo.charAt(9))) {

            throw new IllegalArgumentException(
                    "CPF inválido."
            );
        }

        // Cálculo do segundo dígito verificador
        soma = 0;

        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpfLimpo.charAt(i))
                    * (11 - i);
        }

        resto = soma % 11;

        int segundoDigito;

        if (resto < 2) {
            segundoDigito = 0;
        } else {
            segundoDigito = 11 - resto;
        }

        if (segundoDigito
                != Character.getNumericValue(cpfLimpo.charAt(10))) {

            throw new IllegalArgumentException(
                    "CPF inválido."
            );
        }

        this.cpf = cpfLimpo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {

        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException(
                    "O telefone não pode ser vazio."
            );
        }

        String telefoneLimpo = telefone.replaceAll("\\D", "");

        if (telefoneLimpo.length() != 10
                && telefoneLimpo.length() != 11) {

            throw new IllegalArgumentException(
                    "O telefone deve possuir 10 ou 11 dígitos."
            );
        }

        if (telefoneLimpo.matches("(\\d)\\1+")) {
            throw new IllegalArgumentException(
                    "Telefone inválido."
            );
        }

        this.telefone = telefoneLimpo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "O e-mail não pode ser vazio."
            );
        }

        String emailLimpo = email.trim().toLowerCase();

        if (!emailLimpo.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            throw new IllegalArgumentException(
                    "E-mail inválido."
            );
        }

        if (!emailLimpo.contains(".")) {
            throw new IllegalArgumentException(
                    "E-mail inválido."
            );
        }

        this.email = emailLimpo;
    }
}