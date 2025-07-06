package control;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class KontoDialog extends JDialog {
    public KontoDialog(JFrame parent) {
        super(parent, "Konto-Info", true);
        setLayout(new BorderLayout());
        setSize(350, 200);

        JPanel input = new JPanel();
        JTextField tfKontoNr = new JTextField(12);
        JButton btnOk = new JButton("Abfragen");
        input.add(new JLabel("KontoNr:"));
        input.add(tfKontoNr);
        input.add(btnOk);
        add(input, BorderLayout.NORTH);

        JTextArea taInfo = new JTextArea();
        taInfo.setEditable(false);
        add(new JScrollPane(taInfo), BorderLayout.CENTER);

        btnOk.addActionListener(e -> {
            String kontoNr = tfKontoNr.getText().trim();
            String infoText;
            try (Connection conn = DBVerbindung.getConnection()) {
                // Kontostand
                PreparedStatement ps1 = conn.prepareStatement(
                    "SELECT Guthaben FROM Konto WHERE KontoNr = ?");
                ps1.setString(1, kontoNr);
                ResultSet rs1 = ps1.executeQuery();
                if (!rs1.next()) {
                    taInfo.setText("Konto nicht gefunden.");
                    return;
                }
                double guthaben = rs1.getDouble("Guthaben");

                // letzte Buchung
                PreparedStatement ps2 = conn.prepareStatement(
                    "SELECT Betrag, Datum FROM Buchung WHERE KontoNr = ? " +
                    "ORDER BY Datum DESC LIMIT 1");
                ps2.setString(1, kontoNr);
                ResultSet rs2 = ps2.executeQuery();
                String buchungInfo = rs2.next()
                    ? rs2.getDouble("Betrag") + " EUR am " + rs2.getDate("Datum")
                    : "Keine Buchungen";

                infoText = "KontoNr: " + kontoNr +
                           "\nGuthaben: " + guthaben + " EUR" +
                           "\nLetzte Buchung: " + buchungInfo;
            } catch (SQLException ex) {
                infoText = "Fehler: " + ex.getMessage();
            }
            taInfo.setText(infoText);
        });

        setLocationRelativeTo(parent);
    }
}
