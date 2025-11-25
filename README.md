🏦 ContaBancariaProject

Descrição do Projeto

Simulação robusta de uma Conta Bancária em Java. O projeto foca no encapsulamento, tratamento rigoroso de exceções (IllegalArgumentException, IllegalStateException) e garante a qualidade através do Desenvolvimento Orientado a Testes (TDD) e uma suíte completa de 17 Testes Unitários (JUnit 5).

Funcionalidades Principais: Depósito, Saque e Transferência Atômica.

Requisitos Implementados

A classe ContaBancaria e a suíte de testes atendem aos seguintes requisitos:

1. Classe ContaBancaria

Funcionalidade
Detalhes da Implementação
Encapsulamento
Atributos (numeroConta, saldo, titular) são private e acessados via Getters.
Construtores
Construtor padrão (saldo 0.0) e construtor completo.
Validação de Criação
Lança IllegalArgumentException se o saldo inicial for menor ou igual a zero.
Depósito (adicionarValor)
Lança IllegalArgumentException se o valor for menor ou igual a zero.
Saque (subtrairValor)
Lança IllegalArgumentException se o valor for menor ou igual a zero.
Saque (subtrairValor)
Lança IllegalStateException se o saldo for insuficiente.
Transferência (transferir)
Operação atômica. Gerencia exceções internas e retorna false em caso de falha (incluindo destino null), mantendo o estado das contas inalterado.


2. Testes Unitários (JUnit 5)

Funcionalidade
Detalhes da Implementação
Framework
Utilização do JUnit 5 (Jupiter API e Engine).
Cobertura
17 testes implementados, cobrindo todos os cenários de sucesso, falha e exceção.
Isolamento
Uso de @BeforeEach e @AfterEach para garantir que cada teste seja independente.
Legibilidade
Uso de @DisplayName para tornar o relatório de testes descritivo.
Validação de Exceções
Uso de assertThrows para validar o lançamento correto de IllegalArgumentException e IllegalStateException.
Status Final
Todos os testes passam com sucesso (BUILD SUCCESS).


Estrutura do Projeto

O projeto segue a estrutura padrão de um projeto Maven:

Plain Text


ContaBancariaProject/
├── pom.xml
└── src/
    ├── main/java/com/seuprojeto/banco/model/
    │   └── ContaBancaria.java  <- Classe principal
    └── test/java/com/seuprojeto/banco/test/
        └── ContaBancariaTest.java <- Suíte de testes


Como Executar o Projeto

Pré-requisitos

•
Java Development Kit (JDK) 11 ou superior.

•
Apache Maven 3.x.

1. Compilar e Instalar

Navegue até o diretório raiz do projeto (ContaBancariaProject) e execute:

Bash


mvn clean install


2. Executar os Testes Unitários

Para rodar a suíte de testes e verificar se todas as regras de negócio estão corretas, execute:

Bash


mvn clean test


Resultado Esperado:

Plain Text


[INFO]: # "Tests run: 17, Failures: 0, Errors: 0, Skipped: 0"
[INFO]: # "------------------------------------------------------------------------"
[INFO]: # "BUILD SUCCESS"
[INFO]: # "------------------------------------------------------------------------"


Autor:
[Bruna Mesquita/swanmomsen]

