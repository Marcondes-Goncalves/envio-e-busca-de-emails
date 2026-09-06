package com.jakarta.mail.services;

import java.util.Properties;

import com.jakarta.mail.utils.ConfiguracoesEmail;
import com.jakarta.mail.utils.Credenciais;

import jakarta.mail.Authenticator;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.NoSuchProviderException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Store;

import java.io.IOException;

public class EmailService {

    /**
     * Busca o primeiro email da caixa de entrada do Gmail usando POP3S.
     * Exibe o remetente, assunto e corpo do email no console.
     */
    public void buscarEmail() {

        try {

            Properties credenciais = Credenciais.carregar("env.properties");

            String remetente = credenciais.getProperty("EMAIL_GMAIL");
            String senha = credenciais.getProperty("SENHA_GMAIL");

            // Configura a sessão para buscar emails via POP3S do Gmail
            Session session  = Session.getDefaultInstance(

                ConfiguracoesEmail.getConfiguracoesGMAIL("buscar"),
        
                new Authenticator() {

                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(remetente, senha);
                    }
                });

            // Obtém o store para acessar a caixa de correio via POP3S
            Store store = session.getStore("pop3s");
            store.connect(remetente, senha);

            // Obtém a pasta INBOX para ler os emails
            Folder inbox = store.getFolder("INBOX");
            // Abre a pasta INBOX em modo de leitura e escrita
            inbox.open(Folder.READ_WRITE);

            // Obtém a primeira mensagem da pasta INBOX
            Message mensagens = inbox.getMessage(1);

            System.out.println("Remetente: " + mensagens.getFrom()[0]);
            System.out.println("Assunto: " + mensagens.getSubject());
            System.out.println("Corpo: " + mensagens.getContent());

            // Fecha a pasta INBOX e o store
            inbox.close();
            store.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }
    
}
