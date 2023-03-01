@file:Suppress("unused")

package io.kstar.receptors.numeric

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality

/**
 * Interface to an object that can logically be zero or non-zero.
 *
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = STABLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
fun interface Zeroable
{
    fun isZero(): Boolean

    val isNonZero: Boolean get() = !isZero()
}