/**
 * @author: Malak Lahlou Nabil (329571)
 * @author: Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.net;

/**
 * The enumerated type MessageId enumerates the types of messages that the server can send to clients.
 * These messages correspond directly to the methods of the Player interface.
 */

public enum MessageId {

    INIT_PLAYERS,
    RECEIVE_INFO,
    UPDATE_STATE,
    SET_INITIAL_TICKETS,
    CHOOSE_INITIAL_TICKETS,
    NEXT_TURN,
    CHOOSE_TICKETS,
    DRAW_SLOT,
    ROUTE,
    CARDS,
    CHOOSE_ADDITIONAL_CARDS,
    SHOW_PODIUM

}
