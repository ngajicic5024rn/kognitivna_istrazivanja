package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controler.AddUserControler;

public class Register extends Stage {
    private Label lbIme = new Label("Korisnicko ime");
    private Label lbPassword = new Label("Password");
    private Label lbImeiPrezime = new Label("Ime i Prezime");
    private Label lbEmail = new Label("Email");
    private Label lbInstitucija = new Label("Institucija");
    private Label lbNaucnoZvanje = new Label("Naucno zvanje");
    private Label lbOblastSpecifikacije = new Label("Oblast specifikacije");

    private TextField tfIme = new TextField();
    private TextField tfImeiPrezime = new TextField();
    private TextField tfEmail = new TextField();
    private TextField tfInstitucija = new TextField();
    private TextField tfNaucnoZvanje = new TextField();
    private TextField tfOblastSpecifikacije = new TextField();
    private PasswordField tfPassword = new PasswordField();

    private Button btnPotvrdi = new Button("Potvrdi");
    private Button btnOdustani = new Button("Odustani");
    private  Label lblStatus = new Label("");


    public Register() {
        setTitle("Registracija ");
        tfIme.setPromptText("Unesite korisnicko ime ... ");
        tfPassword.setPromptText("Unesite lozinku  ... ");
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(lbIme, 0, 0);
        gridPane.add(lbPassword, 0, 1);
        gridPane.add(lbImeiPrezime, 0, 2);
        gridPane.add(lbEmail, 0, 3);
        gridPane.add(lbInstitucija, 0, 4);
        gridPane.add(lbNaucnoZvanje, 0, 5);
        gridPane.add(lbOblastSpecifikacije, 0, 6);

        gridPane.add(tfIme, 1, 0);
        gridPane.add(tfPassword, 1, 1);
        gridPane.add(tfImeiPrezime, 1, 2);
        gridPane.add(tfEmail, 1, 3);
        gridPane.add(tfInstitucija, 1, 4);
        gridPane.add(tfNaucnoZvanje, 1, 5);
        gridPane.add(tfOblastSpecifikacije, 1, 6);
        gridPane.setAlignment(Pos.CENTER);

        HBox hbDugmad = new HBox(10, btnPotvrdi, btnOdustani);
        hbDugmad.setAlignment(Pos.CENTER);
        lblStatus.setWrapText(true);
        lblStatus.setMaxWidth(400);
        VBox root = new VBox(20, gridPane, hbDugmad, lblStatus);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        btnPotvrdi.setOnAction(new AddUserControler(tfIme, tfPassword, tfImeiPrezime, tfEmail, tfInstitucija, tfNaucnoZvanje, tfOblastSpecifikacije, lblStatus));
        btnOdustani.setOnAction(e -> {
           this.close();
        });

        Scene scene = new Scene(root,800,600);
        setScene(scene);

    }
}
