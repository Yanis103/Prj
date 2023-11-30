package interfaceG;
import javax.swing.table.DefaultTableModel;

import classe.Binome;
import classe.Etudiant;
import classe.EtudiantBinome;
import classe.Projet;
import dao.BinomeDAO;
import dao.EtudiantBinomeDAO;
import dao.EtudiantDAO;
import dao.ProjetDAO;
import exception.PlusDeDeuxEtudiants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
 
public class AppBinome {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;
    private BinomeDAO binomeDAO;
    private ProjetDAO projetDAO;
    private EtudiantDAO etudiantDAO;
    private EtudiantBinomeDAO etudiantBinomeDAO;
    private DefaultTableModel tableModel;
    private final String[] columnNames = {"ID", "Binôme Référence","Projet", "Sujet","Etudiant 1","Etudiant 2","Date Remise Effective","Note Rapport"};
    private JPanel buttonPanel;
    
    public AppBinome() {
    	/*	Création de la Frame	*/
        frame = new JFrame("Gestion des Binômes");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(245, 245, 245));
        
        binomeDAO = new BinomeDAO();
        projetDAO = new ProjetDAO();
        etudiantDAO = new EtudiantDAO();
        etudiantBinomeDAO = new EtudiantBinomeDAO(); 
        List<Binome> binomes = binomeDAO.getAllBinomes();
        Object[][] data = new Object[binomes.size()][8];
        for (int i = 0; i < binomes.size(); i++) {
            Binome binome = binomes.get(i);
            int idBinome = binome.getIdBinome();
            data[i][0] = idBinome;
            data[i][1] = binome.getBinomeReference();
            data[i][2] = binome.getProjet().getNomMatiere();  // Utiliser le nom de la matière du projet
            data[i][3] = binome.getProjet().getSujet();
            Etudiant[] etudiants = etudiantBinomeDAO.getEtudiantsByIdBinome(idBinome);
            data[i][4] = etudiants[0] == null ? " " : etudiants[0].getNom()+" "+etudiants[0].getPrenom();
            data[i][5] = etudiants[1] == null ? " " : etudiants[1].getNom()+" "+etudiants[1].getPrenom();
            data[i][6] = binome.getDateRemiseEffective();
            data[i][7] = binome.getNoteRapport();
            
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
        scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        
        JButton addButton = createStyledButton("Ajouter");
        /*Créer le binome et l'ajouter à la base de données*/
        addButton.addActionListener(e -> {
            List<Projet> projets = projetDAO.getAllProjets();
            String[] projetNames = projets.stream()
                    .map(Projet::getNomMatiere)
                    .toArray(String[]::new);
            String selectedProjetName = (String) JOptionPane.showInputDialog(frame, "Sélectionnez la Matière du Projet :",
                    "Sélection de la Matière du Projet", JOptionPane.PLAIN_MESSAGE, null, projetNames, projetNames[0]);
            
            if (selectedProjetName == null) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
            	return;
            }
           
            Projet selectedProjet = projets.stream()
                    .filter(projet -> projet.getNomMatiere().equals(selectedProjetName))
                    .findFirst()
                    .orElse(null);
            
            
            String binomeReference = JOptionPane.showInputDialog(frame, "Référence du binôme :");
            if( binomeReference == null ) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
                return;
            }
            LocalDate dateRemiseEffective = LocalDate.parse("2023-01-01");
           
            Binome nouveauBinome = new Binome(selectedProjet, null, binomeReference, dateRemiseEffective);
            binomeDAO.addBinome(nouveauBinome);
            /*Création des EtudiantBinome et les ajouter dans la base de données*/
            /*Spécifier le nom et le prenom de l'étudiant 1*/
            List<Etudiant> etudiants = etudiantDAO.getAllEtudiants();
            String[] etudiantNames = etudiants.stream()
                    .map(etudiant -> etudiant.getNom() + " " + etudiant.getPrenom())
                    .toArray(String[]::new);
 
            String selectedEtudiantName = (String) JOptionPane.showInputDialog(frame, "Sélectionnez l'Étudiant :",
                    "Sélection de l'Étudiant 1", JOptionPane.PLAIN_MESSAGE, null, etudiantNames, etudiantNames[0]);
            if( selectedEtudiantName  == null ) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
                return;
            }
            Etudiant selectedEtudiant = etudiants.stream()
                    .filter(etudiant -> (etudiant.getNom() + " " + etudiant.getPrenom()).equals(selectedEtudiantName))
                    .findFirst()
                    .orElse(null);
           
