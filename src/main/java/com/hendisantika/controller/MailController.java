package com.hendisantika.controller;

import com.hendisantika.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-sendGrid
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 19/02/22
 * Time: 08.17
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping(value = "/api")
public class MailController {

    @Autowired
    MailService mailService;

    @GetMapping("/send-text")
    public String send() throws IOException {
        return mailService.sendTextEmail();
    }

    @GetMapping("/send")
    public String sendWithTemplate() throws IOException {
        return mailService.send();
    }
}
