package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import classe.Projet;

/**
 * Cette class permet d'effectuer des opérations sur la table Projet de la base de données Projet.
 */
public class ProjetDAO {
    private Connection connection;
    
    /**
     * Le constructeur vérifie que la connexion avec la base de données se passe convenablement.
     */
    public ProjetDAO() {
        try {
            connection = Connexion.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cette fonction permet de lister les projets présents dans la table Projet
     * @return les projets présents dans la base de données sous forme d'ArrayList<Projet>
     */
    public List<Projet> getAllProjets() {
        List<Projet> projets = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Projet");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Projet projet = new Projet();
                projet.setIdProjet(resultSet.getInt("idProjet"));
                projet.setNomMatiere(resultSet.getString("nomMatiere"));
                projet.setSujet(resultSet.getString("sujet"));
                projet.setDateRemisePrevue(resultSet.getDate("dateRemisePrevue").toLocalDate());
                projets.add(projet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projets;
    }
    
    /**
     * Cette fonction permet de renvoyer le projet qui possède l'identifiant id
     * @return le projet dont l'identifiant est id 
     */
    public Projet getProjetById(int id) {
        Projet projet = null;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Projet WHERE idProjet = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                projet = new Projet();
                projet.setIdProjet(resultSet.getInt("idProjet"));
                projet.setNomMatiere(resultSet.getString("nomMatiere"));
                projet.setSujet(resultSet.getString("sujet"));
                projet.setDateRemisePrevue(resultSet.getDate("dateRemisePrevue").toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projet;
    }
    
    /**
     * Cette fonction permet d'ajouter le Projet projet à la table Projet.
     */
    public void addProjet(Projet projet) {
        try {
            String query = "INSERT INTO Projet (nomMatiere, sujet, dateRemisePrevue) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, projet.getNomMatiere());
            statement.setString(2, projet.getSujet());
            statement.setDate(3, java.sql.Date.valueOf(projet.getDateRemisePrevue()));

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création du projet a échoué, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    projet.setIdProjet(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La création du projet a échoué, aucun ID retourné.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Cette fonction permet de mettre à jour les informations du Projet projet.
     */
    public void updateProjet(Projet projet) {
        try {
            String query = "UPDATE Projet SET nomMatiere = ?, sujet = ?, dateRemisePrevue = ? WHERE idProjet = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, projet.getNomMatiere());
            statement.setString(2, projet.getSujet());
            statement.setDate(3, java.sql.Date.valueOf(projet.getDateRemisePrevue()));
            statement.setInt(4, projet.getIdProjet());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     *Cette méthode permet de supprimer de la base le Projet dont l'identifiant est id
     */
    public void deleteProjet(int id) {
        try {
            String query = "DELETE FROM Projet WHERE idProjet = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void closeConnection() {
      	 if (this.connection != null) {
      	        try {
      	            connection.close();
      	        } catch (SQLException e) {
      	            e.printStackTrace();
      	        }
      	 }
    }
}
