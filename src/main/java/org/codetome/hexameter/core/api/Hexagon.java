package org.codetome.hexameter.core.api;

import java.util.List;

import org.codetome.hexameter.core.api.contract.SatelliteData;
import org.codetome.hexameter.core.backport.Optional;

/**
 * Represents a Hexagon.
 * <em>Please note</em> that all coordinates are relative to the {@link HexagonalGrid} containing this {@link Hexagon}.
 */
public interface Hexagon<T extends SatelliteData> {
	public static final short[] triangles = new short[] {
		0, 1, 2,
		0, 2, 5,
		2, 3, 5,
		3, 4, 5
	};
	
    /**
     * Returns an unique {@link String} representing this {@link Hexagon}.
     */
    String getId();

    /**
     * Returns a list containing the {@link Point}s of this {@link Hexagon}.
     */
    List<Point> getPoints();
    
    /**
     * Returns an array of the vertices of this {@link Hexagon}.
     */
    List<Double> getVertices();
    
    /**
     * Returns a rectangle defining the <strong>external</strong> boundary box of this {@link Hexagon}
     * (a rectangle that hits the 2 pointy corners and the 2 flat sides).
     */
    Rectangle getExternalBoundingBox();
    
    /**
     * Returns a rectangle defining the <strong>internal</strong> boundary box of this {@link Hexagon}
     * (the biggest rectangle that hits the outline of the {@link Hexagon} exactly 4 times).
     */
    Rectangle getInternalBoundingBox();

    /**
     * Returns the {@link CubeCoordinate} of this {@link Hexagon}.
     */
    CubeCoordinate getCubeCoordinate();

    /**
     * Returns this {@link Hexagon}'s <b>x</b> (cube) coordinate on the {@link HexagonalGrid}.
     */
    int getGridX();

    /**
     * Returns this {@link Hexagon}'s <b>y</b> coordinate on the {@link HexagonalGrid}.
     * The Y coordinate is not present in the cube model but it is in the cube model.
     * This method is just for convenience.
     */
    int getGridY();

    /**
     * Returns this {@link Hexagon}'s <b>z</b> (cube) coordinate on the {@link HexagonalGrid}.
     */
    int getGridZ();

    /**
     * Returns the center <b>x</b> (pixel) coordinate of this {@link Hexagon}.
     */
    double getCenterX();

    /**
     * Returns the center <b>y</b> (pixel) coordinate of this {@link Hexagon}.
     *
     * @return center y
     */
    double getCenterY();

    /**
     * Returns this {@link Hexagon}'s satellite data.
     */
    Optional<T> getSatelliteData();

    /**
     * Can be used to add arbitrary satellite data to a {@link Hexagon}.
     */
    void setSatelliteData(T data);

    /**
     * Clears the satellite data of this Hexagon.
     */
    void clearSatelliteData();

}
