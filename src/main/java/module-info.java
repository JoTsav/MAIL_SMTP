module com.mail.mail_smtp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.mail.mail_smtp to javafx.fxml;
    exports com.mail.mail_smtp;
}