package interfaceG;
import javax.swing.*;

import classe.Binome;
import classe.Projet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
 
public class App extends JFrame {
    private JButton studentButton, formationButton, projectButton, binomeButton,notationButton;
    private JLabel titleLabel;

    public App() {
        setTitle("Gestion des Projets Étudiants");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 1));
        getContentPane().setBackground(Color.WHITE);
        // Utilisation d'une police plus élégante pour le titre
        titleLabel = new JLabel("Gestion des Projets Étudiants");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(52, 152, 219));
 
        // Utilisation de boutons stylisés avec des couleurs vives
        studentButton = createStyledButton("Étudiants"," ",52,152,219);
        formationButton = createStyledButton("Formations"," ",52,152,219);
        projectButton = createStyledButton("Projets"," ",52,152,219);
        binomeButton = createStyledButton("Gestion des binomes"," ",52,152,219);
        notationButton = createStyledButton("Notation"," ",52,152,219);
        JButton boutonVisualisation = createStyledButton("Visualisation des notes"," ",52,152,219);
         
        // Ajout des composants à la fenêtre
        add(titleLabel);
        add(studentButton);
        add(formationButton);
        add(projectButton);
        add(binomeButton);
        add(notationButton);
        add(boutonVisualisation);

        /* Action pour le bouton Étudiants */
        studentButton.addActionListener(e -> {
            AppEtudiant.main(null);
            dispose();
        });

        /* Action pour le bouton Formations */
        formationButton.addActionListener(e -> {
            AppFormation.main(null);
            dispose();
        });

        /* Action pour le bouton Projets */
        projectButton.addActionListener(e -> {
            AppProjet.main(null);
            dispose();
        });

        /* Action pour le bouton Binômes */
        binomeButton.addActionListener(e -> {
            AppBinome.main(null);
            dispose();
        });
        
        notationButton.addActionListener(e -> {
            AppEtudiantBinome.main(null);
            dispose();
        });
        
        /* Action pour le bouton Visualisation des notes */
        boutonVisualisation.addActionListener(e -> {
            AppVueData.main(null);
            dispose();
        });

        setVisible(true);
    }

    // Méthode pour créer des boutons stylisés
    private JButton createStyledButton(String text, String iconPath, int r, int g, int b) {
        JButton button = new JButton(text);
        ImageIcon icon = new ImageIcon(iconPath);
        JLabel label = new JLabel(icon, JLabel.LEFT);
        // Définir le label comme composant du bouton
        button.add(label);
        
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(new Color(r,g,b)); // Couleur de fond
        button.setForeground(Color.WHITE); // Couleur du texte
        button.setFocusPainted(false); // Désactiver la mise en évidence du focus
        
        return button;
    }    


    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}