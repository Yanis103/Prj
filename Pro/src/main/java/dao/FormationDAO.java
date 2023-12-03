package dao;
import java.sql.*;
import java.util.*;

import classe.Formation;

/**
 * Cette class permet d'effectuer des opérations sur la table Formation de la base de données Projet.
 */
public class FormationDAO {
    private Connection connection;
    
    /**
     * Le constructeur vérifie que la connexion avec la base de données se passe convenablement.
     */
    public FormationDAO() {
        try {
            connection = Connexion.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Cette fonction permet de lister les formations présentes dans la table Formation
     * @return les formations présentes dans la base de données sous forme d'ArrayList<Formation>
     */
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

    /**
     * Cette fonction permet de renvoyer la formation qui possède l'identifiant id
     * @return la formation dont l'identifiant est id 
     */
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
    
    /**
     * @return une table de hashahe dont la clé est l'identifiant de la formation et dont la valeur associée est la nom de la formation.
     */
    public Map<Integer, String> getFormationNamesAndIDs() {
        Map<Integer, String> formationMap = new HashMap<>();
        String query = "SELECT idFormation, nomFormation FROM Formation";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int idFormation = resultSet.getInt("idFormation");
                String nomFormation = resultSet.getString("nomFormation");
                formationMap.put(idFormation, nomFormation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return formationMap;
    }

    
    /**
     * Cette fonction permet d'ajouter la Formation formation à la table Formation.
     */
    public void addFormation(Formation formation) {
        try {
            String query = "INSERT INTO Formation (nomFormation, promotion) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, formation.getNomFormation());
            statement.setString(2, formation.getPromotion());
            /*Cette instruction retourne le nombre de lignes affectées par la requête*/
            int affectedRows = statement.executeUpdate();
            /*Si aucune ligne générée cela veut dire que l'ajout à échouer : 
             * 		-Car formation possède un idFormation égal à celui d'une autre Formation présente dans la base. 
             */
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
    
    /**
     * Cette fonction permet de mettre à jour une Formation
     */
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
    
    /**
     *Cette méthode permet de supprimer de la base la Formation dont l'identifiant est id
     */
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
    
    public void closeConnection() {
    	if (this.connection != null) {
   	        try {
   	            this.connection.close();
   	        } catch (SQLException e) {
   	            e.printStackTrace();
   	        }
   	 }
   }

}
