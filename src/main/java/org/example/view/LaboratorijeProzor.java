package org.example.view;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.Config;
import org.example.model.IzvodjenjaPoIstrazivacu;
import org.example.model.Laboratorija;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LaboratorijeProzor extends Stage {

    private TableView<Laboratorija> tvLaboratorija = new TableView<>();

    public LaboratorijeProzor() {
        TableColumn<Laboratorija, String> tcNaziv = new TableColumn<>("Naziv");
        tcNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));

        TableColumn<Laboratorija, Integer> tcBrojResursa = new TableColumn<>("Broj resursa");
        tcBrojResursa.setCellValueFactory(new PropertyValueFactory<>("brResursa"));

        TableColumn<Laboratorija, String> tcOpisLaboratorije = new TableColumn<>("Opis laboratorije");
        tcOpisLaboratorije.setCellValueFactory(new PropertyValueFactory<>("opisLaboratorije"));

        tvLaboratorija.getColumns().addAll(tcNaziv,tcOpisLaboratorije,tcBrojResursa);

        Scene scene = new Scene(tvLaboratorija,800,600);
        setScene(scene);
        proveriFunkciju(Config.getConnection());
    }

    private void proveriFunkciju(Connection connection) {

        String query =
                "SELECT \n" +
                        "    l.naziv,\n" +
                        "l.opis_lok,\n" +
                        "    COUNT(lr.resurs_id) AS broj_resursa\n" +
                        "FROM laboratorija l\n" +
                        "JOIN laboratorija_resurs lr\n" +
                        "ON l.id = lr.laboratorija_id\n" +
                        "GROUP BY l.id, l.naziv\n" +
                        "ORDER BY broj_resursa DESC;";

        try {

            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);


            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {
                String naziv = resultSet.getString("naziv");
                String opisLaboratorije = resultSet.getString("opis_lok");
                Integer brojResurs = resultSet.getInt("broj_resursa");
                Laboratorija laboratorija = new Laboratorija(naziv,brojResurs,opisLaboratorije);

                tvLaboratorija.getItems().add(laboratorija);


            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
