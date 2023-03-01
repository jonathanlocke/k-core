@file:Suppress("unused")

package io.kstar.receptors.collection

import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality

/**
 * Interface to an object that supports writing values
 *
 * @author  Jonathan Locke
 * @see Indexable
 */
@TypeQuality
(
    stability = STABILITY_UNDETERMINED,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
interface WriteIndexable<Value>
{
    /**
     * Returns the value for the given index
     */
    operator fun set(index: Int, value: Value): Value
}
