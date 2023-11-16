package gestionnaire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionProjetsApp extends JFrame {
    private JButton studentButton, formationButton, projectButton, binomeButton;
    private JLabel titleLabel;

    public GestionProjetsApp() {
        setTitle("Gestion des Projets Étudiants");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        titleLabel = new JLabel("Gestion des Projets Étudiants");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        studentButton = createButton("Étudiants");
        formationButton = createButton("Formations");
        projectButton = createButton("Projets");
        binomeButton = createButton("Binômes");

        add(titleLabel);
        add(studentButton);
        add(formationButton);
        add(projectButton);
        add(binomeButton);

        setVisible(true);
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
                    new StudentApp().setVisible(true);
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
        SwingUtilities.invokeLater(GestionProjetsApp::new);
    }
}
