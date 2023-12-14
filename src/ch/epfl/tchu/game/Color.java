/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.game;

import java.util.List;

/**
 * The listed type Color represents the eight colors of the game used to color the cars and the roads.
 */
public enum Color {
    
    BLACK,
    VIOLET,
    BLUE,
    GREEN,
    YELLOW,
    ORANGE,
    RED,
    WHITE;
    
    public final static List<Color> ALL = List.of(Color.values());
    public final static int COUNT = ALL.size();
    

}