            EtudiantBinome nouvelEtudiantBinome = new EtudiantBinome(selectedEtudiant, nouveauBinome, null);
            try {
            	etudiantBinomeDAO.addEtudiantBinome(nouvelEtudiantBinome);
            }catch(SQLException s) {
            	JOptionPane.showMessageDialog(frame, s.getMessage());
                return;
            }
            catch(PlusDeDeuxEtudiants s) {
            	JOptionPane.showMessageDialog(frame, s.getMessage());
                return;
            }
            /*Spécifier le nom et le prenom de l'étuduant 2*/
            String[] etudiantNames2 = etudiants.stream()
                    .map(etudiant -> etudiant.getNom() + " " + etudiant.getPrenom())
                    .toArray(String[]::new);

            String selectedEtudiantName2 = (String) JOptionPane.showInputDialog(frame, "Sélectionnez l'Étudiant :",
                    "Sélection de l'Étudiant 2", JOptionPane.PLAIN_MESSAGE, null, etudiantNames2, etudiantNames2[0]);
            if( selectedEtudiantName2 == null ) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
                return;
            }
            Etudiant selectedEtudiant2 = etudiants.stream()
                    .filter(etudiant -> (etudiant.getNom() + " " + etudiant.getPrenom()).equals(selectedEtudiantName2))
                    .findFirst()
                    .orElse(null);
             
            EtudiantBinome nouvelEtudiantBinome2 = new EtudiantBinome(selectedEtudiant2, nouveauBinome, null);
            try {
            	etudiantBinomeDAO.addEtudiantBinome(nouvelEtudiantBinome2);
            }
            catch(SQLException s) {
            	JOptionPane.showMessageDialog(frame, "Les deux étudiants doivent etre différents !");
            }
            catch(PlusDeDeuxEtudiants s) {
            	JOptionPane.showMessageDialog(frame, s.getMessage());
                
            }
            
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
            
            if( selectedProjetName  == null ) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
                return;
            }

            Projet selectedProjet = projets.stream()
                    .filter(projet -> projet.getNomMatiere().equals(selectedProjetName))
                    .findFirst()
                    .orElse(null);
            String binomeReference = JOptionPane.showInputDialog(frame, "Nouvelle référence du binôme :", binomeToUpdate.getBinomeReference());
            if( binomeReference   == null ) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
                return;
            }
            String nouvelleDateStr = JOptionPane.showInputDialog(frame, "Nouvelle Date Remise Effective (YYYY-MM-DD)", binomeToUpdate.getDateRemiseEffective());
            if( nouvelleDateStr   == null ) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
                return;
            }
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
        
        
        this.buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(noteButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(retourButton);
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.setSize(screenWidth, screenHeight); // Utiliser setSize au lieu de pack
        frame.setLocationRelativeTo(null); // Centrer la fenêtre*
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
        Object[][] newData = new Object[binomes.size()][8];
        for (int i = 0; i < binomes.size(); i++) {
                Binome binome = binomes.get(i);
                int idBinome = binome.getIdBinome();
                newData[i][0] = idBinome;
                newData[i][1] = binome.getBinomeReference();
                newData[i][2] = binome.getProjet().getNomMatiere();  // Utiliser le nom de la matière du projet
                newData[i][3] = binome.getProjet().getSujet();
                Etudiant[] etudiants = etudiantBinomeDAO.getEtudiantsByIdBinome(idBinome);
                newData[i][4] = etudiants[0] == null ? " " : etudiants[0].getNom()+" "+etudiants[0].getPrenom();
                newData[i][5] = etudiants[1] == null ? " " : etudiants[1].getNom()+" "+etudiants[1].getPrenom();
                newData[i][6] = binome.getDateRemiseEffective();
                newData[i][7] = binome.getNoteRapport()== null ? "" : binome.getNoteRapport();         
        }

        tableModel.setDataVector(newData, columnNames);
    }

    
    public JFrame getFrame() {
    	return this.frame;
    }
    
    public JPanel getPanel() {
    	return this.buttonPanel;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppBinome::new);
    }
}
