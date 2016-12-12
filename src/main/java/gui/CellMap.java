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
        for ( int i=0; i<automatonMap.size(); i++){
            for ( int j=0; j<automatonMap.get(i).size(); j++){
                automatonMap.get(i).get(j).setFill( getCellColor( map.get(new Coords2d(i, j))));;
            }
        }
    }

    public void clearScreen(){
        for ( int i=0; i<automatonMap.size(); i++){
            for ( int j=0; j<automatonMap.get(i).size(); j++){
                automatonMap.get(i).get(j).setFill( getCellColor( BinaryState.ALIVE));
            }
        }

        createMap(context.getCellsWidth(), context.getCellsHeigth());
        drawMap();
        setCellsPositions();
    }

    public void drawMap(){
        for ( int i=0; i<automatonMap.size(); i++){
            for ( int j=0; j<automatonMap.get(i).size(); j++){
                simulationWindow.getChildren().add(automatonMap.get(i).get(j));
            }
        }
        simulationWindow.requestLayout();
    }


    public void setCellsPositions(int interval){
        for ( int i=0; i<automatonMap.size(); i++){
            for ( int j=0; j<automatonMap.get(i).size(); j++){
                automatonMap.get(i).get(j).setLayoutX((i)*cellSize+i*interval);
                automatonMap.get(i).get(j).setLayoutY((j)*cellSize+j*interval);
            }
        }
        simulationWindow.requestLayout();
    }

    public void setCellsPositions(){
        setCellsPositions(0);
    }

    public void createMap(int x, int y){
        automatonMap = new ArrayList<>();
        for (int i=0; i<x; i++){
            ArrayList<Shape> tmp = new ArrayList<>();
            for(int m=0; m<y; m++){
                Rectangle cell = new Rectangle( cellSize, cellSize, getCellColor(defaultState));

                // if ctrl is clicked, then draw on pane
                cell.setOnMouseExited(t -> {
                    if(t.isControlDown())
                        clickOnCell(cell);
                });

                cell.setOnMouseClicked( event -> clickOnCell(cell));

                tmp.add(cell);
            }
            automatonMap.add((ArrayList) tmp.clone());
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
        for ( int i=0; i<automatonMap.size(); i++){
            for ( int j=0; j<automatonMap.get(i).size(); j++){
                map.put(
                        new Coords2d(i, j),
                        getCellState(
                                getCellColor(
                                        new Coords2d(i, j))));
            }
        }

        return map;
    }

    protected void calculateCellSize(){
        cellSize = (
                (int) Math.min(simulationWindow.getWidth(),
                        simulationWindow.getHeight()))/
                ( Math.max(context.getCellsWidth(),
                        context.getCellsHeigth()));
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
        return (Color) automatonMap.get(coords.getX()).get(coords.getY()).getFill();
    }

    protected ArrayList<ArrayList<Shape>> getMap(){
        return automatonMap;
    }

}

