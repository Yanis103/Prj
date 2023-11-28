package interfaceG;

import javax.swing.*;
import dao.AdminDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Identification extends JFrame {
    private JLabel titleLabel, idLabel, passwordLabel, topImageLabel;
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public Identification() {
        setTitle("Identification");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true); // Permet le redimensionnement de la fenêtre
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        // Chargement de l'image depuis le fichier (ajustez le chemin selon votre fichier)
        ImageIcon topImage = new ImageIcon(getClass().getResource("/dauphine-psl.png"));

        // Utilisation d'un JPanel pour la disposition des composants
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Ajout de l'image en haut
        topImageLabel = new JLabel(topImage);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(topImageLabel, gbc);

        // Utilisation d'une police plus élégante pour le titre
        titleLabel = new JLabel("Identification");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(52, 152, 219));
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        idLabel = new JLabel("Identifiant:");
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(idLabel, gbc);

        idField = new JTextField();
        gbc.gridx = 1;
        panel.add(idField, gbc);

        passwordLabel = new JLabel("Mot de passe:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        loginButton = createStyledButton("Connexion", "", 52, 152, 219);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminDAO adminDAO = new AdminDAO();
                if (adminDAO.adminExistant(idField.getText(), new String(passwordField.getPassword()))) {
                    dispose();
                    App.main(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Identifiant ou mot de passe incorrect");
                }
            }
        });

        // Ajout du JPanel à la fenêtre
        add(panel);
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Méthode pour créer des boutons stylisés
    private JButton createStyledButton(String text, String iconPath, int r, int g, int b) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(new Color(r, g, b));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Identification());
    }
}
