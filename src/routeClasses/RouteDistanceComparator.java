package routeClasses;

import java.util.Comparator;

/**
 * Компаратор для сортировки коллекции по дистанции, используется в команде print_field_descending_distance
 */
public class RouteDistanceComparator implements Comparator<Route> {

    @Override
    public int compare(Route r1, Route r2) {
        return (int) (r1.getDistance() - r2.getDistance());
    }
}
