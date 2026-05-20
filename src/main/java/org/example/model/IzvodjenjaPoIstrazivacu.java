package org.example.model;

public class IzvodjenjaPoIstrazivacu {
    private String imeIPrezime;
    private int brojIzvodjenja;

    public IzvodjenjaPoIstrazivacu(String imeIPrezime, int brojIzvodjenja) {
        this.imeIPrezime = imeIPrezime;
        this.brojIzvodjenja = brojIzvodjenja;
    }

    public String getImeIPrezime() {
        return imeIPrezime;
    }

    public int getBrojIzvodjenja() {
        return brojIzvodjenja;
    }
}
