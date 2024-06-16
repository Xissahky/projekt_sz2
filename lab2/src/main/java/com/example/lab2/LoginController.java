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
import org.hibernate.query.Query;
import com.example.entity.User;
import com.example.util.HibernateUtil;

public class LoginController {

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
    private void handleLogin() throws IOException {
        String login = loginField.getText();
        String pass = passwordField.getText();

        if (login.isEmpty() || pass.isEmpty()) {
            errorMessage.setText("Login and password cannot be empty");
            return;
        }

        if (authenticate(login, pass)) {
            errorMessage.setText("Login successful");
            loadGameMenu();
        } else {
            errorMessage.setText("Invalid login or password");
        }
    }

    private boolean authenticate(String login, String pass) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE login = :login AND password = :pass");
            query.setParameter("login", login);
            query.setParameter("pass", pass);

            User user = query.uniqueResult();
            return user != null;
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage.setText("Database connection error");
            return false;
        }
    }

    private void loadGameMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_menu.fxml"));
        stage.setScene(new Scene(loader.load()));
        MainController controller = loader.getController();
        controller.setStage(stage);
    }

    @FXML
    private void goToRegister() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
        stage.setScene(new Scene(loader.load()));
        RegisterController controller = loader.getController();
        controller.setStage(stage);
    }
}
