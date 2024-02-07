package routeClasses;


/**
 * Класс, представляющий поле locationFrom класса Route.
 */
public class LocationTo {

    private Integer x; //Поле не может быть null
    private Float y; //Поле не может быть null
    private float z;
    private String name; //Длина строки не должна быть больше 875, Поле не может быть null

    /**
     * Instantiates a new Location to.
     */
    public LocationTo() {}

    /**
     * Instantiates a new Location to.
     *
     * @param x    the x
     * @param y    the y
     * @param z    the z
     * @param name the name
     */
    public LocationTo(Integer x, Float y, float z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public Integer getX() {
        return x;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public Float getY() {
        return y;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(Float y) {
        this.y = y;
    }

    /**
     * Gets z.
     *
     * @return the z
     */
    public float getZ() {
        return z;
    }

    /**
     * Sets z.
     *
     * @param z the z
     */
    public void setZ(float z) {
        this.z = z;
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
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

}
