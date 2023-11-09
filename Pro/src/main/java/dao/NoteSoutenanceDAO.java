package dao;

import classe.NoteSoutenance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteSoutenanceDAO {
    private Connection connection;

    public NoteSoutenanceDAO() {
        try {
            connection = Connexion.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<NoteSoutenance> getAllNotesSoutenance() {
        List<NoteSoutenance> notesSoutenance = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM NoteSoutenance");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                NoteSoutenance noteSoutenance = new NoteSoutenance();
                noteSoutenance.setIdNoteSoutenance(resultSet.getInt("idNoteSoutenance"));
                noteSoutenance.setIdBinome(resultSet.getInt("idBinome"));
                noteSoutenance.setIdEtudiant(resultSet.getInt("idEtudiant"));
                noteSoutenance.setNoteSoutenance(resultSet.getDouble("noteSoutenance"));
                notesSoutenance.add(noteSoutenance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notesSoutenance;
    }

    public NoteSoutenance getNoteSoutenanceById(int id) {
        NoteSoutenance noteSoutenance = null;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM NoteSoutenance WHERE idNoteSoutenance = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                noteSoutenance = new NoteSoutenance();
                noteSoutenance.setIdNoteSoutenance(resultSet.getInt("idNoteSoutenance"));
                noteSoutenance.setIdBinome(resultSet.getInt("idBinome"));
                noteSoutenance.setIdEtudiant(resultSet.getInt("idEtudiant"));
                noteSoutenance.setNoteSoutenance(resultSet.getDouble("noteSoutenance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return noteSoutenance;
    }

    public void addNoteSoutenance(NoteSoutenance noteSoutenance) {
        try {
            String query = "INSERT INTO NoteSoutenance (idBinome, idEtudiant, noteSoutenance) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, noteSoutenance.getIdBinome());
            statement.setInt(2, noteSoutenance.getIdEtudiant());
            statement.setDouble(3, noteSoutenance.getNoteSoutenance());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création de la note de soutenance a échoué, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    noteSoutenance.setIdNoteSoutenance(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La création de la note de soutenance a échoué, aucun ID retourné.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateNoteSoutenance(NoteSoutenance noteSoutenance) {
        try {
            String query = "UPDATE NoteSoutenance SET idBinome = ?, idEtudiant = ?, noteSoutenance = ? WHERE idNoteSoutenance = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, noteSoutenance.getIdBinome());
            statement.setInt(2, noteSoutenance.getIdEtudiant());
            statement.setDouble(3, noteSoutenance.getNoteSoutenance());
            statement.setInt(4, noteSoutenance.getIdNoteSoutenance());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteNoteSoutenance(int id) {
        try {
            String query = "DELETE FROM NoteSoutenance WHERE idNoteSoutenance = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

