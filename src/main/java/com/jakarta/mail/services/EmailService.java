package com.jakarta.mail.services;

import java.util.List;
import java.util.Properties;
import jakarta.mail.Multipart;

import com.jakarta.mail.utils.ConfiguracoesEmail;
import com.jakarta.mail.utils.Credenciais;

import jakarta.mail.Address;
import jakarta.mail.Authenticator;
import jakarta.mail.BodyPart;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.NoSuchProviderException;
import jakarta.mail.Part;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.ContentType;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;

import java.io.IOException;

/**
 * Serviço para buscar e exibir emails usando Jakarta Mail.
 * EmailService
 */
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
            inbox.open(Folder.READ_ONLY);

            // Obtém a primeira mensagem da pasta INBOX
            Message mensagens = inbox.getMessage(1);

            System.out.println("Remetente: " + mensagens.getFrom()[0]);
            System.out.println("Assunto: " + mensagens.getSubject());
            exibirCorpoEmail(mensagens);

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

    /**
     * Método que exibe informações sobre um anexo de email, incluindo o nome do arquivo e o tipo MIME.
     * Futuramente pode ser utilizado para processar anexos de emails, como salvar em disco ou enviar para outro serviço.
     * @param parteEmail
     * @param parte
     * @throws MessagingException
     * @throws IOException
     */
    public void exibirOuProcessarAnexo(BodyPart parteEmail, Part parte) throws MessagingException, IOException {

        String nomeArquivoBruto = parteEmail.getFileName();

        String nomeArquivo = nomeArquivoBruto != null 
                            ? MimeUtility.decodeText(nomeArquivoBruto)
                            : "Anexo sem nome";

        String tipoMime = new ContentType(parte.getContentType()).getBaseType();

        System.out.println("Nome do arquivo: " + nomeArquivo);
        System.out.println("Tipo MIME: " + tipoMime);
    }

    /**
     * Exibe o corpo do email que podem conter texto/html, texto/plain e anexos.
     * Exibe também informações sobre anexos como nome do anexo e o seu tipo MIME, se presentes.
     * @param parte
     * @throws MessagingException
     * @throws IOException
     */
    public void exibirCorpoEmail(Part parte) throws MessagingException, IOException {

        if (parte.isMimeType("text/html")) {
            System.out.println("Corpo HTML: ");
            System.out.println(parte.getContent());
            return;
        }
        
        if (parte.isMimeType("text/plain")) {
            System.out.println("Corpo Texto: ");
            System.out.println(parte.getContent());
            return;
        }
        
        if (parte.isMimeType("multipart/*")) {

            Multipart multipart = (Multipart) parte.getContent();

            for (int i = 0; i < multipart.getCount(); i++) {

                BodyPart parteEmail = multipart.getBodyPart(i);

                boolean temAnexo = Part.ATTACHMENT.equalsIgnoreCase(parteEmail.getDisposition()) 
                        || parteEmail.getFileName() != null;

                //Esse percurso recursivo é importante porque e-mails costumam usar uma estrutura como multipart/mixed 
                //contendo anexos e um multipart/alternative, que por sua vez contém as versões text/plain e text/html do corpo.
                if (!temAnexo) {

                    exibirCorpoEmail(parteEmail);

                }else {

                    exibirOuProcessarAnexo(parteEmail, parte);
                    
                }
            }
        }
    }

    /**
     * Envia um e-mail para os destinatários especificados com o assunto e corpo fornecidos.
     * @param destinatarios
     * @param assunto
     * @param corpo
     */
    public void enviarEmail(List<String> destinatarios, String assunto, String corpo) {

        try {

            Properties credenciais = Credenciais.carregar("env.properties");

            String remetente = credenciais.getProperty("EMAIL_GMAIL");
            String senha = credenciais.getProperty("SENHA_GMAIL");

            Session session = Session.getDefaultInstance(

                ConfiguracoesEmail.getConfiguracoesGMAIL("enviar"), 

                new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remetente, senha);
                }
            });

            // Lista de endereços de destinatários
            Address[] enderecos = InternetAddress.parse(String.join(",", destinatarios));

            // Criação da mensagem de e-mail
            MimeMessage mensagem = new MimeMessage(session);

            // Configuração do remetente, destinatários, assunto e corpo do e-mail
            mensagem.setFrom(new InternetAddress(remetente));
            mensagem.setRecipients(Message.RecipientType.TO, enderecos);
            mensagem.setSubject(assunto);
            mensagem.setText(corpo);

            // Envio da mensagem de e-mail
            Transport.send(mensagem);

            // Fechamento do transporte de e-mail
            session.getTransport().close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
}
