package dao;

import classe.Binome;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BinomeDAO {
    private Connection connection;

    public BinomeDAO() {
        try {
            connection = Connexion.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Binome> getAllBinomes() {
        List<Binome> binomes = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Binome");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Binome binome = new Binome();
                binome.setIdBinome(resultSet.getInt("idBinome"));
                binome.setIdProjet(resultSet.getInt("idProjet"));
                binome.setNoteRapport(resultSet.getDouble("noteRapport"));
                binome.setBinomeReference(resultSet.getString("binomeReference"));
                binome.setDateRemiseEffective(resultSet.getDate("dateRemiseEffective").toLocalDate());
                binomes.add(binome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return binomes;
    }

    public void addBinome(Binome binome) {
        try {
            String query = "INSERT INTO Binome (idProjet, noteRapport, binomeReference, dateRemiseEffective) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, binome.getIdProjet());
            statement.setDouble(2, binome.getNoteRapport());
            statement.setString(3, binome.getBinomeReference());
            statement.setDate(4, Date.valueOf(binome.getDateRemiseEffective()));

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

    public void updateBinome(Binome binome) {
        try {
            String query = "UPDATE Binome SET idProjet = ?, noteRapport = ?, binomeReference = ?, dateRemiseEffective = ? WHERE idBinome = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, binome.getIdProjet());
            statement.setDouble(2, binome.getNoteRapport());
            statement.setString(3, binome.getBinomeReference());
            statement.setDate(4, Date.valueOf(binome.getDateRemiseEffective()));
            statement.setInt(5, binome.getIdBinome());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
