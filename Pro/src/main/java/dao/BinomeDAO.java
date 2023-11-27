package dao;

import classe.Binome;
import classe.Projet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette class permet d'effectuer des opérations sur la table Binome de la base de données Projet.
 */
public class BinomeDAO {
    private Connection connection;
    
    /**
     * Le constructeur vérifie que la connexion avec la base de données se passe convenablement.
     */
    public BinomeDAO() {
        try {
            connection = Connexion.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     
    /**
     * Cette fonction permet de lister les binomes présents dans la table Binome
     * @return les binomes présents dans la base de données sous forme d'ArrayList<Binome>
     */
    public List<Binome> getAllBinomes() {
        List<Binome> binomes = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Binome");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Binome binome = new Binome();
                binome.setIdBinome(resultSet.getInt("idBinome"));
             // Obtenez le projet à l'aide de son ID
                int idProjet = resultSet.getInt("idProjet");
                ProjetDAO projetDAO = new ProjetDAO();
                Projet projet = projetDAO.getProjetById(idProjet);

                binome.setProjet(projet);
                binome.setNoteRapport((Double) resultSet.getObject("noteRapport"));
                binome.setBinomeReference(resultSet.getString("binomeReference"));
                binome.setDateRemiseEffective(resultSet.getDate("dateRemiseEffective").toLocalDate());

                

                binomes.add(binome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return binomes;
    }
    
    /**
     * Cette fonction permet de renvoyer le binome qui possède l'identifiant id
     * @return le binome dont l'identifiant est id 
     */
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
                binome.setNoteRapport((Double) resultSet.getObject("noteRapport"));
                binome.setBinomeReference(resultSet.getString("binomeReference"));
                binome.setDateRemiseEffective(resultSet.getDate("dateRemiseEffective").toLocalDate());

               
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return binome;
    }
    
    /**
     * Cette fonction permet d'ajouter le Binome binome à la table Binome.
     */
    public void addBinome(Binome binome) {
        try {
            String query = "INSERT INTO Binome (idProjet,noteRapport , binomeReference, dateRemiseEffective) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, binome.getProjet().getIdProjet());
            statement.setObject(2, binome.getNoteRapport());
            statement.setString(3, binome.getBinomeReference());
            statement.setDate(4, java.sql.Date.valueOf(binome.getDateRemiseEffective()));

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création du binôme a échoué, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    binome.setIdBinome(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La création du binôme a échoué, aucun ID retourné.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Cette fonction permet de mettre à jour les informations du Binome binome sauf la note de soutenance.
     */
    public void updateBinome(Binome binome) {
        try {
            String query = "UPDATE Binome SET idProjet = ?, binomeReference = ?, dateRemiseEffective = ? WHERE idBinome = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, binome.getProjet().getIdProjet());

            statement.setString(2, binome.getBinomeReference());
            statement.setDate(3, java.sql.Date.valueOf(binome.getDateRemiseEffective()));
            statement.setInt(4, binome.getIdBinome());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Cette méthode permet de mettre à jour la note de soutenance du Binome binome.
     */
    public void updateBinome2(Binome binome) {
        try {
            String query = "UPDATE Binome SET noteRapport = ? WHERE idBinome = ?";
            PreparedStatement statement = connection.prepareStatement(query);         
            statement.setDouble(1, binome.getNoteRapport());
            statement.setInt(2, binome.getIdBinome());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     *Cette méthode permet de supprimer de la base le Binome dont l'identifiant est id
     */
    public void deleteBinome(int id) {
        try {
            String query = "DELETE FROM Binome WHERE idBinome = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
