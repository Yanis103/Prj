package dao;
import classe.Etudiant;
import classe.Formation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
 
/**
 * Cette class permet d'effectuer des opérations sur la table Etudiant de la base de données Projet.
 */
public class EtudiantDAO {
    private Connection connection;

    /**
     * Le constructeur vérifie que la connexion avec la base de données se passe convenablement.
     */
    public EtudiantDAO() {
        try {
            this.connection = Connexion.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Cette fonction permet de lister les étudiants présents dans la table Etudiant
     * @return les étudiants présents dans la base de données sous forme d'ArrayList<Etudiant>
     */
    public List<Etudiant> getAllEtudiants() {
    	/*On crée une liste vide d'étudiant*/
        List<Etudiant> etudiants = new ArrayList<>();
        try {
        	/*On parcourt tous les étudiants un à un tout en récupérant le contenu des attributs*/
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Etudiant");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setIdEtudiant(resultSet.getInt("idEtudiant"));
                etudiant.setNom(resultSet.getString("nom"));
                etudiant.setPrenom(resultSet.getString("prenom"));

                /*On récupère l'identifiant de la formation */
                int idFormation = resultSet.getInt("idFormation");
                FormationDAO formationDAO = new FormationDAO();
                /*On récupère la formation dont l'identifiant est idFormation*/
                Formation formation = formationDAO.getFormationById(idFormation);
                etudiant.setFormation(formation);
                etudiants.add(etudiant);
                formationDAO.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return etudiants;
    }

    /**
     * Cette fonction permet de renvoyer l'étudiant qui possède le numéro étudiant id
     * @return l'étudiant dont le numéro étudiant est id 
     */
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

                // Obtenez la formation à l'aide de son ID
                int idFormation = resultSet.getInt("idFormation");
                FormationDAO formationDAO = new FormationDAO();
                Formation formation = formationDAO.getFormationById(idFormation);
                etudiant.setFormation(formation);
                formationDAO.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return etudiant;
    }

    /**
     * Cette fonction permet d'ajouter l'Etudiant etudiant à la table Etudiant
     */
    public void addEtudiant(Etudiant etudiant) {
        try {
            String query = "INSERT INTO Etudiant (nom, prenom, idFormation) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, etudiant.getNom());
            statement.setString(2, etudiant.getPrenom());
            statement.setInt(3, etudiant.getFormation().getIdFormation());
            /*Cette instruction retourne le nombre de lignes affectées par la requête*/
            int affectedRows = statement.executeUpdate();
            /*Si aucune ligne générée cela veut dire que l'ajout à échouer : 
             * 		-Car etudiant possède un idEtudiant égal à celui d'un autre étudiant présent dans la base.
             * 		-Car l'identifiant de la formation ne correspond à aucune Formation existante. 
             * 
             */
            if (affectedRows == 0) {
                throw new SQLException("L'ajout de l'étudiant a échoué, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    etudiant.setIdEtudiant(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("L'ajout de l'étudiant a échoué, aucun ID retourné.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cette fonction permet de mettre à jour un Etudiant
     */
    public void updateEtudiant(Etudiant etudiant) {
        try {
            String query = "UPDATE Etudiant SET nom = ?, prenom = ?, idFormation = ? WHERE idEtudiant = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, etudiant.getNom());
            statement.setString(2, etudiant.getPrenom());
            statement.setInt(3, etudiant.getFormation().getIdFormation());
            statement.setInt(4, etudiant.getIdEtudiant());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     *Cette méthode permet de supprimer de la base l'Etudiant dont le numéro étudiant est id
     */
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
