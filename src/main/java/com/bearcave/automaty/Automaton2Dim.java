package com.bearcave.automaty;

/**
 * Created by miwas on 05.11.16.
 */
public abstract class Automaton2Dim extends Automaton {

    private int width = 0;
    private int height = 0;

    protected boolean hasNextCoordinates(CellCoordinates coords) {
        if ( coords.getWidth()<width && coords.getHeight()<height){
            return true;
        } else {
            return false;
        }
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    protected CellCoordinates initialCoordinates() {
        return new Coords2d(0, 0);
    }

    /**
     *
     * @param cellCoordinates
     * @return pozycja nastepnej komorki
     */
    protected CellCoordinates nextCoordinates(CellCoordinates cellCoordinates) {
        if ( this.hasNextCoordinates(cellCoordinates)){
            if ( cellCoordinates.getHeight() < this.height){
                return new Coords2d( cellCoordinates.getWidth()+1, cellCoordinates.getHeight());
            } else if (cellCoordinates.getWidth() < this.width) {
                return new Coords2d( 0, cellCoordinates.getHeight()+1);
            } else return null;
        } else {
            return null;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
