package com.example.sso.listener;

import java.util.UUID;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.example.sso.event.OnRegistrationCompleteEvent;
import com.example.sso.model.User;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
//		service.createVerificationToken(user, token);

		String recipientAddress = user.getEmail();
		String subject = "Registration Confirmation";
		String confirmationUrl = event.getAppUrl() + "/regitrationConfirm.html?token=" + token;
//		String message = messages.getMessage("message.regSucc", null, event.getLocale());

//		SimpleMailMessage email = new SimpleMailMessage();
//		email.setTo(recipientAddress);
//		email.setSubject(subject);
//		email.setText(message + " rn" + "http://localhost:8080" + confirmationUrl);
//		mailSender.send(email);
	}

}
