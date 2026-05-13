package org.example.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controler.PregledControler;
import org.example.model.Eksperiment;

import java.time.LocalDate;

public class PregledProzor extends Stage {
    private RadioButton rbPlanirani = new RadioButton("Planirani");
    private RadioButton rbZavrseni = new RadioButton("Zavrseni");
    private TableView<Eksperiment> tvEksperimenti = new TableView();
    ToggleGroup grupaStatus = new ToggleGroup();

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

        tvEksperimenti.getColumns().addAll(nazivEksperimenta, tipEksperimenta, datumEksperimenta, nazivLaboratorije, status);
        HBox hb = new HBox(10);
        hb.getChildren().addAll(rbPlanirani, rbZavrseni);
        VBox vb = new VBox(10);
        vb.getChildren().addAll(tvEksperimenti, hb);
        Scene scene = new Scene(vb, 800, 600);
        setScene(scene);
        hb.setAlignment(Pos.CENTER);
        vb.setAlignment(Pos.CENTER);
        grupaStatus.selectedToggleProperty().addListener(new PregledControler(tvEksperimenti, rbPlanirani ,rbZavrseni));
        rbPlanirani.setSelected(true);
    }


}
