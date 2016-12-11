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
import javafx.scene.layout.VBox;
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

    @FXML private VBox additionalOptionsBox;
    @FXML private Label ruleLabel;
    @FXML private Slider ruleSlider;

    public final static int GAMEOFLIFE = 1;
    public final static int LANGTONANT= 2;
    public final static int WIREWORLD = 3;
    public final static int ONEDIMAUTOMATON = 4;


    protected Automaton automaton = null;
    private boolean isPlaying = false;
    protected CellMap cellMap;
    protected AdditionalOptions options;


    public Controller() {
        choosenAutomatonGroup = new ToggleGroup();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameOfLifeRButton.setToggleGroup(choosenAutomatonGroup);
        langtonAntRButton.setToggleGroup(choosenAutomatonGroup);
        wireWorldRButton.setToggleGroup(choosenAutomatonGroup);
        oneDimRButton.setToggleGroup(choosenAutomatonGroup);
        choosenAutomatonGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (choosenAutomatonGroup.getSelectedToggle() != null) {
                chooseGame();
            }
        });
        simulationWindow.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> System.out.println("Width: " + newSceneWidth));
        simulationWindow.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> System.out.println("Height: " + newSceneHeight));

        neighborhoodLevelSlider.valueProperty().addListener((arg0, arg1, arg2) -> {
            neighborhoodLevelLabel.setText(String.valueOf(getNeighborhoodLevel()));
            if ( cellMap != null)
                reset();
        });

        ruleSlider.valueProperty().addListener((arg0, arg1, arg2) -> {
            ruleLabel.setText(String.valueOf(getRule()));
            if ( cellMap != null)
                reset();
        });
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
            setDisable(false);
        }
    }

    public void clear(ActionEvent actionEvent) {
        cellMap.clearScreen();
        reset();
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////

    public void reset() {
        automaton = null;
        setDisable(false);
    }


    public int getSelectedGame(){
        Toggle selectedTogle = choosenAutomatonGroup.getSelectedToggle();
        if ( selectedTogle == gameOfLifeRButton){
            return GAMEOFLIFE;
        } else if ( selectedTogle == wireWorldRButton){
            return WIREWORLD;
        } else if ( selectedTogle == langtonAntRButton){
            return LANGTONANT;
        } else if (selectedTogle == oneDimRButton){
            return ONEDIMAUTOMATON;
        }

        return 0;
    }

    public void chooseGame(){
        switch ( getSelectedGame() ){
            case GAMEOFLIFE:
                if (cellMap == null){
                    cellMap = new GameOfLifeMap(this);
                } else {
                    cellMap = new GameOfLifeMap(cellMap);
                }

                automaton = new GameOfLife(
                        getCellsWidth(),
                        getCellsHeigth(),
                        cellMap.translateForAutomaton(),
                        getNeighborhoodLevel());
                break;
            case WIREWORLD:
                if (cellMap == null){
                    cellMap = new WireWorldMap(this);
                } else {
                    cellMap = new WireWorldMap(cellMap);
                }
                automaton = new WireWorld(
                        getCellsWidth(),
                        getCellsHeigth(),
                        cellMap.translateForAutomaton(),
                        getNeighborhoodLevel());
                break;
            case ONEDIMAUTOMATON:
                if (cellMap == null){
                    cellMap = new OneDimAutomatonMap(this);
                } else {
                    cellMap = new OneDimAutomatonMap(cellMap);
                }

                options = new AdditionalOptionsForOneDimAutomaton(this);

                automaton = new OneDimAutomaton(
                        getCellsWidth(),
                        getRule(),
                        cellMap.translateForAutomaton());
                break;
        }
    }

    private void makeOneMove(){
        if ( automaton == null) {
            chooseGame();
        }

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
        Runnable task = () -> startInBackgroundThread();
        Thread backgroundThread = new Thread(task);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
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

        for ( int i=0; i<cellMap.getMap().size(); i++){
            for ( int j=0; j<cellMap.getMap().get(i).size(); j++){
                cellMap.getMap().get(i).get(j).setDisable(isLock);
            }
        }
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

    public int getRule(){
        return (int) ruleSlider.getValue();
    }

    public VBox getAdditionalOptionsBox() {
        return additionalOptionsBox;
    }
}
