# 📬 Aprendizado de jakartaMAIL API

Este projeto é um estudo prático para aprender a utilizar a API Jakarta Mail (anteriormente conhecida como JavaMail) em aplicações Java.

Projeto de estudo sobre a API **Jakarta Mail**, utilizada para envio e recebimento de e-mails em aplicações Java.

## 📑 Sumário

- [Sobre o projeto](#sobre-o-projeto)
- [Pré-requisitos](#pré-requisitos)
- [Configuração da senha de aplicativo do Gmail](#configuração-da-senha-de-aplicativo-do-gmail)
- [Configurando as credenciais](#configurando-as-credenciais)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Segurança](#segurança)

## Sobre o projeto

Este projeto tem como objetivo estudar e aprender a utilizar a API Jakarta Mail para envio e recebimento de e-mails em aplicações Java.

## Pré-requisitos

- Java 25
- Maven
- Uma conta Gmail válida com senha de aplicativo configurada

## Configuração da senha de aplicativo do Gmail

Para utilizar a API Jakarta Mail com uma conta Gmail, é necessário criar uma **senha de aplicativo** específica para permitir que a aplicação envie e receba e-mails em seu nome. Isso pode ser feito nas configurações de segurança da sua conta Google.

Após criar a senha de aplicativo, utilize-a no lugar da sua senha normal ao configurar a autenticação na API Jakarta Mail. Isso garante que sua conta permaneça segura enquanto a aplicação tem acesso autorizado para enviar e receber e-mails.

> ⚠️ Nunca compartilhe sua senha de aplicativo. Armazene-a de forma segura, por exemplo, utilizando variáveis de ambiente ou um gerenciador de segredos.

## Configurando as credenciais

As credenciais do Gmail são carregadas a partir de um arquivo de propriedades utilizando a classe `Credenciais`. Isso permite manter as credenciais fora do código-fonte e facilita a configuração em diferentes ambientes ao utilizar a API Jakarta Mail.

1. Na raiz do projeto, crie um arquivo de propriedades, por exemplo `credenciais.properties`, contendo as chaves `EMAIL_GMAIL` e `SENHA_GMAIL`:

   ```properties
   EMAIL_GMAIL=seu_email@gmail.com
   SENHA_GMAIL=sua_senha_de_aplicativo
   ```

2. Utilize o caminho desse arquivo ao chamar o método. Exemplo: `Credenciais.carregar(caminho/para/credenciais.properties)`.

3. Adicione o arquivo `credenciais.properties` ao `.gitignore` para evitar que suas credenciais sejam versionadas no controle de código-fonte.

## Estrutura do projeto

```
src/main/java/com/jakarta/mail/
├── services/
│   └── EmailService.java         # lógica de envio de e-mails via Jakarta Mail
└── utils/
    ├── ConfiguracoesEmail.java   # configurações SMTP do Gmail
    └── Credenciais.java          # carregamento das credenciais do remetente via arquivo.properties ou em produção seria via variáveis de ambiente
```

## Segurança

- Nunca versione arquivos `.properties` com credenciais reais; mantenha-os no `.gitignore`.
- Prefira variáveis de ambiente ou um gerenciador de segredos em vez de arquivos em texto puro.
- Utilize sempre uma senha de aplicativo do Google, nunca a senha principal da conta.