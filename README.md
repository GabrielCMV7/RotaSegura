# Rota Segura - Sistema de Locação de Veículos

## Dados do aluno

**Nome:** Gabriel Correia Machado Vieira  
**RA:** 1301392611025  
**Curso:** Tecnologia em Desenvolvimento de Software Multiplataforma  
**Unidade:** Faculdade de Tecnologia de Marília

---

## Sobre o projeto

O **Rota Segura** é um sistema de locação de veículos desenvolvido em Java com o objetivo de auxiliar no gerenciamento do ciclo de locação de uma empresa de aluguel de veículos.

O sistema permite realizar o cadastro de clientes e veículos, realizar locações, controlar a disponibilidade dos veículos, registrar devoluções, gerar contratos e relatórios, além de armazenar os dados em arquivo para posterior recuperação.

---

## Funcionalidades

O sistema possui as seguintes funcionalidades:

- Cadastro de clientes;
- Validação de nome, CPF, telefone e e-mail;
- Cadastro de veículos;
- Validação de placa e ano do veículo;
- Categorias de veículos:
  - Popular;
  - Sedan;
  - SUV;
- Realização de locações;
- Controle de disponibilidade dos veículos;
- Impedimento de locação de veículo ocupado;
- Devolução de veículos;
- Validação das datas de locação;
- Cálculo de diária, seguro e manutenção;
- Geração de contratos;
- Geração de relatório de fechamento;
- Persistência dos dados em arquivo de texto;
- Recuperação dos dados ao iniciar o sistema;
- Tratamento de entradas inválidas e situações inconsistentes.

---

## Tecnologias utilizadas

- Java
- JDK 27
- IntelliJ IDEA
- Programação Orientada a Objetos
- Java Collections
- Generics
- Arquivos de texto para persistência

---

## Arquitetura do projeto

O projeto foi organizado em pacotes para separar as responsabilidades das classes.

### `model`

Contém as principais entidades do sistema:

- `Cliente`
- `Contrato`
- `Veiculo`
- `Popular`
- `Sedan`
- `SUV`

A classe `Veiculo` é abstrata e serve como classe base para as categorias de veículos.

### `interfaces`

Contém a interface:

- `Relatorio`

Ela define um padrão para geração e impressão de relatórios.

### `service`

Contém as classes responsáveis pelas regras e serviços do sistema:

- `Locadora`
- `Repositorio<T>`
- `PersistenciaService`
- `RelatorioFechamento`

A classe `Repositorio<T>` utiliza Generics para permitir o armazenamento de diferentes tipos de objetos.

### `exceptions`

Pacote reservado para exceções específicas do sistema.

### `Main`

A classe `Main` é responsável pela execução do sistema e pela interação com o usuário através do menu principal.

---

## Resumo da arquitetura

A arquitetura do sistema foi organizada de forma a separar as entidades, regras de negócio, interfaces e ponto de entrada da aplicação.

O pacote `model` concentra as entidades e regras relacionadas aos dados do domínio. A classe abstrata `Veiculo` representa a estrutura comum dos veículos e possui as especializações `Popular`, `Sedan` e `SUV`.

O pacote `interfaces` contém a interface `Relatorio`, utilizada para padronizar a geração de relatórios.

O pacote `service` concentra as regras de negócio e serviços da aplicação. A classe `Locadora` é responsável pelo gerenciamento de clientes, veículos e contratos. O `Repositorio<T>` fornece uma estrutura genérica para armazenamento dos objetos. O `PersistenciaService` realiza o salvamento e carregamento dos dados, enquanto `RelatorioFechamento` gera o relatório geral da locadora.

A classe `Main` funciona como ponto de entrada da aplicação e apresenta o menu de interação com o usuário.

Essa organização facilita a manutenção do sistema e permite separar as responsabilidades de cada parte da aplicação.

---

## Conceitos de Programação Orientada a Objetos utilizados

### Encapsulamento

Os atributos das classes são privados e seu acesso é controlado por métodos.

Exemplo:

```java
private String cpf;
```

Os métodos de acesso também possuem validações para impedir o armazenamento de dados inválidos.

Por exemplo, o CPF passa por validação de quantidade de dígitos e dos dígitos verificadores antes de ser armazenado.

---

### Herança

A classe abstrata `Veiculo` é utilizada como classe base para os diferentes tipos de veículos.

A estrutura é:

```text
Veiculo
├── Popular
├── Sedan
└── SUV
```

As classes `Popular`, `Sedan` e `SUV` herdam características e comportamentos da classe `Veiculo`.

---

### Abstração

A classe `Veiculo` foi definida como abstrata porque representa uma estrutura geral de veículo.

Ela possui métodos abstratos como:

```java
public abstract double calcularDiaria();

public abstract double calcularSeguro();

public abstract double calcularManutencao();
```

Cada categoria de veículo fornece sua própria implementação desses métodos.

---

### Polimorfismo

O polimorfismo é utilizado quando o sistema trabalha com objetos através da referência da classe `Veiculo`.

