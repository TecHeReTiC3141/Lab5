package routeClasses;

/**
 * Класс, представляющий поле locationFrom класса Route.
 */
public class LocationFrom {

    private Float x; //Поле не может быть null
    private Long y; //Поле не может быть null
    private String name; //Поле может быть null

    /**
     * Instantiates a new Location from.
     */
    public LocationFrom() {}

    /**
     * Instantiates a new Location from.
     *
     * @param x    the x
     * @param y    the y
     * @param name the name
     */
    public LocationFrom(Float x, Long y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public Float getX() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public Long getY() {
        return y;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(Float x) {
        this.x = x;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(Long y) {
        this.y = y;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

}
