package com.bearcave.automaty;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by miwas on 18.11.16.
 */
public class AutomatonGUIWithoutGUIForm extends Application{

    Automaton automaton;


    public void start(Stage primaryStage) throws Exception {
        Button startButton = new Button("START");
        startButton.setOnAction(e->onStartButtonClick());

        StackPane frame = new StackPane();
        frame.getChildren().add(startButton);

        Scene scene = new Scene(frame, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Automaton");
        primaryStage.show();
    }

    public void onStartButtonClick() {

        //automaton = new GameOfLife();

        System.out.println("You clicked the button");

    }
}
