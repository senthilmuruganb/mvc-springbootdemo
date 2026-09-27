package com.example.demo.repository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.User;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;
@Repository
public class LoginRepository {
	private final String URL = "jdbc:postgresql://localhost:5432/academics";
    private final String USER = "postgres";
    private final String PASSWORD = "admin";

    public String findUsernameByEmail(String email,String password) {
        String username = null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("select username from userdata where email=? and password=?")) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();

            if (rs!=null && rs.next()) {
            	username = rs.getString("username");
            }
            else {
            	username="NO SUCH USER";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return username;
    }
    
    
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT userid, username, age, email,password FROM userdata")) {

            while (rs.next()) {
                users.add(new User(
                        rs.getString("userid"),
                        rs.getString("username"),rs.getInt("age"),
                        rs.getString("email"),rs.getString("password")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
    
    public void insertUser(User user) {
        String sql = "INSERT INTO userdata (userid, username,age, email,password) VALUES (?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUserId());
            ps.setString(2, user.getUserName());
            ps.setInt(3, user.getAge());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateUser(User user) {
        String sql = "UPDATE userdata SET username = ?, email = ? WHERE userid = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getUserId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteUserById(String userid) {
        String sql = "delete from userdata WHERE userid = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userid);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // 👇 NEW: Get one user by ID
    public User findUserById(String userid) {
        String sql = "SELECT userid, username, age,email FROM userdata WHERE userid = ?";
        User user = null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getString("userid"),
                        rs.getString("username"),
                        rs.getInt("age"),rs.getString("email")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}


