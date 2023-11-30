package interfaceG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {
    private JButton studentButton, formationButton, projectButton, binomeButton, notationButton;
    private JLabel titleLabel;

    public App() {
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
        titleLabel = new JLabel("Gestion des Projets Étudiants");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(52, 152, 219));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // Ajout du JPanel en haut de la fenêtre
        add(topPanel, BorderLayout.NORTH);

        setLayout(new GridLayout(7, 1, 10, 10)); // Ajout de marges entre les composants
        getContentPane().setBackground(Color.WHITE);

        // Utilisation de boutons stylisés avec des couleurs vives
        studentButton = createStyledButton("Étudiants", "", 52, 152, 219);
        formationButton = createStyledButton("Formations", "", 52, 152, 219);
        projectButton = createStyledButton("Projets", "", 52, 152, 219);
        binomeButton = createStyledButton("Gestion des binômes", "", 52, 152, 219);
        notationButton = createStyledButton("Notation", "", 52, 152, 219);
        JButton boutonVisualisation = createStyledButton("Visualisation des notes", "", 52, 152, 219);

        // Ajout des composants à la fenêtre
        add(studentButton);
        add(formationButton);
        add(projectButton);
        add(binomeButton);
        add(notationButton);
        add(boutonVisualisation);

        /* Action pour le bouton Déconnexion */
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Identification.main(null);
                dispose();
            }
        });

        /* Autres actions pour les boutons */
        studentButton.addActionListener(e -> {
            AppEtudiant.main(null);
            setVisible(false);
        });

        formationButton.addActionListener(e -> {
            AppFormation.main(null);
            setVisible(false);
        });

        projectButton.addActionListener(e -> {
            AppProjet.main(null);
            setVisible(false);
        });

        binomeButton.addActionListener(e -> {
            AppBinome.main(null);
            setVisible(false);
        });

        notationButton.addActionListener(e -> {
            AppEtudiantBinome.main(null);
            setVisible(false);
        });

        boutonVisualisation.addActionListener(e -> {
            AppVueData.main(null);
            setVisible(false);
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
        button.setBackground(new Color(r, g, b)); // Couleur de fond
        button.setForeground(Color.WHITE); // Couleur du texte
        button.setFocusPainted(false); // Désactiver la mise en évidence du focus

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}
