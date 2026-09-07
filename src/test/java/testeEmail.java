import org.junit.jupiter.api.Test;

import com.jakarta.mail.services.EmailService;
import java.util.List;

public class testeEmail {

    EmailService emailService = new EmailService();

    List<String> destinatarios = List.of("josemarcondesgoncalves@gmail.com", "marcondes.gonsalves@usinasclotilde.com.br");

    @Test 
    public void testarBuscarEmail() {
        emailService.buscarEmail();
    }


    @Test 
    public void testarEnviarEmail() {

        emailService.enviarEmail(

            destinatarios, 

            "Assunto de Teste", 
            "Corpo do e-mail de teste"
        );
    }

}
