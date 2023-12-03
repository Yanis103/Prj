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
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(new Color(245, 245, 245));
        
        formationDAO = new FormationDAO();
        List<Formation> formations = formationDAO.getAllFormations();
        Object[][] data = new Object[formations.size()][3];
        for (int i = 0; i < formations.size(); i++) {
            Formation formation = formations.get(i);
            data[i][0] = formation.getIdFormation();
            data[i][1] = formation.getNomFormation();
            data[i][2] = formation.getPromotion();
        } 
        // Créer une table (JTable) avec les données et les noms de colonnes spécifiés
        table = new JTable(data, columnNames);
        table.setBackground(new Color(255, 255, 255));
        table.setSelectionBackground(new Color(173, 216, 230));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.setFont(new Font("Arial", Font.PLAIN, 14)); // Changer la police pour le contenu de la table
        table.setShowVerticalLines(true); // Afficher les lignes verticales
        table.setShowHorizontalLines(true); // Afficher les lignes horizontales
        table.setRowHeight(40); // Définir la hauteur des lignes
        // Créer un panneau de défilement (JScrollPane) pour la table
        scrollPane = new JScrollPane(table);
        // Ajouter le panneau de défilement à la fenêtre au centre (zone Centre de BorderLayout)
        frame.add(scrollPane, BorderLayout.CENTER);

        
        JButton addButton = createStyledButton("Ajouter");
        addButton.addActionListener(e -> {
            String nomFormation = JOptionPane.showInputDialog(frame, "Nom de la Formation:");
            if (nomFormation == null) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
            	return;
            }
            String promotion = JOptionPane.showInputDialog(frame, "Promotion:");
            if (promotion == null) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
            	return;
            }
            if(nomFormation == null || promotion == null) {
            	JOptionPane.showMessageDialog(frame, "Un des champs n'a pas été spécifié !");
                return;
            }
            Formation nouvelleFormation = new Formation();
            nouvelleFormation.setNomFormation(nomFormation);
            nouvelleFormation.setPromotion(promotion);
            formationDAO.addFormation(nouvelleFormation);
            
            // Rafraîchir la table après l'ajout
            refreshTable();
        });
        tableModel = new DefaultTableModel(data, columnNames);
        table.setModel(tableModel);

        
        
        JButton updateButton = createStyledButton("Mettre à jour");
        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Sélectionnez une formation à mettre à jour.");
                return;
            }
            int idToUpdate = (int) table.getValueAt(selectedRow, 0);
            Formation formationToUpdate = formationDAO.getFormationById(idToUpdate);
            String nomFormation = JOptionPane.showInputDialog(frame, "Nouveau nom de la formation:", formationToUpdate.getNomFormation());
            if (nomFormation== null) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
            	return;
            }
            String promotion = JOptionPane.showInputDialog(frame, "Nouvelle promotion:", formationToUpdate.getPromotion());
            if (promotion== null) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
            	return;
            }
            formationToUpdate.setNomFormation(nomFormation);
            formationToUpdate.setPromotion(promotion);
            formationDAO.updateFormation(formationToUpdate);
            // Rafraîchir la table après la mise à jour
            refreshTable();
        });

        
        
        JButton deleteButton = createStyledButton("Supprimer");
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
        
        
        // Créer un bouton avec le texte "Retour au menu principal"
        JButton retourButton = createStyledButton("Retour au menu principal");
        // Ajouter un écouteur d'événements (ActionListener) au bouton
        retourButton.addActionListener(new ActionListener() {
        	// Lorsque le bouton est cliqué, exécuter les instructions suivantes :
            // Fermer la fenêtre actuelle
            @Override
            public void actionPerformed(ActionEvent e) {
            	 formationDAO.closeConnection();
            	 frame.setVisible(false);
            }
        });

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(retourButton);
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.setLocationRelativeTo(null); // Centrer la fenêtre
        frame.setVisible(true);

    }
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setMargin(new Insets(10, 20, 10, 20)); // Ajouter des marges pour un aspect visuel plus agréable
        return button;
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

