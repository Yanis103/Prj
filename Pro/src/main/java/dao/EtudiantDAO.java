package dao;

import classe.Connexion;
import classe.Etudiant;
import classe.Formation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import exception.EtudiantInexistant;

public class EtudiantDAO {
    private Connection connection;

    // Constructeur
    public EtudiantDAO() {
        try {
            this.connection = Connexion.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean existeEtudiant(int id) {
    	List<Etudiant> etudiants = this.getAllEtudiants();
    	for(Etudiant e : etudiants) {
    		if(e.getIdEtudiant() == id) {
    			return true;
    		}
    	}
    	return false ;
    }
    
    // Méthode pour obtenir tous les étudiants
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

                // Obtenez la formation à l'aide de son ID
                int idFormation = resultSet.getInt("idFormation");
                FormationDAO formationDAO = new FormationDAO();
                Formation formation = formationDAO.getFormationById(idFormation);
                etudiant.setFormation(formation);

                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return etudiants;
    }

    // Méthode pour obtenir un étudiant par son ID
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return etudiant;
    }
    
    public List<Etudiant> getEtudiantByNom(String nom) {
    	List<Etudiant> etudiants = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Etudiant WHERE nom = ?");
            statement.setString(1, nom);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setIdEtudiant(resultSet.getInt("idEtudiant"));
                etudiant.setNom(resultSet.getString("nom"));
                etudiant.setPrenom(resultSet.getString("prenom"));

                // Obtenez la formation à l'aide de son ID
                int idFormation = resultSet.getInt("idFormation");
                FormationDAO formationDAO = new FormationDAO();
                Formation formation = formationDAO.getFormationById(idFormation);
                etudiant.setFormation(formation);
                
                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return etudiants;
    }
    
    public List<Etudiant> getEtudiantByPrenom(String prenom) {
    	List<Etudiant> etudiants = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Etudiant WHERE prenom = ?");
            statement.setString(1, prenom);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setIdEtudiant(resultSet.getInt("idEtudiant"));
                etudiant.setNom(resultSet.getString("nom"));
                etudiant.setPrenom(resultSet.getString("prenom"));

                // Obtenez la formation à l'aide de son ID
                int idFormation = resultSet.getInt("idFormation");
                FormationDAO formationDAO = new FormationDAO();
                Formation formation = formationDAO.getFormationById(idFormation);
                etudiant.setFormation(formation);
                
                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return etudiants;
    }
    
    public List<Etudiant> getEtudiantByIdFormation(int id) {
    	List<Etudiant> etudiants = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Etudiant WHERE idFormation = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setIdEtudiant(resultSet.getInt("idEtudiant"));
                etudiant.setNom(resultSet.getString("nom"));
                etudiant.setPrenom(resultSet.getString("prenom"));

                // Obtenez la formation à l'aide de son ID
                int idFormation = resultSet.getInt("idFormation");
                FormationDAO formationDAO = new FormationDAO();
                Formation formation = formationDAO.getFormationById(idFormation);
                etudiant.setFormation(formation);
                
                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return etudiants;
    }
    
    /*Modification de la table */
    // Méthode pour ajouter un nouvel étudiant
    public void addEtudiant(Etudiant etudiant) {
        try {
            String query = "INSERT INTO Etudiant (nom, prenom, idFormation) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, etudiant.getNom());
            statement.setString(2, etudiant.getPrenom());
            statement.setInt(3, etudiant.getFormation().getIdFormation());
            /*On vérifie que l'insertion s'est bien passée*/
            int affectedRows = statement.executeUpdate();

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

    // Méthode pour mettre à jour les informations d'un étudiant
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

    // Méthode pour supprimer un étudiant par son ID
    public void deleteEtudiant(int id) {
        try {
        	if(! this.existeEtudiant(id)) {
        		throw new EtudiantInexistant(id);
        	}
            String query = "DELETE FROM Etudiant WHERE idEtudiant = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            
            statement.executeUpdate();
        } catch (EtudiantInexistant e) {
            e.getMessage();
        } catch (SQLException se) {
        	se.printStackTrace();
        }
    }

    

}
