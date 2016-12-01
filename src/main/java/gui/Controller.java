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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

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

    Automaton automaton;
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

        cellMap = new CellMap( (int) rowSlider.getValue(), (int) columnSlider.getValue());
        cellMap.drawMap( simulationWindow );

        automaton = new GameOfLife((int) rowSlider.getValue(), (int) columnSlider.getValue(), cellMap.translateForAutomaton());

    }

    //////////////////////////// Uchwyty do kontrolek //////////////////////////////////////////////////////////////////
    /**
     * Uchwyt do przycisku "Jeden ruch"
     * @param actionEvent
     */
    public void oneMove(ActionEvent actionEvent) {
        automaton = automaton.nextState();
        cellMap.updateCellMap(automaton);
    }

    /**
     * Uchwyt do przycisku "Start"
     * @param actionEvent
     */
    public void startSimulation(ActionEvent actionEvent) {

    }


    public void setWidth(MouseEvent mouseEvent) {



    }
    ///////////////////////////////// Metody prywatne //////////////////////////////////////////////////////////////////

    private int getTimeLoop(){
        return (int) timeLoopSlider.getValue();
    }

    private void makeOneMove(){
        automaton = automaton.nextState();
    }

    private class CellMap{

        private int cellSize = 14;
        Map<CellCoordinates, Circle> cellMap;
        Map<CellState, Color> cellDictionary;


        CellMap(int x, int y){
            createCellDictionary();
            createMap(x, y);
            setCellsPositions();
        }

        private void createCellDictionary() {
            cellDictionary = new HashMap<>();
            cellDictionary.put(BinaryState.DEAD, Color.LIGHTGRAY);
            cellDictionary.put(BinaryState.ALIVE, Color.YELLOW);
        }

        void createMap(int x, int y){
            cellMap = new HashMap<>();
            for (int i=0; i<x; i++){
                for(int m=0; m<y; m++){
                    Coords2d coords = new Coords2d(i, m);
                    Circle circle = new Circle(cellSize/2, getCellColor(BinaryState.DEAD));

                    circle.setOnMouseClicked( event -> {
                        if ( !isPlaying ){
                            if ( circle.getFill() == getCellColor(BinaryState.DEAD)) {
                                circle.setFill(getCellColor(BinaryState.ALIVE));
                            } else {
                                circle.setFill(getCellColor(BinaryState.DEAD));
                            }
                        }
                    });

                    cellMap.put(coords, circle);
                }
            }
        }

        void updateCellMap(Automaton automaton){
            for (Map.Entry<CellCoordinates, Circle> entry : cellMap.entrySet()) {
                entry.getValue().setFill( getCellColor( automaton.getCellState(entry.getKey())));
            }
        }

        void drawMap(Pane pane){
            for (Map.Entry<CellCoordinates, Circle> entry : cellMap.entrySet()) {
                pane.getChildren().add(entry.getValue());
            }
            pane.requestLayout();
        }

        Map<CellCoordinates, CellState> translateForAutomaton(){
            Map<CellCoordinates, CellState> automatonMap = new HashMap<>();
            for (Map.Entry<CellCoordinates, Circle> entry : cellMap.entrySet()) {
                automatonMap.put(entry.getKey(), getCellState( getCellColor( entry.getKey())));
            }
            return automatonMap;
        }

        void setCellsPositions(){
            for (Map.Entry<CellCoordinates, Circle> entry : cellMap.entrySet()) {
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
            return BinaryState.DEAD;
        }

        Color getCellColor(CellState state){
            return cellDictionary.get(state);
        }

        Color getCellColor(CellCoordinates coords){
            return (Color) cellMap.get(coords).getFill();
        }

        Color getCellColor(int x, int y){
            return getCellColor(new Coords2d(x, y));
        }
    }


}
