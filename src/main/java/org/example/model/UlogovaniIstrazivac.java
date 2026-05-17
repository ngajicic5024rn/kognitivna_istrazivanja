package org.example.model;

public class UlogovaniIstrazivac {
    private String ime_i_prezime;
    private String username;
    private String email;
    private String institucija;
    private String naucnoZvanje;
    private String oblastSpecifikacije;
    private Integer id;

    public UlogovaniIstrazivac(String ime_i_prezime, String username, String email, String institucija, String naucnoZvanje, String oblastSpecifikacije, Integer id) {
        this.ime_i_prezime = ime_i_prezime;
        this.username = username;
        this.email = email;
        this.institucija = institucija;
        this.naucnoZvanje = naucnoZvanje;
        this.oblastSpecifikacije = oblastSpecifikacije;
        this.id = id;
    }

    public String getIme_i_prezime() {
        return ime_i_prezime;
    }

    public void setIme_i_prezime(String ime_i_prezime) {
        this.ime_i_prezime = ime_i_prezime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstitucija() {
        return institucija;
    }

    public void setInstitucija(String institucija) {
        this.institucija = institucija;
    }

    public String getNaucnoZvanje() {
        return naucnoZvanje;
    }

    public void setNaucnoZvanje(String naucnoZvanje) {
        this.naucnoZvanje = naucnoZvanje;
    }

    public String getOblastSpecifikacije() {
        return oblastSpecifikacije;
    }

    public void setOblastSpecifikacije(String oblastSpecifikacije) {
        this.oblastSpecifikacije = oblastSpecifikacije;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
