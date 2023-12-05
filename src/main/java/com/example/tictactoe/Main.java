package com.example.tictactoe;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tic Tac Toe");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("TicTacToe.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        TicTacToeController controller = loader.getController();
        controller.setStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
