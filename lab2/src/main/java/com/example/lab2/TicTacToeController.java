package com.example.lab2;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class TicTacToeController {

    @FXML
    private GridPane ticTacToeGrid;

    @FXML
    private Label statusLabel;

    @FXML
    private Button btn00, btn01, btn02, btn10, btn11, btn12, btn20, btn21, btn22;

    private Stage stage;

    private boolean isXTurn = true;
    private String[][] board = new String[3][3];

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleButtonClick(javafx.event.ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String id = clickedButton.getId();
        int row = Character.getNumericValue(id.charAt(3));
        int col = Character.getNumericValue(id.charAt(4));

        if (board[row][col] == null) {
            board[row][col] = isXTurn ? "X" : "O";
            clickedButton.setText(board[row][col]);
            if (checkWin()) {
                statusLabel.setText("Player " + (isXTurn ? "X" : "O") + " wins!");
                disableButtons();
            } else if (isBoardFull()) {
                statusLabel.setText("It's a draw!");
            } else {
                isXTurn = !isXTurn;
                statusLabel.setText("Player " + (isXTurn ? "X" : "O") + "'s turn");
            }
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != null && board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2])) {
                return true;
            }
            if (board[0][i] != null && board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i])) {
                return true;
            }
        }
        if (board[0][0] != null && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) {
            return true;
        }
        if (board[0][2] != null && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0])) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private void disableButtons() {
        btn00.setDisable(true);
        btn01.setDisable(true);
        btn02.setDisable(true);
        btn10.setDisable(true);
        btn11.setDisable(true);
        btn12.setDisable(true);
        btn20.setDisable(true);
        btn21.setDisable(true);
        btn22.setDisable(true);
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
        statusLabel.setText("Rules: Get three in a row to win. X goes first.");
    }
}