Por exemplo:

```java
Veiculo veiculo;
```

Essa referência pode representar um objeto `Popular`, `Sedan` ou `SUV`.

Assim, os métodos:

```java
calcularDiaria()
calcularSeguro()
calcularManutencao()
```

produzem valores diferentes de acordo com o tipo real do veículo.

---

### Generics

O sistema utiliza uma classe genérica chamada `Repositorio<T>`.

Exemplos de utilização:

```java
Repositorio<Cliente>
Repositorio<Veiculo>
Repositorio<Contrato>
```

Isso permite reutilizar a mesma estrutura de armazenamento para diferentes tipos de objetos, mantendo a segurança de tipos.

---

### Interface

A interface `Relatorio` estabelece um comportamento comum para as classes que geram relatórios.

Ela possui o método:

```java
String gerarRelatorio();
```

A interface é utilizada por:

- `Contrato`
- `RelatorioFechamento`

Dessa forma, diferentes relatórios seguem um padrão comum.

---

## Diagrama simples de classes

```text
                         ┌─────────────────────────┐
                         │      <<abstract>>        │
                         │         Veiculo         │
                         ├─────────────────────────┤
                         │ - placa                 │
                         │ - modelo                │
                         │ - ano                   │
                         │ - disponivel            │
                         ├─────────────────────────┤
                         │ + calcularDiaria()      │
                         │ + calcularSeguro()      │
                         │ + calcularManutencao()  │
                         └────────────┬────────────┘
                                      │
                  ┌───────────────────┼───────────────────┐
                  │                   │                   │
          ┌───────▼───────┐   ┌───────▼───────┐   ┌──────▼──────┐
          │    Popular    │   │     Sedan     │   │     SUV     │
          └───────────────┘   └───────────────┘   └─────────────┘


        ┌────────────────┐
        │    Cliente     │
        ├────────────────┤
        │ - nome         │
        │ - cpf          │
        │ - telefone     │
        │ - email        │
        └───────┬────────┘
                │
                │
                ▼
        ┌────────────────┐
        │    Contrato    │
        ├────────────────┤
        │ - dataInicio   │
        │ - dataFim      │
        │ - valorTotal   │
        │ - finalizado   │
        └───────┬────────┘
                │
                │
                ▼
        ┌────────────────┐
        │    Veiculo     │
        └────────────────┘


                  ┌──────────────────────┐
                  │       Locadora       │
                  ├──────────────────────┤
                  │ - clientes            │
                  │ - veiculos            │
                  │ - contratos           │
                  └──────────┬───────────┘
                             │
                             │ utiliza
                             ▼
                    ┌──────────────────┐
                    │  Repositorio<T>  │
                    └──────────────────┘


                  ┌──────────────────────┐
                  │   <<interface>>      │
                  │       Relatorio      │
                  ├──────────────────────┤
                  │ + gerarRelatorio()   │
                  │ + imprimirRelatorio()│
                  └──────────┬───────────┘
                             │
                    ┌────────┴─────────┐
                    │                  │
                    ▼                  ▼
             ┌────────────┐   ┌────────────────────┐
             │  Contrato  │   │RelatorioFechamento │
             └────────────┘   └────────────────────┘
```

---

## Persistência dos dados

O sistema utiliza um arquivo de texto para manter os dados armazenados mesmo após o encerramento da aplicação.

O arquivo utilizado é:

```text
dados_rotasegura.txt
```

A classe `PersistenciaService` é responsável por salvar e carregar os dados.

Os dados são organizados nas seguintes seções:

```text
[CLIENTES]
[VEICULOS]
[CONTRATOS]
```

Ao iniciar a aplicação, o sistema verifica a existência do arquivo e tenta carregar os dados anteriormente armazenados.

O arquivo `dados_rotasegura.txt` está incluído no `.gitignore`, portanto os dados gerados localmente não são enviados para o GitHub.

---

## Instruções para compilar e executar a aplicação

### Requisitos

Para executar o projeto é necessário ter:

- Java JDK 27;
- IntelliJ IDEA.

### Execução pelo IntelliJ IDEA

1. Abra o projeto `RotaSegura` no IntelliJ IDEA.
2. Verifique se o projeto está configurado para utilizar o **JDK 27**.
3. No painel de arquivos, localize:

```text
src
└── br.com.rotasegura
    └── Main.java
```

4. Clique com o botão direito em `Main.java`.
5. Selecione:

```text
Run 'Main.main()'
```

6. O sistema será iniciado no console.
7. O menu principal será apresentado para interação com o usuário.

### Compilação pelo terminal

Na pasta raiz do projeto, é possível compilar os arquivos Java com:

```bash
javac -d out $(find src -name "*.java")
```

Depois, execute a aplicação com:

```bash
java -cp out br.com.rotasegura.Main
```

No Windows, caso o comando `find` não esteja disponível, a execução pelo IntelliJ IDEA é recomendada.

Ao executar o sistema, o arquivo `dados_rotasegura.txt` será utilizado para persistência dos dados.

