/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Trail {

    private final List<Route> routes;
    private final Station station1;
    private final Station station2;
    private final int length;
    private static List<Route> trailRoutes;

    private Trail(Station station1, Station station2, List<Route> routes) {

        this.station1 = station1;
        this.station2 = station2;

        this.routes = routes;

        int tempLength = 0;
        for (Route route : routes)
            tempLength += route.length();

        length = tempLength;
    }

    /**
     *
     * @param
     *            routes: A list of all the given roads.
     *
     * @return a trail: The longest path of all the given roads.
     *
     */

    public static Trail longest(List<Route> routes) {

        Trail trail = new Trail(null, null, List.of());

        if (routes == null || routes.isEmpty())
            return trail;

        List<Trail> cs = new ArrayList<Trail>();

        for (Route r : routes) {

            cs.add(new Trail(r.station1(), r.station2(), List.of(r)));

            cs.add(new Trail(r.station2(), r.station1(), List.of(r)));

        }

        List<Trail> allTrails = new ArrayList<Trail>();

        while (!cs.isEmpty()) {

            allTrails.addAll(cs);

            List<Trail> cs1 = new ArrayList<Trail>();

            cs.forEach(t -> {

                routes.forEach(r -> {

                    List<Route> routes1 = new ArrayList<>(List.of());

                    if (!t.routes.contains(r)) {
                        routes1.addAll(t.routes);
                        routes1.add(r);

                    }

                    if (r.station1().equals(t.station2()))

                        cs1.add(new Trail(t.station1(), r.station2(), routes1));

                    if (r.station2().equals(t.station2()))

                        cs1.add(new Trail(t.station1(), r.station1(), routes1));

                });

            });

            cs = cs1;
        }

        trail = allTrails.get(0);

        for (Trail t : allTrails)
            if (t.length > trail.length)
                trail = t;

        trailRoutes = trail.routes;

        return trail;
    }

    /**
     *
     * @return int length: the length of the trail.
     */

    public int length() {

        return length;
    }

    /**
     *
     * @return Station station1 if the length of the trail is different than 0.
     *         Otherwise, return null.
     */

    public Station station1() {

        return length != 0 ? station1 : null;
    }

    /**
     *
     * @return Station station2 if the length of the trail is different than 0.
     *         Otherwise, return null.
     */

    public Station station2() {

        return length != 0 ? station2 : null;

    }

    /**
     * @return "The string is empty" if the liste of given routes is null or its
     *         size equals to 0 Otherwise, the method returns the textual
     *         representation of the trail with its departure's station, the
     *         arrival's station and the length of the trail.
     */

    public String toString() {

        return routes.size() != 0 && routes != null ? station1.name() + " - "
                + station2.name() + " (" + length + ")" : "The string is empty";
    }

    public List<Route> getTrailRoutes(){
        return trailRoutes;
    }

}