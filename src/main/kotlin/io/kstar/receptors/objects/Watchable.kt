@file:Suppress("unused")

package io.kstar.receptors.objects

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality

/**
 * A value that can be watched. Calling [observe] returns the current value.
 *
 * @author jonathanl (shibo)
 */
@TypeQuality
(
    stability = STABLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
fun interface Watchable<Value>
{
    /**
     * Returns the observed value
     */
    fun observe(): Value
}
