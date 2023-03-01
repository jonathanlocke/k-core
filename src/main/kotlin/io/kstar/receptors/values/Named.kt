@file:Suppress("unused")

package io.kstar.receptors.values

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality

/**
 * An object with a name. The default implementation returns the simple name of the class of this object
 *
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = STABLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
interface Named
{
    /**
     * Returns the name of this object. If this method is not overridden, the simple name of the class of the object
     * implementing this interface is used.
     */
    fun name(): String
    {
        return javaClass.simpleName
    }
}
