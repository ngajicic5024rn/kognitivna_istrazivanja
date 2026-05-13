package org.example.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GlavniProzor extends Stage {
    Button buttonPregled = new Button("Pregled");
    Button buttonBrisanje = new Button("Brisanje");
    Button buttonIzmena = new Button("Izmena");

    public GlavniProzor() {
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(buttonPregled, buttonBrisanje, buttonIzmena);
        Scene scene = new Scene(hb, 800, 600);
        setScene(scene);
        buttonPregled.setOnAction(e -> {
        PregledProzor pregled = new PregledProzor();
        pregled.show();
        });
        buttonBrisanje.setOnAction(e -> {

        });
        buttonIzmena.setOnAction(e -> {

        });
    }
}
