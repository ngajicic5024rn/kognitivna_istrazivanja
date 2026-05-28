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
import org.example.controler.LogInControler;

public class LogIn extends Stage {

    private Label lbUsername = new Label("Korisničko ime:");
    private Label lbPassword = new Label("Lozinka:");
    private TextField tfUsername = new TextField();
    private PasswordField tfPassword = new PasswordField();
    private Button btnPrijava = new Button("Prijavi se");
    private Button btnOdustani = new Button("Odustani");
    private Label lblError = new Label("");

    public LogIn() {
        setTitle("Prijava");

        tfUsername.setPromptText("Unesite korisničko ime...");
        tfPassword.setPromptText("Unesite lozinku...");

        HBox hbPolja = new HBox(15);
        VBox vbLevi = new VBox(15, lbUsername, lbPassword);
        VBox vbDesni = new VBox(15, tfUsername, tfPassword);
        vbLevi.setAlignment(Pos.CENTER_RIGHT);
        vbDesni.setAlignment(Pos.CENTER_LEFT);
        hbPolja.getChildren().addAll(vbLevi, vbDesni);
        hbPolja.setAlignment(Pos.CENTER);

        HBox hbDugmad = new HBox(15, btnPrijava, btnOdustani);
        hbDugmad.setAlignment(Pos.CENTER);

        lblError.setWrapText(true);
        lblError.setMaxWidth(400);

        VBox root = new VBox(20, hbPolja, hbDugmad, lblError);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        btnPrijava.setOnAction(new LogInControler(tfUsername, tfPassword, lblError));
        btnOdustani.setOnAction(e -> this.close());

        Scene scene = new Scene(root, 800, 600);
        setScene(scene);
    }
}