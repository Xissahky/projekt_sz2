package com.example.lab2;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class MainController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void startTicTacToe() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tic_tac_toe.fxml"));
        stage.setScene(new Scene(loader.load()));
        TicTacToeController controller = loader.getController();
        controller.setStage(stage);
    }

    @FXML
    private void startBattleship() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("battleship_setup.fxml"));
        stage.setScene(new Scene(loader.load()));
        BattleshipSetupController controller = loader.getController();
        controller.setStage(stage);
    }
}



