package org.example.controler;

import javafx.collections.FXCollections;
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

public class AddUserControler implements EventHandler<ActionEvent> {

    private TextField tfIme ;
    private PasswordField tfPassword ;
    private Label lblStatus;

    private TextField tfImeiPrezime;
    private TextField tfEmail;
    private TextField tfInstitucija;
    private TextField tfNaucnoZvanje ;
    private TextField tfOblastSpecifikacije;




    public AddUserControler(TextField tfIme, PasswordField tfPassword, TextField tfImeiPrezime, TextField tfEmail, TextField tfInstitucija, TextField tfNaucnoZvanje, TextField tfOblastSpecifikacije, Label lblStatus) {
        this.tfIme = tfIme;
        this.tfPassword = tfPassword;
        this.tfImeiPrezime = tfImeiPrezime;
        this.tfEmail = tfEmail;
        this.tfInstitucija = tfInstitucija;
        this.tfNaucnoZvanje = tfNaucnoZvanje;
        this.tfOblastSpecifikacije = tfOblastSpecifikacije;
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


        try {
            if (UserFileService.userExists(username.trim())) {
                setError("Greška: Korisnik '" + username.trim() + "' već postoji!");
                return;
            }
            UserFileService.saveUser(username.trim(), password);
            setSuccess("Korisnik '" + username.trim() + "' je uspešno registrovan!");
            boolean uspesno = this.runQuery(
                    Config.getConnection(),
                    tfIme.getText(),
                    tfImeiPrezime.getText(),
                    tfEmail.getText(),
                    tfInstitucija.getText(),
                    tfNaucnoZvanje.getText(),
                    tfOblastSpecifikacije.getText()
            );
            tfIme.clear();
            tfPassword.clear();
            if (uspesno) {
                int id = runQuerygetidIstrazivaca(Config.getConnection(), tfIme.getText());
                GlavniProzor.ulogovaniIstrazivac = new UlogovaniIstrazivac(tfImeiPrezime.getText(),tfIme.getText(),tfEmail.getText(), tfInstitucija.getText(), tfNaucnoZvanje.getText(), tfOblastSpecifikacije.getText(),id);
                GlavniProzor glavni = new GlavniProzor();
                glavni.show();
            }
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

    private boolean runQuery(
            Connection connection,
            String username,
            String imeIPrezime,
            String email,
            String institucija,
            String naucnoZvanje,
            String oblastSpecifikacije
    ) {

        String query =
                "INSERT INTO istrazivac \n" +
                        "(username, ime_i_prezime, email, institucija, naucno_zvanje, oblast_spec) \n" +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try {

            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, imeIPrezime);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, institucija);
            preparedStatement.setString(5, naucnoZvanje);
            preparedStatement.setString(6, oblastSpecifikacije);

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            return false;
        }
    }
    private int runQuerygetidIstrazivaca(Connection connection, String username) {
        String query = "SELECT id FROM istrazivac where username = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
            return 0;


        } catch (SQLException e) {
            return 0;
        }
    }
}
