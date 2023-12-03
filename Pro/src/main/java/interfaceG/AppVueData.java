package interfaceG;

import dao.VueDataDAO;
import classe.VueData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AppVueData {
        private JFrame frame;
        private JTable table;
        private JScrollPane scrollPane;
        private VueDataDAO vueDataDAO;
        private DefaultTableModel tableModel;
        private final String[] columnNames = {"Nom Etudiant", "Prénom Etudiant","Binôme Référence","Nom Matière", "Sujet","Note Rapport", "Note Soutenance", "Note Finale"};

        public AppVueData() {
            frame = new JFrame("Visualisation des notes");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setLocationRelativeTo(null);

            vueDataDAO = new VueDataDAO();
            List<String> formations = vueDataDAO.getFormations();

            frame.setLayout(new BorderLayout());

            // Création du modèle de table avec les colonnes définies
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            table = new JTable(tableModel);
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
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(formations.size(), 1));

            for (String f : formations) {
                JButton bouton = createStyledButton(f, 52, 152, 219);
                buttonPanel.add(bouton);
                bouton.addActionListener(e -> {
                    affichage(f);
                });
            }
            frame.add(buttonPanel, BorderLayout.WEST);
            
            // Créer un bouton avec le texte "Retour au menu principal"
            JButton retourButton = createStyledButton("Retour au menu principal",52,152,219);
            // Ajouter un écouteur d'événements (ActionListener) au bouton
            retourButton.addActionListener(new ActionListener() {
            	// Lorsque le bouton est cliqué, exécuter les instructions suivantes :
                // Fermer la fenêtre actuelle
                @Override
                public void actionPerformed(ActionEvent e) {
                	vueDataDAO.closeConnection();
                	frame.setVisible(false);	 
                }
                
            });
            
            JPanel bottomPanel = new JPanel();
            bottomPanel.add(retourButton);
            frame.add(bottomPanel, BorderLayout.SOUTH);
            frame.setVisible(true);
        }

        public void affichage(String nomFormation) {
            List<VueData> vueDataList = vueDataDAO.getAllVueDataFormation(nomFormation);

            // Effacer le contenu actuel de la table
            this.tableModel = (DefaultTableModel) table.getModel();
            tableModel.setRowCount(0);
            
            Object[][] data = new Object[vueDataList.size()][12];
            for (int i = 0; i < vueDataList.size(); i++) {
                VueData vueData = vueDataList.get(i);
                data[i][0] = vueData.getEtudiantNom();
                data[i][1] = vueData.getEtudiantPrenom();
                data[i][2] = vueData.getBinomeReference();
                data[i][3] = vueData.getNomMatiere();
                data[i][4] = vueData.getSujet();
                data[i][5] = vueData.getNoteRapport();
                data[i][6] = vueData.getNoteSoutenance();
                data[i][7] = String.format("%.2f", vueData.getNoteFinale());
            }
            tableModel = new DefaultTableModel(data, columnNames);
            table.setModel(tableModel);
        }

        private JButton createStyledButton(String text, int r, int g, int b) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBackground(new Color(r, g, b));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            return button;
        }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppVueData::new);
    }
}
