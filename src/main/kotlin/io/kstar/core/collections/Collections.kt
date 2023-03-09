@file:Suppress("unused")

package io.kstar.core.collections

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality

/**
 * Collection utility methods
 *
 * @author jonathanl (shibo)
 */
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
object Collections
{
    /**
     * Returns a value from this collection of values, or null if the collection is empty.
     * If the collection is ordered, the first value will be returned, otherwise, which
     * value is returned is not defined.
     *
     * @return A value from the collection, or null if the collection is empty
     */
    fun <T> Collection<T>.pickOne(): T?
    {
        return if (isNotEmpty()) iterator().next() else null
    }

    /**
     * Adds the given value to this collection if it is not null
     *
     * @return True if the value was added, false otherwise
     */
    fun <T> MutableCollection<T>.addIfNotNull(value: T?): Boolean
    {
        if (value != null)
        {
            return add(value)
        }
        return false
    }
}