package dao;
import classe.Admin;
import classe.Etudiant;
import classe.Formation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class AdminDAO {
	private Connection connection;

    /**
     * Le constructeur vérifie que la connexion avec la base de données se passe convenablement.
     */
    public AdminDAO() {
        try {
            this.connection = Connexion.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Admin> getAllAdmins() {
    	/*On crée une liste vide d'étudiant*/
        List<Admin> admins = new ArrayList<>();
        try {
        	/*On parcourt tous les étudiants un à un tout en récupérant le contenu des attributs*/
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Admin");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Admin admin = new Admin();
                admin.setIdentifiant(resultSet.getString("id"));
                admin.setMotDePasse(resultSet.getString("motDePasse"));
                admins.add(admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return admins;
    }
    
    public boolean adminExistant(String id, String motDePasse) {
    	List<Admin> admins = this.getAllAdmins();
    	for(Admin a : admins) {
    		if(a.getIdentifiant().compareTo(id)==0 && a.getMotDePasse().compareTo(motDePasse)==0) {
    			return true;
    		}
    	}
    	return false;
    }
}

 
