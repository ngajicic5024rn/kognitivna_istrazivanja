package org.example.controler;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.Config;
import org.example.UserFileService;
import org.example.view.GlavniProzor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUserControler implements EventHandler<ActionEvent> {

    private TextField tfIme ;
    private PasswordField tfPassword ;
    private Label lblStatus;

    public AddUserControler(TextField tfIme, PasswordField tfPassword, Label lblStatus) {
        this.tfIme = tfIme;
        this.tfPassword = tfPassword;
        this.lblStatus = lblStatus;
    }

    @Override
    public void handle(ActionEvent event) {
        String username = this.tfIme.getText();
        String password = this.tfPassword.getText();

        lblStatus.setText("");

        if (username == null || username.isBlank()) {
            setError("Greška: Korisničko ime ne sme biti prazno!");
            return;
        }
        if (password == null || password.isBlank()) {
            setError("Greška: Lozinka ne sme biti prazna!");
            return;
        }

        /*
        this.runQuery(Config.getConnection(), username.trim(), password);
        //this.contactDtoTableView.setItems(FXCollections.observableArrayList(
           //     ContactDto.readAll(Config.getConnection())));
        this.tfIme.clear();
        this.tfPassword.clear();

         */
        try {
            if (UserFileService.userExists(username.trim())) {
                setError("Greška: Korisnik '" + username.trim() + "' već postoji!");
                return;
            }
            UserFileService.saveUser(username.trim(), password);
            setSuccess("Korisnik '" + username.trim() + "' je uspešno registrovan!");
            tfIme.clear();
            tfPassword.clear();
            GlavniProzor glavni = new GlavniProzor();
            glavni.show();
        } catch (IOException e) {
            setError("Greška pri čuvanju korisnika u fajl!");
        }
    }

    private void setError(String msg) {
        lblStatus.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        lblStatus.setText(msg);
    }

    private void setSuccess(String msg) {
        lblStatus.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        lblStatus.setText(msg);
    }

    /*
    private void runQuery(Connection connection, String firstName, String password) {
        String query = "INSERT INTO korisnik(username, password) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, password);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    */
}
