package gui;


import com.bearcave.automaty.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
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

    @FXML private Button oneMoveButton;
    @FXML private Button startButton;

    @FXML private Pane simulationWindow;

    @FXML final ToggleGroup choosenAutomatonGroup;
    @FXML private RadioButton gameOfLifeRButton;
    @FXML private RadioButton langtonAntRButton;
    @FXML private RadioButton wireWorldRButton;

    private final static int GAMEOFLIFE = 1;
    private final static int LANGTONANT= 2;
    private final static int WIREWORLD = 3;
    private String choosenGame = null;


    Automaton automaton = null;
    private boolean isPlaying = false;
    private boolean isSetUp = false;
    CellMap cellMap;

    public Controller() {
        choosenAutomatonGroup = new ToggleGroup();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameOfLifeRButton.setToggleGroup(choosenAutomatonGroup);
        langtonAntRButton.setToggleGroup(choosenAutomatonGroup);
        wireWorldRButton.setToggleGroup(choosenAutomatonGroup);


        cellMap = new CellMap( getCellsWidth(), getCellsHeigth());
        cellMap.drawMap( simulationWindow );
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
        if ( !isPlaying){
            isPlaying = !isPlaying;
            setDisable(true);
            startButton.setText("Stop");

            makeOneMove();
            /*
            while (isPlaying){
                new Thread(() -> {
                    makeOneMove();
                    try {
                        Thread.sleep( getTimeLoop());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ).start();
            }
            */
        } else {
            isPlaying = !isPlaying;
            startButton.setText("Start");

        }
    }



    ///////////////////////////////// Metody prywatne //////////////////////////////////////////////////////////////////

    private int getTimeLoop(){
        return (int) timeLoopSlider.getValue();
    }

    private int getCellsWidth(){
        return (int) rowSlider.getValue();
    }

    private int getCellsHeigth(){
        return (int) columnSlider.getValue();
    }

    private void chooseGame(){
        if (choosenAutomatonGroup.getSelectedToggle() == gameOfLifeRButton){
            automaton = new GameOfLife(getCellsWidth(), getCellsHeigth(), cellMap.translateForAutomaton());
        } else if (choosenAutomatonGroup.getSelectedToggle() == langtonAntRButton) {

        } else if (choosenAutomatonGroup.getSelectedToggle() == wireWorldRButton) {
            automaton = new WireWorld(getCellsWidth(), getCellsHeigth(), cellMap.translateForAutomaton());
        }
    }

    private void setDisable(boolean isLock){
        columnSlider.setDisable(isLock);
        rowSlider.setDisable(isLock);
        gameOfLifeRButton.setDisable(isLock);
        langtonAntRButton.setDisable(isLock);
        wireWorldRButton.setDisable(isLock);

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

    public void reset(ActionEvent actionEvent) {
        automaton = null;
        setDisable(false);
    }

///////////////////////// CELLMAP //////////////////////////////////////////////////////////////////////////////////
    private class CellMap{

        private int cellSize = 12;
        Map<CellCoordinates, Shape> cellMap;
        Map<CellState, Color> cellDictionary;


        CellMap(int x, int y){
            createCellDictionary();
            createMap(x, y);
            setCellsPositions();
        }

        private void createCellDictionary() {
            cellDictionary = new HashMap<>();

            cellDictionary.put(QuadState.DEAD, Color.LIGHTGRAY);
            cellDictionary.put(QuadState.BLUE, Color.ALICEBLUE);
            cellDictionary.put(QuadState.GREEN, Color.GREEN);
            cellDictionary.put(QuadState.YELLOW, Color.YELLOW);
            cellDictionary.put(QuadState.RED, Color.RED);
            cellDictionary.put(null, Color.DARKRED);
        }

        private CellState getAutomatonCellState(CellState quadstate){

            //game of life
            if (choosenAutomatonGroup.getSelectedToggle() == gameOfLifeRButton) {
                switch ((QuadState) quadstate) {
                    case YELLOW:
                        return BinaryState.ALIVE;
                    default:
                        return BinaryState.DEAD;
                }
            } else if (choosenAutomatonGroup.getSelectedToggle() == wireWorldRButton){
                switch ((QuadState) quadstate) {
                    case YELLOW:
                        return WireElectronState.WIRE;
                    case RED:
                        return WireElectronState.ELECTRON_HEAD;
                    case GREEN:
                        return WireElectronState.ELECTRON_TAIL;
                    default:
                        return WireElectronState.VOID;
                }
            }
            return null;
        }

        private Map<CellCoordinates, CellState> translateForAutomaton(){
            Map<CellCoordinates, CellState> automatonMap = new HashMap<>();
            for (Map.Entry<CellCoordinates, Shape> entry : cellMap.entrySet()) {
                automatonMap.put(entry.getKey(), getAutomatonCellState(
                        getCellState(
                                getCellColor(
                                        entry.getKey()))));
            }

        return automatonMap;
    }

        private QuadState translateCellFromAutomaton(BinaryState state){
            switch (state){
                case ALIVE:
                    return QuadState.YELLOW;
                default:
                    return QuadState.DEAD;
            }
        }

        void createMap(int x, int y){
            cellMap = new HashMap<>();
            for (int i=0; i<x; i++){
                for(int m=0; m<y; m++){
                    Coords2d coords = new Coords2d(i, m);
                    Rectangle cell = new Rectangle( cellSize, cellSize, getCellColor(QuadState.DEAD));

                    cell.setOnMouseClicked( event -> {
                        clickOnCell(cell);
                    });

                    cellMap.put(coords, cell);
                }
            }
        }

        void updateCellMap(Map<CellCoordinates, CellState> automatonMap){
            CellState state = null;
            for (Map.Entry<CellCoordinates, Shape> entry : cellMap.entrySet()) {
                if ( choosenAutomatonGroup.getSelectedToggle() == gameOfLifeRButton){
                    state = translateCellFromAutomaton((BinaryState) automatonMap.get(entry.getKey()));
                } else if (choosenAutomatonGroup.getSelectedToggle() == wireWorldRButton){
                    // do wejsciowej wartosci zawierajaca kolo kolor jest ustawiany zgodnie ze stanem automatonMap o tej samej pozycji co kolo
                    entry.getValue().setFill( getCellColor( state ));
                }

            }
        }

        void drawMap(Pane pane){
            for (Map.Entry<CellCoordinates, Shape> entry : cellMap.entrySet()) {
                pane.getChildren().add(entry.getValue());
            }
            pane.requestLayout();
        }

        void setCellsPositions(){
            for (Map.Entry<CellCoordinates, Shape> entry : cellMap.entrySet()) {
                entry.getValue().setLayoutX((entry.getKey().getWidth()+1)*cellSize);
                entry.getValue().setLayoutY((entry.getKey().getHeight()+1)*cellSize);
            }
        }

        CellState getCellState(Color color){
            for (Map.Entry<CellState, Color> entry : cellDictionary.entrySet()) {
                if ( entry.getValue() == color){
                    return entry.getKey();
                }
            }

            // sth must be returned
            return QuadState.DEAD;
        }

        Color getCellColor(CellState state){
            return cellDictionary.get(state);
        }

        Color getCellColor(CellCoordinates coords){
            return (Color) cellMap.get(coords).getFill();
        }

        public void clickOnCell(Shape cell){
            if ( choosenAutomatonGroup.getSelectedToggle() == gameOfLifeRButton){
                if ( !isPlaying ){
                    if ( cell.getFill() == getCellColor(QuadState.DEAD)) {
                        cell.setFill(getCellColor(QuadState.YELLOW));
                    } else {
                        cell.setFill(getCellColor(QuadState.DEAD));
                    }
                }
            } else if ( choosenAutomatonGroup.getSelectedToggle() == wireWorldRButton){
                if ( !isPlaying ){
                    switch ((QuadState) getCellState( (Color) cell.getFill() )){
                        case DEAD:
                            cell.setFill(getCellColor(QuadState.YELLOW));
                            break;
                        case YELLOW:
                            cell.setFill(getCellColor(QuadState.RED));
                            break;
                        case RED:
                            cell.setFill(getCellColor(QuadState.GREEN));
                            break;
                        case GREEN:
                            cell.setFill(getCellColor(QuadState.DEAD));
                            break;
                    }
                }
            }
        }
    }


}
