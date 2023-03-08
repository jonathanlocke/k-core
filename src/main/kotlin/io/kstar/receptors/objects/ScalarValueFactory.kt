@file:Suppress("unused")

package io.kstar.receptors.objects

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality

/**
 * A factory that creates a value object for the given scalar. The method [new] checks its argument
 * using [isValidScalar]. If the value is valid, it calls [onNew] to create the value.
 *
 * **Example**
 *
 * ```
 * class Confidence(val value: Double) : ValueFactory<Double, Confidence>
 * {
 *     override fun isValidScalar(scalar: Double): Boolean = scalar in 0.0..1.0
 *     override fun onNew(scalar: Double): Confidence = Confidence(scalar)
 * }
 * ```
 *
 * @param <Value> The type of object to create
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = STABLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
fun interface ScalarValueFactory<Scalar, Value>
{
    /**
     * Returns a new instance of the given type
     */
    fun new(scalar: Scalar): Value
    {
        require(isValidScalar(scalar)) { "$scalar is not valid" }
        return onNew(scalar)
    }

    /**
     * Returns true if the given new value is valid
     */
    fun isValidScalar(scalar: Scalar): Boolean = true

    /**
     * Returns a new instance of the given type
     */
    fun onNew(scalar: Scalar): Value
}
