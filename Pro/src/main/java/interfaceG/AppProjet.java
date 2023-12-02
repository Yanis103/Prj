package interfaceG;

import classe.Formation;
import classe.Projet;
import dao.ProjetDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
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
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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

        
        // Créer un bouton avec le texte "Filtrer"
        JButton boutonFiltrer = createStyledButton("Filtrer");
        // Ajouter un écouteur d'événements au bouton (utilisation d'une expression lambda)
        boutonFiltrer.addActionListener(e -> {
            // Créer un dialogue de filtrage
            JDialog dialog = new JDialog(frame, "Filtrer");
            dialog.setLayout(new GridLayout(3, 2));
            List<Projet> projetsT = projetDAO.getAllProjets();
            String[] nomsMatieres = projetsT.stream()
                    .map(Projet::getNomMatiere)
                    .toArray(String[]::new);
            // Ajouter une option vide au début de la liste des noms des matieres
            String[] matieresAvecNeutre = new String[nomsMatieres.length + 1];
            matieresAvecNeutre[0] = ""; // Option vide
            System.arraycopy(nomsMatieres, 0, matieresAvecNeutre, 1, nomsMatieres.length);
            JComboBox<String> nomsMatieresComboBox = new JComboBox<>(matieresAvecNeutre);
            dialog.add(new JLabel(" Nom matière:"));
            dialog.add(nomsMatieresComboBox);
            
            String[] sujets = projetsT.stream()
                    .map(Projet::getSujet)
                    .toArray(String[]::new);
            // Ajouter une option vide au début de la liste des sujets
            String[] sujetsAvecNeutre = new String[sujets.length + 1];
            sujetsAvecNeutre[0] = ""; // Option vide
            System.arraycopy(sujets, 0, sujetsAvecNeutre, 1, sujets.length);
            JComboBox<String> sujetsComboBox = new JComboBox<>(sujetsAvecNeutre);
            dialog.add(new JLabel("Sujet:"));
            dialog.add(sujetsComboBox);
            // Ajouter un bouton de filtrage dans le dialogue
            JButton filtrerButton = new JButton("Filtrer");
            filtrerButton.addActionListener(filtrerEvent -> {
                String matiereSelectionnee = (String) nomsMatieresComboBox.getSelectedItem();
                String sujetSelectionne = (String) sujetsComboBox.getSelectedItem();
                // Créer un TableRowSorter pour la tableModel
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
                table.setRowSorter(sorter);

                // Créer une liste pour stocker les filtres
                List<RowFilter<Object, Object>> filters = new ArrayList<>();
                // Ajouter les filtres non vides aux critères de filtrage
                
                if (!matiereSelectionnee.isEmpty()) {
                    filters.add(RowFilter.regexFilter(matiereSelectionnee, 1));
                }
                if (!sujetSelectionne.isEmpty()) {
                    filters.add(RowFilter.regexFilter(sujetSelectionne, 2));
                }
                // Combinez les filtres en un seul
                RowFilter<Object, Object> compoundRowFilter = RowFilter.andFilter(filters);
                // Appliquer le filtre combiné à TableRowSorter
                sorter.setRowFilter(compoundRowFilter);

                // Fermer le dialogue après l'application des filtres
                dialog.dispose();
            });
            // Ajouter un bouton Annuler pour fermer le dialogue sans filtrer
            // Ajouter un bouton Annuler pour fermer le dialogue et annuler le filtre
            JButton annulerButton = new JButton("Annuler");
            annulerButton.addActionListener(cancelEvent -> {
                // Réinitialiser le tri et le filtre de la table à son état d'origine
                table.setRowSorter(null);
                dialog.dispose();
            });
            dialog.add(filtrerButton);
            dialog.add(annulerButton);
            dialog.setSize(300, 200);
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        });
        // Créer un modèle de table par défaut avec les données (data) et les noms de colonnes (columnNames)
        tableModel = new DefaultTableModel(data, columnNames);
        // Appliquer le modèle de table à la JTable
        table.setModel(tableModel);
     
        
        
        JButton addButton = createStyledButton("Ajouter");
        addButton.addActionListener(e -> {
            String nomMatiere = JOptionPane.showInputDialog(frame, "Nom de la Matière:");
            if (nomMatiere== null) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
            	return;
            }
            String sujet = JOptionPane.showInputDialog(frame, "Sujet du Projet:");
            if (sujet == null) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
            	return;
            }
            String dateRemisePrevueString = JOptionPane.showInputDialog(frame, "Date Remise Prévue (YYYY-MM-DD):");
            if (dateRemisePrevueString == null) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
            	return;
            }
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
            if (nomMatiere == null) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
            	return;
            }
            String sujet = JOptionPane.showInputDialog(frame, "Nouveau sujet du projet:", projetToUpdate.getSujet());
            if (sujet== null) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
            	return;
            }
            String dateRemisePrevueString = JOptionPane.showInputDialog(frame, "Nouvelle date Remise Prévue (YYYY-MM-DD):");
            if (dateRemisePrevueString== null) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
            	return;
            }
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
        
        
        // Créer un bouton avec le texte "Retour au menu principal"
        JButton retourButton = createStyledButton("Retour au menu principal");
        // Ajouter un écouteur d'événements (ActionListener) au bouton
        retourButton.addActionListener(new ActionListener() {
        	// Lorsque le bouton est cliqué, exécuter les instructions suivantes :
            // Fermer la fenêtre actuelle
            @Override
            public void actionPerformed(ActionEvent e) {
            	 App.main(null);
            	 frame.setVisible(false);
            }
        });
        
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(boutonFiltrer);
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

