package interfaceG;

import dao.VueDataDAO;
import classe.VueData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AppVueData {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;
    private VueDataDAO vueDataDAO;
    private DefaultTableModel tableModel;
    private final String[] columnNames = {"Nom Etudiant", "Prénom Etudiant", "Nom Formation", "Promotion", "Nom Matière", "Sujet", "Note Rapport", "Note Soutenance", "Binôme Référence", "Date Remise Effective", "Date Remise Prévue", "Note Finale"};

    public AppVueData() {
        frame = new JFrame("Gestion des Vues de Données");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        vueDataDAO = new VueDataDAO();

        List<VueData> vueDataList = vueDataDAO.getAllVueData();

        Object[][] data = new Object[vueDataList.size()][12];

        for (int i = 0; i < vueDataList.size(); i++) {
            VueData vueData = vueDataList.get(i);
            data[i][0] = vueData.getEtudiantNom();
            data[i][1] = vueData.getEtudiantPrenom();
            data[i][2] = vueData.getNomFormation();
            data[i][3] = vueData.getPromotion();
            data[i][4] = vueData.getNomMatiere();
            data[i][5] = vueData.getSujet();
            data[i][6] = vueData.getNoteRapport();
            data[i][7] = vueData.getNoteSoutenance();
            data[i][8] = vueData.getBinomeReference();
            data[i][9] = vueData.getDateRemiseEffective();
            data[i][10] = vueData.getDateRemisePrevue();
            data[i][11] = String.format("%.2f", vueData.getNoteFinale());
        }

        table = new JTable(data, columnNames);
        scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(data, columnNames);
        table.setModel(tableModel);

        frame.setSize(1200, 400); // Définir une taille appropriée
        frame.setLocationRelativeTo(null); // Centrer la fenêtre
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppVueData::new);
    }
}
