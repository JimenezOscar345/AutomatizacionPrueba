package Linktic.utils;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtils {

    private static final String HOST = "imap.gmail.com";
    private static final String USERNAME = ConfigReader.readProperty("email");
    private static final String PASSWORD = ConfigReader.readProperty("emailPassword");

    public static String getOTP() {
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore();
            store.connect(HOST, USERNAME, PASSWORD);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // Esperar hasta que llegue el último correo con el código OTP.
            String otp = null;
            while (otp == null) {
                Message[] messages = emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
                for (int i = messages.length - 1; i >= 0; i--) { // Iterar desde el último mensaje
                    Message message = messages[i];
                    if (message.getSubject().contains("OTP Andina Seguros")) {
                        String content = getTextFromMessage(message);
                        // Extraer el código OTP del contenido del correo
                        otp = extractOTP(content);
                        if (otp != null) {
                            break;
                        }
                    }
                }
                if (otp == null) {
                    // Esperar 5 segundos antes de volver a intentar
                    Thread.sleep(5000);
                }
            }

            emailFolder.close(false);
            store.close();
            return otp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getNextOTP() {
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore();
            store.connect(HOST, USERNAME, PASSWORD);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            for (int i = messages.length - 1; i >= 0; i--) { // Iterar desde el último mensaje
                Message message = messages[i];
                if (message.getSubject().contains("OTP Andina Seguros")) {
                    String content = getTextFromMessage(message);
                    // Extraer el código OTP del contenido del correo
                    String otp = extractOTP(content);
                    if (otp != null) {
                        return otp;
                    }
                }
            }

            emailFolder.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getTextFromMessage(Message message) throws Exception {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result.append("\n").append(bodyPart.getContent());
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result.append("\n").append(html);
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        return result.toString();
    }

    private static String extractOTP(String content) {
        // Usar una expresión regular para encontrar el código OTP de 6 dígitos en el HTML
        Pattern pattern = Pattern.compile("<div class=\"code\">(\\d{6})</div>");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static void refreshEmail() throws Exception {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        Session emailSession = Session.getDefaultInstance(properties);
        Store store = emailSession.getStore();
        store.connect(HOST, USERNAME, PASSWORD);

        Folder emailFolder = store.getFolder("INBOX");
        emailFolder.open(Folder.READ_ONLY);

        // Refrescar el correo electrónico
        emailFolder.getMessageCount();

        emailFolder.close(false);
        store.close();
    }
}