package org.example.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.model.Dizajner;
import org.example.model.UlogovaniIstrazivac;

public class GlavniProzor extends Stage {
    Button buttonPregled = new Button("Pregled");
    Button buttonPregledLaboratorija = new Button("Pregled Laboratorija");
    Button buttonPregledDizajnera = new Button("Pregled Dizajnera");


    public static UlogovaniIstrazivac ulogovaniIstrazivac;


    public GlavniProzor() {
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(buttonPregled,buttonPregledLaboratorija, buttonPregledDizajnera);
        Scene scene = new Scene(hb, 800, 600);
        setScene(scene);
        buttonPregled.setOnAction(e -> {
            PregledProzor pregled = new PregledProzor();
            pregled.show();
        });
        buttonPregledLaboratorija.setOnAction(e -> {
           LaboratorijeProzor laboratorijeProzor = new LaboratorijeProzor();
           laboratorijeProzor.show();
        });

        buttonPregledDizajnera.setOnAction(e -> {
            DizajnerProzor dizajnerProzor = new DizajnerProzor();
            dizajnerProzor.show();
        });
    }
}
