package org.kodigo.airport.utilities;

import org.kodigo.airport.Airport;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.Properties;

public class Email implements IMessageSender {

  private static final BufferedReader INPUT = Airport.INPUT;

  @Override
  public void sendMessage() {
    System.out.println("Enter the email address to which the report will be sent");
    String email = getEmail();

    try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
      var prop = new Properties();
      prop.load(input);
      String username = prop.getProperty("username");
      String password = prop.getProperty("password");

      var session =
          Session.getInstance(
              prop,
              new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                  return new PasswordAuthentication(username, password);
                }
              });

      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(username));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
      message.setSubject("Airport Report");

      // Text content of the email
      var msg = "The airport report is attached to this email in excel format";
      var mimeBodyPart = new MimeBodyPart();
      mimeBodyPart.setContent(msg, "text/html");
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(mimeBodyPart);

      // Attach the file
      var attachmentBodyPart = new MimeBodyPart();
      attachmentBodyPart.attachFile(new File("AirportReport.xlsx"));
      multipart.addBodyPart(attachmentBodyPart);

      message.setContent(multipart);

      Transport.send(message);
    } catch (MessagingException | IOException e) {
      System.out.println("Error when sending email");
    }
    System.out.println("The email was sent to " + email + " successfully");
  }

  private static String getEmail() {
    var email = "";
    boolean validEmail;
    try {
      do {
        email = INPUT.readLine();
        validEmail = emailValidator(email);
        if (!validEmail)
          System.out.println(
              "Invalid email format, make sure to enter a valid email (example@mailprovider.com)");
      } while (!validEmail);
    } catch (IOException e) {
      System.out.println("There was an input error when trying to get the email address");
    }
    return email;
  }

  private static boolean emailValidator(String email) {
    var regExEmail = "^(.+)@(.+)$";
    return email.matches(regExEmail);
  }
}
