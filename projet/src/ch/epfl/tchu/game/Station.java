/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.game;

import ch.epfl.tchu.Preconditions;


/**
 * The class Station represents a station used in the game.
 */
public final class Station {

    private final int id;
    private final String name;

    /**
     * @Builds a station with the given identification number and name.
     * 
     * @param id, the identification number
     * 
     * @param name, the name of the station.
     * 
     * @throws IllegalArgumentException if id<0.
     */

    public Station(int id, String name) {
        Preconditions.checkArgument(id >= 0);
        this.id = id;
        this.name = name;

    }

    /**
     * @return the identification number
     */
    public int id() {
        return id;
    }

    /**
     * @return the Station's name
     */
    public String name() {
        return name;
    }

    /**
     * @return the Station's name by overriding the method toString()
     */
    @Override
    public String toString() {
        return name;
    }

}
