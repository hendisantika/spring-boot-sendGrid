package com.hendisantika.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-sendGrid
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 19/02/22
 * Time: 08.09
 * To change this template use File | Settings | File Templates.
 */
@Service
@Slf4j
public class MailService {
    public String sendTextEmail() throws IOException {
        /*
         * The sender email should be the same as we used to Create a Single Sender
         * Verification
         */
        Email from = new Email("hendisantika@yahoo.co.id");
        String subject = "The subject";
        Email to = new Email("hendi@yopmail.com");
        Content content = new Content("text/plain", "This is a test email");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("SENDGRID_API_KEY");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            log.info(response.getBody());
            return response.getBody();
        } catch (IOException ex) {
            throw ex;
        }
    }

}
