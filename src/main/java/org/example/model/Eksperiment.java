package org.example.model;

import java.time.LocalDate;

public class Eksperiment {
    private String nazivEksperimenta;
    private String nazivLaboratorije;
    private String status;
    private LocalDate datum;
    private String tip;
    private  int idEksperimenta;

    public Eksperiment(String nazivEksperimenta, String nazivLaboratorije, String status, LocalDate datum, String tip, int idEksperimenta) {
        this.nazivEksperimenta = nazivEksperimenta;
        this.nazivLaboratorije = nazivLaboratorije;
        this.status = status;
        this.datum = datum;
        this.tip = tip;
        this.idEksperimenta = idEksperimenta;
    }

    public int getIdEksperimenta() {
        return idEksperimenta;
    }

    public void setIdEksperimenta(int idEksperimenta) {
        this.idEksperimenta = idEksperimenta;
    }

    public String getNazivEksperimenta() {
        return nazivEksperimenta;
    }

    public void setNazivEksperimenta(String nazivEksperimenta) {
        this.nazivEksperimenta = nazivEksperimenta;
    }

    public String getNazivLaboratorije() {
        return nazivLaboratorije;
    }

    public void setNazivLaboratorije(String nazivLaboratorije) {
        this.nazivLaboratorije = nazivLaboratorije;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
