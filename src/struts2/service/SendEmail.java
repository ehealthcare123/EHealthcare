package struts2.service;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {
     

public void send2(String patient, String doctor, String mail, String inhalt) {

    
    
	pdfnew p = new pdfnew(inhalt,patient, doctor);

    final String username = "swp.gruppe9@gmail.com"; //ur email
    final String password = "swpgruppe9";

    Properties props = new Properties();
    props.put("mail.smtp.auth", true);
    props.put("mail.smtp.starttls.enable", true);
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }                            
});

    try {
        
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("swp.gruppe9@gmail.com"));//ur email
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(mail));//u will send to
        message.setSubject("Rezept");    
        message.setText("PFA");
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();


     
     
    //attached 1 --------------------------------------------
        File file = new File(new File(System.getProperty("user.home")), "Rezept"+patient+".pdf");
    messageBodyPart = new MimeBodyPart();   
    DataSource source = new FileDataSource(file);      
    messageBodyPart.setDataHandler(new DataHandler(source));
    messageBodyPart.setFileName("Rezept"+patient+".pdf");
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

       
        System.out.println("sending");
        Transport.send(message);
        System.out.println("Done");
        
   p.deletePdf("Rezept"+patient+".pdf");
   

            
}catch (MessagingException e) {
        e.printStackTrace();
    }
  }
}
  
