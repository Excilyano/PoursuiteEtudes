import java.util.Properties;
//import javax.mail.*;
//import javax.mail.internet.*;
 
public class Application {
    public static void main(String args[]){
         
        try{
         
    //ouverture d'une session. la session gére les informations de configuration (nom d'utilisateur, mot de passe, hôte) nécessaires pour utiliser les fonctionnalités de JavaMail
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    props.setProperty("mail.from", "adresse@expediteur"); // @ expediteur
    Session session= Session.getInstance(props);
     
    //Le message
    Message     message     = new MimeMessage(session);
    InternetAddress recipient   = new InternetAddress("adresse@destination");//***
    message.setRecipient(Message.RecipientType.TO, recipient);
    message.setSubject("Hello JavaMail");
    message.setText("JavaMail vous dit bonjour!");
     
    //Transport
    Transport.send(message);
        }catch(NoSuchProviderException e) {
            System.err.println("Pas de transport disponible pour ce protocole");
            System.err.println(e);
        }
        catch(AddressException e) {
            System.err.println("Adresse invalide");
            System.err.println(e);
        }
        catch(MessagingException e) {
            System.err.println("Erreur dans le message");
            System.err.println(e);
        }
    }
 
}