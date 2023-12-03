package dao;

import classe.VueData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VueDataDAO {
    private Connection connection;

    public VueDataDAO() {
        try {
            this.connection = Connexion.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<VueData> getAllVueData() {
        List<VueData> vueDataList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM dataVue;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                VueData vueData = new VueData();
                vueData.setEtudiantNom(resultSet.getString("etudiantNom"));
                vueData.setEtudiantPrenom(resultSet.getString("etudiantPrenom"));
                vueData.setNomFormation(resultSet.getString("nomFormation"));
                vueData.setPromotion(resultSet.getString("promotion"));
                vueData.setNomMatiere(resultSet.getString("nomMatiere"));
                vueData.setSujet(resultSet.getString("sujet"));
                vueData.setNoteRapport(resultSet.getDouble("noteRapport"));
                vueData.setNoteSoutenance(resultSet.getDouble("noteSoutenance"));
                vueData.setBinomeReference(resultSet.getString("binomeReference"));
                vueData.setDateRemiseEffective(resultSet.getDate("dateRemiseEffective").toLocalDate());
                vueData.setDateRemisePrevue(resultSet.getDate("dateRemisePrevue").toLocalDate());

                // Calcul de la note finale
                double noteFinale = vueData.calculerNoteFinale(
                        vueData.getNoteRapport(),
                        vueData.getNoteSoutenance(),
                        vueData.getDateRemisePrevue(),
                        vueData.getDateRemiseEffective()
                );
                vueData.setNoteFinale(noteFinale);

                vueDataList.add(vueData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vueDataList;
    }

	public List<VueData> getAllVueDataFormation(String nomFormation) {
	    List<VueData> vueDataList = new ArrayList<>();
	
	    try {
	        String sql = "SELECT * FROM dataVue WHERE nomFormation = ?;";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, nomFormation);
	        ResultSet resultSet = statement.executeQuery();
	
	        while (resultSet.next()) {
	            VueData vueData = new VueData();
	            vueData.setEtudiantNom(resultSet.getString("etudiantNom"));
	            vueData.setEtudiantPrenom(resultSet.getString("etudiantPrenom"));
	            vueData.setNomFormation(resultSet.getString("nomFormation"));
	            vueData.setPromotion(resultSet.getString("promotion"));
	            vueData.setNomMatiere(resultSet.getString("nomMatiere"));
	            vueData.setSujet(resultSet.getString("sujet"));
	            vueData.setNoteRapport(resultSet.getDouble("noteRapport"));
	            vueData.setNoteSoutenance(resultSet.getDouble("noteSoutenance"));
	            vueData.setBinomeReference(resultSet.getString("binomeReference"));
	            vueData.setDateRemiseEffective(resultSet.getDate("dateRemiseEffective").toLocalDate());
	            vueData.setDateRemisePrevue(resultSet.getDate("dateRemisePrevue").toLocalDate());
	
	            // Calcul de la note finale
	            double noteFinale = vueData.calculerNoteFinale(
	                    vueData.getNoteRapport(),
	                    vueData.getNoteSoutenance(),
	                    vueData.getDateRemisePrevue(),
	                    vueData.getDateRemiseEffective()
	            );
	            vueData.setNoteFinale(noteFinale);
	
	            vueDataList.add(vueData);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	
	    return vueDataList;
	}
	
	public List<String> getFormations(){
		List<String> formations = new ArrayList<>();
		try {
	        String sql = "SELECT DISTINCT nomFormation FROM dataVue;";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        ResultSet resultSet = statement.executeQuery();
	
	        while (resultSet.next()) {
	            String formation = resultSet.getString("nomFormation");
	            formations.add(formation);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	
	    return formations;
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