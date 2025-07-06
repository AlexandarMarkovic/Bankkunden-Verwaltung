package control;

import model.Kunde;

import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KundenTableModel extends AbstractTableModel {
    private final List<Kunde> daten = new ArrayList<>();

    public KundenTableModel() {
        ladeDaten();
    }
    public Kunde getKundeAt(int row) {
        return daten.get(row);
    }
    public void refresh() {
        daten.clear();
        ladeDaten();
        fireTableDataChanged();
    }

    private void ladeDaten() {
        String sql = "SELECT KundenNr, Nachname, Vorname, Geburtsdatum, Adresse, Telefon FROM Kunde";
        try (Connection conn = DBVerbindung.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                daten.add(new Kunde(
                    rs.getInt("KundenNr"),
                    rs.getString("Nachname"),
                    rs.getString("Vorname"),
                    rs.getDate("Geburtsdatum").toLocalDate(),
                    rs.getString("Adresse"),
                    rs.getString("Telefon")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override public int getRowCount() { return daten.size(); }
    @Override public int getColumnCount() { return 7; }

    @Override
    public Object getValueAt(int row, int col) {
        Kunde k = daten.get(row);
        return switch (col) {
            case 0 -> k.getKundenNr();
            case 1 -> k.getNachname();
            case 2 -> k.getVorname();
            case 3 -> k.getGeburtsdatum();
            case 4 -> k.getAdresse();
            case 5 -> k.getTelefon();
            case 6 -> k.istMinderjaehrig() ? "Ja" : "Nein";
            default -> null;
        };
    }

    @Override
    public String getColumnName(int col) {
        return switch (col) {
            case 0 -> "KundenNr";
            case 1 -> "Nachname";
            case 2 -> "Vorname";
            case 3 -> "Geburtsdatum";
            case 4 -> "Adresse";
            case 5 -> "Telefon";
            case 6 -> "Minderjährig";
            default -> "";
        };
    }
}
