package dao;

import classe.EtudiantBinome;
import classe.Formation;
import classe.Projet;
import exception.PlusDeDeuxEtudiants;
import classe.Etudiant;
import classe.Binome;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette class permet d'effectuer des opérations sur la table EtudiantBinome de la base de données Projet.
 */
public class EtudiantBinomeDAO {
    private Connection connection;
    
    /**
     * Le constructeur vérifie que la connexion avec la base de données se passe convenablement.
     */
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

                // Obtenez l'étudiant à l'aide de son ID
                int idEtudiant = resultSet.getInt("idEtudiant");
                EtudiantDAO etudiantDAO = new EtudiantDAO();
                Etudiant etudiant = etudiantDAO.getEtudiantById(idEtudiant);

                // Obtenez le binôme à l'aide de son ID
                int idBinome = resultSet.getInt("idBinome");
                BinomeDAO binomeDAO = new BinomeDAO();
                Binome binome = binomeDAO.getBinomeById(idBinome);

                etudiantBinome.setEtudiant(etudiant);
                etudiantBinome.setBinome(binome);
                etudiantBinome.setNoteSoutenance((Double) resultSet.getObject("noteSoutenance"));

                etudiantBinomes.add(etudiantBinome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return etudiantBinomes;
    }
    
    /**
     * Permet d'ajouter l'étudiant dont le numéro étudiant est etudiantBinome.getEtudiant() dans le binome dont l'identifiant est etudiantBinome.getBinome().getIdBinome() 
     */
    public void addEtudiantBinome(EtudiantBinome etudiantBinome)throws PlusDeDeuxEtudiants, SQLException {
        	/*On vérfie au préalable que le binome n'est pas complet sinon ajout impossible*/
        	int idBinome = etudiantBinome.getBinome().getIdBinome();
        	String verifQuery = "SELECT COUNT(*) AS nombre_etudiants FROM EtudiantBinome WHERE idBinome = ?";
        	PreparedStatement statementVerif = connection.prepareStatement(verifQuery);
        	statementVerif.setInt(1, idBinome);
        	ResultSet resultSet = statementVerif.executeQuery();
 
        	if (resultSet.next()) {
        	    int nombreEtudiants = resultSet.getInt("nombre_etudiants");
        	    if (nombreEtudiants >= 2) {
        	        throw new PlusDeDeuxEtudiants();
        	    }
        	} 
        	 
        	String query = "INSERT INTO EtudiantBinome (idEtudiant, idBinome, noteSoutenance) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, etudiantBinome.getEtudiant().getIdEtudiant());
            statement.setInt(2, etudiantBinome.getBinome().getIdBinome());
            statement.setObject(3, etudiantBinome.getNoteSoutenance());

            statement.executeUpdate();
       
    }
    
    
    public void updateEtudiantBinome(EtudiantBinome etudiantBinome) {
        try {
            String query = "UPDATE EtudiantBinome SET noteSoutenance = ? WHERE idEtudiant = ? AND idBinome = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, etudiantBinome.getNoteSoutenance());
            statement.setInt(2, etudiantBinome.getEtudiant().getIdEtudiant());
            statement.setInt(3, etudiantBinome.getBinome().getIdBinome());

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


    public EtudiantBinome getEtudiantBinomeById(int idEtudiant, int idBinome) {
        EtudiantBinome etudiantBinome = null;

        try {
            String query = "SELECT * FROM EtudiantBinome WHERE idEtudiant = ? AND idBinome = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idEtudiant);
            statement.setInt(2, idBinome);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                etudiantBinome = new EtudiantBinome();
                etudiantBinome.setEtudiant(getEtudiantById(idEtudiant));
                etudiantBinome.setBinome(getBinomeById(idBinome));
                etudiantBinome.setNoteSoutenance(resultSet.getDouble("noteSoutenance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return etudiantBinome;
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
    public Binome getBinomeById(int id) {
        Binome binome = null;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Binome WHERE idBinome = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                binome = new Binome();
                binome.setIdBinome(resultSet.getInt("idBinome"));
                
                // Obtenez le projet à l'aide de son ID
                int idProjet = resultSet.getInt("idProjet");
                ProjetDAO projetDAO = new ProjetDAO();
                Projet projet = projetDAO.getProjetById(idProjet);

                binome.setProjet(projet);
                binome.setNoteRapport(resultSet.getDouble("noteRapport"));
                binome.setBinomeReference(resultSet.getString("binomeReference"));
                binome.setDateRemiseEffective(resultSet.getDate("dateRemiseEffective").toLocalDate());

               
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return binome;
    }

}
