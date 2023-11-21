package interfaceG;

import classe.Projet;
import dao.ProjetDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class AppProjet {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;
    private ProjetDAO projetDAO;
    private DefaultTableModel tableModel;
    private final String[] columnNames = {"ID Projet", "Nom Matière", "Sujet", "Date Remise Prévue"};

    public AppProjet() {
        frame = new JFrame("Gestion des Projets");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(245, 245, 245));

        projetDAO = new ProjetDAO();
        List<Projet> projets = projetDAO.getAllProjets();
        Object[][] data = new Object[projets.size()][4];
        for (int i = 0; i < projets.size(); i++) {
            Projet projet = projets.get(i);
            data[i][0] = projet.getIdProjet();
            data[i][1] = projet.getNomMatiere();
            data[i][2] = projet.getSujet();
            data[i][3] = projet.getDateRemisePrevue();
        }
        table = new JTable(data, columnNames);
        scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        
        
        JButton addButton = createStyledButton("Ajouter");
        addButton.addActionListener(e -> {
            String nomMatiere = JOptionPane.showInputDialog(frame, "Nom de la Matière:");
            String sujet = JOptionPane.showInputDialog(frame, "Sujet du Projet:");
            String dateRemisePrevueString = JOptionPane.showInputDialog(frame, "Date Remise Prévue (YYYY-MM-DD):");
            if(nomMatiere == null || sujet == null || dateRemisePrevueString == null ) {
            	JOptionPane.showMessageDialog(frame, "Un des champs n'a pas été spécifié !");
                return;
            }
            LocalDate dateRemisePrevue = LocalDate.parse(dateRemisePrevueString);
            Projet nouveauProjet = new Projet();
            nouveauProjet.setNomMatiere(nomMatiere);
            nouveauProjet.setSujet(sujet);
            nouveauProjet.setDateRemisePrevue(dateRemisePrevue);

            projetDAO.addProjet(nouveauProjet);

            // Rafraîchir la table après l'ajout
            refreshTable();
        });
        tableModel = new DefaultTableModel(data, columnNames);
        table.setModel(tableModel);

        
        
        JButton updateButton = createStyledButton("Mettre à jour");
        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Sélectionnez un projet à mettre à jour.");
                return;
            }

            int idToUpdate = (int) table.getValueAt(selectedRow, 0);
            Projet projetToUpdate = projetDAO.getProjetById(idToUpdate);

            String nomMatiere = JOptionPane.showInputDialog(frame, "Nouveau nom de la matière:", projetToUpdate.getNomMatiere());
            String sujet = JOptionPane.showInputDialog(frame, "Nouveau sujet du projet:", projetToUpdate.getSujet());
            String dateRemisePrevueString = JOptionPane.showInputDialog(frame, "Nouvelle date Remise Prévue (YYYY-MM-DD):");

            LocalDate dateRemisePrevue = LocalDate.parse(dateRemisePrevueString);

            projetToUpdate.setNomMatiere(nomMatiere);
            projetToUpdate.setSujet(sujet);
            projetToUpdate.setDateRemisePrevue(dateRemisePrevue);

            projetDAO.updateProjet(projetToUpdate);

            // Rafraîchir la table après la mise à jour
            refreshTable();
        });
        
        
        
        JButton deleteButton = createStyledButton("Supprimer");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Sélectionnez un projet à supprimer.");
                return;
            }

            int idToDelete = (int) table.getValueAt(selectedRow, 0);
            projetDAO.deleteProjet(idToDelete);

            // Rafraîchir la table après la suppression
            refreshTable();
        });
        
        
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        // Créer un bouton avec le texte "Retour au menu principal"
        JButton retourButton = createStyledButton("Retour au menu principal");
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
        List<Projet> projets = projetDAO.getAllProjets();
        Object[][] newData = new Object[projets.size()][4];

        for (int i = 0; i < projets.size(); i++) {
            Projet projet = projets.get(i);
            newData[i][0] = projet.getIdProjet();
            newData[i][1] = projet.getNomMatiere();
            newData[i][2] = projet.getSujet();
            newData[i][3] = projet.getDateRemisePrevue();
        }

        tableModel.setDataVector(newData, columnNames);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppProjet::new);
    }
}

