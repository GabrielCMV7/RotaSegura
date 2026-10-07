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

## Conceitos de Programação Orientada a Objetos utilizados

### Encapsulamento

Os atributos das classes são privados e seu acesso é controlado por métodos.

Exemplo:

```java
private String cpf;