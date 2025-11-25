package com.seuprojeto.banco.test;

import com.seuprojeto.banco.model.ContaBancaria;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para a Classe ContaBancaria")
public class ContaBancariaTest {

    private ContaBancaria conta;
    private final double SALDO_PADRAO = 100.0;

    @BeforeEach
    @DisplayName("Inicializa uma nova ContaBancaria com saldo padrão antes de cada teste")
    void setup() {
        // Inicializa a conta com um saldo inicial para a maioria dos testes
        // Para testes de construtor, a conta será criada dentro do próprio teste
        conta = new ContaBancaria("12345-6", "Titular Teste", SALDO_PADRAO);
    }

    @AfterEach
    @DisplayName("Limpeza pós-teste (define a referência da conta como null)")
    void tearDown() {
        conta = null;
        System.out.println("--- Teste finalizado ---");
    }

    // --- Testes de Construtor ---

    @Test
    @DisplayName("Deve verificar se o construtor inicializa a conta corretamente com saldo inicial positivo")
    void testConstrutorComSaldoInicialPositivo() {
        ContaBancaria novaConta = new ContaBancaria("98765-4", "Novo Titular", 50.0);
        assertEquals("98765-4", novaConta.getNumeroConta());
        assertEquals("Novo Titular", novaConta.getTitular());
        assertEquals(50.0, novaConta.getSaldo(), "O saldo deve ser igual ao saldo inicial fornecido.");
    }

