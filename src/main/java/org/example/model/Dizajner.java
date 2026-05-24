package org.example.model;

public class Dizajner {
    private String ime;
    private String teorija;
    private Integer iskustvo;

    public Dizajner(String ime, String teorija, Integer iskustvo) {
        this.ime = ime;
        this.teorija = teorija;
        this.iskustvo = iskustvo;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getTeorija() {
        return teorija;
    }

    public void setTeorija(String teorija) {
        this.teorija = teorija;
    }

    public Integer getIskustvo() {
        return iskustvo;
    }

    public void setIskustvo(Integer iskustvo) {
        this.iskustvo = iskustvo;
    }
}
