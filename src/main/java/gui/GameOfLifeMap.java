package gui;

import com.bearcave.automaty.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;


/**
 * Created by miwas on 10.12.16.
 */
public class GameOfLifeMap extends CellMap {

    public GameOfLifeMap(Controller context) {
        super(context);
        defaultState = BinaryState.DEAD;
        initialize();
    }

    public GameOfLifeMap(CellMap previousMap) {
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
        if ( context.isPlaying() ) return;
        context.reset();
        if ( cell.getFill() == getCellColor(BinaryState.DEAD)) {
            cell.setFill(getCellColor(BinaryState.ALIVE));
        } else {
            cell.setFill(getCellColor(BinaryState.DEAD));
        }
    }
}
