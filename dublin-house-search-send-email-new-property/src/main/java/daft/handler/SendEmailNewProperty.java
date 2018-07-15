package daft.handler;

import data.SearchMatchInfo;

import java.util.Properties;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmailNewProperty implements ISearchMatchHandler {

    @Override
    public void handleNewMatch(SearchMatchInfo matchInfo) {
        // System.out.println(matchInfo.getProperty().getFields().get("description"));

        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", "housesearch.contact@gmail.com");
        props.put("mail.smtp.password", "dhs2018&tyu");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress("housesearch.contact@gmail.com"));
            InternetAddress[] toAddress = new InternetAddress[1];

            toAddress[0] = new InternetAddress(matchInfo.getUser().getEmail());

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject("New property found: " + matchInfo.getSearchName());
            message.setText(matchInfo.getProperty().getUrl());
            Transport transport = session.getTransport("smtp");
            transport.connect(host, "housesearch.contact@gmail.com", "dhs2018&tyu");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }

}
