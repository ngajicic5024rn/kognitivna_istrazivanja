package org.example.controler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.Config;
import org.example.UserFileService;
import org.example.model.UlogovaniIstrazivac;
import org.example.view.GlavniProzor;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            if (runQueryLogIn(Config.getConnection(), username)){
                GlavniProzor glavni = new GlavniProzor();
                glavni.show();

            }

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

    private boolean runQueryLogIn(Connection connection, String username ) {
        String query = "select * from istrazivac where username = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Integer id = resultSet.getInt("id");
                String ime_i_prezime = resultSet.getString("ime_i_prezime");
                String email = resultSet.getString("email");
                String institucija = resultSet.getString("institucija");
                String naucnoZvanje = resultSet.getString("naucno_zvanje");
                String oblastSpec = resultSet.getString("oblast_spec");

                GlavniProzor.ulogovaniIstrazivac = new UlogovaniIstrazivac(ime_i_prezime, username, email, institucija, naucnoZvanje, oblastSpec, id);
                return true;
            }

        } catch (SQLException e) {
            return false;
        }
        return false;
    }

}