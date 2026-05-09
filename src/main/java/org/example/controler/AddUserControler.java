package org.example.controler;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.Config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUserControler implements EventHandler<ActionEvent> {

    private TextField tfIme ;
    private PasswordField tfPassword ;

    public AddUserControler(TextField tfIme, PasswordField tfPassword) {
        this.tfIme = tfIme;
        this.tfPassword = tfPassword;
    }

    @Override
    public void handle(ActionEvent event) {
        String firstName = this.tfIme.getText();
        String password = this.tfPassword.getText();
        if (firstName == null || firstName.isBlank() || password == null || password.isBlank()) {
           // NULL_NAMES_ALTERT.showAndWait();
            return;
        }
        this.runQuery(Config.getConnection(), firstName.trim(), password);
        //this.contactDtoTableView.setItems(FXCollections.observableArrayList(
           //     ContactDto.readAll(Config.getConnection())));
        this.tfIme.clear();
        this.tfPassword.clear();
    }

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
}
