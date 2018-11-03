package com.example.sso.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.sso.domain.User;
import com.example.sso.domain.VerificationToken;
import com.example.sso.event.OnRegistrationCompleteEvent;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		User user = event.getUser();
		sendEmail(event, user);
	}

	private void sendEmail(OnRegistrationCompleteEvent event, User user) {
		String recipientAddress = user.getEmail();
		VerificationToken verificationToken = user.getVerificationToken();
		String subject = "Registration Confirmation";
		String confirmationUrl = event.getAppUrl() + "/user/registrationConfirm.html?token="
				+ verificationToken.getToken();
		String message = this.messageSource.getMessage("message.regSucc", null, event.getLocale());

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText(message + " rn" + "http://localhost:8080" + confirmationUrl);
		this.mailSender.send(email);
	}

}
