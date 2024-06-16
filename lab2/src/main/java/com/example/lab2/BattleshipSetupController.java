package com.example.lab2;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class BattleshipSetupController {
    @FXML
    private GridPane playerGrid;

    @FXML
    private Label statusLabel;

    private Stage stage;
    private boolean[][] playerShips = new boolean[10][10];

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                final int currentRow = row;
                final int currentCol = col;
                Button button = new Button();
                button.setPrefSize(30, 30);
                button.setOnAction(event -> placeShip(currentRow, currentCol));
                playerGrid.add(button, col, row);
            }
        }
    }

    private void placeShip(int row, int col) {
        if (!playerShips[row][col]) {
            playerShips[row][col] = true;
            Button button = (Button) getNodeFromGridPane(playerGrid, row, col);
            button.setStyle("-fx-background-color: gray;");
        }
    }

    private Button getNodeFromGridPane(GridPane gridPane, int row, int col) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (Button) node;
            }
        }
        return null;
    }

    @FXML
    private void startGame() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("battleship_game.fxml"));
        stage.setScene(new Scene(loader.load()));
        BattleshipController controller = loader.getController();
        controller.setStage(stage);
        controller.setPlayerShips(playerShips);
    }
}
