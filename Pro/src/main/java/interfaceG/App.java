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
    private JButton studentButton, formationButton, projectButton, binomeButton;
    private JLabel titleLabel;

    public App() {
        setTitle("Gestion des Projets Étudiants");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        // Utilisation d'une police plus élégante pour le titre
        titleLabel = new JLabel("Gestion des Projets Étudiants");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Utilisation de boutons stylisés avec des couleurs vives
        studentButton = createStyledButton("Étudiants","",52,152,219);
        formationButton = createStyledButton("Formations","/icon_etudiant.jpg",52,152,219);
        projectButton = createStyledButton("Projets","/icon_etudiant.jpg",52,152,219);
        binomeButton = createStyledButton("Binômes","/icon_etudiant.jpg",52,152,219);

        // Ajout des composants à la fenêtre
        add(titleLabel);
        add(studentButton);
        add(formationButton);
        add(projectButton);
        add(binomeButton);

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

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setForeground(Color.BLUE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (button.getText().equals("Étudiants")) {
                    /*new StudentApp().setVisible(true);*/
                } else if (button.getText().equals("Formations")) {
                    // Action pour afficher les formations
                } else if (button.getText().equals("Projets")) {
                    // Action pour afficher les projets
                } else if (button.getText().equals("Binômes")) {
                    // Action pour afficher les binômes
                }
            }
        });

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.RED);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.BLUE);
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}