---

## Menu principal

Após iniciar a aplicação, o seguinte menu é apresentado:

```text
======================================
          ROTA SEGURA
       SISTEMA DE LOCAÇÃO
======================================
1 - Cadastrar cliente
2 - Cadastrar veículo
3 - Realizar locação
4 - Devolver veículo
5 - Listar clientes
6 - Listar veículos
7 - Relatório de fechamento
8 - Salvar dados
9 - Resumo da locadora
0 - Sair
======================================
```

---

## Cenários de teste realizados

Foram realizados testes para verificar o funcionamento das principais funcionalidades e também o tratamento de situações inválidas.

### 1. Cadastro de clientes

Foram cadastrados dois clientes com dados válidos:

```text
João da Silva
CPF: 529.982.247-25
Telefone: (14) 99999-8888
E-mail: joao@email.com
```

```text
Maria Oliveira
CPF: 168.995.350-09
Telefone: (14) 98888-7777
E-mail: maria@email.com
```

Também foram testados dados inválidos:

- CPF inválido: `123.456.789-00`
- E-mail inválido: `carlos`
- Telefone inválido: `11111111111`

Os dados inválidos foram rejeitados pelo sistema.

---

### 2. Cadastro de veículos

Foram cadastrados três veículos, representando as categorias exigidas:

```text
Popular
Modelo: Fiat Argo
Placa: ABC-1D23
Ano: 2024
```

```text
Sedan
Modelo: Toyota Corolla
Placa: DEF-2E34
Ano: 2023
```

```text
SUV
Modelo: Jeep Compass
Placa: GHI-3F45
Ano: 2025
```

Também foram testadas placas e anos inválidos, que foram rejeitados pelo sistema.

---

### 3. Realização de locação

Foi realizada uma locação para João da Silva utilizando o Fiat Argo.

```text
Data de retirada: 07/10/2026
Data de devolução: 10/10/2026
```

A locação foi realizada com sucesso e o veículo passou para o estado de indisponível.

---

### 4. Tentativa de locação de veículo ocupado

Após a primeira locação, foi realizada uma tentativa de alugar o mesmo Fiat Argo para Maria Oliveira.

O sistema identificou que o veículo estava ocupado e rejeitou corretamente a nova locação.

---

### 5. Devolução

O Fiat Argo foi devolvido com sucesso.

Após a devolução, o sistema alterou sua situação para disponível novamente.

---

### 6. Nova locação

Após a devolução, o Fiat Argo foi alugado novamente para Maria Oliveira:

```text
Data de retirada: 12/10/2026
Data de devolução: 15/10/2026
```

A segunda locação foi realizada com sucesso e o veículo voltou a ficar indisponível.

---

### 7. Validação de datas

Foram realizados testes com datas inconsistentes, incluindo:

- Data de devolução anterior à data de retirada;
- Data de retirada anterior à data atual;
- Datas em formato inválido.

O sistema rejeitou as entradas inválidas e apresentou mensagens de erro apropriadas.

---

### 8. Relatório de fechamento

Foi gerado o relatório de fechamento após a realização das locações.

O relatório apresentou:

- Quantidade total de contratos;
- Faturamento total;
- Quantidade de veículos disponíveis;
- Quantidade de veículos indisponíveis.

No teste realizado, foram registrados dois contratos e o sistema identificou corretamente a situação da frota.

---

### 9. Persistência dos dados

Foi realizado o salvamento dos dados utilizando o arquivo:

```text
dados_rotasegura.txt
```

Em seguida:

1. A aplicação foi encerrada;
2. A aplicação foi executada novamente;
3. Os dados foram carregados automaticamente;
4. Foram conferidos os clientes;
5. Foram conferidos os veículos;
6. Foram conferidos os contratos;
7. Foi verificada a disponibilidade dos veículos.

Os dados foram recuperados corretamente.

Também foi verificado que o veículo que possuía uma locação ativa continuou indisponível após o fechamento e reabertura do sistema.

---

## Resultado dos testes

Os testes realizados demonstraram que o sistema consegue:

- Cadastrar e validar clientes;
- Cadastrar e validar veículos;
- Controlar a disponibilidade da frota;
- Impedir locações de veículos ocupados;
- Validar datas;
- Calcular valores de locação;
- Registrar devoluções;
- Gerar contratos;
- Gerar relatórios;
- Persistir dados;
- Recuperar dados após o reinício da aplicação;
- Tratar entradas inválidas e situações inconsistentes.

---

## Conclusão

O projeto **Rota Segura** implementa um sistema de locação de veículos utilizando conceitos fundamentais de Programação Orientada a Objetos.

A aplicação apresenta encapsulamento, herança, abstração, polimorfismo, interfaces e Generics, além de validação de dados, tratamento de erros, persistência e organização em pacotes.

Dessa forma, o sistema atende aos principais requisitos propostos para o projeto e fornece uma estrutura que pode ser expandida futuramente com novas categorias de veículos, formas de persistência e funcionalidades de gerenciamento.