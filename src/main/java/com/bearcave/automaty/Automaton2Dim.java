package com.bearcave.automaty;

/**
 * Created by miwas on 05.11.16.
 */
public abstract class Automaton2Dim extends Automaton {

    private int width = 0;
    private int height = 0;

    protected boolean hasNextCoordinates(CellCoordinates coords) {
        return ( coords.getX()<width-1 || coords.getY()<height-1);
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
        if ( cellCoordinates.getY() < this.height-1){
            return new Coords2d(cellCoordinates.getX(), cellCoordinates.getY()+1);
        } else if ( cellCoordinates.getX() < this.width-1 ) {
            return new Coords2d(cellCoordinates.getX()+1, 0);
        } else return null;

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
