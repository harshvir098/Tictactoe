package com.example.tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class TicTacToeController {

    @FXML
    private GridPane ticTacToeBoard;
    private boolean isHumanVsHuman = false;
    private boolean isPlayer1Turn = true;
    private Stage stage;

    @FXML
    private Label player1WinsLabel;
    @FXML
    private Label player2WinsLabel;
    @FXML
    private Label player1LossesLabel;
    @FXML
    private Label player2LossesLabel;

    @FXML
    private Label selectedModeLabel;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void humanVsHumanSelected() {
        isHumanVsHuman = true;
        selectedModeLabel.setText("Human vs Human");
        resetScoreboard();
        initializeGame();
        System.out.println("Human vs Human mode selected");

    }

    @FXML
    public void humanVsComputerSelected() {
        isHumanVsHuman = false;
        selectedModeLabel.setText("Human vs Computer");
        resetScoreboard();
        initializeGame();
        isPlayer1Turn = true;
        System.out.println("Human vs Computer mode selected");
    }





    public void ClickedButton(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        if (clickedButton != null) {
            if (clickedButton.getText().isEmpty()) {
                if ((isHumanVsHuman || isPlayer1Turn) && !isBoardFull()) {
                    String currentPlayerSymbol = isPlayer1Turn ? "X" : "O";
                    clickedButton.setText(currentPlayerSymbol);

                    if (checkWin(currentPlayerSymbol)) {
                        if (isHumanVsHuman) {
                            showAlert("Player " + (isPlayer1Turn ? "1" : "2") + " wins!");
                            updateScoreboard(isPlayer1Turn ? player1WinsLabel : player2WinsLabel, isPlayer1Turn ? player2LossesLabel : player1LossesLabel);
                        }  else {
                            showAlert("You win!");
                            updateScoreboard(player1WinsLabel, player2LossesLabel);
                        }
                        initializeGame();
                    } else if (isBoardFull()) {
                        showAlert("It's a draw!");
                        initializeGame();
                    } else {
                        if (!isHumanVsHuman) {
                            isPlayer1Turn = false;
                            makeComputerMove();
                            isPlayer1Turn = true;
                        } else {
                            isPlayer1Turn = !isPlayer1Turn;
                        }
                    }
                } else if (!isBoardFull()) {

                    clickedButton.setText("O");
                    if (checkWin("O")) {
                        showAlert("Computer wins!");
                        updateScoreboard(player2WinsLabel, player1LossesLabel);
                        initializeGame();
                    } else if (isBoardFull()) {
                        showAlert("It's a draw!");
                        initializeGame();
                    } else {
                        isPlayer1Turn = true;
                    }
                }
            } else {
                showAlert("Board is already filled!");
            }
        }
    }




    private boolean checkWin(String playerSymbol) {

        for (int row = 0; row < 3; row++) {
            Button b1 = (Button) getNodeFromGridPane(row, 0);
            Button b2 = (Button) getNodeFromGridPane(row, 1);
            Button b3 = (Button) getNodeFromGridPane(row, 2);

            if (b1.getText().equals(playerSymbol) && b2.getText().equals(playerSymbol) && b3.getText().equals(playerSymbol)) {
                return true;
            }
        }


        for (int col = 0; col < 3; col++) {
            Button b1 = (Button) getNodeFromGridPane(0, col);
            Button b2 = (Button) getNodeFromGridPane(1, col);
            Button b3 = (Button) getNodeFromGridPane(2, col);

            if (b1.getText().equals(playerSymbol) && b2.getText().equals(playerSymbol) && b3.getText().equals(playerSymbol)) {
                return true;
            }
        }


        Button topLeft = (Button) getNodeFromGridPane(0, 0);
        Button mid = (Button) getNodeFromGridPane(1, 1);
        Button bottomRight = (Button) getNodeFromGridPane(2, 2);

        if (topLeft.getText().equals(playerSymbol) && mid.getText().equals(playerSymbol) && bottomRight.getText().equals(playerSymbol)) {
            return true;
        }

        Button topRight = (Button) getNodeFromGridPane(0, 2);
        Button bottomLeft = (Button) getNodeFromGridPane(2, 0);

        if (topRight.getText().equals(playerSymbol) && mid.getText().equals(playerSymbol) && bottomLeft.getText().equals(playerSymbol)) {
            return true;
        }

        return false; // No win
    }


    private Node getNodeFromGridPane(int row, int col) {
        for (Node node : ticTacToeBoard.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return node;
            }
        }
        return null;
    }

    private void updateScoreboard(Label playerLabel, Label opponentLabel) {
        int wins = Integer.parseInt(playerLabel.getText().split(":")[1].trim());
        wins++;
        playerLabel.setText("Wins: " + wins);

        int losses = Integer.parseInt(opponentLabel.getText().split(":")[1].trim());
        losses++;
        opponentLabel.setText("Losses: " + losses);
    }

    private void resetScoreboard() {
        player1WinsLabel.setText("Wins: 0");
        player1LossesLabel.setText("Losses: 0");
        player2WinsLabel.setText("Wins: 0");
        player2LossesLabel.setText("Losses: 0");
    }

    private boolean isBoardFull() {
        for (Node node : ticTacToeBoard.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (button.getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }


    private void makeComputerMove() {


        for (int i = 0; i < 9; i++) {
            Button button = (Button) ticTacToeBoard.getChildren().get(i);
            if (button.getText().isEmpty()) {
                button.setText("O");
                break;
            }
        }
    }



    private void initializeGame() {

        ticTacToeBoard.getChildren().forEach(node -> {
            if (node instanceof Button) {
                ((Button) node).setText("");
            }
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
