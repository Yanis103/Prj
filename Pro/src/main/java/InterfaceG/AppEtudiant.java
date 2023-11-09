package InterfaceG;

import javax.swing.table.DefaultTableModel;

import Dao.EtudiantDAO;
import classe.Etudiant;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class AppEtudiant {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;
    private EtudiantDAO etudiantDAO;
    private DefaultTableModel tableModel;
    private final String[] columnNames = {"ID", "Nom", "Prénom", "ID Formation"};
    

    public AppEtudiant() {
        frame = new JFrame("Gestion des Etudiants");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        etudiantDAO = new EtudiantDAO();
        List<Etudiant> etudiants = etudiantDAO.getAllEtudiants();

       
        Object[][] data = new Object[etudiants.size()][4];

        for (int i = 0; i < etudiants.size(); i++) {
            Etudiant etudiant = etudiants.get(i);
            data[i][0] = etudiant.getIdEtudiant();
            data[i][1] = etudiant.getNom();
            data[i][2] = etudiant.getPrenom();
            data[i][3] = etudiant.getIdFormation();
        }

        table = new JTable(data, columnNames);
        scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(e -> {
            String nom = JOptionPane.showInputDialog(frame, "Nom de l'étudiant:");
            String prenom = JOptionPane.showInputDialog(frame, "Prénom de l'étudiant:");
            int idFormation = Integer.parseInt(JOptionPane.showInputDialog(frame, "ID de Formation:"));

            Etudiant nouveauEtudiant = new Etudiant();
            nouveauEtudiant.setNom(nom);
            nouveauEtudiant.setPrenom(prenom);
            nouveauEtudiant.setIdFormation(idFormation);

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
            int idFormation = Integer.parseInt(JOptionPane.showInputDialog(frame, "Nouvel ID de Formation:", etudiantToUpdate.getIdFormation()));

            etudiantToUpdate.setNom(nom);
            etudiantToUpdate.setPrenom(prenom);
            etudiantToUpdate.setIdFormation(idFormation);

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
             newData[i][3] = etudiant.getIdFormation();
         }

         tableModel.setDataVector(newData, columnNames);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppEtudiant::new);
    }
}
