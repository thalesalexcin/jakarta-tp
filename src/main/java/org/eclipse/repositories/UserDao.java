package org.eclipse.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.config.MySqlConnection;
import org.eclipse.models.User;

public class UserDao implements GenericDao<User, Integer> {

	@Override
    public List<User> findAll() {
        List<User> users = null;
        Connection connection = MySqlConnection.getConnection();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                String select = "SELECT id, nom, prenom, email FROM utilisateur";
                ResultSet resultSet = statement.executeQuery(select);
                users = new ArrayList<User>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String lastName = resultSet.getString("nom");
                    String firstName = resultSet.getString("prenom");
                    String email = resultSet.getString("email");
                    
                    users.add(new User(id, lastName, firstName, email));
                }
            } catch (Exception e) {
            	System.err.println("Unable to findAll User");
            }
        }
        return users;
    }
 
    @Override
    public User findById(Integer userId) {
        Connection connection = MySqlConnection.getConnection();
        if (connection != null) {
            try {
                String select = "SELECT id, nom, prenom, email FROM utilisateur WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(select);
                statement.setInt(1, userId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                	 int id = resultSet.getInt("id");
                     String lastName = resultSet.getString("nom");
                     String firstName = resultSet.getString("prenom");
                     String email = resultSet.getString("email");
                     
                     return new User(id, lastName, firstName, email);
                }
            } catch (Exception e) {
            	System.err.println("Unable to find User");
            }
        }
        return null;
    }
    
    public User findByEmailAndPassword(String userEmail, String userPassword) {
        Connection connection = MySqlConnection.getConnection();
        if (connection != null) {
            try {
                String select = "SELECT id, nom, prenom, email FROM utilisateur WHERE email = ? AND motDePasse = ?";
                PreparedStatement statement = connection.prepareStatement(select);
                statement.setString(1, userEmail);
                statement.setString(2, userPassword);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                	 int id = resultSet.getInt("id");
                     String lastName = resultSet.getString("nom");
                     String firstName = resultSet.getString("prenom");
                     String email = resultSet.getString("email");
                     
                     return new User(id, lastName, firstName, email);
                }
            } catch (Exception e) {
            	System.err.println("Unable to find User");
            }
        }
        return null;
    }
 
    @Override
    public User save(User model) {
        Connection connection = MySqlConnection.getConnection();
        if (connection != null) {
            try {
                String insert = "INSERT INTO utilisateur VALUES (null, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(insert,
                        PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setString(1, model.getLastName());
                statement.setString(2, model.getFirstName());
                statement.setString(3, model.getEmail());
                statement.setString(4, model.getPassword());
                statement.executeUpdate();
                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    model.setId(result.getInt(1));
                    return model;
                }
            } catch (Exception e) {
            	System.err.println("Unable to save User");
            }
        }
        return null;
    }
 
    @Override
    public User update(User model) {
        Connection connection = MySqlConnection.getConnection();
        if (connection != null) {
            try {
                String update = "UPDATE utilisateur SET nom = ?, prenom = ?, email = ? WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(update);
                statement.setString(1, model.getLastName());
                statement.setString(2, model.getFirstName());
                statement.setString(3, model.getEmail());
                statement.setInt(4, model.getId());
                int result = statement.executeUpdate();
                if (result > 0) {
                    return model;
                }
            } catch (Exception e) {
                System.err.println("Unable to update User");
            }
        }
        return null;
    }
 
    @Override
    public boolean remove(Integer id) {
        Connection connection = MySqlConnection.getConnection();
        if (connection != null) {
            try {
                String delete = "DELETE FROM utilisateur WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(delete);
                statement.setInt(1, id);
                int result = statement.executeUpdate();
                return result > 0;
            } catch (Exception e) {
            	System.err.println("Unable to delete User");
            }
        }
        return false;
    }

}
