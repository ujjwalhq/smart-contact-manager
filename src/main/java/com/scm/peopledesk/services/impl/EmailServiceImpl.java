package com.scm.peopledesk.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.scm.peopledesk.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender eMailSender;

  @Override
  public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("verfiy@peopledesk.com");

        eMailSender.send(message);

  }


  @Override
  public void sendEmailWithHtml() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'sendEmailWithHtml'");
  }

  @Override
  public void sendEMailWithAttachment() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'sendEMailWithAttachment'");
  }

}
