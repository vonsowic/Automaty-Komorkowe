package gui;

import com.bearcave.automaty.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;


/**
 * Created by miwas on 10.12.16.
 */
public class WireWorldMap extends CellMap {
    public WireWorldMap(Controller context) {
        super(context);
        defaultState = WireElectronState.VOID;
        initialize();
    }

    public WireWorldMap(CellMap previousMap) {
        super(previousMap);
        defaultState = WireElectronState.VOID;
        this.createCellDictionary();
        changeMap();
    }

    @Override
    protected void createCellDictionary() {
        super.createCellDictionary();
        cellDictionary.put(defaultState, Color.LIGHTGRAY);
        cellDictionary.put(WireElectronState.WIRE, Color.YELLOW);
        cellDictionary.put(WireElectronState.ELECTRON_TAIL, Color.GREEN);
        cellDictionary.put(WireElectronState.ELECTRON_HEAD, Color.RED);
    }

    @Override
    public void clickOnCell(Shape cell) {
        if ( context.isPlaying() ) return;
        context.reset();
        switch ((WireElectronState) getCellState((Color) cell.getFill() )){
            case VOID:
                cell.setFill(getCellColor(WireElectronState.WIRE));
                break;
            case WIRE:
                cell.setFill(getCellColor(WireElectronState.ELECTRON_HEAD));
                break;
            case ELECTRON_HEAD:
                cell.setFill(getCellColor(WireElectronState.ELECTRON_TAIL));
                break;
            default:
                cell.setFill(getCellColor(WireElectronState.VOID));
        }
    }
}
