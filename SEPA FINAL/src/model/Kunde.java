package model;

import java.time.LocalDate;
import java.time.Period;

public class Kunde {
    private int kundenNr;
    private String nachname;
    private String vorname;
    private LocalDate geburtsdatum;
    private String adresse;
    private String telefon;

    public Kunde(int kundenNr, String nachname, String vorname,
                 LocalDate geburtsdatum, String adresse, String telefon) {
        this.kundenNr = kundenNr;
        this.nachname = nachname;
        this.vorname = vorname;
        this.geburtsdatum = geburtsdatum;
        this.adresse = adresse;
        this.telefon = telefon;
    }

    // Getter …
    public int getKundenNr() { return kundenNr; }
    public String getNachname() { return nachname; }
    public String getVorname()  { return vorname; }
    public LocalDate getGeburtsdatum() { return geburtsdatum; }
    public String getAdresse()  { return adresse; }
    public String getTelefon()  { return telefon; }

    // markiert Minderjährige
    public boolean istMinderjaehrig() {
        return Period.between(geburtsdatum, LocalDate.now()).getYears() < 18;
    }
}

