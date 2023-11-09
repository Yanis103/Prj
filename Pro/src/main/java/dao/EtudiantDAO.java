package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import classe.Etudiant;

public class EtudiantDAO {
    private Connection connection;

    public EtudiantDAO() {
        try {
            connection = Connexion.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Etudiant> getAllEtudiants() {
        List<Etudiant> etudiants = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Etudiant");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setIdEtudiant(resultSet.getInt("idEtudiant"));
                etudiant.setNom(resultSet.getString("nom"));
                etudiant.setPrenom(resultSet.getString("prenom"));
                etudiant.setIdFormation(resultSet.getInt("idFormation"));
                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return etudiants;
    }

    public Etudiant getEtudiantById(int id) {
        Etudiant etudiant = null;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Etudiant WHERE idEtudiant = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                etudiant = new Etudiant();
                etudiant.setIdEtudiant(resultSet.getInt("idEtudiant"));
                etudiant.setNom(resultSet.getString("nom"));
                etudiant.setPrenom(resultSet.getString("prenom"));
                etudiant.setIdFormation(resultSet.getInt("idFormation"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return etudiant;
    }

    public void addEtudiant(Etudiant etudiant) {
        try {
            String query = "INSERT INTO Etudiant (nom, prenom, idFormation) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, etudiant.getNom());
            statement.setString(2, etudiant.getPrenom());
            statement.setInt(3, etudiant.getIdFormation());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création de l'étudiant a échoué, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    etudiant.setIdEtudiant(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La création de l'étudiant a échoué, aucun ID retourné.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEtudiant(Etudiant etudiant) {
        try {
            String query = "UPDATE Etudiant SET nom = ?, prenom = ?, idFormation = ? WHERE idEtudiant = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, etudiant.getNom());
            statement.setString(2, etudiant.getPrenom());
            statement.setInt(3, etudiant.getIdFormation());
            statement.setInt(4, etudiant.getIdEtudiant());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEtudiant(int id) {
        try {
            String query = "DELETE FROM Etudiant WHERE idEtudiant = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
