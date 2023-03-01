@file:Suppress("unused")

package io.kstar.receptors.collection

import io.kstar.annotations.quality.Documentation
import io.kstar.annotations.quality.Stability
import io.kstar.annotations.quality.Testing
import io.kstar.annotations.quality.TypeQuality

/**
 * An interface for any object that can contain values, typically a collection.
 *
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = Stability.STABILITY_UNDETERMINED,
    testing = Testing.TESTING_NOT_NEEDED,
    documentation = Documentation.DOCUMENTED
)
fun interface Container<Value>
{
    /**
     * Returns true if the given value is contained in this object
     */
    operator fun contains(value: Value): Boolean

    /**
     * @param values The values to check
     * @return True if all values are contained by this object
     */
    fun containsAll(values: Iterable<Value>): Boolean = values.all { contains(it) }

    /**
     * @param values The values to check
     * @return True if none of the given values are contained by this object
     */
    fun containsNone(values: Iterable<Value>): Boolean = values.none { contains(it) }
}
