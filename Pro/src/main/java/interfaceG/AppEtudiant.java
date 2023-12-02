package interfaceG;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import classe.Etudiant;
import classe.Formation;  // Importer la classe Formation
import dao.EtudiantDAO;
import dao.FormationDAO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AppEtudiant {
    private JFrame frame; 
    private JTable table;
    private JScrollPane scrollPane;
    private EtudiantDAO etudiantDAO;
    private FormationDAO formationDAO;  // Ajouter le DAO de Formation
    private DefaultTableModel tableModel;
    private final String[] columnNames = {"ID", "Nom", "Prénom", "Formation"};  // Modifier le nom de la colonne

    public AppEtudiant() { 
    	// Créer une nouvelle fenêtre avec le titre "Gestion des Etudiants"
        frame = new JFrame("Gestion des Etudiants");
        // Définir l'opération par défaut lorsque l'utilisateur ferme la fenêtre
        // Dans ce cas, l'application sera fermée
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Définir le gestionnaire de disposition (layout manager) de la fenêtre
        // Dans ce cas, BorderLayout est utilisé, qui divise la fenêtre en cinq zones : Nord, Sud, Est, Ouest, Centre.
        frame.setLayout(new BorderLayout()); 
        frame.getContentPane().setBackground(Color.WHITE);
        
        // Instancier un objet DAO pour l'entité Etudiant
        etudiantDAO = new EtudiantDAO();
        // Instancier un objet DAO pour l'entité Formation
        formationDAO = new FormationDAO();  
        // Récupérer la liste de tous les étudiants depuis la base de données
        List<Etudiant> etudiants = etudiantDAO.getAllEtudiants();
        // Créer un tableau multidimensionnel pour stocker les données des étudiants (ID, Nom, Prénom, Formation)
        Object[][] data = new Object[etudiants.size()][4];
        // Remplir le tableau de données avec les informations des étudiants
        for (int i = 0; i < etudiants.size(); i++) {
            Etudiant etudiant = etudiants.get(i);
            data[i][0] = etudiant.getIdEtudiant();
            data[i][1] = etudiant.getNom();
            data[i][2] = etudiant.getPrenom();
            data[i][3] = etudiant.getFormation().getNomFormation();  // Utiliser le nom de la formation
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
            dialog.setLayout(new GridLayout(5, 2));
            // Ajouter des champs pour Numéro Étudiant, Nom, Prénom et Formation
            JTextField numeroEtudiantField = new JTextField();
            JTextField nomField = new JTextField();
            JTextField prenomField = new JTextField();
            List<Formation> formations = formationDAO.getAllFormations();
            String[] formationNames = formations.stream()
                    .map(Formation::getNomFormation)
                    .toArray(String[]::new);
            JComboBox<String> formationComboBox = new JComboBox<>(formationNames);
            dialog.add(new JLabel("Numéro Étudiant:"));
            dialog.add(numeroEtudiantField);
            dialog.add(new JLabel("Nom:"));
            dialog.add(nomField);
            dialog.add(new JLabel("Prénom:"));
            dialog.add(prenomField);
            dialog.add(new JLabel("Formation:"));
            dialog.add(formationComboBox);
            // Ajouter un bouton de filtrage dans le dialogue
            JButton filtrerButton = new JButton("Filtrer");
            filtrerButton.addActionListener(filtrerEvent -> {
                // Récupérer les valeurs des champs de filtrage
                String numeroEtudiant = numeroEtudiantField.getText();
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String selectedFormationName = (String) formationComboBox.getSelectedItem();

                // Créer un TableRowSorter pour la tableModel
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
                table.setRowSorter(sorter);

                // Créer une liste pour stocker les filtres
                List<RowFilter<Object, Object>> filters = new ArrayList<>();
                // Ajouter les filtres non vides aux critères de filtrage
                if (!numeroEtudiant.isEmpty()) {
                    filters.add(RowFilter.regexFilter(numeroEtudiant, 0));
                }
                if (!nom.isEmpty()) {
                    filters.add(RowFilter.regexFilter(nom, 1));
                }
                if (!prenom.isEmpty()) {
                    filters.add(RowFilter.regexFilter(prenom, 2));
                }
                if (!selectedFormationName.isEmpty()) {
                    filters.add(RowFilter.regexFilter(selectedFormationName, 3));
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
        
        
        
        // Créer un bouton avec le texte "Ajouter"
        JButton addButton = createStyledButton("Ajouter");
        // Ajouter un écouteur d'événements au bouton (utilisation d'une expression lambda)
        addButton.addActionListener(e -> {
        	// Demander à l'utilisateur le nom de l'étudiant
            String nom = JOptionPane.showInputDialog(frame, "Nom de l'étudiant:");
            if (nom  == null) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
            	return;
            }
            // Demander à l'utilisateur le nom de l'étudiant
            String prenom = JOptionPane.showInputDialog(frame, "Prénom de l'étudiant:");
            if (prenom == null) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
            	return;
            }
            // Obtenir la liste de toutes les formations depuis la base de données
            List<Formation> formations = formationDAO.getAllFormations(); 
            // Convertir la liste de formations en un tableau de noms de formations
            String[] formationNames = formations.stream()
                    .map(Formation::getNomFormation)
                    .toArray(String[]::new);
            // Demander à l'utilisateur de sélectionner le nom de la formation dans une boîte de dialogue
            String selectedFormationName = (String) JOptionPane.showInputDialog(frame, "Sélectionnez le Nom de la Formation :",
                    "Sélection du Nom de la Formation", JOptionPane.PLAIN_MESSAGE, null, formationNames, formationNames[0]);
            if (selectedFormationName  == null) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
            	return;
            }
            // Rechercher la formation sélectionnée dans la liste des formations
            Formation selectedFormation = formations.stream()
                    .filter(formation -> formation.getNomFormation().equals(selectedFormationName))
                    .findFirst()
                    .orElse(null);
            // On vérifie que tous les champs ont été précisés
            if(nom == null || prenom == null || selectedFormationName == null) {
            	JOptionPane.showMessageDialog(frame, "Un des champs n'a pas été spécifié !");
                return;
            }
            // Créer un nouvel objet Etudiant avec les informations fournies
            Etudiant nouveauEtudiant = new Etudiant();
            nouveauEtudiant.setNom(nom);
            nouveauEtudiant.setPrenom(prenom);
            nouveauEtudiant.setFormation(selectedFormation);  // Utiliser l'objet Formation
            // Ajouter le nouvel étudiant à la base de données
            etudiantDAO.addEtudiant(nouveauEtudiant);
            // Rafraîchir la table après l'ajout
            refreshTable();
        });
        // Créer un modèle de table par défaut avec les données (data) et les noms de colonnes (columnNames)
        tableModel = new DefaultTableModel(data, columnNames);
        // Appliquer le modèle de table à la JTable
        table.setModel(tableModel);

        
        // Créer un bouton avec le texte "Mettre à jour"
        JButton updateButton = createStyledButton("Mettre à jour");
        // Ajouter un écouteur d'événements au bouton (utilisation d'une expression lambda)
        updateButton.addActionListener(e -> {
        	// Récupérer l'indice de la ligne sélectionnée dans la JTable
            int selectedRow = table.getSelectedRow();
            // Vérifier si une ligne est sélectionnée
            if (selectedRow == -1) {
            	// Vérifier si une ligne est sélectionnée
                JOptionPane.showMessageDialog(frame, "Sélectionnez un étudiant à mettre à jour.");
                return;
            }
            // Récupérer l'ID de l'étudiant à mettre à jour à partir de la colonne 0 de la ligne sélectionnée
            int idToUpdate = (int) table.getValueAt(selectedRow, 0);
            // Récupérer l'objet Etudiant à mettre à jour depuis la base de données en utilisant son ID
            Etudiant etudiantToUpdate = etudiantDAO.getEtudiantById(idToUpdate);
            // Demander à l'utilisateur le nouveau nom de l'étudiant avec une boîte de dialogue
            String nom = JOptionPane.showInputDialog(frame, "Nouveau nom de l'étudiant:", etudiantToUpdate.getNom());
            if (nom  == null) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
            	return;
            }
            // Demander à l'utilisateur le nouveau prénom de l'étudiant avec une boîte de dialogue
            String prenom = JOptionPane.showInputDialog(frame, "Nouveau prénom de l'étudiant:", etudiantToUpdate.getPrenom());
            if (prenom  == null) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
            	return;
            }
            // Obtenir la liste de toutes les formations depuis la base de données
            List<Formation> formations = formationDAO.getAllFormations();
            // Convertir la liste de formations en un tableau de noms de formations
            String[] formationNames = formations.stream()
                    .map(Formation::getNomFormation)
                    .toArray(String[]::new);
            // Demander à l'utilisateur de sélectionner le nom de la formation dans une boîte de dialogue
            String selectedFormationName = (String) JOptionPane.showInputDialog(frame, "Sélectionnez le Nom de la Formation :",
                    "Sélection du Nom de la Formation", JOptionPane.PLAIN_MESSAGE, null, formationNames, formationNames[0]);
            if (selectedFormationName  == null) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
            	return;
            }
            // Rechercher la formation sélectionnée dans la liste des formations
            Formation selectedFormation = formations.stream()
                    .filter(formation -> formation.getNomFormation().equals(selectedFormationName))
                    .findFirst()
                    .orElse(null);
            // On vérifie que tous les champs ont été précisés
            if(nom == null || prenom == null || selectedFormationName == null) {
            	JOptionPane.showMessageDialog(frame, "Un des champs n'a pas été spécifié !");
                return;
            }
            // Mettre à jour les propriétés de l'objet Etudiant avec les nouvelles informations
            etudiantToUpdate.setNom(nom);
            etudiantToUpdate.setPrenom(prenom);
            etudiantToUpdate.setFormation(selectedFormation);
            // Mettre à jour l'étudiant dans la base de données à l'aide du DAO
            etudiantDAO.updateEtudiant(etudiantToUpdate);
            // Rafraîchir la table après la mise à jour
            refreshTable();
        });

        
        // Créer un bouton avec le texte "Supprimer"
        JButton deleteButton = createStyledButton("Supprimer");
        // Ajouter un écouteur d'événements au bouton (utilisation d'une expression lambda)
        deleteButton.addActionListener(e -> {
        	// Récupérer l'indice de la ligne sélectionnée dans la JTable
            int selectedRow = table.getSelectedRow();
            // Vérifier si une ligne est sélectionnée
            if (selectedRow == -1) {
            	 // Afficher un message d'erreur si aucune ligne n'est sélectionnée
                JOptionPane.showMessageDialog(frame, "Sélectionnez un étudiant à supprimer.");
                return;
            }
            // Récupérer l'ID de l'étudiant à supprimer à partir de la colonne 0 de la ligne sélectionnée
            int idToDelete = (int) table.getValueAt(selectedRow, 0);
            etudiantDAO.deleteEtudiant(idToDelete);
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
        
        // Créer un panneau pour les boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        // Ajouter les boutons (Ajouter, Mettre à jour, Supprimer, Retour au menu principal) au panneau
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
    
    /**
     * Méthode pour rafraîchir le contenu de la JTable avec les données les plus récentes depuis la base de données
     */
    private void refreshTable() {
    	// Obtenir la liste mise à jour des étudiants depuis la base de données
        List<Etudiant> etudiants = etudiantDAO.getAllEtudiants();
        // Créer un nouveau tableau bidimensionnel pour stocker les données mises à jour
        Object[][] newData = new Object[etudiants.size()][4];
        for (int i = 0; i < etudiants.size(); i++) {
            Etudiant etudiant = etudiants.get(i);
            newData[i][0] = etudiant.getIdEtudiant();
            newData[i][1] = etudiant.getNom();
            newData[i][2] = etudiant.getPrenom();
            newData[i][3] = etudiant.getFormation().getNomFormation();
        }
        // Mettre à jour le modèle de table avec les nouvelles données
        tableModel.setDataVector(newData, columnNames);
    }

    /**
     * Méthode principale pour lancer l'application Swing des étudiants
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppEtudiant::new);
    }
}
