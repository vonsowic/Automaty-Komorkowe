package gui;

import com.bearcave.automaty.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.*;

/**
 * Created by miwas on 27.11.16.
 */
public class Controller implements Initializable{

    @FXML private Slider timeLoopSlider;
    @FXML private Slider neighborhoodLevelSlider;

    @FXML private Button oneMoveButton;
    @FXML private Button startButton;
    @FXML private Button intervalButton;
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

    @FXML private Label generationLabel;
    private int generation = 0;

    public final static int GAMEOFLIFE = 1;
    public final static int LANGTONANT= 2;
    public final static int WIREWORLD = 3;
    public final static int ONEDIMAUTOMATON = 4;

    private int columnNumber = 33;
    private int rowNumber = 40;


    protected Automaton automaton = null;
    private boolean isPlaying = false;
    private boolean isIntervalSet = false;
    protected CellMap cellMap;


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
        //simulationWindow.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> System.out.println("Width: " + newSceneWidth));
        //simulationWindow.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> System.out.println("Height: " + newSceneHeight));

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

    /**
     * set or remove intervals between cells
     */
    public void setInterval(ActionEvent actionEvent) {
        if (isIntervalSet){
            intervalButton.setText("Dodaj odstępy");
            cellMap.setCellsPositions(0);
        } else {
            intervalButton.setText("Usuń odstępy");
            cellMap.setCellsPositions(1);
        }
        isIntervalSet = !isIntervalSet;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////

    public void reset() {
        automaton = null;
        setDisable(false);
        initializeGeneretion();
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
                        getColumnQuantity(),
                        getRowQuantity(),
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
                        getColumnQuantity(),
                        getRowQuantity(),
                        cellMap.translateForAutomaton(),
                        getNeighborhoodLevel());
                break;
            case ONEDIMAUTOMATON:
                if (cellMap == null){
                    cellMap = new OneDimAutomatonMap(this);
                } else {
                    cellMap = new OneDimAutomatonMap(cellMap);
                }

                automaton = new OneDimAutomaton(
                        getColumnQuantity(),
                        getRule(),
                        cellMap.translateForAutomaton());
                break;
        }
    }

    private void makeOneMove(){
        if ( automaton == null) {
            chooseGame();
        }

        incrementGeneration();
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
        gameOfLifeRButton.setDisable(isLock);
        neighborhoodLevelSlider.setDisable(isLock);
        langtonAntRButton.setDisable(isLock);
        wireWorldRButton.setDisable(isLock);
        oneDimRButton.setDisable(isLock);
        oneMoveButton.setDisable(isLock);
        clearButton.setDisable(isLock);
        ruleSlider.setDisable(isLock);

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

    public int getRowQuantity(){
        return rowNumber;
    }

    public int getColumnQuantity(){
        return columnNumber;
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

    private void incrementGeneration(){
        generationLabel.setText( String.valueOf(++generation));
    }

    private void initializeGeneretion(){
        generation = 0;
        generationLabel.setText( String.valueOf(generation));
    }

}
