@file:Suppress("unused")

package io.kstar.receptors.values

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.UNTESTED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.values.Bytes

/**
 * An interface to any object whose size can be measured in bytes.
 *
 * @author jonathanl (shibo)
 */
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = UNTESTED,
    documentation = DOCUMENTED
)
fun interface ByteSized
{
    /**
     * Returns the number of bytes in this object
     */
    fun bytes(): Bytes
}
