package Simulation.Environment;

import java.io.Serializable;

/**
 * Represents a location in the environment.
 *
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class Location implements Serializable {

    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return true if the compareSubject has the same x and y values.
     */
    public boolean equals(Object compareSubject) {
        if (compareSubject instanceof Location other) {
            return this.x == other.getX() && this.y == other.getY();
        } else {
            return false;
        }
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
