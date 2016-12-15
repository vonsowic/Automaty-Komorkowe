package gui;

import com.bearcave.automaty.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by miwas on 11.12.16.
 */
public class OneDimAutomatonMap extends CellMap {
    public OneDimAutomatonMap(Controller context) {
        super(context);
        defaultState = BinaryState.DEAD;
        initialize();
    }

    public OneDimAutomatonMap(CellMap previousMap) {
        super(previousMap);
        defaultState = BinaryState.DEAD;
        this.createCellDictionary();
        changeMap();
    }

    @Override
    protected void createCellDictionary() {
        super.createCellDictionary();
        cellDictionary.put(defaultState, Color.LIGHTGRAY);
        cellDictionary.put(BinaryState.ALIVE, Color.YELLOW);
    }

    @Override
    public void clickOnCell(Shape cell) {
        if ( context.isPlaying()) return;
        context.reset();
        if ( cell.getFill() == getCellColor(BinaryState.DEAD)) {
            cell.setFill(getCellColor(BinaryState.ALIVE));
        } else {
            cell.setFill(getCellColor(BinaryState.DEAD));
        }
    }

    @Override
    public Map<CellCoordinates, CellState> translateForAutomaton(){
        Map<CellCoordinates, CellState> map = new HashMap<>();
        for ( int x=0; x<automatonMap.get(0).size(); x++){
            Coords1D coords = new Coords1D(x);
            map.put(
                    coords,
                    getCellState(
                            getCellColor(
                                    new Coords2d(x, 0))));
        }

        return map;
    }

    @Override
    public void updateCellMap(Map<CellCoordinates, CellState> map) {
        for ( int y=automatonMap.size()-1; y>0; y--){ //iteracja po wierszach
            for ( int x=0; x<automatonMap.get(y).size(); x++){ //iteracja po kolumnach
                getMap()
                        .get(y)
                        .get(x)
                        .setFill(getCellColor(new Coords2d(x, y-1)));
            }
        }
        for(Map.Entry<CellCoordinates, CellState> entry : map.entrySet()){
            getMap()
                    .get(entry.getKey().getY())
                    .get(entry.getKey().getX())
                    .setFill(getCellColor(entry.getValue()));
        }
    }


}
