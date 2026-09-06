import org.junit.jupiter.api.Test;

import com.jakarta.mail.services.EmailService;

public class testeEmail {

    EmailService emailService = new EmailService();

    @Test 
    public void testarBuscarEmail() {
        emailService.buscarEmail();
    }

}
