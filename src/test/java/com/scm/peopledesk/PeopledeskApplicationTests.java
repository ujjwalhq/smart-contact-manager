package com.scm.peopledesk;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scm.peopledesk.services.EmailService;

@SpringBootTest
class PeopledeskApplicationTests {

  @Autowired
  private EmailService service;

  @Test
	void sendEmailTest(){
      service.sendEmail("theujjwal27@gmail.com", "For Testing email service", "This email is for test by peopledesk project");
  }

}
