package org.example.controler;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import org.example.Config;
import org.example.model.Eksperiment;
import org.example.model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IzmenaStatusaControler  implements EventHandler<ActionEvent> {

    private ComboBox<Status> statusaComboBox;
    private TableView<Eksperiment> tvEksperimenti ;

    public IzmenaStatusaControler(ComboBox<Status> statusaComboBox, TableView<Eksperiment> tvEksperimenti) {
        this.statusaComboBox = statusaComboBox;
        this.tvEksperimenti = tvEksperimenti;
    }

    @Override
    public void handle(ActionEvent event) {
       Status izabraniStatus = statusaComboBox.getSelectionModel().getSelectedItem();
       Eksperiment izabraniEksperiment = tvEksperimenti.getSelectionModel().getSelectedItem();

        if(this.runQuery(Config.getConnection(),izabraniStatus.getId(), izabraniEksperiment.getIdEksperimenta() )){
            izabraniEksperiment.setStatus(izabraniStatus.getStatus());
            tvEksperimenti.refresh();
        }

    }

    private boolean runQuery(Connection connection, Integer id_status, Integer id) {
        String query = "UPDATE  izvodjenje set id_status = ? where id_izvodjenja = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_status);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
