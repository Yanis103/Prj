package interfaceG;

import javax.swing.*;
import dao.AdminDAO;
import classe.Admin;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Identification extends JFrame {
    private JLabel titleLabel, idLabel, passwordLabel, topImageLabel;
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton loginButton;
    
    
    public Identification() {
    	// Définit le titre de la fenêtre
    	setTitle("Identification");
    	// Indique comment la fenêtre doit réagir lorsque l'utilisateur ferme la fenêtre
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	// Maximise la fenêtre pour occuper tout l'écran
    	setExtendedState(JFrame.MAXIMIZED_BOTH);
    	// Permet le redimensionnement de la fenêtre par l'utilisateur
    	setResizable(true);
    	
    	// Charge une image depuis le fichier. Assurez-vous d'ajuster le chemin selon votre fichier.
    	ImageIcon topImage = new ImageIcon(getClass().getResource("/dauphine-1.jpg"));
    	// Redimensionne l'image selon les besoins (par exemple, 800x200 pixels) en utilisant un algorithme de lissage
    	Image image = topImage.getImage().getScaledInstance(990, 133, Image.SCALE_SMOOTH);
    	ImageIcon resizedTopImage = new ImageIcon(image);
    	// Utilise un JPanel pour la disposition des composants dans la fenêtre
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridBagLayout());
    	panel.setBackground(Color.WHITE);
    	// Configuration des contraintes de disposition des composants
    	GridBagConstraints gbc = new GridBagConstraints();
    	gbc.insets = new Insets(10, 10, 10, 10); // Marges entre les composants
    	gbc.fill = GridBagConstraints.HORIZONTAL; // Les composants s'étendent horizontalement
    	// Ajoute l'image redimensionnée en haut du panel
    	topImageLabel = new JLabel(resizedTopImage);
    	gbc.gridx = 0;
    	gbc.gridy = -1;
    	gbc.gridwidth = 2; // L'image occupe deux colonnes dans la grille
    	panel.add(topImageLabel, gbc);

        // Utilisation d'une police plus élégante pour le titre
        titleLabel = new JLabel("Identification");
        titleLabel.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 40));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(52, 152, 219));
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        idLabel = new JLabel("Identifiant");
        idLabel.setFont(new Font("Verdana", Font.BOLD,17));
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(idLabel, gbc);

        idField = new JTextField();
        Dimension preferredSize = new Dimension(200, 30);
        idField.setPreferredSize(preferredSize);
        gbc.gridx = 1;
        panel.add(idField, gbc);


        passwordLabel = new JLabel("Mot de passe");
        passwordLabel.setFont(new Font("Verdana", Font.BOLD,17));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(preferredSize);
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
                    adminDAO.closeConnection();
                	App.main(null);
                    setVisible(false);
                } else {
                	adminDAO.closeConnection();
                    JOptionPane.showMessageDialog(null, "Identifiant ou mot de passe incorrect");
                }
            }
        });
        
        JButton createAccountButton = createStyledButton("Créer compte", "", 46, 204, 113);
        gbc.gridy = 5; // Vous pouvez ajuster la position du bouton en fonction de votre mise en page
        panel.add(createAccountButton, gbc);

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Afficher une boîte de dialogue pour la création de compte
                String newId = JOptionPane.showInputDialog(null, "Nouvel identifiant :");

                // Vérifier si l'utilisateur a appuyé sur "Annuler" ou a fermé la boîte de dialogue
                if (newId == null) {
                    return; // L'utilisateur a annulé la création de compte
                }

                // Afficher une boîte de dialogue pour la saisie du nouveau mot de passe de manière sécurisée
                JPasswordField passwordField = new JPasswordField();
                int option = JOptionPane.showOptionDialog(
                    null,
                    passwordField,
                    "Nouveau mot de passe",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null
                );

                // Vérifier si l'utilisateur a appuyé sur "OK"
                if (option == JOptionPane.OK_OPTION) {
                    // Récupérer le mot de passe saisi de manière sécurisée
                    char[] passwordChars = passwordField.getPassword();
                    String newPassword = new String(passwordChars);

                    // Ajouter la logique pour enregistrer le nouvel administrateur dans la base de données
                    AdminDAO adminDAO = new AdminDAO();
                    Admin newAdmin = new Admin();
                    newAdmin.setIdentifiant(newId);
                    newAdmin.setMotDePasse(newPassword);

                    // Ajouter le nouvel administrateur
                    try {
                        adminDAO.addAdmin(newAdmin);
                        JOptionPane.showMessageDialog(null, "Compte administrateur créé avec succès !");
                        adminDAO.closeConnection();
                    } catch (SQLException e1) {
                    	adminDAO.closeConnection();
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Erreur lors de la création du compte administrateur.");
                    }
                } else {
                    // L'utilisateur a appuyé sur "Annuler" ou a fermé la boîte de dialogue
                }
            }
        });

        // Ajout du JPanel à la fenêtre
        add(panel);
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
