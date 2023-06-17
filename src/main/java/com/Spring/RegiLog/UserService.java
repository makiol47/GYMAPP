package com.Spring.RegiLog;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class UserService {

    private static final String INSERT_QUERY = "INSERT INTO Users(Username, Password) VALUES (?, ?)";
    private static final String SELECT_QUERY = "SELECT * FROM Users WHERE username = ? AND password = ?";
    private static final String WRITE_BMI = "UPDATE Users SET Bmi = ? WHERE username = ?";
    private static final String WRITE_TRAINING_PLAN = "UPDATE Users SET Training_Plan = ? WHERE username = ?";

    private String status;

    public void userRegister(User user) {
        if (isUsernameTaken(user.getName())) {
            status = "Username is already taken. Please choose a different username.";
        } else {
            try (Connection con = DBConnection.createDBConnection();
                 PreparedStatement pstm = con.prepareStatement(INSERT_QUERY)) {
                pstm.setString(1, user.getName());
                pstm.setString(2, user.getPassword());
                int cnt = pstm.executeUpdate();
                if (cnt != 0)
                    status = "User successfully registered";
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean userLogin(User user) {
        try (Connection con = DBConnection.createDBConnection();
             PreparedStatement pstm = con.prepareStatement(SELECT_QUERY)) {
            pstm.setString(1, user.getName());
            pstm.setString(2, user.getPassword());
            ResultSet rs = pstm.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void updateUserBMI(User user) {
        try (Connection con = DBConnection.createDBConnection();
             PreparedStatement pstm = con.prepareStatement(WRITE_BMI)) {
            pstm.setDouble(1, user.getBmi());
            pstm.setString(2, user.getName());
            int cnt = pstm.executeUpdate();
            if (cnt != 0)
                status = "User BMI updated successfully";
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateUserTrainingPlan(User user) {
        try (Connection con = DBConnection.createDBConnection();
             PreparedStatement pstm = con.prepareStatement(WRITE_TRAINING_PLAN)) {
            pstm.setString(1, user.getTrainingPlan());
            pstm.setString(2, user.getName());
            int cnt = pstm.executeUpdate();
            if (cnt != 0) {
                status = "User training plan updated successfully";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isUsernameTaken(String username) {
        try (Connection con = DBConnection.createDBConnection();
             PreparedStatement pstm = con.prepareStatement("SELECT * FROM Users WHERE username = ?")) {
            pstm.setString(1, username);
            ResultSet rs = pstm.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public String getStatus() {
        return status;
    }
}
