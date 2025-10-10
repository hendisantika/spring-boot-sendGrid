package com.hendisantika.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${sendgrid.template.id}")
    private String templateId;

    @Value("${sendgrid.from.email}")
    private String fromEmail;

    @Value("${sendgrid.to.email}")
    private String toEmail;

    public String sendTextEmail() throws IOException {
        /*
         * The sender email should be the same as we used to Create a Single Sender
         * Verification
         */
        Email from = new Email(fromEmail);
        String subject = "Test Email from SendGrid";
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", "This is a test email sent via SendGrid API");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            log.info("SendGrid Response Status Code: {}", response.getStatusCode());
            log.info("SendGrid Response Body: {}", response.getBody());
            log.info("SendGrid Response Headers: {}", response.getHeaders());

            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                return "Email sent successfully! Status Code: " + response.getStatusCode();
            } else {
                return "Failed to send email. Status Code: " + response.getStatusCode() + ", Body: " + response.getBody();
            }
        } catch (IOException ex) {
            log.error("Error sending email: ", ex);
            throw ex;
        }
    }

    public String send() throws IOException {
        /*
         * The sender email should be the same as we used to Create a Single Sender
         * Verification
         */
        Email from = new Email(fromEmail);
        String subject = "Test Email with Template";
        Email to = new Email(toEmail);
        Mail mail = new Mail();
        DynamicTemplatePersonalization personalization = new DynamicTemplatePersonalization();
        personalization.addTo(to);
        mail.setFrom(from);
        mail.setSubject(subject);
        // this is the dynamic value of first_name variable on our template
        //feel free to create a variable firstName  passed with the send method
        personalization.addDynamicTemplateData("full_name", "Uchiha Sasuke");
        mail.addPersonalization(personalization);
        mail.setTemplateId(templateId);
        // feel free to save this varible on the env
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            log.info("SendGrid Template Email Response Status Code: {}", response.getStatusCode());
            log.info("SendGrid Template Email Response Body: {}", response.getBody());
            log.info("SendGrid Template Email Response Headers: {}", response.getHeaders());

            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                return "Email with template sent successfully! Status Code: " + response.getStatusCode();
            } else {
                return "Failed to send email with template. Status Code: " + response.getStatusCode() + ", Body: " + response.getBody();
            }
        } catch (IOException ex) {
            log.error("Error sending email with template: ", ex);
            throw ex;
        }
    }

    // This class handle the dynamic data for the template
    // Feel free to customize this class
    private static class DynamicTemplatePersonalization extends Personalization {

        @JsonProperty(value = "dynamic_template_data")
        private Map<String, Object> dynamic_template_data;

        @JsonProperty("dynamic_template_data")
        public Map<String, Object> getDynamicTemplateData() {
            if (dynamic_template_data == null) {
                return Collections.emptyMap();
            }
            return dynamic_template_data;
        }

        public void addDynamicTemplateData(String key, Object value) {
            if (dynamic_template_data == null) {
                dynamic_template_data = new HashMap<>();
                dynamic_template_data.put(key, value);
            } else {
                dynamic_template_data.put(key, value);
            }
        }

    }
}
