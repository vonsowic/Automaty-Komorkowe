package gui;

import com.bearcave.automaty.*;
import javafx.scene.control.Cell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by miwas on 08.12.16.
 */
public abstract class CellMap{

    protected int cellSize = 12;
    protected ArrayList<ArrayList<Shape>> automatonMap;
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
        createMap(context.getRowQuantity(), context.getColumnQuantity());
        setCellsPositions();
        drawMap();
    }


    public abstract void clickOnCell(Shape cell);

    protected void createCellDictionary() {
        cellDictionary = new HashMap<>();
        cellDictionary.put(defaultState, Color.LIGHTGRAY);
        cellDictionary.put(null, Color.DARKRED);
        cellDictionary.put(CellMapState.CLEAN, Color.WHITE);
    }

    public void updateCellMap(Map<CellCoordinates, CellState> map) {
        for ( int y=0; y<automatonMap.size(); y++){
            for ( int x=0; x<automatonMap.get(y).size(); x++){
                automatonMap.get(y).get(x).setFill( getCellColor( map.get(new Coords2d(x, y))));
            }
        }
    }

    public void clearScreen(){
        for ( int y=0; y<automatonMap.size(); y++){
            for ( int x=0; x<automatonMap.get(y).size(); x++){
                automatonMap.get(y).get(x).setFill( getCellColor( CellMapState.CLEAN));
            }
        }

        createMap(context.getRowQuantity(), context.getColumnQuantity());
        drawMap();
        setCellsPositions();
    }

    private void drawMap(){
        for ( int y=0; y<automatonMap.size(); y++){
            for ( Shape cell : automatonMap.get(y)){
                simulationWindow.getChildren().add(cell);
            }
        }
        simulationWindow.requestLayout();
    }


    public void setCellsPositions(int interval){
        for ( int y=0; y<automatonMap.size(); y++){
            for ( int x=0; x<automatonMap.get(y).size(); x++){
                automatonMap.get(y).get(x).setLayoutX((x)*cellSize+x*interval);
                automatonMap.get(y).get(x).setLayoutY((y)*cellSize+y*interval);
            }
        }
        simulationWindow.requestLayout();
    }

    private void setCellsPositions(){
        setCellsPositions(0);
    }

    private void createMap(int numberOfRows, int numberOfColumns){
        automatonMap = new ArrayList<>();
        for (int y=0; y<numberOfRows; y++){
            ArrayList<Shape> tmp = new ArrayList<>();
            for(int x=0; x<numberOfColumns; x++){
                Rectangle cell = new Rectangle( cellSize, cellSize, getCellColor(defaultState));

                // if shift is clicked, then draw on pane
                cell.setOnMouseExited(t -> {
                    if(t.isShiftDown())
                        clickOnCell(cell);
                });

                cell.setOnMouseClicked( event -> clickOnCell(cell));

                tmp.add(cell);
            }
            automatonMap.add((ArrayList<Shape>) tmp.clone());
        }
    }

    public void changeMap(){

        for ( int i=0; i<automatonMap.size(); i++){
            for ( int j=0; j<automatonMap.get(i).size(); j++){
                Shape cell = automatonMap.get(i).get(j);
                // if ctrl is clicked, then draw on pane
                cell.setOnMouseExited(t -> {
                    if(t.isControlDown())
                        clickOnCell(cell);
                });

                cell.setOnMouseClicked( event -> clickOnCell(cell));
            }
        }
    }

    public Map<CellCoordinates, CellState> translateForAutomaton(){
        Map<CellCoordinates, CellState> map = new HashMap<>();
        for ( int y=0; y<automatonMap.size(); y++){
            for ( int x=0; x<automatonMap.get(y).size(); x++){
                map.put(
                        new Coords2d(x, y),
                        getCellState(
                                getCellColor(
                                        new Coords2d(x, y))));
            }
        }

        return map;
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
        return (Color) automatonMap.get(coords.getY()).get(coords.getX()).getFill();
    }

    protected ArrayList<ArrayList<Shape>> getMap(){
        return automatonMap;
    }

}

