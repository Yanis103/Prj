package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import classe.EtudiantBinome;

public class EtudiantBinomeDAO {
    private Connection connection;

    public EtudiantBinomeDAO() {
        try {
            connection = Connexion.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<EtudiantBinome> getAllEtudiantBinomes() {
        List<EtudiantBinome> etudiantBinomes = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM EtudiantBinome");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                EtudiantBinome etudiantBinome = new EtudiantBinome();
                etudiantBinome.setIdEtudiant(resultSet.getInt("idEtudiant"));
                etudiantBinome.setIdBinome(resultSet.getInt("idBinome"));
                etudiantBinomes.add(etudiantBinome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return etudiantBinomes;
    }

    public void addEtudiantBinome(EtudiantBinome etudiantBinome) {
        try {
            String query = "INSERT INTO EtudiantBinome (idEtudiant, idBinome) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, etudiantBinome.getIdEtudiant());
            statement.setInt(2, etudiantBinome.getIdBinome());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEtudiantBinome(int idEtudiant, int idBinome) {
        try {
            String query = "DELETE FROM EtudiantBinome WHERE idEtudiant = ? AND idBinome = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idEtudiant);
            statement.setInt(2, idBinome);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
