package com.mail.mail_smtp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;

/**
 * @author Joseph Kianda Tshava (221131188)
 * @version MAIL_SMTP
 */
public class Main extends Application {

    private ClientSMTP clientSMTP;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Create a new instance of ClientSMTP
            clientSMTP = new ClientSMTP();

            // Create the main layout
            BorderPane root = new BorderPane();
            GridPane pane = new GridPane();
            root.setPadding(new Insets(10, 20, 20, 10));

            // Create input fields and labels
            Label hostNameLabel = new Label("Host Name: ");
            TextField hostField = new TextField();
            clientSMTP.hostField = hostField;

            Label portNumLabel = new Label("Port Number: ");
            TextField portField = new TextField();
            clientSMTP.portField = portField;

            Label senderLabel = new Label("Mail from: ");
            TextField senderField = new TextField("sender@csc2b.uj.ac.za");
            clientSMTP.senderField = senderField;

            Label recipientLabel = new Label("Mail to: ");
            TextField recipientField = new TextField("recipient@csc2b.uj.ac.za");
            clientSMTP.recipientField = recipientField;

            Label textLabel = new Label("Text field: ");
            TextArea textArea = new TextArea();
            textArea.setPrefHeight(200);
            clientSMTP.textArea = textArea;

            // Create the send button and status label
            Button sendButton = new Button("Send Mail");
            sendButton.setOnAction(e -> clientSMTP.sendEmail());
            Label statusLabel = new Label();
            clientSMTP.statusLabel = statusLabel;



            // Add components to the grid pane
            pane.add(hostNameLabel, 0, 0);
            pane.add(hostField, 1, 0);
            pane.add(portNumLabel, 0, 1);
            pane.add(portField, 1, 1);
            pane.add(senderLabel, 0, 2);
            pane.add(senderField, 1, 2);
            pane.add(recipientLabel, 0, 3);
            pane.add(recipientField, 1, 3);
            pane.add(textLabel, 0, 4);
            pane.add(textArea, 1, 4);
            pane.add(sendButton, 1, 5);
            pane.add(statusLabel, 1, 6);

            // Set up the primary stage
            primaryStage.setScene(new Scene(pane, 600, 300));
            primaryStage.setTitle("MAIL SMTP");

            // Loading and setting the application icon/
            try {
                Image icon = new Image(new FileInputStream("src/main/java/com/mail/mail_smtp/image/icon.png"));
                primaryStage.getIcons().add(icon);
            } catch (Exception e) {
                System.out.println("Error loading icon: " + e.getMessage());
            }

            primaryStage.show();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}