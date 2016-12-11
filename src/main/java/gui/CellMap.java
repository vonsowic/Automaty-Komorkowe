package gui;

import com.bearcave.automaty.*;
import javafx.scene.control.Cell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by miwas on 08.12.16.
 */
public abstract class CellMap{

    protected int cellSize = 12;
    protected Map<CellCoordinates, Shape> automatonMap;
    protected Map<CellState, Color> cellDictionary;
    protected Controller context;
    protected Pane simulationWindow;
    protected CellState defaultState;

    public CellMap(Controller context){
        this.context = context;
        simulationWindow = context.getSimulationWindow();
    }

    public CellMap(CellMap previousMap){
        this.context = previousMap.context;
        this.simulationWindow = previousMap.simulationWindow ;
        this.automatonMap = previousMap.automatonMap;
    }

    protected void initialize(){
        createCellDictionary();
        createMap(context.getCellsWidth(), context.getCellsHeigth());
        setCellsPositions();
        drawMap();
    }


    public abstract void clickOnCell(Shape cell);

    protected void createCellDictionary() {
        cellDictionary = new HashMap<>();
        cellDictionary.put(defaultState, Color.LIGHTGRAY);
        cellDictionary.put(null, Color.DARKRED);
    }

    public void updateCellMap(Map<CellCoordinates, CellState> map) {
        for (Map.Entry<CellCoordinates, Shape> entry : automatonMap.entrySet()) {
            // do wejsciowej wartosci zawierajaca kolo kolor jest ustawiany zgodnie ze stanem automatonMap o tej samej pozycji co kolo
            entry.getValue().setFill( getCellColor( map.get(entry.getKey())));
        }
    }

    public void clearScreen(){
        for (Map.Entry<CellCoordinates, Shape> entry : automatonMap.entrySet()) {
            entry.getValue().setFill( getCellColor( BinaryState.ALIVE));
        }
        createMap(context.getCellsWidth(), context.getCellsHeigth());
        drawMap();
        setCellsPositions();
    }

    public void drawMap(){
        for (Map.Entry<CellCoordinates, Shape> entry : automatonMap.entrySet()) {
            simulationWindow.getChildren().add(entry.getValue());
        }
        simulationWindow.requestLayout();
    }

    public void setCellsPositions(){
        for (Map.Entry<CellCoordinates, Shape> entry : automatonMap.entrySet()) {
            entry.getValue().setLayoutX((entry.getKey().getX()+1)*cellSize);
            entry.getValue().setLayoutY((entry.getKey().getY()+1)*cellSize);
        }
    }

    public void createMap(int x, int y){
        automatonMap = new HashMap<>();
        for (int i=0; i<x; i++){
            for(int m=0; m<y; m++){
                Coords2d coords = new Coords2d(i, m);
                Rectangle cell = new Rectangle( cellSize, cellSize, getCellColor(defaultState));

                // if ctrl is clicked, then draw on pane
                cell.setOnMouseExited(t -> {
                    if(t.isControlDown())
                        clickOnCell(cell);
                });

                cell.setOnMouseClicked( event -> clickOnCell(cell));

                automatonMap.put(coords, cell);
            }
        }
    }

    public void changeMap(){
        for ( Map.Entry<CellCoordinates, Shape> entry : getMap().entrySet()){

            Shape cell = entry.getValue();
            // if ctrl is clicked, then draw on pane
            cell.setOnMouseExited(t -> {
                if(t.isControlDown())
                    clickOnCell(cell);
            });

            cell.setOnMouseClicked( event -> clickOnCell(cell));
        }
    }

    public Map<CellCoordinates, CellState> translateForAutomaton(){
        Map<CellCoordinates, CellState> map = new HashMap<>();
        for (Map.Entry<CellCoordinates, Shape> entry : automatonMap.entrySet()) {
            map.put(
                    entry.getKey(),
                    getCellState(
                            getCellColor(
                                    entry.getKey())));
        }

        return map;
    }

    protected void calculateCellSize(){

    }

    protected CellState getCellState(Color color){
        for (Map.Entry<CellState, Color> entry : cellDictionary.entrySet()) {
            if ( entry.getValue() == color){
                return entry.getKey();
            }
        }

        // sth must be returned
        return defaultState;
    }

    protected Color getCellColor(CellState state){
        return cellDictionary.get(state);
    }

    protected Color getCellColor(CellCoordinates coords){
        return (Color) automatonMap.get(coords).getFill();
    }

    protected Map<CellCoordinates, Shape> getMap(){
        return automatonMap;
    }
}

