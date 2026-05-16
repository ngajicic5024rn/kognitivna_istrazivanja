package org.example.view;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Config;
import org.example.controler.IzmenaStatusaControler;
import org.example.controler.PregledControler;
import org.example.model.Eksperiment;
import org.example.model.Status;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PregledProzor extends Stage {
    private RadioButton rbPlanirani = new RadioButton("Planirani");
    private RadioButton rbZavrseni = new RadioButton("Zavrseni");
    private TableView<Eksperiment> tvEksperimenti = new TableView();
    ToggleGroup grupaStatus = new ToggleGroup();
    private Button btnIzmenaStatusa = new Button("Izmeni status");
    private  ComboBox<Status> cbStatusi = new ComboBox<>();


    public PregledProzor() {
        rbPlanirani.setToggleGroup(grupaStatus);
        rbZavrseni.setToggleGroup(grupaStatus);

        TableColumn<Eksperiment, String> nazivEksperimenta = new TableColumn<>("Naziv Eksperimenta");
        nazivEksperimenta.setCellValueFactory(new PropertyValueFactory<>("nazivEksperimenta"));

        TableColumn<Eksperiment, String> tipEksperimenta = new TableColumn<>("Tip Eksperimenta");
        tipEksperimenta.setCellValueFactory(new PropertyValueFactory<>("tip"));

        TableColumn<Eksperiment, LocalDate> datumEksperimenta = new TableColumn<>("Datum Eksperimenta");
        datumEksperimenta.setCellValueFactory(new PropertyValueFactory<>("datum"));

        TableColumn<Eksperiment, String> nazivLaboratorije = new TableColumn<>("Naziv Laboratorije");
        nazivLaboratorije.setCellValueFactory(new PropertyValueFactory<>("nazivLaboratorije"));

        TableColumn<Eksperiment, String> status = new TableColumn<>("Status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        HBox hbIzmena = new HBox(10);
        hbIzmena.setAlignment(Pos.CENTER);
        hbIzmena.getChildren().addAll(btnIzmenaStatusa,cbStatusi);

        ucitajStatuse();

        tvEksperimenti.getColumns().addAll(nazivEksperimenta, tipEksperimenta, datumEksperimenta, nazivLaboratorije, status);
        HBox hb = new HBox(10);
        hb.getChildren().addAll(rbPlanirani, rbZavrseni);
        VBox vb = new VBox(10);
        vb.getChildren().addAll(tvEksperimenti, hb,hbIzmena);
        Scene scene = new Scene(vb, 800, 600);
        setScene(scene);
        hb.setAlignment(Pos.CENTER);
        vb.setAlignment(Pos.CENTER);
        grupaStatus.selectedToggleProperty().addListener(new PregledControler(tvEksperimenti, rbPlanirani ,rbZavrseni));
        rbPlanirani.setSelected(true);
        btnIzmenaStatusa.setOnAction( new IzmenaStatusaControler(cbStatusi,tvEksperimenti));
    }

    private void ucitajStatuse() {

        String query = " select id_status, naziv from status ";

        try {

            PreparedStatement preparedStatement =
                    Config.getConnection().prepareStatement(query);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            List<Status> statusi = new ArrayList<>();

            while (resultSet.next()) {

                int id = resultSet.getInt("id_status");
                String naziv = resultSet.getString("naziv");

                Status status = new Status(id, naziv);

                statusi.add(status);
            }

            cbStatusi.setItems(
                    FXCollections.observableArrayList(statusi)
            );
        cbStatusi.getSelectionModel().selectFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
