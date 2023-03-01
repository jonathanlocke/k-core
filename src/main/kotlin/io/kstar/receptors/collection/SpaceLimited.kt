package io.kstar.receptors.collection

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABILITY_UNDETERMINED
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality
import kotlin.math.max

/**
 * An object that can store a limited number of values.
 *
 *  * [hasRoomFor] - Returns true if this object has room for the given number of values
 *  * [onOutOfRoom] - Called if the object runs out of room
 *  * [roomLeft] - Returns the amount of room left, in values
 *  * [totalRoom] - Returns the total amount of room in this object, in values
 *
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = STABILITY_UNDETERMINED,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
interface SpaceLimited : Sized
{
    /**
     * @param values The number of values desired to add
     * @return True if the given number of values can be added
     */
    fun hasRoomFor(values: Int): Boolean
    {
        return if (values <= roomLeft())
        {
            true
        }
        else
        {
            onOutOfRoom(values)
            false
        }
    }

    /**
     * Called when some number of values cannot be added to this store
     *
     * @param values The number of values that could not be added
     */
    fun onOutOfRoom(values: Int)
    {
    }

    /**
     * Returns the amount of room remaining
     */
    fun roomLeft(): Int
    {
        return max(0, totalRoom() - size())
    }

    /**
     * Returns the number of values that can be held in this store
     *
     * @return The amount of room in this store
     */
    fun totalRoom(): Int
    {
        return Int.MAX_VALUE
    }
}
