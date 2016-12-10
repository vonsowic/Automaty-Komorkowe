package gui;


import com.bearcave.automaty.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.net.URL;
import java.util.*;

/**
 * Created by miwas on 27.11.16.
 */
public class Controller implements Initializable{

    @FXML private Slider timeLoopSlider;
    @FXML private Slider rowSlider;
    @FXML private Slider columnSlider;
    @FXML private Slider neighborhoodLevelSlider;

    @FXML private Button oneMoveButton;
    @FXML private Button startButton;
    @FXML private Button resetButton;
    @FXML private Button clearButton;

    @FXML private Pane simulationWindow;

    @FXML final ToggleGroup choosenAutomatonGroup;
    @FXML private RadioButton gameOfLifeRButton;
    @FXML private RadioButton langtonAntRButton;
    @FXML private RadioButton wireWorldRButton;
    @FXML private RadioButton oneDimRButton;

    @FXML private Label neighborhoodLevelLabel;

    public final static int GAMEOFLIFE = 1;
    public final static int LANGTONANT= 2;
    public final static int WIREWORLD = 3;


    Automaton automaton = null;
    private boolean isPlaying = false;
    CellMap cellMap;

    public Controller() {
        choosenAutomatonGroup = new ToggleGroup();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameOfLifeRButton.setToggleGroup(choosenAutomatonGroup);
        langtonAntRButton.setToggleGroup(choosenAutomatonGroup);
        wireWorldRButton.setToggleGroup(choosenAutomatonGroup);
        oneDimRButton.setToggleGroup(choosenAutomatonGroup);


        cellMap = new CellMap( this);
        cellMap.drawMap( simulationWindow );

        simulationWindow.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> System.out.println("Width: " + newSceneWidth));
        simulationWindow.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> System.out.println("Height: " + newSceneHeight));

        neighborhoodLevelSlider.valueProperty().addListener((arg0, arg1, arg2) -> neighborhoodLevelLabel.setText(String.valueOf(getNeighborhoodLevel())));

    }

    //////////////////////////// Uchwyty do kontrolek //////////////////////////////////////////////////////////////////
    /**
     * Uchwyt do przycisku "Jeden ruch"
     * @param actionEvent
     */
    public void oneMove(ActionEvent actionEvent) {
        makeOneMove();
    }

    /**
     * Uchwyt do przycisku "Start"
     * @param actionEvent
     */
    public void startSimulation(ActionEvent actionEvent) {
        isPlaying = !isPlaying;
        if ( isPlaying){
            setDisable(true);
            startButton.setText("Stop");
            startSimulation();

        } else {
            startButton.setText("Start");
            resetButton.setDisable(false);
            oneMoveButton.setDisable(false);
            clearButton.setDisable(false);
        }
    }

    public void reset(ActionEvent actionEvent) {
        automaton = null;
        setDisable(false);
    }

    public void clear(ActionEvent actionEvent) {
        cellMap.clearScreen();
        reset(null);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////

    public int getSelectedGame(){
        Toggle selectedTogle = choosenAutomatonGroup.getSelectedToggle();
        if ( selectedTogle == gameOfLifeRButton){
            return GAMEOFLIFE;
        } else if ( selectedTogle == wireWorldRButton){
            return WIREWORLD;
        }

        return 0;
    }

    private int getTimeLoop(){
        return (int) timeLoopSlider.getValue();
    }

    private int getNeighborhoodLevel(){
        return (int) neighborhoodLevelSlider.getValue();
    }

    public int getCellsWidth(){
        return (int) rowSlider.getValue();
    }

    public int getCellsHeigth(){
        return (int) columnSlider.getValue();
    }

    public Pane getSimulationWindow() {
        return simulationWindow;
    }

    public boolean isPlaying(){
        return isPlaying;
    }

    public void chooseGame(){
        switch ( getSelectedGame() ){
            case GAMEOFLIFE:
                automaton = new GameOfLife(
                        getCellsWidth(),
                        getCellsHeigth(),
                        cellMap.translateForAutomaton(),
                        getNeighborhoodLevel());
            break;
            case WIREWORLD:
                automaton = new WireWorld(
                        getCellsWidth(),
                        getCellsHeigth(),
                        cellMap.translateForAutomaton(),
                        getNeighborhoodLevel());
            break;
        }
    }

    public void setDisable(boolean isLock){
        columnSlider.setDisable(isLock);
        rowSlider.setDisable(isLock);
        gameOfLifeRButton.setDisable(isLock);
        neighborhoodLevelSlider.setDisable(isLock);
        langtonAntRButton.setDisable(isLock);
        wireWorldRButton.setDisable(isLock);
        oneDimRButton.setDisable(isLock);
        oneMoveButton.setDisable(isLock);
        resetButton.setDisable(isLock);
        clearButton.setDisable(isLock);

        for ( Map.Entry<CellCoordinates, Shape> entry : cellMap.cellMap.entrySet()){
            entry.getValue().setDisable(isLock);
        }
    }

    private void makeOneMove(){
        if ( automaton == null)
            chooseGame();

        automaton = automaton.nextState();
        cellMap.updateCellMap(automaton.getCellMap());
    }

    private void startInBackgroundThread(){
        // Update the Label on the JavaFx Application Thread
        while (isPlaying) {
            Platform.runLater(() -> makeOneMove());

            try {
                Thread.sleep(getTimeLoop());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void startSimulation(){

        // Create a Runnable
        Runnable task = () -> startInBackgroundThread();

        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();

    }



}
