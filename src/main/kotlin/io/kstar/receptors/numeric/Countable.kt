@file:Suppress("unused")

package io.kstar.receptors.numeric

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE
import io.kstar.annotations.quality.Testing.UNTESTED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.values.Count


/**
 * Interface to something that has a [Count] value.
 *
 * @author  Jonathan Locke
 * @see Count
 */
@TypeQuality
(
    stability = STABLE,
    testing = UNTESTED,
    documentation = DOCUMENTED
)
interface Countable<T : Countable<T>> : Numeric<T>
{
    fun count(): Count = Count(asLong())
}
