package control;

import model.Kunde;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class KundenController {
    private final KundenTableModel model;
    private final view.KundenView view;

    public KundenController(KundenTableModel model, view.KundenView view) {
        this.model = model;
        this.view  = view;
        initController();
    }

    private void initController() {
        // Wenn eine Zeile im Table selektiert wird, fülle die Felder:
        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            int row = view.getTable().getSelectedRow();
            if (row >= 0) {
                Kunde k = model.getKundeAt(row);
                view.getTfNachname().setText(k.getNachname());
                view.getTfVorname().setText(k.getVorname());
                view.getTfGeburtsdatum().setText(k.getGeburtsdatum().toString());
                view.getTfAdresse().setText(k.getAdresse());
                view.getTfTelefon().setText(k.getTelefon());
            }
        });

        // Speichern-Button:
        view.getBtnSave().addActionListener(e -> updateKunde());
    }

    private void updateKunde() {
        int row = view.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Bitte erst einen Kunden auswählen.");
            return;
        }

        int kundenNr = (int) model.getValueAt(row, 0);
        String sql = "UPDATE Kunde SET Nachname=?, Vorname=?, Geburtsdatum=?, Adresse=?, Telefon=? WHERE KundenNr=?";
        try (Connection conn = DBVerbindung.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, view.getTfNachname().getText());
            ps.setString(2, view.getTfVorname().getText());
            ps.setDate(3, java.sql.Date.valueOf(view.getTfGeburtsdatum().getText()));
            ps.setString(4, view.getTfAdresse().getText());
            ps.setString(5, view.getTfTelefon().getText());
            ps.setInt(6, kundenNr);

            int updated = ps.executeUpdate();
            if (updated == 1) {
                JOptionPane.showMessageDialog(view, "Kunde gespeichert.");
                model.refresh();                     // Daten neu laden
                view.getTable().repaint();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Fehler: " + ex.getMessage());
        }
    }
}

