package com.example.lab2;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Random;

public class BattleshipController {

    @FXML
    private GridPane playerGrid, enemyGrid;

    @FXML
    private Label statusLabel;

    private Stage stage;
    private boolean[][] playerShips;
    private boolean[][] enemyShips = new boolean[10][10];
    private boolean[][] enemyHits = new boolean[10][10];
    private Random random = new Random();
    private boolean playerTurn = true;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setPlayerShips(boolean[][] playerShips) {
        this.playerShips = playerShips;
        placeEnemyShips();
        initializeGrids();
    }

    private void initializeGrids() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                final int currentRow = row;
                final int currentCol = col;
                Button playerButton = new Button();
                playerButton.setPrefSize(30, 30);
                if (playerShips[row][col]) {
                    playerButton.setStyle("-fx-background-color: gray;");
                }
                playerGrid.add(playerButton, col, row);

                Button enemyButton = new Button();
                enemyButton.setPrefSize(30, 30);
                enemyButton.setOnAction(event -> handlePlayerMove(currentRow, currentCol, enemyButton));
                enemyGrid.add(enemyButton, col, row);
            }
        }
    }

    private void handlePlayerMove(int row, int col, Button button) {
        if (playerTurn && !enemyHits[row][col]) {
            enemyHits[row][col] = true;
            if (enemyShips[row][col]) {
                button.setStyle("-fx-background-color: red;");
                statusLabel.setText("Hit! Your turn again.");
            } else {
                button.setStyle("-fx-background-color: blue;");
                statusLabel.setText("Miss! Enemy's turn.");
                playerTurn = false;
                enemyMove();
            }
        }
    }

    private void enemyMove() {
        int row, col;
        do {
            row = random.nextInt(10);
            col = random.nextInt(10);
        } while (enemyHits[row][col]);

        enemyHits[row][col] = true;
        Button button = (Button) getNodeFromGridPane(playerGrid, row, col);

        if (playerShips[row][col]) {
            button.setStyle("-fx-background-color: red;");
            statusLabel.setText("Enemy hit! Enemy's turn.");
            // Implement logic to finish off the ship here
        } else {
            button.setStyle("-fx-background-color: blue;");
            statusLabel.setText("Enemy missed! Your turn.");
            playerTurn = true;
        }
    }

    private void placeEnemyShips() {
        int shipsPlaced = 0;
        while (shipsPlaced < 5) { // Example: place 5 ships
            int row = random.nextInt(10);
            int col = random.nextInt(10);
            if (!enemyShips[row][col]) {
                enemyShips[row][col] = true;
                shipsPlaced++;
            }
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
    private void backToMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_menu.fxml"));
        stage.setScene(new Scene(loader.load()));
        MainController controller = loader.getController();
        controller.setStage(stage);
    }

    @FXML
    private void viewRules() {
        statusLabel.setText("Rules: Place your ships, then take turns to attack. First to sink all opponent's ships wins.");
    }
}
