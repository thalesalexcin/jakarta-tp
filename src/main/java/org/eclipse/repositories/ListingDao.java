package org.eclipse.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.config.MySqlConnection;
import org.eclipse.models.Listing;

public class ListingDao implements GenericDao<Listing, Integer> {

	@Override
    public List<Listing> findAll() {
        List<Listing> listings = null;
        Connection connection = MySqlConnection.getConnection();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                String select = "SELECT id, titre, description, prix, ville, idUtilisateur FROM annonce";
                ResultSet resultSet = statement.executeQuery(select);
                listings = new ArrayList<Listing>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("titre");
                    String description = resultSet.getString("description");
                    double price = resultSet.getDouble("prix");
                    String city = resultSet.getString("ville");
                    int ownerId = resultSet.getInt("idUtilisateur");
                    
                    listings.add(new Listing(id, title, description, price, city, ownerId));
                }
            } catch (Exception e) {
            	System.err.println("Unable to findAll Listing");
            }
        }
        return listings;
    }
	
	public List<Listing> findAllByOwnerId(Integer queryOwnerId) {
        List<Listing> listings = null;
        Connection connection = MySqlConnection.getConnection();
        if (connection != null) {
            try {
            	String select = "SELECT id, titre, description, prix, ville FROM annonce WHERE idUtilisateur = ?";
                PreparedStatement statement = connection.prepareStatement(select);
                statement.setInt(1, queryOwnerId);
                ResultSet resultSet = statement.executeQuery();
                listings = new ArrayList<Listing>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("titre");
                    String description = resultSet.getString("description");
                    double price = resultSet.getDouble("prix");
                    String city = resultSet.getString("ville");
                    
                    listings.add(new Listing(id, title, description, price, city, queryOwnerId));
                }
            } catch (Exception e) {
            	System.err.println(e.getMessage());
            }
        }
        return listings;
    }
	
	public List<Listing> findAllByNotOwnerId(Integer queryOwnerId) {
        List<Listing> listings = null;
        Connection connection = MySqlConnection.getConnection();
        if (connection != null) {
            try {
            	String select = "SELECT id, titre, description, prix, ville, idUtilisateur FROM annonce WHERE idUtilisateur != ?";
                PreparedStatement statement = connection.prepareStatement(select);
                statement.setInt(1, queryOwnerId);
                ResultSet resultSet = statement.executeQuery();
                listings = new ArrayList<Listing>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("titre");
                    String description = resultSet.getString("description");
                    double price = resultSet.getDouble("prix");
                    String city = resultSet.getString("ville");
                    int ownerId = resultSet.getInt("idUtilisateur");
                    
                    listings.add(new Listing(id, title, description, price, city, ownerId));
                }
            } catch (Exception e) {
            	System.err.println("Unable to findAll Listing");
            }
        }
        return listings;
    }
 
    @Override
    public Listing findById(Integer listingId) {
        Connection connection = MySqlConnection.getConnection();
        if (connection != null) {
            try {
            	String select = "SELECT id, titre, description, prix, ville, idUtilisateur FROM annonce WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(select);
                statement.setInt(1, listingId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                	 int id = resultSet.getInt("id");
                     String title = resultSet.getString("titre");
                     String description = resultSet.getString("description");
                     double price = resultSet.getDouble("prix");
                     String city = resultSet.getString("ville");
                     int ownerId = resultSet.getInt("idUtilisateur");
                     
                     return new Listing(id, title, description, price, city, ownerId);
                }
            } catch (Exception e) {
            	System.err.println("Unable to find Listing");
            }
        }
        return null;
    }
 
    @Override
    public Listing save(Listing model) {
        Connection connection = MySqlConnection.getConnection();
        if (connection != null) {
            try {
                String insert = "INSERT INTO annonce VALUES (null, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setString(1, model.getTitle());
                statement.setString(2, model.getDescription());
                statement.setDouble(3, model.getPrice());
                statement.setString(4, model.getCity());
                statement.setInt(5, model.getOwnerId());
                statement.executeUpdate();
                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    model.setId(result.getInt(1));
                    return model;
                }
            } catch (Exception e) {
            	System.err.println("Unable to save Listing");
            }
        }
        return null;
    }
 
    @Override
    public Listing update(Listing model) {
        Connection connection = MySqlConnection.getConnection();
        if (connection != null) {
            try {
                String update = "UPDATE annonce SET titre = ?, description = ?, prix = ?, ville = ? WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(update);
                statement.setString(1, model.getTitle());
                statement.setString(2, model.getDescription());
                statement.setDouble(3, model.getPrice());
                statement.setString(4, model.getCity());
                statement.setInt(5, model.getId());
                int result = statement.executeUpdate();
                if (result > 0) {
                    return model;
                }
            } catch (Exception e) {
                System.err.println("Unable to update Listing");
            }
        }
        return null;
    }
 
    @Override
    public boolean remove(Integer id) {
        Connection connection = MySqlConnection.getConnection();
        if (connection != null) {
            try {
                String delete = "DELETE FROM annonce WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(delete);
                statement.setInt(1, id);
                int result = statement.executeUpdate();
                return result > 0;
            } catch (Exception e) {
            	System.err.println("Unable to delete Listing");
            }
        }
        return false;
    }

}
