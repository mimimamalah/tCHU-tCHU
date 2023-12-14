/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.game;

import java.util.List;
import java.util.TreeSet;

import ch.epfl.tchu.Preconditions;

/**
 * The class Ticket represents a ticket that goes from a station to another or
 * others stations. It implements the interface "Comparable".
 */
public final class Ticket implements Comparable<Ticket> {

    private final List<Trip> trips;
    private final String s1;

    /**
     * Builds a ticket made up of the list of trips given and initializes the
     * class´s attributes
     * 
     * @param trips, the list of all possible trips.
     * 
     * @throws IllegalArgumentException
     *             if the list of trips is empty.
     * 
     * @throws IllegalArgumentException
     *             if the name of departure´s station is different from another
     *             one on the list.
     */

    public Ticket(List<Trip> trips) {

        Preconditions.checkArgument(!trips.isEmpty());

        for (Trip t : trips) 
            Preconditions.checkArgument(
                    trips.get(0).from().name().equals(t.from().name()));
        

        this.trips = List.copyOf(trips);

        s1 = computeText(", ", trips);

    }

    /**
     * Builds a ticket consisting of a single path, whose attributes are from,
     * to and points.
     * 
     * @param from, the departure station
     * 
     * @param to, the arrival station
     * 
     * @param points, the points worth the trip
     * 
     */

    public Ticket(Station from, Station to, int points) {
        this(List.of(new Trip(from, to, points)));

    }

    /**
     * @return the textual representation of the ticket
     */

    public String text() {
        return s1;

    }

    private static String computeText(String separator, List<Trip> trips) {
        TreeSet<String> s2 = new TreeSet<>();
        
        for (Trip t : trips) 
            s2.add(t.to().name() + " (" + t.points() + ")");
        

        if (s2.size() > 1) {
            return trips.get(0).from().name() + " - " + "{"
                    + String.join(separator, s2) + "}";
        } else {
            return String.format("%s - %s", trips.get(0).from().name(),
                    s2.last());
        }

    }

    /**
     * @param connectivity
     * 
     * @return the number of points gained or lost during the trip (depends on
     *         if the trip is completed or not)
     */

    public int points(StationConnectivity connectivity) {
        int maxPoints = Integer.MIN_VALUE;

        for (Trip t : trips) {
            if (t.points(connectivity) > maxPoints) {
                maxPoints = t.points(connectivity);
            }
        }

        return maxPoints;

    }

    /**
     * Compares the textual representation of two tickets.
     * 
     * @return a negative integer if the first string comes before the second in
     *         an alphabetic order a positive integer in the opposite case or 0
     *         if both are equal.
     */

    @Override
    public int compareTo(Ticket that) {
        return this.text().compareTo(that.text());
    }

    /**
     * @return the textual representation of the ticket as a String.
     */

    @Override
    public String toString() {
        return text();

    }

}
