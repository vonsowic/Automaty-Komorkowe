package com.bearcave.automaty;

import java.util.Set;

/**
 * Interface used for getting neighbors of cell
 * @author Michał Wąsowicz
 * @version 1.0
 */
public interface CellNeighborhood {

    /**
     * @param cell - used as parameter to calculate neighbors' coordinates
     * @return set with neighbors' coordinates
     */
    Set<CellCoordinates> cellNeighbors(CellCoordinates cell);
}
