package org.eclipse.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.config.MySqlConnection;
import org.eclipse.models.Favorite;
import org.eclipse.models.Listing;

public class FavoriteDao implements GenericDao<Favorite, Integer> {

	@Override
    public List<Favorite> findAll() {
        List<Favorite> favorites = null;
        Connection connection = MySqlConnection.getConnection();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                String select = "SELECT id, idUtilisateur, idAnnonce FROM favori";
                ResultSet resultSet = statement.executeQuery(select);
                favorites = new ArrayList<Favorite>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int userId = resultSet.getInt("idUtilisateur");
                    int listingId = resultSet.getInt("idAnnonce");
                    
                    favorites.add(new Favorite(id, listingId, userId));
                }
            } catch (Exception e) {
            	System.err.println("Unable to Favorite Listing");
            }
        }
        return favorites;
    }
	
	public List<Favorite> findAllByOwnerId(Integer queryOwnerId) {
        List<Favorite> favorites = null;
        Connection connection = MySqlConnection.getConnection();
        if (connection != null) {
            try {
            	String select = "SELECT id, idUtilisateur, idAnnonce FROM favori WHERE idUtilisateur = ?";
                PreparedStatement statement = connection.prepareStatement(select);
                statement.setInt(1, queryOwnerId);
                ResultSet resultSet = statement.executeQuery();
                favorites = new ArrayList<Favorite>();
                while (resultSet.next()) {
                	int id = resultSet.getInt("id");
                    int userId = resultSet.getInt("idUtilisateur");
                    int listingId = resultSet.getInt("idAnnonce");
                    
                    favorites.add(new Favorite(id, listingId, userId));
                }
            } catch (Exception e) {
            	System.err.println(e.getMessage());
            }
        }
        return favorites;
    }	
	
	public boolean isFavorite(Integer userId, Integer listingId) {
        Connection connection = MySqlConnection.getConnection();
        if (connection != null) {
            try {
            	String select = "SELECT id FROM favori WHERE idUtilisateur = ? AND idAnnonce = ?";
                PreparedStatement statement = connection.prepareStatement(select);
                statement.setInt(1, userId);
                statement.setInt(2, listingId);
                ResultSet resultSet = statement.executeQuery();
                
                return resultSet.next();
            } catch (Exception e) {
            	System.err.println(e.getMessage());
            }
        }
        return false;
    }
 
    @Override
    public Favorite findById(Integer favoriteId) {
        Connection connection = MySqlConnection.getConnection();
        if (connection != null) {
            try {
            	String select = "SELECT id, idUtilisateur, idAnnonce FROM favori WHERE idUtilisateur = ?";
                PreparedStatement statement = connection.prepareStatement(select);
                statement.setInt(1, favoriteId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                	int id = resultSet.getInt("id");
                    int userId = resultSet.getInt("idUtilisateur");
                    int listingId = resultSet.getInt("idAnnonce");
                    
                    return new Favorite(id, listingId, userId);
                }
            } catch (Exception e) {
            	System.err.println("Unable to find Favorite");
            }
        }
        return null;
    }
 
    @Override
    public Favorite save(Favorite model) {
        Connection connection = MySqlConnection.getConnection();
        if (connection != null) {
            try {
                String insert = "INSERT INTO favori VALUES (null, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setInt(1, model.getUserId());
                statement.setInt(2, model.getListingId());
                statement.executeUpdate();
                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    model.setId(result.getInt(1));
                    return model;
                }
            } catch (Exception e) {
            	System.err.println("Unable to save Favorite");
            }
        }
        return null;
    }
 
    @Override
    public Favorite update(Favorite model) {
        //it doesn't make sens to update a Favorite. you either save or remove one
    	return null;
    }
 
    @Override
    public boolean remove(Integer id) {
        Connection connection = MySqlConnection.getConnection();
        if (connection != null) {
            try {
                String delete = "DELETE FROM favori WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(delete);
                statement.setInt(1, id);
                int result = statement.executeUpdate();
                return result > 0;
            } catch (Exception e) {
            	System.err.println("Unable to delete Favorite");
            }
        }
        return false;
    }

}
