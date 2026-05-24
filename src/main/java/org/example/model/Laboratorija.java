package org.example.model;

public class Laboratorija {

    private String naziv;
    private Integer brResursa;
    private String opisLaboratorije;

    public Laboratorija(String naziv, Integer brResursa, String opisLaboratorije) {
        this.naziv = naziv;
        this.brResursa = brResursa;
        this.opisLaboratorije = opisLaboratorije;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getBrResursa() {
        return brResursa;
    }

    public void setBrResursa(Integer brResursa) {
        this.brResursa = brResursa;
    }

    public String getOpisLaboratorije() {
        return opisLaboratorije;
    }

    public void setOpisLaboratorije(String opisLaboratorije) {
        this.opisLaboratorije = opisLaboratorije;
    }
}
