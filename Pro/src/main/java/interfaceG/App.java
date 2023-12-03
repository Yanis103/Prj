package interfaceG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {
    private JButton studentButton, formationButton, projectButton, binomeButton, notationButton;
    private JLabel titleLabel;

    // Constructeur de la classe App
    public App() {
        // Configuration de la fenêtre principale
        setTitle("Gestion des Projets Étudiants");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximise la fenêtre
        setUndecorated(true); // Supprime la décoration de la fenêtre
        setLocationRelativeTo(null);

        // Utilisation d'un JPanel pour regrouper le bouton "Déconnexion" et le titre
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Bouton Déconnexion
        JButton logoutButton = createStyledButton("Déconnexion", "", 52, 152, 219);
        topPanel.add(logoutButton, BorderLayout.EAST);

        // Utilisation d'une police plus élégante pour le titre
        titleLabel = new JLabel("Gestion des Projets Étudiants                     ");
        titleLabel.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 40));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(52, 152, 219));

        // Ajout du JPanel en haut de la fenêtre
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/dauphin2.jpg"));
        Image image = imageIcon.getImage().getScaledInstance(400, 100, Image.SCALE_SMOOTH);
        ImageIcon resizedTopImage = new ImageIcon(image);
        JLabel imageLabel = new JLabel(resizedTopImage);
        topPanel.add(imageLabel, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Création d'un panneau pour les boutons
        JPanel buttonsPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        buttonsPanel.setBackground(Color.WHITE);

        // Utilisation de boutons stylisés avec des couleurs vives
        studentButton = createStyledButton("Étudiants", "", 52, 152, 219);
        formationButton = createStyledButton("Formations", "", 52, 152, 219);
        projectButton = createStyledButton("Projets", "", 52, 152, 219);
        binomeButton = createStyledButton("Gestion des binômes", "", 52, 152, 219);
        notationButton = createStyledButton("Notation", "", 52, 152, 219);
        JButton boutonVisualisation = createStyledButton("Visualisation des notes", "", 52, 152, 219);

        // Ajout des composants au panneau des boutons
        buttonsPanel.add(studentButton);
        buttonsPanel.add(formationButton);
        buttonsPanel.add(projectButton);
        buttonsPanel.add(binomeButton);
        buttonsPanel.add(notationButton);
        buttonsPanel.add(boutonVisualisation);

        // Ajout du panneau des boutons au centre de la fenêtre
        add(buttonsPanel, BorderLayout.CENTER);

        // Action pour le bouton Déconnexion
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Identification.main(null);
                setVisible(false);
            }
        });

        // Autres actions pour les boutons
        studentButton.addActionListener(e -> {
            AppEtudiant.main(null);
        });

        formationButton.addActionListener(e -> {
            AppFormation.main(null);
        });

        projectButton.addActionListener(e -> {
            AppProjet.main(null);
        });

        binomeButton.addActionListener(e -> {
            AppBinome.main(null);
        });

        notationButton.addActionListener(e -> {
            AppEtudiantBinome.main(null);
        });

        boutonVisualisation.addActionListener(e -> {
            AppVueData.main(null);
        });

        setVisible(true);
    }

    // Méthode pour créer des boutons stylisés
    private JButton createStyledButton(String text, String iconPath, int r, int g, int b) {
        JButton button = new JButton(text);
        ImageIcon icon = new ImageIcon(iconPath);
        JLabel label = new JLabel(icon, JLabel.LEFT);
        button.add(label);

        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setBackground(new Color(r, g, b));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        return button;
    }

    // Point d'entrée de l'application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}
