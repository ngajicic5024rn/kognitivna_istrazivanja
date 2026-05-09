package org.example.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controler.AddUserControler;

public class Register extends Stage {
    private Label lbIme = new Label("Korisnicko ime");
    private Label lbPassword = new Label("Password");
    private TextField tfIme = new TextField();
    private PasswordField tfPassword = new PasswordField();
    private Button btnPotvrdi = new Button("Potvrdi");
    private Button btnOdustani = new Button("Odustani");

    public Register() {
        HBox hb = new HBox(10);
        VBox vbLevi = new VBox(10);
        vbLevi.getChildren().addAll(lbIme, lbPassword,btnPotvrdi);
        VBox vbDesni = new VBox(10);
        vbDesni.getChildren().addAll(tfIme, tfPassword,btnOdustani);
        hb.getChildren().addAll(vbLevi, vbDesni);
        vbLevi.setAlignment(Pos.CENTER);
        vbDesni.setAlignment(Pos.CENTER);
        hb.setAlignment(Pos.CENTER);
        btnPotvrdi.setOnAction(new AddUserControler(tfIme,tfPassword));
        btnOdustani.setOnAction(e -> {
           this.close();
        });


        Scene scene = new Scene(hb,800,600);
        setScene(scene);



    }

}
