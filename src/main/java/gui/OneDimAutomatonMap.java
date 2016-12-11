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
        for (Map.Entry<CellCoordinates, Shape> entry : automatonMap.entrySet()) {
            if ( entry.getKey().getY() == 0) {
                Coords1D coords = new Coords1D(entry.getKey().getX());
                map.put(
                        coords,
                        getCellState(
                                getCellColor(
                                        entry.getKey())));
            }
        }

        return map;
    }

    @Override
    public void updateCellMap(Map<CellCoordinates, CellState> map) {

        for ( int i=context.getCellsHeigth()-1; i>0; i--){
            for (int j=0; j<context.getCellsWidth(); j++ ){
                getMap()
                        .get(new Coords2d(i, j))
                        .setFill(getCellColor(new Coords2d(i-1, j)));
            }
        }

        for (Map.Entry<CellCoordinates, CellState> entry : map.entrySet()) {
            // do wejsciowej wartosci zawierajaca kolo kolor jest ustawiany zgodnie ze stanem automatonMap o tej samej pozycji co kolo
            getMap().get(new Coords2d(entry.getKey().getX(), entry.getKey().getY())).setFill(getCellColor(map.get(entry.getKey())));
        }
    }
}
