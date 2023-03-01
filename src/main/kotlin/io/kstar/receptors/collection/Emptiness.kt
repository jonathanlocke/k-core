package io.kstar.receptors.collection

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABILITY_UNDETERMINED
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality

/**
 * Identifies whether an object, such as a collection, is empty or non-empty.
 *
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = STABILITY_UNDETERMINED,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
fun interface Emptiness
{
    /**
     * Returns true if this object contains no values
     */
    fun isEmpty(): Boolean

    /**
     * Returns true if this object contains one or more values
     */
    fun isNonEmpty(): Boolean = !isEmpty()
}
