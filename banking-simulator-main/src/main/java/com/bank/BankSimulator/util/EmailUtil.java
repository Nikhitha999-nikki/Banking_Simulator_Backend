package com.bank.BankSimulator.util;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


 
 
 
public class EmailUtil{
	
	private static final String FROM_EMAIL = "noreplysbipamidi@gmail.com";
	private static final String APP_PASSWORD = "yvxt weiq qzhd mzsv";
	
	public static void sendEmail(String to ,String subject, String body){
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		 Session session = Session.getInstance(props,new Authenticator() {
			 protected PasswordAuthentication getPasswordAuthentication() {
				 return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
			 }
		 });
		 
		 
		try {
			MimeMessage message = new MimeMessage(session);
			//InternetAddress internet = new InternetAddress(FROM_EMAIL);
			message.setFrom(new InternetAddress(FROM_EMAIL));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
			System.out.println("Email sent to : "+to);
		}
		catch(MessagingException e) {
			System.out.println("Email Error : "+e.getMessage());
		}
		
		
	}
}
