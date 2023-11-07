package classe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StudentApp {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Etudiant> students;

    public StudentApp() {
        frame = new JFrame("Gestion des Étudiants");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        students = new ArrayList<>();

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nom");
        tableModel.addColumn("Prénom");
        tableModel.addColumn("ID Formation");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(e -> addStudent());

        JButton updateButton = new JButton("Mettre à jour");
        updateButton.addActionListener(e -> updateStudent());

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(e -> deleteStudent());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    private void addStudent() {
        String nom = JOptionPane.showInputDialog(frame, "Nom de l'étudiant:");
        String prenom = JOptionPane.showInputDialog(frame, "Prénom de l'étudiant:");
        int idFormation = Integer.parseInt(JOptionPane.showInputDialog(frame, "ID de Formation:"));

        Etudiant newStudent = new Etudiant(students.size() + 1, nom, prenom, idFormation);
        students.add(newStudent);
        refreshTable();
    }

    private void updateStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Sélectionnez un étudiant à mettre à jour.");
            return;
        }

        int idToUpdate = (int) table.getValueAt(selectedRow, 0);
        Etudiant studentToUpdate = getStudentById(idToUpdate);

        String nom = JOptionPane.showInputDialog(frame, "Nouveau nom de l'étudiant:", studentToUpdate.getNom());
        String prenom = JOptionPane.showInputDialog(frame, "Nouveau prénom de l'étudiant:", studentToUpdate.getPrenom());
        int idFormation = Integer.parseInt(JOptionPane.showInputDialog(frame, "Nouvel ID de Formation:", studentToUpdate.getIdFormation()));

        studentToUpdate.setNom(nom);
        studentToUpdate.setPrenom(prenom);
        studentToUpdate.setIdFormation(idFormation);

        refreshTable();
    }

    private void deleteStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Sélectionnez un étudiant à supprimer.");
            return;
        }

        int idToDelete = (int) table.getValueAt(selectedRow, 0);
        students.removeIf(student -> student.getId() == idToDelete);
        refreshTable();
    }

    private Etudiant getStudentById(int id) {
        for (Etudiant student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Etudiant student : students) {
            Object[] row = {student.getId(), student.getNom(), student.getPrenom(), student.getIdFormation()};
            tableModel.addRow(row);
        }
    }
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
    public boolean IsVisible() {
        return frame.isVisible();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentApp::new);
    }
}
