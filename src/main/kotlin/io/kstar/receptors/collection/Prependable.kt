@file:Suppress("unused")

package io.kstar.receptors.collection

import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.language.Iterators.asList

/**
 * An object, often a collection or related type, to which objects can be prepended.
 *
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = STABILITY_UNDETERMINED,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
interface Prependable<Value> : SpaceLimited
{
    /**
     * Adds the given value
     *
     * @return True if the prepending succeeded, false otherwise
     */
    fun onPrepend(value: Value)

    /**
     * Adds the given value
     *
     * @return True if the prepending succeeded, false otherwise
     */
    fun prepend(value: Value): Prependable<Value>
    {
        if (hasRoomFor(1))
        {
            onPrepend(value)
        }
        return this
    }

    /**
     * @param values A sequence of values to prepend, in order
     * @return True if the prepending succeeded, false otherwise
     */
    fun prependAll(values: Iterator<Value>): Prependable<Value>
    {
        for (value in values.asList().reversed())
        {
            prepend(value)
        }
        return this
    }

    /**
     * @param values A sequence of values to prepend, in order
     * @return True if the prepending succeeded, false otherwise
     */
    fun prependAll(values: Iterable<Value>): Prependable<Value> = prependAll(values.iterator())

    /**
     * @param values A sequence of values to prepend, in order
     * @return True if the prepending succeeded, false otherwise
     */
    fun prependAll(values: Collection<Value>): Prependable<Value> = prependAll(values.iterator())

    /**
     * @param values A sequence of values to prepend, in order
     * @return True if the prepending succeeded, false otherwise
     */
    fun prependAll(values: Array<Value>): Prependable<Value> = prependAll(values.toList())

    /**
     * Prepends the given value if it is not null
     *
     * @param value The value to prepend
     * @return True if the value was added, false otherwise
     */
    fun prependIfNotNull(value: Value?): Prependable<Value>
    {
        if (value != null)
        {
            prepend(value)
        }
        return this
    }
}