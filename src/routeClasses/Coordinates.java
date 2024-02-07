package routeClasses;

/**
 * Класс, представляющий поле coordinates класса Route.
 */
public class Coordinates {

    private long x;
    private double y;

    /**
     * Instantiates a new Coordinates.
     */
    public Coordinates() {
    }

    /**
     * Instantiates a new Coordinates.
     *
     * @param x the x
     * @param y the y
     */
    public Coordinates(long x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public long getX() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(long x) {
        this.x = x;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(double y) {
        this.y = y;
    }
}
