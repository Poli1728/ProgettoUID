module com.calendly.calendly {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires spring.security.crypto;
    opens com.calendly.calendly.Model;
    opens com.calendly.calendly to javafx.fxml;
    exports com.calendly.calendly;
    exports com.calendly.calendly.Controller;
    opens com.calendly.calendly.Controller to javafx.fxml;
    exports com.calendly.calendly.Controller.GestioneAppuntamenti;
    opens com.calendly.calendly.Controller.GestioneAppuntamenti to javafx.fxml;

}