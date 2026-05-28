package org.example.view;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.Config;
import org.example.model.IzvodjenjaPoIstrazivacu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PogledView extends Stage {
    private TableView<IzvodjenjaPoIstrazivacu> tvIzvodjenja = new TableView<>();

    public PogledView() {
        TableColumn<IzvodjenjaPoIstrazivacu, String> tcIme = new TableColumn<>("Ime") ;
        tcIme.setCellValueFactory(new PropertyValueFactory<>("imeIPrezime"));

        TableColumn<IzvodjenjaPoIstrazivacu, Integer> tcBroj = new TableColumn<>("Broj Izvodjenja") ;
        tcBroj.setCellValueFactory(new PropertyValueFactory<>("brojIzvodjenja"));

        tvIzvodjenja.getColumns().addAll(tcIme, tcBroj);
        Scene scene = new Scene(tvIzvodjenja, 800, 600);
        setScene(scene);

        kreirajView(Config.getConnection());

        tvIzvodjenja.getItems().addAll(ucitajPodatkeIzViewa(Config.getConnection()));

    }

    private List<IzvodjenjaPoIstrazivacu> ucitajPodatkeIzViewa(Connection connection) {

        List<IzvodjenjaPoIstrazivacu> lista = new ArrayList();

        String query =
                "SELECT ime_i_prezime, broj_izvodjenja \n" +
                        "FROM broj_izvodjenja_po_istrazivacu";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String imeIPrezime = rs.getString("ime_i_prezime");
                int brojIzvodjenja = rs.getInt("broj_izvodjenja");

                lista.add(new IzvodjenjaPoIstrazivacu(
                        imeIPrezime,
                        brojIzvodjenja
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    private void kreirajView(Connection connection) {

        String query =
                "CREATE OR REPLACE VIEW broj_izvodjenja_po_istrazivacu AS \n" +
                        "SELECT \n" +
                        "i.ime_i_prezime, \n" +
                        "COUNT(ui.id_izvodjenje) AS broj_izvodjenja \n" +
                        "FROM istrazivac i \n" +
                        "JOIN izvodjac iz ON i.id = iz.id_istrazivaca \n" +
                        "JOIN ucesce_izvodjaca ui ON iz.id = ui.id_izvodjac \n" +
                        "GROUP BY i.id, i.ime_i_prezime \n" +
                        "HAVING COUNT(ui.id_izvodjenje) >= 2";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
