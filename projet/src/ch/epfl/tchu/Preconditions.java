/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu;

/**
 *  The class Preconditions either checks the conditions that have to be verified 
 *  or throws an exception.
 */

public final class Preconditions {

    private Preconditions() {

    }

    /**
     * @throws IllegalArgumentException
     *             If the boolean "shouldBeTrue" is false.
     */

    public static void checkArgument(boolean shouldBeTrue) {
        if (!shouldBeTrue)
            throw new IllegalArgumentException();

    }

}
