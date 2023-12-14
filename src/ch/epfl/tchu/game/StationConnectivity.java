/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.game;

/**
 * The interface represents the connectivity of the player´s network 
 */

public interface StationConnectivity {
    
    /*
     * The method represents the connection between two stations
     * 
     * @param Station s1 
     * 
     * 
     * @param Station s2
     */
    
    boolean connected(Station s1, Station s2);

}
