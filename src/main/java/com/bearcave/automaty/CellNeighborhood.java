package com.bearcave.automaty;

import java.util.Set;

/**
 * Created by miwas on 05.11.16.
 */
public interface CellNeighborhood {
    public Set<CellCoordinates> cellNeighbors(CellCoordinates cell);
}
