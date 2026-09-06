package com.jakarta.mail.utils;

import java.util.Properties;

public class ConfiguracoesEmail {

    public static String hostPop3Gmail = "pop.gmail.com";

    /**
     * Retorna as configurações de email do Gmail para envio ou checagem de emails.
     * @param enviarOuChecar true para envio de emails, false para checagem de emails
     * @return as configurações de email do Gmail apropriadas para a operação desejada
     */
    public static Properties getConfiguracoesGMAIL(String tipoConfig) {
        
        Properties configuracoesGMAIL = new Properties();

        if (tipoConfig.equals("enviar")) {

            // configurações para envio de e-mails via SMTP do Gmail
            configuracoesGMAIL.put("mail.smtp.auth", "true");
            configuracoesGMAIL.put("mail.smtp.host", "smtp.gmail.com");
            configuracoesGMAIL.put("mail.smtp.port", "465");
            configuracoesGMAIL.put("mail.smtp.ssl.enable", "true");
            configuracoesGMAIL.put("mail.smtp.ssl.protocols", "TLSv1.2");
            
        } else if (tipoConfig.equals("buscar")) {

            // configurações para checagem de e-mails via POP3S (SSL implícito) do Gmail
            configuracoesGMAIL.put("mail.pop3s.host", hostPop3Gmail);
            configuracoesGMAIL.put("mail.pop3s.port", "995");
            configuracoesGMAIL.put("mail.pop3s.ssl.enable", "true");
            configuracoesGMAIL.put("mail.pop3s.ssl.protocols", "TLSv1.2");

        }

        return configuracoesGMAIL;
    }
}
