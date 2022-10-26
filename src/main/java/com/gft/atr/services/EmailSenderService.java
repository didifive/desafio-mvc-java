package com.gft.atr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.gft.atr.entities.Usuario;

@Service
public class EmailSenderService {
	
	@Autowired
  private JavaMailSender mailSender;
   
  public void sendEmailConfirmacaoCadastroTo(Usuario usuario) {
  	String from = "didigftteste@gmail.com";
  	String to = usuario.getEmail();
  	 
  	SimpleMailMessage message = new SimpleMailMessage();
  	 
  	message.setFrom(from);
  	message.setTo(to);
  	message.setSubject("Confirmação de cadastro");
  	message.setText(String.format("Olá, este email é uma confirmação que %s se cadastrou no site Awesome Tasty Recipes com o usuário %s."
  							,usuario.getNome()
  							,usuario.getNomeUsuario()));
  	 
  	mailSender.send(message);
  }

}
