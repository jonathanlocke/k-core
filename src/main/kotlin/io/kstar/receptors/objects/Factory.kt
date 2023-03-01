@file:Suppress("unused")

package io.kstar.receptors.objects

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality

/**
 * A factory that creates an object.
 **
 * @param <Value> The type of object to create
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = STABLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
fun interface Factory<Value>
{
    /**
     * Returns a new instance of the given type
     */
    fun new(): Value
    {
        return onNew()
    }

    /**
     * Returns a new instance of the given type
     */
    fun onNew(): Value
}