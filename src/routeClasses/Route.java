package routeClasses;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс Route, объекты которого являются элементами коллекции.
 */
public class Route implements Comparable<Route> {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private LocationFrom from; //Поле не может быть null
    private LocationTo to; //Поле может быть null
    private double distance; //Поле не может быть null, Значение поля должно быть больше 1

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    /**
     * Instantiates a new Route.
     */
    public Route() {

    }

    /**
     * Instantiates a new Route.
     *
     * @param name         the name
     * @param creationDate the creation date
     * @param coordinates  the coordinates
     * @param from         the from
     * @param to           the to
     * @param distance     the distance
     */
    public Route(String name, ZonedDateTime creationDate, Coordinates coordinates, LocationFrom from, LocationTo to, double distance) {
        this.name = name;
        this.creationDate = creationDate;
        this.coordinates = coordinates;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
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
     * Gets creation date.
     *
     * @return the creation date
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Gets coordinates.
     *
     * @return the coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Gets from.
     *
     * @return the from
     */
    public LocationFrom getFrom() {
        return from;
    }

    /**
     * Gets to.
     *
     * @return the to
     */
    public LocationTo getTo() {
        return to;
    }

    /**
     * Gets distance.
     *
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets creation date.
     *
     * @param creationDate the creation date
     */
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Sets coordinates.
     *
     * @param coordinates the coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Sets from.
     *
     * @param from the from
     */
    public void setFrom(LocationFrom from) {
        this.from = from;
    }

    /**
     * Sets to.
     *
     * @param to the to
     */
    public void setTo(LocationTo to) {
        this.to = to;
    }

    /**
     * Sets distance.
     *
     * @param distance the distance
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    // Сортируем Route по id
    @Override
    public int compareTo(Route o) {
        return (int) (this.getId() - o.getId());
    }

    /**
     * Show values string.
     *
     * @return the string
     */
    public String showValues() {
        return "id=%s,name=%s,coordinatesX=%s,coordinatesY=%s,creationDate=%s,fromX=%s,fromY=%s,fromName=%s,distance=%s".formatted(
                this.getId(), this.getName(), this.getCoordinates().getX(), this.getCoordinates().getY(),
                this.getCreationDate().format(dateFormat), this.getFrom().getX(), this.getFrom().getY(), this.getFrom().getName(), this.getDistance())
                + (this.getTo() == null ? "" : ",toX=%s,toY=%s,toZ=%s,toName=%s".formatted(this.getTo().getX(),
                this.getTo().getY(), this.getTo().getZ(), this.getTo().getName()));
    }

    public String toString() {
        return "Route [%s]".formatted(this.showValues());
    }

    /**
     * Get serialized string string.
     *
     * @return the string
     */
    public String getSerializedString(){
        return "{%s}".formatted(this.showValues());
    }

}
