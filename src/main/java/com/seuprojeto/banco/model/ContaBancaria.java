package com.seuprojeto.banco.model;

public class ContaBancaria {

    // Atributos devem ser private (Encapsulamento)
    private String numeroConta;
    private double saldo;
    private String titular;

    // Construtor 1: Inicializa saldo com 0.0 por padrão
    public ContaBancaria() {
        this.saldo = 0.0;
    }

    // Construtor 2: Inicializa numeroConta, titular e saldo inicial
    public ContaBancaria(String numeroConta, String titular, double saldoInicial) {
        if (saldoInicial <= 0) {
            throw new IllegalArgumentException("O saldo inicial não pode ser negativo ou zero.");
        }
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    // Getters (para numeroConta e titular)
    public String getNumeroConta() {
        return numeroConta;
    }

    public String getTitular() {
        return titular;
        // O saldo deve ser acessado diretamente, mas para fins de teste e consistência,
        // vou adicionar um getter para o saldo também, embora não seja explicitamente
        // pedido, é uma prática comum e necessária para verificar o estado.
    }

    // Getter para saldo (necessário para verificar o estado da conta nos testes)
    public double getSaldo() {
        return saldo;
    }

    // Método adicionarValor (depositar)
    public void adicionarValor(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor a ser depositado deve ser maior que zero.");
        }
        this.saldo += valor;
    }

    // Método subtrairValor (sacar)
    public double subtrairValor(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor a ser sacado deve ser maior que zero.");
        }
        if (valor > this.saldo) {
            throw new IllegalStateException("Saldo insuficiente para o saque.");
        }
        this.saldo -= valor;
        return valor; // Retorna o valor sacado
    }

    // Método transferir
    public boolean transferir(ContaBancaria destino, double valor) {
        // Tratamento de exceção: destino nulo
        if (destino == null) {
            // O requisito pede para retornar false e não propagar a exceção
            // para problemas irrecuperáveis (como destino nulo), o que é um pouco
            // contraditório com o tratamento de exceções internas.
            // Vou interpretar que o tratamento de exceções internas (sacar/depositar)
            // deve ser gerenciado para retornar false, mas o problema de destino nulo
            // é um caso de falha de lógica que deve ser tratado.
            // No entanto, seguindo a lógica de "retornar false sem propagar a exceção"
            // para falhas, vou tratar o destino nulo como uma falha.
            return false;
        }

        try {
            // 1. Tenta sacar da conta de origem
            double valorSacado = this.subtrairValor(valor);

            // 2. Tenta depositar na conta de destino
            destino.adicionarValor(valorSacado);

            // 3. Se ambos forem bem-sucedidos
            return true;

        } catch (IllegalArgumentException | IllegalStateException e) {
            // O requisito pede: "O tratamento de exceções internas (de sacar e depositar)
            // deve ser gerenciado para que transferir retorne false sem propagar a exceção,
            // a menos que o problema seja irrecuperável."
            // Como as exceções de sacar e depositar são recuperáveis (saldo insuficiente, valor inválido),
            // a transferência falha e retorna false.
            return false;
        }
    }
}
