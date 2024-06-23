package ana.epj2.model;

/**
 * Represents a location in the e-mobility system.
 */
public class Location {
    private int x;
    private int y;
    /**
     * Constructs a new Location with the specified coordinates.
     *
     * @param x the x-coordinate of the location
     * @param y the y-coordinate of the location
     */
    public Location(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    /**
     * Returns a string representation of the object.
     * @return a string representation of the object
     */
    @Override
    public String toString()
    {
        return "["+ x +"," + y + "]";
    }
}
