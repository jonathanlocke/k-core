@file:Suppress("unused")

package io.kstar.receptors.collection

import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality

/**
 * Interface to an object that can be treated like a stack
 *
 * @author Jonathan Locke
 */
@TypeQuality
(
    stability = STABILITY_UNDETERMINED,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
interface Stacked<Value> : Emptiness
{
    /**
     * Pushes the given value onto this stacked object
     */
    fun push(value: Value)

    /**
     * Pops a value from this stacked object
     * @throws Exception Thrown if the stack is empty
     */
    fun pop(): Value
}
