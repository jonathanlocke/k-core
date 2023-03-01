@file:Suppress("unused")

package io.kstar.receptors.collection

import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality

/**
 * An object, often a collection or related type, to which objects can be appended. Provides default implementations for
 * appending values from objects that implement [Iterable] or [Iterator]. Note that all Java collections are
 * [Iterable], so they can be appended with [.appendAll]
 *
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = STABILITY_UNDETERMINED,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
interface Appendable<Value> :
    SpaceLimited,
    Stacked<Value>
{
    /**
     * Appends the given value
     *
     * @return True if the value was appended, false otherwise
     */
    fun append(value: Value): Appendable<Value>
    {
        if (hasRoomFor(1))
        {
            onAppend(value)
        }
        return this
    }

    /**
     * Appends the given values
     *
     * @param values A sequence of values to append
     * @return True if all values were appended, false otherwise
     */
    fun appendAll(values: Iterator<Value>): Appendable<Value>
    {
        values.forEach { append(it) }
        return this
    }

    /**
     * Appends the given values
     *
     * @param values A sequence of values to append
     * @return True if all values were appended, false otherwise
     */
    fun appendAll(values: Array<Value>): Appendable<Value>
    {
        values.forEach { append(it) }
        return this
    }

    /**
     * Appends the given values
     *
     * @param values A sequence of values to append
     * @return Self reference for chaining
     */
    fun appendAll(values: Iterable<Value>): Appendable<Value> = appendAll(values.iterator())

    /**
     * Appends the given values
     *
     * @param values A sequence of values to append
     * @return Self reference for chaining
     */
    fun appendAll(values: Collection<Value>): Appendable<Value> = appendAll(values.iterator())

    /**
     * Appends the given value
     */
    fun onAppend(value: Value)

    /**
     * Pushes the given value onto this appendable
     *
     * @param value The value to push
     */
    override fun push(value: Value)
    {
        append(value)
    }
}
