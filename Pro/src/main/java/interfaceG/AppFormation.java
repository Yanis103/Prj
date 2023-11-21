package interfaceG;

import classe.Formation;
import dao.FormationDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AppFormation {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;
    private FormationDAO formationDAO;
    private DefaultTableModel tableModel;
    private final String[] columnNames = {"ID Formation", "Nom Formation", "Promotion"};

    public AppFormation() {
        frame = new JFrame("Gestion des Formations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        formationDAO = new FormationDAO();
        List<Formation> formations = formationDAO.getAllFormations();

        Object[][] data = new Object[formations.size()][3];

        for (int i = 0; i < formations.size(); i++) {
            Formation formation = formations.get(i);
            data[i][0] = formation.getIdFormation();
            data[i][1] = formation.getNomFormation();
            data[i][2] = formation.getPromotion();
        }

        table = new JTable(data, columnNames);
        scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(e -> {
            String nomFormation = JOptionPane.showInputDialog(frame, "Nom de la Formation:");
            String promotion = JOptionPane.showInputDialog(frame, "Promotion:");

            Formation nouvelleFormation = new Formation();
            nouvelleFormation.setNomFormation(nomFormation);
            nouvelleFormation.setPromotion(promotion);

            formationDAO.addFormation(nouvelleFormation);

            // Rafraîchir la table après l'ajout
            refreshTable();
        });

        tableModel = new DefaultTableModel(data, columnNames);
        table.setModel(tableModel);

        JButton updateButton = new JButton("Mettre à jour");
        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Sélectionnez une formation à mettre à jour.");
                return;
            }

            int idToUpdate = (int) table.getValueAt(selectedRow, 0);
            Formation formationToUpdate = formationDAO.getFormationById(idToUpdate);

            String nomFormation = JOptionPane.showInputDialog(frame, "Nouveau nom de la formation:", formationToUpdate.getNomFormation());
            String promotion = JOptionPane.showInputDialog(frame, "Nouvelle promotion:", formationToUpdate.getPromotion());

            formationToUpdate.setNomFormation(nomFormation);
            formationToUpdate.setPromotion(promotion);

            formationDAO.updateFormation(formationToUpdate);

            // Rafraîchir la table après la mise à jour
            refreshTable();
        });

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Sélectionnez une formation à supprimer.");
                return;
            }

            int idToDelete = (int) table.getValueAt(selectedRow, 0);
            formationDAO.deleteFormation(idToDelete);

            // Rafraîchir la table après la suppression
            refreshTable();
        });

        JPanel buttonPanel = new JPanel();
        // Créer un bouton avec le texte "Retour au menu principal"
        JButton retourButton = new JButton("Retour au menu principal");
        // Ajouter un écouteur d'événements (ActionListener) au bouton
        retourButton.addActionListener(new ActionListener() {
        	// Lorsque le bouton est cliqué, exécuter les instructions suivantes :
            // Fermer la fenêtre actuelle
            @Override
            public void actionPerformed(ActionEvent e) {
            	 frame.dispose();
            	 App.main(null);
            }
        });
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(retourButton);
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.pack();
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }

    private void refreshTable() {
        List<Formation> formations = formationDAO.getAllFormations();
        Object[][] newData = new Object[formations.size()][3];

        for (int i = 0; i < formations.size(); i++) {
            Formation formation = formations.get(i);
            newData[i][0] = formation.getIdFormation();
            newData[i][1] = formation.getNomFormation();
            newData[i][2] = formation.getPromotion();
        }

        tableModel.setDataVector(newData, columnNames);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppFormation::new);
    }
}

