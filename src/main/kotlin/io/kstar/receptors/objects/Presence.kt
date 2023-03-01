@file:Suppress("unused")

package io.kstar.receptors.objects

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality

/**
 * Interface to an object which can contain a value or not
 *
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = STABLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
interface Presence
{
    /**
     * Returns true if this object's value is missing
     */
    val isAbsent: Boolean get() = !isPresent

    /**
     * Returns true if this object has a value
     */
    val isPresent: Boolean
}