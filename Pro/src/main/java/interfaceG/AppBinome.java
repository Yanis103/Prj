package interfaceG;

import javax.swing.table.DefaultTableModel;

import classe.Binome;
import classe.Projet;
import dao.BinomeDAO;
import dao.ProjetDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class AppBinome {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;
    private BinomeDAO binomeDAO;
    private ProjetDAO projetDAO;
    private DefaultTableModel tableModel;
    private final String[] columnNames = {"ID", "Projet", "Note Rapport", "Binôme Référence", "Date Remise Effective"};

    public AppBinome() {
        frame = new JFrame("Gestion des Binômes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(245, 245, 245));

        binomeDAO = new BinomeDAO();
        projetDAO = new ProjetDAO();
        List<Binome> binomes = binomeDAO.getAllBinomes();
        Object[][] data = new Object[binomes.size()][5];
        for (int i = 0; i < binomes.size(); i++) {
            Binome binome = binomes.get(i);
            data[i][0] = binome.getIdBinome();
            data[i][1] = binome.getProjet().getNomMatiere();  // Utiliser le nom de la matière du projet
            data[i][2] = binome.getNoteRapport();
            data[i][3] = binome.getBinomeReference();
            data[i][4] = binome.getDateRemiseEffective();
        }
        table = new JTable(data, columnNames);
        scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        
        
        JButton addButton = createStyledButton("Ajouter");
        addButton.addActionListener(e -> {
            List<Projet> projets = projetDAO.getAllProjets();
            String[] projetNames = projets.stream()
                    .map(Projet::getNomMatiere)
                    .toArray(String[]::new);

            String selectedProjetName = (String) JOptionPane.showInputDialog(frame, "Sélectionnez la Matière du Projet :",
                    "Sélection de la Matière du Projet", JOptionPane.PLAIN_MESSAGE, null, projetNames, projetNames[0]);

            Projet selectedProjet = projets.stream()
                    .filter(projet -> projet.getNomMatiere().equals(selectedProjetName))
                    .findFirst()
                    .orElse(null);  
            String binomeReference = JOptionPane.showInputDialog(frame, "Référence du binôme :");
            String dateRemiseEffectiveString = JOptionPane.showInputDialog(frame, "Date Remise Effective (YYYY-MM-DD)");
            
            if(selectedProjet == null || binomeReference == null || dateRemiseEffectiveString == null ) {
            	JOptionPane.showMessageDialog(frame, "Un des champs n'a pas été spécifié !");
                return;
            }
            
            LocalDate dateRemiseEffective = LocalDate.parse(dateRemiseEffectiveString);
            Binome nouveauBinome = new Binome(selectedProjet, null, binomeReference, dateRemiseEffective);
            binomeDAO.addBinome(nouveauBinome);

            // Rafraîchir la table après l'ajout
            refreshTable();
        });
        tableModel = new DefaultTableModel(data, columnNames);
        table.setModel(tableModel);

        
        
        JButton updateButton = createStyledButton("Mettre à jour");
        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Sélectionnez un binôme à mettre à jour.");
                return;
            }

            int idToUpdate = (int) table.getValueAt(selectedRow, 0);
            Binome binomeToUpdate = binomeDAO.getBinomeById(idToUpdate);

            List<Projet> projets = projetDAO.getAllProjets();
            String[] projetNames = projets.stream()
                    .map(Projet::getNomMatiere)
                    .toArray(String[]::new);

            String selectedProjetName = (String) JOptionPane.showInputDialog(frame, "Sélectionnez la Matière du Projet :",
                    "Sélection de la Matière du Projet", JOptionPane.PLAIN_MESSAGE, null, projetNames, projetNames[0]);

            Projet selectedProjet = projets.stream()
                    .filter(projet -> projet.getNomMatiere().equals(selectedProjetName))
                    .findFirst()
                    .orElse(null);

         
            String binomeReference = JOptionPane.showInputDialog(frame, "Nouvelle référence du binôme :", binomeToUpdate.getBinomeReference());
            String nouvelleDateStr = JOptionPane.showInputDialog(frame, "Nouvelle Date Remise Effective (YYYY-MM-DD)", binomeToUpdate.getDateRemiseEffective());
            LocalDate dateRemiseEffective = LocalDate.parse(nouvelleDateStr);

            binomeToUpdate.setProjet(selectedProjet);
        
            binomeToUpdate.setBinomeReference(binomeReference);
            binomeToUpdate.setDateRemiseEffective(dateRemiseEffective);

            binomeDAO.updateBinome(binomeToUpdate);

            // Rafraîchir la table après la mise à jour
            refreshTable();
        });
        
        
        JButton noteButton= createStyledButton("Noter");
        noteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Sélectionnez un binôme à noter.");
                return;
            }

            int idToUpdate = (int) table.getValueAt(selectedRow, 0);
            Binome binomeToUpdate = binomeDAO.getBinomeById(idToUpdate);

            // JSpinner pour la saisie de la note entre 0 et 20
            Double noteRapport = binomeToUpdate.getNoteRapport() != null ? binomeToUpdate.getNoteRapport() : 0.0;
            SpinnerNumberModel numberModel = new SpinnerNumberModel(noteRapport.doubleValue(), 0.0, 20.0, 0.1);


            JSpinner spinner = new JSpinner(numberModel);
            spinner.setEditor(new JSpinner.NumberEditor(spinner, "0.0")); 

            int option = JOptionPane.showOptionDialog(frame, spinner, "Nouvelle note de rapport", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (option == JOptionPane.OK_OPTION) {
                double nouvelleNoteRapport = ((Number) spinner.getValue()).doubleValue();

                // Vérifier que la note est entre 0 et 20
                if (nouvelleNoteRapport >= 0 && nouvelleNoteRapport <= 20) {
                    binomeToUpdate.setNoteRapport(nouvelleNoteRapport);
                    binomeDAO.updateBinome2(binomeToUpdate);

                    // Rafraîchir la table après la mise à jour
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(frame, "La note doit être entre 0 et 20.");
                }
            }
        });
        
        
        
        JButton deleteButton = createStyledButton("Supprimer");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Sélectionnez un binôme à supprimer.");
                return;
            }

            int idToDelete = (int) table.getValueAt(selectedRow, 0);
            binomeDAO.deleteBinome(idToDelete);

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
        buttonPanel.add(noteButton);
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
        List<Binome> binomes = binomeDAO.getAllBinomes();
        Object[][] newData = new Object[binomes.size()][5];

        for (int i = 0; i < binomes.size(); i++) {
            Binome binome = binomes.get(i);
            newData[i][0] = binome.getIdBinome();
            newData[i][1] = binome.getProjet().getNomMatiere();
            newData[i][2] = binome.getNoteRapport()== null ? "" : binome.getNoteRapport();
            newData[i][3] = binome.getBinomeReference();
            newData[i][4] = binome.getDateRemiseEffective();
        }

        tableModel.setDataVector(newData, columnNames);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppBinome::new);
    }
}
