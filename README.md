# MyStore Desktop
Sistema que irá imprimir relatórios, etiquetas e códigos de barra.

#### 🧭 Baixando e rodando o projeto
```bash

# Clone este repositório
$ git clone https://github.com/tcc-mystore/mystore_desktop.git

# Acesse a pasta do projeto no terminal/cmd
$ cd mystore_desktop/arquivos_do_projeto

# Execute a Aplicativo
$ java -jar mystore_api-0.0.1.jar

# Execute a API
$ java -jar mystore_api-0.0.1.jar
# O servidor inciará na porta:8080 - acesse http://localhost:8080 

```

## 🚀 Como o projeto foi iniciado

Este projeto é divido em apenas uma parte:
1. Backend (mystore_api/arquivos_do_projeto) 

💡Esta aplicação precisa que o Backend esteja sendo executado para funcionar.

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
[Git](https://git-scm.com) e [JDK 11](https://www.oracle.com/br/java/technologies/javase-jdk11-downloads.html). 
Além disto é bom ter um editor para trabalhar com o código como [Spring Tools Suite](https://spring.io/tools).

#### 🎲 Rodando o servidor (Backend)

```bash
# Acesse a pasta do projeto no terminal/cmd
$ cd mystore_api/arquivos_do_projeto

# Execute a aplicação em modo de desenvolvimento
$ java -jar mystore_api-0.0.1.jar

# O servidor inciará na porta:8080 - acesse http://localhost:8080 

# Testando os JWT Tokens
$ https://jwt.io/

# Criptografando as senhas
$ https://bcrypt-generator.com/

```
#### 🛠️ Construindo a aplicação

```bash
# Configurando o projeto inicial [https://start.spring.io/](https://start.spring.io/).
$ spring-boot-starter

#Instala a dependência do Java Web [https://search.maven.org/artifact/org.springframework.boot/spring-boot-starter-web/2.2.2.RELEASE].
$ spring-boot-starter-web

# Instala a dependência de restart da aplicação, recomendado apenas para ambiente de desenvolvimento [https://search.maven.org/artifact/org.springframework.boot/spring-boot-devtools/2.2.2.RELEASE].
$ spring-boot-devtools

#Instala a dependência do JFreeChart [https://github.com/jfree/jfreechart].
$ jfreechart

```

## 🛠️ Construído com

* [Spring Tools 4](https://spring.io/tools) - IDE Spring Tools
* [Workbench](https://www.mysql.com/products/workbench/) - MySQL Workbench
* [Postman](https://www.postman.com/) - Postman API


## 👨‍💻 Equipe de Desenvolvimento

* **Geverson Souza** - [Geverson Souza](https://www.linkedin.com/in/srgeverson/)

## ✒️ Autores

* **Geverson Souza** - [Geverson Souza](https://www.linkedin.com/in/srgeverson/)

## 📌 Versão ainda em produção

Nós usamos [Bitbucket](https://bitbucket.org/) para controle de versão.