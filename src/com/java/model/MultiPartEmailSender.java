package com.java.model;
import java.util.*;
import java.io.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import javax.activation.*;

import org.apache.commons.io.IOUtils;

public class MultiPartEmailSender {
	
	public static void sendHtmlEmail(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String messageContent, InputStream is) throws AddressException, MessagingException{

		// sets SMTP server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.mime.address.strict", "false");
        
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        
        Session mailSession = Session.getDefaultInstance(props, auth);

        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        message.setRecipients(Message.RecipientType.TO, toAddresses);
        message.setSubject(subject);
        message.setSentDate(new Date());

        //
        // This HTML mail have to 2 part, the BODY and the embedded image
        //
        MimeMultipart multipart = new MimeMultipart("related");

        // first part  (the html)
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = messageContent;
        messageBodyPart.setContent(htmlText, "text/html");

        // add it
        multipart.addBodyPart(messageBodyPart);
        
        // second part (the image)
        messageBodyPart = new MimeBodyPart();

        try {
			DataSource source = new ByteArrayDataSource(IOUtils.toByteArray(is), "image/jpg");
			
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setHeader("Content-ID","<image>");
	
	        // add it
	        multipart.addBodyPart(messageBodyPart);
	
	        // put everything together
	        message.setContent(multipart);
	        
	        // sends the e-mail
	        Transport.send(message);
        
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
} 