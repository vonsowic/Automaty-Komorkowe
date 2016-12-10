package gui;

import com.bearcave.automaty.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by miwas on 08.12.16.
 */
public class CellMap{

    private int cellSize = 12;
    Map<CellCoordinates, Shape> cellMap;
    Map<CellState, Color> cellDictionary;
    Controller context;
    private Pane simulationWindow;

    public CellMap(Controller context){
        this.context = context;
        simulationWindow = context.getSimulationWindow();
        createCellDictionary();
        createMap(context.getCellsWidth(), context.getCellsHeigth());
        setCellsPositions();
    }


    public void updateCellMap(Map<CellCoordinates, CellState> automatonMap){
        CellState state = null;
        for (Map.Entry<CellCoordinates, Shape> entry : cellMap.entrySet()) {
            switch ( context.getSelectedGame() ){
                case Controller.GAMEOFLIFE:
                    state = translateCellFromAutomaton((BinaryState) automatonMap.get(entry.getKey()));
                    break;
                case Controller.WIREWORLD:
                    state = translateCellFromAutomaton((WireElectronState) automatonMap.get(entry.getKey()));
                    break;
            }
            // do wejsciowej wartosci zawierajaca kolo kolor jest ustawiany zgodnie ze stanem automatonMap o tej samej pozycji co kolo
            entry.getValue().setFill( getCellColor( state ));

        }
    }

    private void calculateCellSize(){

    }

    public void drawMap(Pane pane){
        for (Map.Entry<CellCoordinates, Shape> entry : cellMap.entrySet()) {
            pane.getChildren().add(entry.getValue());
        }
        pane.requestLayout();
    }

    public void setCellsPositions(){
        for (Map.Entry<CellCoordinates, Shape> entry : cellMap.entrySet()) {
            entry.getValue().setLayoutX((entry.getKey().getX()+1)*cellSize);
            entry.getValue().setLayoutY((entry.getKey().getY()+1)*cellSize);
        }
    }

    public void createMap(int x, int y){
        cellMap = new HashMap<>();
        for (int i=0; i<x; i++){
            for(int m=0; m<y; m++){
                Coords2d coords = new Coords2d(i, m);
                Rectangle cell = new Rectangle( cellSize, cellSize, getCellColor(QuadState.DEAD));

                // if ctrl is clicked, then draw on pane
                cell.setOnMouseExited(t -> {
                    if(t.isControlDown())
                        clickOnCell(cell);
                });

                cell.setOnMouseClicked( event -> clickOnCell(cell));

                cellMap.put(coords, cell);
            }
        }
    }

    public Map<CellCoordinates, CellState> translateForAutomaton(){
        Map<CellCoordinates, CellState> automatonMap = new HashMap<>();
        for (Map.Entry<CellCoordinates, Shape> entry : cellMap.entrySet()) {
            automatonMap.put(entry.getKey(), getAutomatonCellState(
                    getCellState(
                            getCellColor(
                                    entry.getKey()))));
        }

        return automatonMap;
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

    private CellState getAutomatonCellState(CellState quadstate) {

        //game of life
        switch (context.getSelectedGame()) {
            case Controller.GAMEOFLIFE:
                switch ((QuadState) quadstate) {
                    case YELLOW:
                        return BinaryState.ALIVE;
                    default:
                        return BinaryState.DEAD;
                }

            case Controller.WIREWORLD:
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

    private QuadState translateCellFromAutomaton(BinaryState state){
        switch (state){
            case ALIVE:
                return QuadState.YELLOW;
            default:
                return QuadState.DEAD;
        }
    }

    private QuadState translateCellFromAutomaton(WireElectronState state){
        switch (state){
            case WIRE:
                return QuadState.YELLOW;
            case ELECTRON_HEAD:
                return QuadState.RED;
            case ELECTRON_TAIL:
                return QuadState.GREEN;
            default:
                return QuadState.DEAD;
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
        if ( context.isPlaying() ) return;

        switch ( context.getSelectedGame()){
            case Controller.GAMEOFLIFE:
                if ( cell.getFill() == getCellColor(QuadState.DEAD)) {
                    cell.setFill(getCellColor(QuadState.YELLOW));
                } else {
                    cell.setFill(getCellColor(QuadState.DEAD));
                }
                break;

            case Controller.WIREWORLD:
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
                break;
        }
    }

    public void clearScreen(){
        for (Map.Entry<CellCoordinates, Shape> entry : cellMap.entrySet()) {
            entry.getValue().setFill( getCellColor( BinaryState.ALIVE));
        }
        createMap(context.getCellsWidth(), context.getCellsHeigth());
        drawMap( simulationWindow );
        setCellsPositions();
    }
}

