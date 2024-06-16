package com.example.lab2;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.example.entity.User;
import com.example.util.HibernateUtil;

public class RegisterController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessage;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleRegister() {
        String login = loginField.getText();
        String pass = passwordField.getText();

        if (login.isEmpty() || pass.isEmpty()) {
            errorMessage.setText("Login and password cannot be empty");
            return;
        }

        if (registerUser(login, pass)) {
            errorMessage.setText("Registration successful");
            backToLogin();
        } else {
            errorMessage.setText("Registration failed");
        }
    }

    private boolean registerUser(String login, String pass) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            User newUser = new User();
            newUser.setLogin(login);
            newUser.setPassword(pass);

            session.save(newUser);
            transaction.commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage.setText("Database error: " + e.getMessage());
            return false;
        }
    }

    @FXML
    private void backToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            stage.setScene(new Scene(loader.load()));
            LoginController controller = loader.getController();
            controller.setStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
