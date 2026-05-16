package org.example.controler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import org.example.Config;
import org.example.model.Eksperiment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class PregledControler implements ChangeListener<Toggle> {
    private RadioButton rbPlanirani;
    private RadioButton rbZavrseni;
    private TableView<Eksperiment> table;

    public PregledControler(TableView<Eksperiment> table, RadioButton rbPlanirani, RadioButton rbZavrseni) {
        this.rbPlanirani = rbPlanirani;
        this.rbZavrseni = rbZavrseni;
        this.table = table;
    }

    @Override
    public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle newValue) {
        if (newValue == rbPlanirani) {
            runQuery(Config.getConnection(), "planirano");
        }
        if (newValue == rbZavrseni) {
            runQuery(Config.getConnection(), "zavrseno");
        }

    }

    private void runQuery(Connection connection, String status ) {
        String query = "select e.naziv as eksperiment, e.id, e.tip, i.datum, l.naziv as laboratorija,\n" +
                "s.naziv as status\n" +
                "from izvodjenje i\n" +
                "join eksperiment e on i.id_eksperiment = e.id\n" +
                "join laboratorija l on i.id_laboratorija = l.id\n" +
                "join status s on i.id_status = s.id_status\n" +
                "where s.naziv = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, status );
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Eksperiment> eksperimenti = new ArrayList<>();

            while (resultSet.next()) {

                String naziv = resultSet.getString("eksperiment");
                String tip = resultSet.getString("tip");

                LocalDate datum = resultSet.getDate("datum").toLocalDate();

                Integer id = resultSet.getInt("id");

                String laboratorija = resultSet.getString("laboratorija");

                String statusEksperimenta = resultSet.getString("status");

                Eksperiment eksperiment = new Eksperiment(
                        naziv,
                        laboratorija,
                        statusEksperimenta,
                        datum,
                        tip,
                        id
                );

                eksperimenti.add(eksperiment);
            }
            table.getItems().clear();
            table.getItems().addAll(eksperimenti);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