    @Test
    @DisplayName("Deve verificar se o construtor padrão inicializa a conta corretamente com saldo zero")
    void testConstrutorPadraoComSaldoZero() {
        ContaBancaria contaPadrao = new ContaBancaria();
        assertEquals(0.0, contaPadrao.getSaldo(), "O saldo deve ser 0.0 para o construtor padrão.");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando o saldo inicial é negativo")
    void testConstrutorLancaExcecaoComSaldoInicialNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ContaBancaria("11111-1", "Titular Negativo", -10.0);
        }, "Deve lançar IllegalArgumentException para saldo inicial negativo.");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando o saldo inicial é zero")
    void testConstrutorLancaExcecaoComSaldoInicialZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ContaBancaria("22222-2", "Titular Zero", 0.0);
        }, "Deve lançar IllegalArgumentException para saldo inicial zero.");
    }

    // --- Testes de Depósito (adicionarValor) ---

    @Test
    @DisplayName("Um teste para verificar um depósito bem-sucedido (o saldo deve aumentar)")
    void testDepositoBemSucedido() {
        double valorDeposito = 50.0;
        double saldoEsperado = conta.getSaldo() + valorDeposito;
        conta.adicionarValor(valorDeposito);
        assertEquals(saldoEsperado, conta.getSaldo(), "O saldo deve aumentar após um depósito bem-sucedido.");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException ao tentar depositar valor negativo")
    void testDepositoLancaExcecaoComValorNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            conta.adicionarValor(-10.0);
        }, "Deve lançar IllegalArgumentException ao tentar depositar valor negativo.");
        assertEquals(SALDO_PADRAO, conta.getSaldo(), "O saldo deve permanecer inalterado após falha no depósito.");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException ao tentar depositar zero")
    void testDepositoLancaExcecaoComValorZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            conta.adicionarValor(0.0);
        }, "Deve lançar IllegalArgumentException ao tentar depositar zero.");
        assertEquals(SALDO_PADRAO, conta.getSaldo(), "O saldo deve permanecer inalterado após falha no depósito.");
    }

    // --- Testes de Saque (subtrairValor) ---

    @Test
    @DisplayName("Um teste para verificar um saque bem-sucedido (o saldo deve diminuir)")
    void testSaqueBemSucedido() {
        double valorSaque = 30.0;
        double saldoEsperado = conta.getSaldo() - valorSaque;
        double valorRetornado = conta.subtrairValor(valorSaque);
        assertEquals(saldoEsperado, conta.getSaldo(), "O saldo deve diminuir após um saque bem-sucedido.");
        assertEquals(valorSaque, valorRetornado, "O valor retornado deve ser igual ao valor sacado.");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException ao tentar sacar valor negativo")
    void testSaqueLancaExcecaoComValorNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            conta.subtrairValor(-10.0);
        }, "Deve lançar IllegalArgumentException ao tentar sacar valor negativo.");
        assertEquals(SALDO_PADRAO, conta.getSaldo(), "O saldo deve permanecer inalterado após falha no saque.");
    }

    @Test
    @DisplayName("Um teste para verificar se o método lança IllegalArgumentException ao tentar sacar zero")
    void testSaqueLancaExcecaoComValorZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            conta.subtrairValor(0.0);
        }, "Deve lançar IllegalArgumentException ao tentar sacar zero.");
        assertEquals(SALDO_PADRAO, conta.getSaldo(), "O saldo deve permanecer inalterado após falha no saque.");
    }

    @Test
    @DisplayName("Um teste para verificar se o método lança IllegalStateException ao tentar sacar um valor maior que o saldo disponível")
    void testSaqueLancaExcecaoComSaldoInsuficiente() {
        double valorSaque = SALDO_PADRAO + 1.0;
        assertThrows(IllegalStateException.class, () -> {
            conta.subtrairValor(valorSaque);
        }, "Deve lançar IllegalStateException para saldo insuficiente.");
        assertEquals(SALDO_PADRAO, conta.getSaldo(), "O saldo deve permanecer inalterado após falha no saque.");
    }

    // --- Testes de Transferência (transferir) ---

    @Test
    @DisplayName("Um teste para verificar uma transferência bem-sucedida (saldo do origem diminui, saldo do destino aumenta)")
    void testTransferenciaBemSucedida() {
        ContaBancaria destino = new ContaBancaria("77777-7", "Destino", 50.0);
        double valorTransferencia = 25.0;
        double saldoOrigemEsperado = conta.getSaldo() - valorTransferencia;
        double saldoDestinoEsperado = destino.getSaldo() + valorTransferencia;

        assertTrue(conta.transferir(destino, valorTransferencia), "A transferência deve retornar true.");
        assertEquals(saldoOrigemEsperado, conta.getSaldo(), "O saldo da conta de origem deve diminuir.");
        assertEquals(saldoDestinoEsperado, destino.getSaldo(), "O saldo da conta de destino deve aumentar.");
    }

    @Test
    @DisplayName("Um teste para verificar se a transferência falha (retorna false) e o saldo das contas permanece inalterado se o valor for maior que o saldo da conta de origem.")
    void testTransferenciaFalhaPorSaldoInsuficiente() {
        ContaBancaria destino = new ContaBancaria("88888-8", "Destino Insuficiente", 50.0);
        double valorTransferencia = conta.getSaldo() + 1.0; // Maior que o saldo
        double saldoOrigemAntes = conta.getSaldo();
        double saldoDestinoAntes = destino.getSaldo();

        assertFalse(conta.transferir(destino, valorTransferencia), "A transferência deve retornar false.");
        assertEquals(saldoOrigemAntes, conta.getSaldo(), "O saldo da conta de origem deve permanecer inalterado.");
        assertEquals(saldoDestinoAntes, destino.getSaldo(), "O saldo da conta de destino deve permanecer inalterado.");
    }

    @Test
    @DisplayName("Um teste para verificar se a transferência falha (retorna false) e o saldo das contas permanece inalterado se o valor for negativo.")
    void testTransferenciaFalhaPorValorNegativo() {
        ContaBancaria destino = new ContaBancaria("99999-9", "Destino Negativo", 50.0);
        double valorTransferencia = -10.0;
        double saldoOrigemAntes = conta.getSaldo();
        double saldoDestinoAntes = destino.getSaldo();

        assertFalse(conta.transferir(destino, valorTransferencia), "A transferência deve retornar false.");
        assertEquals(saldoOrigemAntes, conta.getSaldo(), "O saldo da conta de origem deve permanecer inalterado.");
        assertEquals(saldoDestinoAntes, destino.getSaldo(), "O saldo da conta de destino deve permanecer inalterado.");
    }

    @Test
    @DisplayName("Um teste para verificar se a transferência falha (retorna false) se a conta de destino for null.")
    void testTransferenciaFalhaComDestinoNull() {
        double valorTransferencia = 10.0;
        double saldoOrigemAntes = conta.getSaldo();

        assertFalse(conta.transferir(null, valorTransferencia), "A transferência deve retornar false para destino nulo.");
        assertEquals(saldoOrigemAntes, conta.getSaldo(), "O saldo da conta de origem deve permanecer inalterado.");
        // Não há saldo de destino para verificar
    }

    // --- Testes de Encapsulamento e Getters ---

    @Test
    @DisplayName("Correção da lógica em todos os métodos da ContaBancaria e uso correto de encapsulamento (atributos private, getters)")
    void testEncapsulamentoEGetters() {
        // Os atributos são privados na classe ContaBancaria.
        // Os getters são usados para acessar os valores.
        assertEquals("12345-6", conta.getNumeroConta(), "Deve retornar o número da conta correto.");
        assertEquals("Titular Teste", conta.getTitular(), "Deve retornar o titular correto.");
        assertEquals(SALDO_PADRAO, conta.getSaldo(), "Deve retornar o saldo correto.");
    }

    // --- Testes de Isolamento ---

    @Test
    @DisplayName("Amostra de isolamento dos testes (cada teste é independente e não afeta outros)")
    void testIsolamento() {
        // O @BeforeEach garante que 'conta' é uma nova instância com SALDO_PADRAO (100.0)
        // O @AfterEach garante que a referência é limpa.
        // Este teste apenas verifica o estado inicial, confirmando que o setup funcionou.
        assertEquals(SALDO_PADRAO, conta.getSaldo(), "O saldo deve ser o padrão, provando o isolamento.");
    }
}
