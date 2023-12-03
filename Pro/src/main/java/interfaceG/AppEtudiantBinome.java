package interfaceG;

import classe.Binome;
import classe.Etudiant;
import classe.EtudiantBinome;
import dao.BinomeDAO;
import dao.EtudiantBinomeDAO;
import dao.EtudiantDAO;
import exception.PlusDeDeuxEtudiants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AppEtudiantBinome {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;
    private EtudiantDAO etudiantDAO;
    private BinomeDAO binomeDAO;
    private EtudiantBinomeDAO etudiantBinomeDAO;
    private DefaultTableModel tableModel;
    private final String[] columnNames = {"ID Etudiant", "Nom Etudiant", "Prénom Etudiant", "ID Binôme", "Référence Binôme", "Note Soutenance"};
    private JPanel buttonPanel;
     
    public AppEtudiantBinome() {
        frame = new JFrame("Gestion des Étudiants et des Binômes");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        etudiantDAO = new EtudiantDAO();
        binomeDAO = new BinomeDAO();
        etudiantBinomeDAO = new EtudiantBinomeDAO();

        List<EtudiantBinome> etudiantBinomes = etudiantBinomeDAO.getAllEtudiantBinomes();

        Object[][] data = new Object[etudiantBinomes.size()][6];

        for (int i = 0; i < etudiantBinomes.size(); i++) {
            EtudiantBinome etudiantBinome = etudiantBinomes.get(i);
            Etudiant etudiant = etudiantBinome.getEtudiant();
            Binome binome = etudiantBinome.getBinome();

            data[i][0] = etudiant.getIdEtudiant();
            data[i][1] = etudiant.getNom();
            data[i][2] = etudiant.getPrenom();
            data[i][3] = binome.getIdBinome();
            data[i][4] = binome.getBinomeReference();
            data[i][5] = etudiantBinome.getNoteSoutenance();
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
        addButton.addActionListener(e -> {
            List<Etudiant> etudiants = etudiantDAO.getAllEtudiants();
            String[] etudiantNames = etudiants.stream()
                    .map(etudiant -> etudiant.getNom() + " " + etudiant.getPrenom())
                    .toArray(String[]::new);

            String selectedEtudiantName = (String) JOptionPane.showInputDialog(frame, "Sélectionnez l'Étudiant :",
                    "Sélection de l'Étudiant", JOptionPane.PLAIN_MESSAGE, null, etudiantNames, etudiantNames[0]);
            if (selectedEtudiantName == null) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
            	return;
            }

            Etudiant selectedEtudiant = etudiants.stream()
                    .filter(etudiant -> (etudiant.getNom() + " " + etudiant.getPrenom()).equals(selectedEtudiantName))
                    .findFirst()
                    .orElse(null);

            List<Binome> binomes = binomeDAO.getAllBinomes();
            String[] binomeReferences = binomes.stream()
                    .map(Binome::getBinomeReference)
                    .toArray(String[]::new);

            String selectedBinomeReference = (String) JOptionPane.showInputDialog(frame, "Sélectionnez le Binôme :",
                    "Sélection du Binôme", JOptionPane.PLAIN_MESSAGE, null, binomeReferences, binomeReferences[0]);
            if (selectedBinomeReference == null) {
            	JOptionPane.showMessageDialog(frame, "Opération annulée par l'utilisateur.");
            	return;
            }

            Binome selectedBinome = binomes.stream()
                    .filter(binome -> binome.getBinomeReference().equals(selectedBinomeReference))
                    .findFirst()
                    .orElse(null);
            
            EtudiantBinome nouvelEtudiantBinome = new EtudiantBinome(selectedEtudiant, selectedBinome, null);
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
            // Rafraîchir la table après l'ajout
            refreshTable();
        });

        tableModel = new DefaultTableModel(data, columnNames);
        table.setModel(tableModel);

        JButton noteButton = createStyledButton("Noter");
        noteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Sélectionnez un étudiant à noter.");
                return;
            }

            int idEtudiantToUpdate = (int) table.getValueAt(selectedRow, 0);
            int idBinomeToUpdate = (int) table.getValueAt(selectedRow, 3);
            EtudiantBinome etudiantBinomeToUpdate = etudiantBinomeDAO.getEtudiantBinomeById(idEtudiantToUpdate, idBinomeToUpdate);

            // JSpinner pour la saisie de la note entre 0 et 20
            Double noteSoutenance = etudiantBinomeToUpdate.getNoteSoutenance() != null ? etudiantBinomeToUpdate.getNoteSoutenance() : 0.0;
            SpinnerNumberModel numberModel = new SpinnerNumberModel(noteSoutenance.doubleValue(), 0.0, 20.0, 0.1);

            JSpinner spinner = new JSpinner(numberModel);
            spinner.setEditor(new JSpinner.NumberEditor(spinner, "0.0")); 

            int option = JOptionPane.showOptionDialog(frame, spinner, "Nouvelle note de soutenance", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (option == JOptionPane.OK_OPTION) {
                double nouvelleNoteSoutenance = ((Number) spinner.getValue()).doubleValue();

                // Vérifier que la note est entre 0 et 20
                if (nouvelleNoteSoutenance >= 0 && nouvelleNoteSoutenance <= 20) {
                    etudiantBinomeToUpdate.setNoteSoutenance(nouvelleNoteSoutenance);
                    etudiantBinomeDAO.updateEtudiantBinome(etudiantBinomeToUpdate);

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
                JOptionPane.showMessageDialog(frame, "Sélectionnez un enregistrement à supprimer.");
                return;
            }

            int idEtudiantToDelete = (int) table.getValueAt(selectedRow, 0);
            int idBinomeToDelete = (int) table.getValueAt(selectedRow, 3);
            etudiantBinomeDAO.deleteEtudiantBinome(idEtudiantToDelete, idBinomeToDelete);

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
            	 etudiantBinomeDAO.closeConnection();
            	 binomeDAO.closeConnection();
            	 etudiantDAO.closeConnection();
            	 frame.dispose();
            	 App.main(null);
            }
        });
        

        this.buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(noteButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(retourButton);
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.setSize(screenWidth, screenHeight);
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
        List<EtudiantBinome> etudiantBinomes = etudiantBinomeDAO.getAllEtudiantBinomes();
        Object[][] newData = new Object[etudiantBinomes.size()][6];

        for (int i = 0; i < etudiantBinomes.size(); i++) {
            EtudiantBinome etudiantBinome = etudiantBinomes.get(i);
            Etudiant etudiant = etudiantBinome.getEtudiant();
            Binome binome = etudiantBinome.getBinome();

            newData[i][0] = etudiant.getIdEtudiant();
            newData[i][1] = etudiant.getNom();
            newData[i][2] = etudiant.getPrenom();
            newData[i][3] = binome.getIdBinome();
            newData[i][4] = binome.getBinomeReference();
            newData[i][5] = etudiantBinome.getNoteSoutenance() == null ? "" : etudiantBinome.getNoteSoutenance();

        }

        tableModel.setDataVector(newData, columnNames);
        // Rafraîchir l'affichage
        tableModel.fireTableDataChanged();
    }
    
    public JFrame getFrame() {
    	return this.frame;
    }
    
    public JPanel getPanel() {
    	return this.buttonPanel;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppEtudiantBinome::new);
    }
}