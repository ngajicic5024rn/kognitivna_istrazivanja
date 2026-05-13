package org.example.controler;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.UserFileService;
import org.example.view.GlavniProzor;

import java.io.IOException;

public class LogInControler implements EventHandler<ActionEvent> {

    private TextField tfUsername;
    private PasswordField tfPassword;
    private Label lblError;

    public LogInControler(TextField tfUsername, PasswordField tfPassword, Label lblError) {
        this.tfUsername = tfUsername;
        this.tfPassword = tfPassword;
        this.lblError = lblError;
    }

    @Override
    public void handle(ActionEvent event) {
        String username = tfUsername.getText();
        String password = tfPassword.getText();

        lblError.setText("");

        if (username == null || username.isBlank()) {
            setError("Greška: Korisničko ime ne sme biti prazno!");
            return;
        }
        if (password == null || password.isBlank()) {
            setError("Greška: Lozinka ne sme biti prazna!");
            return;
        }

        try {
            if (!UserFileService.userExists(username.trim())) {
                setError("Greška: Korisnik '" + username.trim() + "' ne postoji! Registrujte se.");
                return;
            }
            if (!UserFileService.authenticate(username.trim(), password)) {
                setError("Greška: Pogrešna lozinka! Pokušajte ponovo.");
                tfPassword.clear();
                return;
            }
            setSuccess("Dobrodošli, " + username.trim() + "! Uspešno ste se prijavili.");
            GlavniProzor glavni = new GlavniProzor();
            glavni.show();

        } catch (IOException e) {
            setError("Greška pri čitanju korisničkih podataka!");
        }
    }

    private void setError(String msg) {
        lblError.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        lblError.setText(msg);
    }

    private void setSuccess(String msg) {
        lblError.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        lblError.setText(msg);
    }
}