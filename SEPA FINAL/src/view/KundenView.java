package view;

import control.KundenController;
import control.KundenTableModel;

import javax.swing.*;
import java.awt.*;

public class KundenView extends JFrame {
    private final JTable table;
    private final JTextField tfNachname, tfVorname, tfGeburtsdatum, tfAdresse, tfTelefon;
    private final JButton btnSave, btnKontoInfo;

    public KundenView() {
        super("Kundenübersicht");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 500);
        setLayout(new BorderLayout());

        // Tabelle
        KundenTableModel model = new KundenTableModel();
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        
        
        // Editier-Panel
        JPanel editPanel = new JPanel(new GridLayout(2, 7, 5, 5));
        tfNachname = new JTextField();
        tfVorname  = new JTextField();
        tfGeburtsdatum = new JTextField();
        tfAdresse  = new JTextField();
        tfTelefon  = new JTextField();
        btnSave    = new JButton("Speichern");
        btnKontoInfo = new JButton("Konto Info");

     // Spaltenüberschriften:
        editPanel.add(new JLabel("Nachname"));
        editPanel.add(new JLabel("Vorname"));
        editPanel.add(new JLabel("Geburtsdatum"));
        editPanel.add(new JLabel("Adresse"));
        editPanel.add(new JLabel("Telefon"));
        editPanel.add(new JLabel());       // Platzhalter für Save
        editPanel.add(new JLabel());       // Platzhalter für Konto Info

     // Eingabefelder + Buttons:
        editPanel.add(tfNachname);
        editPanel.add(tfVorname);
        editPanel.add(tfGeburtsdatum);
        editPanel.add(tfAdresse);
        editPanel.add(tfTelefon);
        editPanel.add(btnSave);            // Spalte 6
        editPanel.add(btnKontoInfo);       // Spalte 7

        add(editPanel, BorderLayout.SOUTH);

        // Controller initialisieren
        new KundenController(model, this);

        // Konto-Info Dialog aufrufen
        btnKontoInfo.addActionListener(e -> {
            new control.KontoDialog(this).setVisible(true);
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Getter für Controller
    public JTable getTable() { return table; }
    public JTextField getTfNachname() { return tfNachname; }
    public JTextField getTfVorname() { return tfVorname; }
    public JTextField getTfGeburtsdatum() { return tfGeburtsdatum; }
    public JTextField getTfAdresse() { return tfAdresse; }
    public JTextField getTfTelefon() { return tfTelefon; }
    public JButton getBtnSave() { return btnSave; }
    public JButton getBtnKontoInfo() { return btnKontoInfo; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(KundenView::new);
    }
}
