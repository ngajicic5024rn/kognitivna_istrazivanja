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
import org.example.controler.BrisanjeSesijeControler;
import org.example.controler.IzmenaStatusaControler;
import org.example.controler.PregledControler;
import org.example.model.Eksperiment;
import org.example.model.Status;
import java.sql.Connection;
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
    private Button btnBrisanjeSesije = new Button("Brisanje sesije");
    private Button btnPogled = new Button("Pogled");
    private Button btnBrojIzvodjenja = new Button("Broj Izvodjenja");
    private Button btnProvera = new Button("Provera");

    private Label lbBrojIzvodjenja = new Label("Broj Izvodjenja");
    private Label lbInfo = new Label("");


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
        vb.getChildren().addAll(tvEksperimenti,lbBrojIzvodjenja, hb,hbIzmena, btnBrisanjeSesije, btnPogled, btnBrojIzvodjenja,btnProvera, lbInfo);
        Scene scene = new Scene(vb, 800, 600);
        setScene(scene);
        hb.setAlignment(Pos.CENTER);
        vb.setAlignment(Pos.CENTER);
        grupaStatus.selectedToggleProperty().addListener(new PregledControler(tvEksperimenti, rbPlanirani ,rbZavrseni));
        rbPlanirani.setSelected(true);
        btnIzmenaStatusa.setOnAction( new IzmenaStatusaControler(cbStatusi,tvEksperimenti));
        btnBrisanjeSesije.setOnAction(new BrisanjeSesijeControler(tvEksperimenti));
        btnPogled.setOnAction(e-> {
            PogledView pogledView = new PogledView();
            pogledView.show();

        });
        btnBrojIzvodjenja.setOnAction(e-> {
            Eksperiment eksperiment = tvEksperimenti.getSelectionModel().getSelectedItem();
            if (eksperiment == null) {
                return;
            }
            kreirajFunkciju(Config.getConnection());
            int broj = brojIzvodjenjaEksperimenta(Config.getConnection(), eksperiment.getIdEksperimenta());
            lbBrojIzvodjenja.setText("Broj izvodjenja eksperimenta " + eksperiment.getNazivEksperimenta() + " je: " +  broj);
        });
        btnProvera.setOnAction(e-> {
            Eksperiment eksperiment = tvEksperimenti.getSelectionModel().getSelectedItem();
            if (eksperiment == null) {
                return;
            }
            kreirajFunkcijuZaProveru(Config.getConnection());
            boolean provera = proveriFunkciju(Config.getConnection(), eksperiment.getIdEksperimenta());
            if (provera) {
                lbInfo.setText("Eksperiment ima barem 1 izvodjenje");
            }else {
                lbInfo.setText("Eksperiment nema ni jedno izvodjenje");
            }

        });
    }

    private void kreirajFunkciju(Connection connection) {

        String query =
                "CREATE FUNCTION broj_izvodjenja_eksperimenta(p_id_eksperiment INT) " +
                        "RETURNS INT " +
                        "DETERMINISTIC " +
                        "BEGIN " +
                        "DECLARE broj INT; " +
                        "SELECT COUNT(*) INTO broj " +
                        "FROM izvodjenje " +
                        "WHERE id_eksperiment = p_id_eksperiment; " +
                        "RETURN broj; " +
                        "END";

        try {

            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);

            preparedStatement.execute();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void kreirajFunkcijuZaProveru(Connection connection) {
        kreirajFunkciju(Config.getConnection());

        String query =
                "CREATE FUNCTION test_broj_izvodjenja_eksperimenta(p_id_eksperiment INT)\n" +
                        "RETURNS BOOLEAN\n" +
                        "DETERMINISTIC\n" +
                        "BEGIN\n" +
                        "    DECLARE broj INT;\n" +
                        "\n" +
                        "    SET broj = broj_izvodjenja_eksperimenta(p_id_eksperiment);\n" +
                        "\n" +
                        "    IF broj >= 1 THEN\n" +
                        "        RETURN TRUE;\n" +
                        "    ELSE\n" +
                        "        RETURN FALSE;\n" +
                        "    END IF;\n" +
                        "END";

        try {

            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);

            preparedStatement.execute();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean proveriFunkciju(Connection connection,int idEksperimenta) {

        String query =
                "SELECT test_broj_izvodjenja_eksperimenta(?) AS rezultat";

        try {

            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);

            preparedStatement.setInt(1, idEksperimenta);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            if (resultSet.next()) {

                boolean rezultat = resultSet.getBoolean("rezultat");
                return rezultat;

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    private int brojIzvodjenjaEksperimenta(Connection connection, int idEksperimenta) {

        String query =
                "SELECT broj_izvodjenja_eksperimenta(?) AS broj";

        try {

            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);

            preparedStatement.setInt(1, idEksperimenta);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("broj");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
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
