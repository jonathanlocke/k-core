@file:Suppress("unused")

package io.kstar.receptors.objects

import io.kstar.receptors.code.Callback
import java.util.function.Function

/**
 * Interface for objects that can copy themselves. The [.copy] method makes a copy. The
 * [.applyToCopy] creates a copy that is transformed by the given function. The method
 * [.copy] creates a copy that is mutated by the given callback.
 *
 * @author Jonathan Locke
 */
fun interface Copyable<Value>
{
    /**
     * Returns a copy of this object
     *
     * @return The copy
     */
    fun copy(): Value

    /**
     * Returns a copy of this object as modified by the given mutator
     *
     * @param mutator the function
     * @return The copy
     */
    fun copy(mutator: Callback<Value>): Value
    {
        val copy = copy()
        mutator.call(copy)
        return copy
    }

    /**
     * Returns a copy of this object as transformed by the given function
     *
     * @param function the function
     * @return The copy
     */
    fun applyToCopy(function: Function<Value, Value>): Value
    {
        return function.apply(copy())
    }
}