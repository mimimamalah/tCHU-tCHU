/**
 * @author: Malak Lahlou Nabil (329571)
 * @author: Benslimane Mohamed (329500)
 */
package ch.epfl.tchu.gui;

import java.util.List;

/**
 * This enumerated type represents the possible languages for the game.
 */

public enum Language {

    FRANCAIS, ENGLISH, SPANISH;

    /**
     * The attribute ALL is a list containing all the languages.
     */

    public final static List<Language> ALL = List.of(Language.values());

}
