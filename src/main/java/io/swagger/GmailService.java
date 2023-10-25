package io.swagger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartBody;

import io.swagger.Repository.TransactionRepository;
import io.swagger.model.Transaction;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/* class to demonstrate use of Gmail list labels API */
@Service
public class GmailService {

    @Autowired
    private static TransactionRepository transactionRepository;

    public GmailService(TransactionRepository transactionRepository) {
        GmailService.transactionRepository = transactionRepository;
    }

    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_READONLY);

    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = GmailService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        // returns an authorized Credential object.
        return credential;
    }

    public static List<String> getEmailBodiesBySubject(Gmail service, String subject) throws IOException {
        String user = "me";
        List<String> emailBodies = new ArrayList<>();

        try {
            // Search for emails with the specified subject.
            String query = "subject:" + subject;
            ListMessagesResponse messagesResponse = service.users().messages().list(user).setQ(query).execute();

            List<Message> messages = messagesResponse.getMessages();
            for (Message message : messages) {
                // Retrieve the email message by ID.
                Message emailMessage = service.users().messages().get(user, message.getId()).execute();

                String emailBody = getEmailBody(service, emailMessage);
                emailBodies.add(emailBody);
            }
        } catch (GoogleJsonResponseException e) {
            // Handle API exceptions here
            e.printStackTrace();
        }

        return emailBodies;
    }

    private static String getEmailBody(Gmail service, Message message) throws IOException {
        String user = "me";
        String messageId = message.getId();
        Message emailMessage = service.users().messages().get(user, messageId).execute();

        if (emailMessage != null && emailMessage.getPayload() != null) {
            List<MessagePart> parts = emailMessage.getPayload().getParts();

            if (parts != null) {
                for (MessagePart part : parts) {
                    if (part.getFilename() != null && part.getFilename().endsWith(".pdf")) {
                        String attachmentId = part.getBody().getAttachmentId();
                        if (attachmentId != null) {
                            // Fetch the attachment data
                            MessagePartBody attachmentPart = service.users().messages().attachments()
                                    .get(user, emailMessage.getId(), attachmentId)
                                    .execute();
                            String base64Content = attachmentPart.getData();
                            System.out.println(base64Content);
                            if (base64Content != null) {
                                byte[] contentBytes = Base64.getUrlDecoder().decode(base64Content);
                                // Extract text from the PDF attachment
                                String pdfText = extractTextFromPDF(contentBytes);
                                if (pdfText != null && !pdfText.isEmpty()) {
                                    return pdfText;
                                }
                            }
                        }
                    }
                }
            }
        }

        // Handle the case where the email message, its content, or PDF attachment is
        // missing.
        // return "Email content not found";
        return "";
    }

    private static String extractTextFromPDF(byte[] pdfData) throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream(pdfData);
                PDDocument document = PDDocument.load(inputStream)) {

            PDFTextStripper textStripper = new PDFTextStripper();

            return textStripper.getText(document);
        } catch (Exception e) {
            return "\n";
        }
    }

    private static void createTransactionsFromEmailBody(List<String> emailBody) throws ParseException {
        List<Transaction> transactions = new ArrayList<>();

        for (String line : emailBody) {
            String[] values = line.split("\\s+"); // Split by one or more spaces

            if (values.length == 5) {
                String description = values[1];
                String date = values[2];
                String type = values[3];
                double amount = 0.0;

                SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    amount = Double.parseDouble(values[4]);
                    java.util.Date utilDate = inputDateFormat.parse(date);

                    // Convert java.util.Date to java.sql.Date
                    Date sqlDate = new Date(utilDate.getTime());
                    Transaction t1 = new Transaction();
                    t1.setAmount(amount);
                    t1.setType(type);
                    t1.setDate(sqlDate);
                    t1.setDescription(description);
                    transactions.add(t1);
                } catch (NumberFormatException e) {
                    // Handle parsing error if necessary
                }

                // System.out.println("Transaction: " + amount + " " + date + " " + type + " " +
                // description);

            }
            for (Transaction t : transactions) {
                transactionRepository.save(t);
            }
        }

    }

    public void getGmailService(String subjectToSearch) throws IOException, GeneralSecurityException, ParseException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Print the labels in the user's account.

        List<String> emailBodies = getEmailBodiesBySubject(service, subjectToSearch);

        if (emailBodies.isEmpty()) {
            System.out.println("No emails found with the subject: " + subjectToSearch);
        } else {

            String[] lines = emailBodies.get(0).split("\n");
            emailBodies = Arrays.asList(lines);
            createTransactionsFromEmailBody(emailBodies);
        }

    }

}
