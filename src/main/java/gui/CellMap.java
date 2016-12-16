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
 * Abstract class responsible for managing pane, which is used to draw Automaton
 * @author Michał Wąsowicz
 * @version 1.0
 */
public abstract class CellMap{

    /**
     * Specify size of cells
     */
    protected int cellSize = 12;

    /**
     * Two-dimensional array with Shapes
     */
    protected ArrayList<ArrayList<Shape>> automatonMap;
    /**
     * Map with Colors assigned to CellState
     */
    protected Map<CellState, Color> cellDictionary;
    protected Controller context;
    protected Pane simulationWindow;
    protected CellState defaultState;


    /**
     * @param context controller which control this CellMap
     */
    public CellMap(Controller context){
        this.context = context;
        simulationWindow = context.getSimulationWindow();

    }


    /**
     * Creates new CellMap with copy of previous map
     * @param previousMap used as model for new CellMap
     */
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

    /**
     * Change color of cell using CellDictionary
     * @param cell is clicked
     * @see #cellDictionary
     */
    public abstract void clickOnCell(Shape cell);

    /**
     * Add Color assigned to CellState in Map
     */
    protected void createCellDictionary() {
        cellDictionary = new HashMap<>();
        cellDictionary.put(defaultState, Color.LIGHTGRAY);
        cellDictionary.put(null, Color.DARKRED);
        cellDictionary.put(CellMapState.CLEAN, Color.WHITE);
    }

    /**
     * @param map is drawn on screen
     */
    public void updateCellMap(Map<CellCoordinates, CellState> map) {
        for ( int y=0; y<automatonMap.size(); y++){
            for ( int x=0; x<automatonMap.get(y).size(); x++){
                automatonMap.get(y).get(x).setFill( getCellColor( map.get(new Coords2d(x, y))));
            }
        }
    }

    /**
     * Clear automaton's pane (obviously)
     */
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


    /**
     * @param interval - parameter used to describe size of space between cells
     */
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
                addListenersToCell(cell);
                tmp.add(cell);
            }
            automatonMap.add((ArrayList<Shape>) tmp.clone());
        }
    }


    /**
     * Adds listener to cell
     * @param cell to this shape method add listener
     */
    protected void addListenersToCell(Shape cell){
        // if ctrl is clicked, then draw on pane
        cell.setOnMouseExited(t -> {
            if(t.isControlDown())
                clickOnCell(cell);
        });

        cell.setOnMouseClicked( event -> clickOnCell(cell));
    }

    /**
     * Updates clickOnCell when new type of Automaton is created
     */
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

    /**
     * @return map with cellCoordinates and states used by Automaton to initialize itself
     */
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


    /**
     * @param color
     * @return CellState based on Color from CellDictionary
     * @see #cellDictionary

     */
    protected CellState getCellState(Color color){
        for (Map.Entry<CellState, Color> entry : cellDictionary.entrySet()) {
            if ( entry.getValue() == color){
                return entry.getKey();
            }
        }

        // sth must be returned
        return defaultState;
    }

    /**
     * @param state
     * @return Color based on State from CellDictionary
     */
    protected Color getCellColor(CellState state){
        return cellDictionary.get(state);
    }

    /**
     * @param coords are used to find color in automatonMap
     * @return Color from autoamtonMap
     * @see #automatonMap
     */
    protected Color getCellColor(CellCoordinates coords){
        return (Color) automatonMap.get(coords.getY()).get(coords.getX()).getFill();
    }


    /**
     * @return #automatonMap
     * @see #automatonMap
     */
    protected ArrayList<ArrayList<Shape>> getMap(){
        return automatonMap;
    }

}

