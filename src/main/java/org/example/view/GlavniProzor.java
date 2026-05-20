package org.example.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.model.UlogovaniIstrazivac;

public class GlavniProzor extends Stage {
    Button buttonPregled = new Button("Pregled");


    public static UlogovaniIstrazivac ulogovaniIstrazivac;


    public GlavniProzor() {
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(buttonPregled);
        Scene scene = new Scene(hb, 800, 600);
        setScene(scene);
        buttonPregled.setOnAction(e -> {
            PregledProzor pregled = new PregledProzor();
            pregled.show();
        });

    }
}
