package org.example.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;



public class MainView extends Stage {
    Button btnLogIn = new Button("Log In");
    Button btnSignUp = new Button("Sign Up");

    public MainView() {
        HBox root = new HBox(20,btnLogIn, btnSignUp);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root,800,600);
        setScene(scene);

        btnLogIn.setOnAction(e -> {
            LogIn logIn = new LogIn();
            logIn.show();
        });

        btnSignUp.setOnAction(e -> {
           Register register = new Register();
           register.show();

        });
    }
}
