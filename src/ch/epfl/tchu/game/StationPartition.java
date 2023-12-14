/* 
 *Author: Mohamed Benslimane
 * Date :  13 mars 2021
*/

package ch.epfl.tchu.game;

import ch.epfl.tchu.Preconditions;

public final class StationPartition implements StationConnectivity {

    public static final class Builder {


        private final int[] linksBuild;

        /**
         * 
         * @param stationCount
         *            the number of stations. Builds a partition of stations
         *            with an identity between 0 and stationCount(excluded).
         * @Throws IllegalArgumentException if stationCount is strictly
         *         negative.
         * 
         */

        public Builder(int stationCount) {

            Preconditions.checkArgument(stationCount >= 0);

            linksBuild = new int[stationCount];

            for (int i = 0; i < stationCount; i++)
                linksBuild[i] = i;

        }

        /**
         * 
         * @param s1
         *            the first station
         * @param s2
         *            the seconde station
         * @return this: The builder Connects the two stations and their
         *         representant by attributing to one representant of the two
         *         stations, the representant of the other.
         */

        public Builder connect(Station s1, Station s2) {

            linksBuild[representative(s1.id())] = representative(s2.id());

            return this;

        }

        /**
         * 
         * @return an instance of a partition with the table of links between
         *         stations. Builds the flattened version of the partition.
         */

        public StationPartition build() {

            for (int i = 0; i < linksBuild.length; i++)
                linksBuild[i] = representative(i);

            return new StationPartition(linksBuild);

        }

        private int representative(int id) {

            while (linksBuild[id] != id)
                id = linksBuild[id];

            return id;

        }

        public int[] getStations() {

            return linksBuild;
        }

    }

    private final int[] links;

    private StationPartition(int[] links) {

        this.links = links;

    }

    /**
     * @return the boolean true when one of the two station's id is out of
     *         bounds and they have the same id. Or when the the two station's
     *         id are in the correct bounds and they have the same representant
     *         in the table of links which means that they are connected. In the
     *         other cases, the method returns false.
     */

    @Override
    public boolean connected(Station s1, Station s2) {

        boolean connected = false;

        if ((s1.id() > links.length - 1 || s2.id() > links.length - 1)) {
            if (s1.id() == s2.id())
                connected = true;
        } else {
            if (links[s1.id()] == links[s2.id()]) {
                connected = true;
            }
        }
        return connected;
    }

    public int[] getLiens() {
        return links;
    }
}
