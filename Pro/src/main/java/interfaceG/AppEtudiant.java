package interfaceG;

import javax.swing.table.DefaultTableModel;

import classe.Etudiant;
import classe.Formation;  // Importer la classe Formation
import dao.EtudiantDAO;
import dao.FormationDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AppEtudiant {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;
    private EtudiantDAO etudiantDAO;
    private FormationDAO formationDAO;  // Ajouter le DAO de Formation
    private DefaultTableModel tableModel;
    private final String[] columnNames = {"ID", "Nom", "Prénom", "Formation"};  // Modifier le nom de la colonne


    public AppEtudiant() {
    	/*Titre de la fenetre*/
        frame = new JFrame("Gestion des Etudiants");
        /*Fermeture de la fenetre*/
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        etudiantDAO = new EtudiantDAO();
        formationDAO = new FormationDAO();  // Initialiser le DAO de Formation
        /*On récupere tous les etudiants de la base de donnees*/
        List<Etudiant> etudiants = etudiantDAO.getAllEtudiants();
        /*On stocke les informations des etudiants dans un tableau qui possede 4 colonnes ; une colonne par attribut*/
        Object[][] data = new Object[etudiants.size()][4];
        for (int i = 0; i < etudiants.size(); i++) {
            Etudiant etudiant = etudiants.get(i);
            data[i][0] = etudiant.getIdEtudiant();
            data[i][1] = etudiant.getNom();
            data[i][2] = etudiant.getPrenom();
            data[i][3] = etudiant.getFormation().getNomFormation();  // Utiliser le nom de la formation
        }
        table = new JTable(data, columnNames);
        
        scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        /*Ona joute un bouton "Ajouter" et on lui associe une action*/
        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(e -> {
        	
        	/*On demande a l utilisateur de preciser le nom, le prenom */
            String nom = JOptionPane.showInputDialog(frame, "Nom de l'étudiant:");
            String prenom = JOptionPane.showInputDialog(frame, "Prénom de l'étudiant:");
            
            /*On stocke dans formations toutes les formations diponibles dans la base de donnees*/
            List<Formation> formations = formationDAO.getAllFormations();  // Obtenir la liste des formations
            String[] formationNames = formations.stream()
                    .map(Formation::getNomFormation)
                    .toArray(String[]::new);
            String selectedFormationName = (String) JOptionPane.showInputDialog(frame, "Sélectionnez le Nom de la Formation :",
                    "Sélection du Nom de la Formation", JOptionPane.PLAIN_MESSAGE, null, formationNames, formationNames[0]);
            Formation selectedFormation = formations.stream()
                    .filter(formation -> formation.getNomFormation().equals(selectedFormationName))
                    .findFirst()
                    .orElse(null);
            /*On cree l etudiant avec l ensemble des informations recoltees*/
            Etudiant nouveauEtudiant = new Etudiant();
            nouveauEtudiant.setNom(nom);
            nouveauEtudiant.setPrenom(prenom);
            nouveauEtudiant.setFormation(selectedFormation);  // Utiliser l'objet Formation

            etudiantDAO.addEtudiant(nouveauEtudiant);

            // Rafraîchir la table après l'ajout
            refreshTable();
        });

        tableModel = new DefaultTableModel(data, columnNames);
        table.setModel(tableModel);

        JButton updateButton = new JButton("Mettre à jour");
        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Sélectionnez un étudiant à mettre à jour.");
                return;
            }

            int idToUpdate = (int) table.getValueAt(selectedRow, 0);
            Etudiant etudiantToUpdate = etudiantDAO.getEtudiantById(idToUpdate);

            String nom = JOptionPane.showInputDialog(frame, "Nouveau nom de l'étudiant:", etudiantToUpdate.getNom());
            String prenom = JOptionPane.showInputDialog(frame, "Nouveau prénom de l'étudiant:", etudiantToUpdate.getPrenom());

            List<Formation> formations = formationDAO.getAllFormations();
            String[] formationNames = formations.stream()
                    .map(Formation::getNomFormation)
                    .toArray(String[]::new);

            String selectedFormationName = (String) JOptionPane.showInputDialog(frame, "Sélectionnez le Nom de la Formation :",
                    "Sélection du Nom de la Formation", JOptionPane.PLAIN_MESSAGE, null, formationNames, formationNames[0]);

            Formation selectedFormation = formations.stream()
                    .filter(formation -> formation.getNomFormation().equals(selectedFormationName))
                    .findFirst()
                    .orElse(null);

            etudiantToUpdate.setNom(nom);
            etudiantToUpdate.setPrenom(prenom);
            etudiantToUpdate.setFormation(selectedFormation);

            etudiantDAO.updateEtudiant(etudiantToUpdate);

            // Rafraîchir la table après la mise à jour
            refreshTable();
        });

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Sélectionnez un étudiant à supprimer.");
                return;
            }

            int idToDelete = (int) table.getValueAt(selectedRow, 0);
            etudiantDAO.deleteEtudiant(idToDelete);

            // Rafraîchir la table après la suppression
            refreshTable();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    private void refreshTable() {
        List<Etudiant> etudiants = etudiantDAO.getAllEtudiants();
        Object[][] newData = new Object[etudiants.size()][4];

        for (int i = 0; i < etudiants.size(); i++) {
            Etudiant etudiant = etudiants.get(i);
            newData[i][0] = etudiant.getIdEtudiant();
            newData[i][1] = etudiant.getNom();
            newData[i][2] = etudiant.getPrenom();
            newData[i][3] = etudiant.getFormation().getNomFormation();
        }

        tableModel.setDataVector(newData, columnNames);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppEtudiant::new);
    }
}
