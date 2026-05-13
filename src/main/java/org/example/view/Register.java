package org.example.view;

import javafx.geometry.Insets;
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
    private  Label lblStatus = new Label("");

    public Register() {
        setTitle("Registracija ");
        tfIme.setPromptText("Unesite korisnicko ime ... ");
        tfPassword.setPromptText("Unesite lozinku  ... ");
        HBox hbGlavni = new HBox(10);
        VBox vbLevi = new VBox(10);
        vbLevi.getChildren().addAll(lbIme, lbPassword);
        VBox vbDesni = new VBox(10);
        vbDesni.getChildren().addAll(tfIme, tfPassword);
        hbGlavni.getChildren().addAll(vbLevi, vbDesni);
        vbLevi.setAlignment(Pos.CENTER);
        vbDesni.setAlignment(Pos.CENTER);
        hbGlavni.setAlignment(Pos.CENTER);
        HBox hbDugmad = new HBox(10, btnPotvrdi, btnOdustani);
        hbDugmad.setAlignment(Pos.CENTER);
        lblStatus.setWrapText(true);
        lblStatus.setMaxWidth(400);
        VBox root = new VBox(20, hbGlavni, hbDugmad, lblStatus);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        btnPotvrdi.setOnAction(new AddUserControler(tfIme, tfPassword, lblStatus));
        btnOdustani.setOnAction(e -> {
           this.close();
        });


        Scene scene = new Scene(root,800,600);
        setScene(scene);



    }

}
