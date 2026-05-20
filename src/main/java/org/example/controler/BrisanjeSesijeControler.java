package org.example.controler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import org.example.Config;
import org.example.model.Eksperiment;
import org.example.model.UlogovaniIstrazivac;
import org.example.view.GlavniProzor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BrisanjeSesijeControler implements EventHandler<ActionEvent> {
    private TableView<Eksperiment> tvEksperimenti;

    public BrisanjeSesijeControler(TableView<Eksperiment> tvEksperimenti) {
        this.tvEksperimenti = tvEksperimenti;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        Eksperiment selectedEksperiment = tvEksperimenti.getSelectionModel().getSelectedItem();
        if (selectedEksperiment == null) {
            return;
        }
        boolean isIzvodjac = runQueryIsIzvodjac(Config.getConnection(), selectedEksperiment.getIdIzvodjenja(), GlavniProzor.ulogovaniIstrazivac.getId());
        boolean isDizajner = runQueryIsDizajner(Config.getConnection(), selectedEksperiment.getIdIzvodjenja(), GlavniProzor.ulogovaniIstrazivac.getId());
        if (isIzvodjac || isDizajner ) {
            if(runQueryObrisiSesiju(Config.getConnection(),selectedEksperiment.getIdIzvodjenja())){
                tvEksperimenti.getItems().remove(selectedEksperiment);
                tvEksperimenti.refresh();
            }
        }
        else{
            System.out.println("Ne ucestvuje");
        }
    }


    private boolean runQueryIsIzvodjac(Connection connection, Integer id_izvodjenje, Integer id_istrazivac ) {
        String query = "SELECT *\n" +
                "FROM ucesce_izvodjaca ui\n" +
                "JOIN izvodjac i\n" +
                "ON ui.id_izvodjac = i.id\n" +
                "WHERE ui.id_izvodjenje = ?\n" +
                "AND i.id_istrazivaca = ?;";


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_izvodjenje);
            preparedStatement.setInt(2, id_istrazivac);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            return false;


        } catch (SQLException e) {
            return false;
        }
    }
    private boolean runQueryIsDizajner(Connection connection, Integer id_izvodjenje, Integer id_istrazivac ) {
        String query = "SELECT *\n" +
                "FROM izvodjenje i\n" +
                "JOIN diz_eks de\n" +
                "ON i.id_eksperiment = de.eksperiment_id\n" +
                "JOIN dizajner d\n" +
                "ON de.dizajner_id = d.id\n" +
                "WHERE i.id_izvodjenja = ?\n" +
                "AND d.id_istrazivaca = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_izvodjenje);
            preparedStatement.setInt(2, id_istrazivac);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            return false;


        } catch (SQLException e) {
            return false;
        }
    }

    private boolean runQueryObrisiSesiju(Connection connection, int idIzvodjenja) {

        try {

            String selectQuery =
                    "SELECT id_sesija FROM izvodjenje WHERE id_izvodjenja = ?";

            PreparedStatement ps1 =
                    connection.prepareStatement(selectQuery);

            ps1.setInt(1, idIzvodjenja);

            ResultSet rs = ps1.executeQuery();

            if(!rs.next()) {
                return false;
            }

            int idSesija = rs.getInt("id_sesija");

            String deleteUcesce =
                    "DELETE FROM ucesce_izvodjaca WHERE id_izvodjenje = ?";

            PreparedStatement ps2 =
                    connection.prepareStatement(deleteUcesce);

            ps2.setInt(1, idIzvodjenja);

            ps2.executeUpdate();

            String deleteIzvodjenje =
                    "DELETE FROM izvodjenje WHERE id_izvodjenja = ?";

            PreparedStatement ps3 =
                    connection.prepareStatement(deleteIzvodjenje);

            ps3.setInt(1, idIzvodjenja);

            ps3.executeUpdate();

            String deleteSesija =
                    "DELETE FROM sesija WHERE id = ?";

            PreparedStatement ps4 =
                    connection.prepareStatement(deleteSesija);

            ps4.setInt(1, idSesija);

            ps4.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
