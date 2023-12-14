/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ch.epfl.tchu.Preconditions;

/**
 * The class Trip represents a trip between a station of departure to a station
 * of arrival.
 */

public final class Trip {

    private final Station from, to;
    private final int points;

    /**
     * Builds a new trip between the two given stations and worth the given
     * number of points
     * 
     * @param Station
     *            from, the station of departure
     * @param Station
     *            to, the station of destination
     * @param int
     *            points, the points you can get by taking this trip
     * @throws IllegalArgumentException
     *             if points <=0
     */

    public Trip(Station from, Station to, int points) {

        Preconditions.checkArgument(points > 0);

        this.from = Objects.requireNonNull(from);
        this.to = Objects.requireNonNull(to);
        this.points = points;

    }

    /**
     * @param List<Station>
     *            from
     * @param List<Station>
     *            to
     * @param int
     *            points
     * @throws IllegalArgumentException
     *             if the sizes of the lists (from and to) equal zero
     * @return the list of all the possible trips going from one of the stations
     *         of the first list (from) to one of the stations of the second
     *         list (to), each one worth the given number of points
     */

    public static List<Trip> all(List<Station> from, List<Station> to,
            int points) {

        Preconditions.checkArgument(!from.isEmpty() && !to.isEmpty());
        Preconditions.checkArgument(points > 0);

        List<Trip> list = new ArrayList<Trip>();

        for (Station start : from) {
            for (Station end : to) {
                list.add(new Trip(start, end, points));
            }
        }

        return list;
    }

    /**
     * @return the station of departure
     */

    public Station from() {
        return from;
    }

    /**
     * @return the station of arrival
     */

    public Station to() {
        return to;
    }

    /**
     * @return the number of points worth the trip
     */

    public int points() {
        return points;
    }

    /**
     * @return the number of points gained when the two stations are connected,
     *         or the number of points lost when it is not the case.
     * 
     * @param StationConnectivity
     *            connectivity.
     */

    public int points(StationConnectivity connectivity) {
        return connectivity.connected(from, to) ? points : -points;

    }

}
