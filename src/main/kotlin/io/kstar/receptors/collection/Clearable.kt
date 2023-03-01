@file:Suppress("unused")

package io.kstar.receptors.collection

import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality

/**
 * Interface to an object that can be cleared
 *
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = STABILITY_UNDETERMINED,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
fun interface Clearable
{
    /**
     * Clears this object. Typically, an object implementing this interfaced has a collection to be cleared, but other
     * values might also be affected, such as indexes or buffers.
     */
    fun clear()
}