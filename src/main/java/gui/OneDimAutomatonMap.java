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
        for ( int i=0; i<automatonMap.size(); i++){
            Coords1D coords = new Coords1D(i);
            map.put(
                    coords,
                    getCellState(
                            getCellColor(
                                    new Coords2d(i, 0))));
        }

        return map;
    }

    @Override
    public void updateCellMap(Map<CellCoordinates, CellState> map) {
        for ( int j=context.getCellsHeigth()-1; j>0; j--){
            for ( int i=0; i<automatonMap.get(i).size(); i++){
                getMap().get(i).get(j).setFill(getCellColor(new Coords2d(i, j-1)));
            }
        }
        for(Map.Entry<CellCoordinates, CellState> entry : map.entrySet()){
            getMap().get(entry.getKey().getX()).get(0).setFill(getCellColor(entry.getValue()));
        }
    }
}
