package com.mail.mail_smtp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * This class represents a ClientSMTP that facilitates sending emails using the SMTP protocol.
 * Provided are methods to establish a connection, send email content, and handle SMTP responses.
 *
 * @author Joseph Kianda Tshava (221131188)
 * @version MAIL_SMTP
 */
public class ClientSMTP {

    Label statusLabel;
    TextArea textArea;
    TextField hostField;
    TextField portField;
    TextField senderField;
    TextField recipientField;

    /**
     * Constructor initializing UI components
     * @param statusLabel
     * @param textArea
     * @param hostField
     * @param portField
     * @param senderField
     * @param recipientField
     */
    public ClientSMTP(Label statusLabel, TextArea textArea, TextField hostField, TextField portField, TextField senderField, TextField recipientField) {
        this.statusLabel = statusLabel;
        this.textArea = textArea;
        this.hostField = hostField;
        this.portField = portField;
        this.senderField = senderField;
        this.recipientField = recipientField;
    }


    // No args
    public ClientSMTP() {}

    /**
     * Checks the SMTP response for errors
     *
     * @param reader
     * @throws IOException
     */
    private void checkSMTPResponse(BufferedReader reader) throws IOException {
        String response = reader.readLine();
        if (response.startsWith("4") || response.startsWith("5")) {
            throw new IOException("SMTP error: " + response);
        }
    }


    /**
     * Sends an email using the SMTP protocol
     */
    public void sendEmail() {
        try {
            // Obtain user inputs
            String host = hostField.getText();
            int port = Integer.parseInt(portField.getText());
            String text = textArea.getText();
            String recipient = recipientField.getText();
            String sender = senderField.getText();

            // Establish a socket connection
            Socket socket = new Socket(host, port);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // SMTP commands
            writer.printf("HELO %s\n", host);
            checkSMTPResponse(reader);
            writer.printf("MAIL FROM: \"%s\"\n", sender);
            checkSMTPResponse(reader);
            writer.printf("RCPT TO: \"%s\"\n", recipient);
            checkSMTPResponse(reader);
            writer.println("DATA");
            checkSMTPResponse(reader);

            // email content
            writer.printf("From: %s\n", sender);
            writer.printf("To: %s\n", recipient);
            writer.println("Subject: Test Email. \n\n");
            writer.printf("%s\n.\n", text);
            checkSMTPResponse(reader);

            writer.println("QUIT");
            checkSMTPResponse(reader);

            // Update status label
            statusLabel.setText("Mail sent successfully.");

            // Close the socket connection
            socket.close();
        } catch (Exception e) {
            System.err.printf("Error: %s", e);
            statusLabel.setText("Mail send failed: " + e.getMessage() + "\nTest a different port number. ");
        }
    }
}