package Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import classe.Formation;

public class FormationDAO {
    private Connection connection;

    public FormationDAO() {
        try {
            connection = Connexion.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Formation> getAllFormations() {
        List<Formation> formations = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Formation");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Formation formation = new Formation();
                formation.setIdFormation(resultSet.getInt("idFormation"));
                formation.setNomFormation(resultSet.getString("nomFormation"));
                formation.setPromotion(resultSet.getString("promotion"));
                formations.add(formation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return formations;
    }

    public Formation getFormationById(int id) {
        Formation formation = null;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Formation WHERE idFormation = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                formation = new Formation();
                formation.setIdFormation(resultSet.getInt("idFormation"));
                formation.setNomFormation(resultSet.getString("nomFormation"));
                formation.setPromotion(resultSet.getString("promotion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return formation;
    }

    public void addFormation(Formation formation) {
        try {
            String query = "INSERT INTO Formation (nomFormation, promotion) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, formation.getNomFormation());
            statement.setString(2, formation.getPromotion());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création de la formation a échoué, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    formation.setIdFormation(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La création de la formation a échoué, aucun ID retourné.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFormation(Formation formation) {
        try {
            String query = "UPDATE Formation SET nomFormation = ?, promotion = ? WHERE idFormation = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, formation.getNomFormation());
            statement.setString(2, formation.getPromotion());
            statement.setInt(3, formation.getIdFormation());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFormation(int id) {
        try {
            String query = "DELETE FROM Formation WHERE idFormation = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
