package org.example.view;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.Config;
import org.example.model.Dizajner;
import org.example.model.Laboratorija;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DizajnerProzor extends Stage {
    private TableView<Dizajner> tvDizajneri = new TableView<>();

    public DizajnerProzor() {
        TableColumn<Dizajner, String> tcIme = new TableColumn<>("Ime i Prezime");
        tcIme.setCellValueFactory(new PropertyValueFactory<>("ime"));

        TableColumn<Dizajner, String> tcTeorija = new TableColumn<>("Teorija");
        tcTeorija.setCellValueFactory(new PropertyValueFactory<>("teorija"));

        TableColumn<Dizajner, Integer> tcIskustvo = new TableColumn<>("Iskustvo");
        tcIskustvo.setCellValueFactory(new PropertyValueFactory<>("iskustvo"));

        tvDizajneri.getColumns().addAll(tcIme, tcTeorija, tcIskustvo);

        Scene scene = new Scene(tvDizajneri, 800, 600);
        setScene(scene);
        prikaziDizajnere(Config.getConnection());
    }

    private void prikaziDizajnere(Connection connection) {

        String query =
                "SELECT i.ime_i_prezime, t.naziv AS teorija, d.iskustvo_god FROM dizajner d JOIN istrazivac i ON d.id = i.id JOIN teorija t ON d.teorija_id = t.id ORDER BY d.iskustvo_god DESC";

        try {

            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);


            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {
                String ime = resultSet.getString("ime_i_prezime");
                String teorija = resultSet.getString("teorija");
                Integer iskustvo = resultSet.getInt("iskustvo_god");
                Dizajner dizajner = new Dizajner(ime, teorija, iskustvo);

                tvDizajneri.getItems().add(dizajner);


            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
