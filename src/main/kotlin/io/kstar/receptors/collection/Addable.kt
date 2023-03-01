@file:Suppress("unused")

package io.kstar.receptors.collection

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABILITY_UNDETERMINED
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.receptors.objects.Matcher
import io.kstar.receptors.objects.Matcher.Companion.matchAll


/**
 * An object, often a collection or related type, to which objects can be added. Provides default implementations for
 * adding values from objects that implement [Iterable] or [Iterator]. Note that all Java collections are
 * [Iterable], so they can be added with [.addAll]
 *
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = STABILITY_UNDETERMINED,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
interface Addable<Value> : SpaceLimited
{
    /**
     * Adds the given value
     *
     * @return True if the value was added
     */
    fun add(value: Value): Boolean
    {
        return hasRoomFor(1) && onAdd(value)
    }

    /**
     * Adds the given values
     *
     * @param values A sequence of values to add
     * @return True if all values were added, false otherwise
     */
    fun addAll(values: Iterable<Value>): Boolean
    {
        return addAll(values.iterator())
    }

    /**
     * Adds the given values
     *
     * @param values A sequence of values to add
     * @return True if all values were added, false otherwise
     */
    fun addAll(values: Iterator<Value>): Boolean
    {
        return addAllMatching(values, matchAll()) > 0
    }

    /**
     * Adds the given values to this store
     *
     * @param values The values to add
     * @return True if the values were added, false otherwise
     */
    fun addAll(values: Collection<Value>): Boolean
    {
        return if (hasRoomFor(values.size))
        {
            addAll(values.iterator())
        }
        else false
    }

    /**
     * Adds the given values
     *
     * @param values The values to add
     * @return True if all values were added, false otherwise
     */
    fun addAll(values: Array<Value>): Boolean
    {
        return addAllMatching(values, matchAll()) >= 0
    }

    /**
     * Adds the given values
     *
     * @param values A sequence of values to add
     * @return The number of values added, or -1 if not all values could be added
     */
    fun addAllMatching(values: Iterable<Value>, matcher: Matcher<Value>): Int
    {
        return addAllMatching(values.iterator(), matcher)
    }

    /**
     * Adds the given values
     *
     * @param values A sequence of values to add
     * @return The number of values added, or -1 if not all values could be added
     */
    fun addAllMatching(values: Collection<Value>, matcher: Matcher<Value>): Int
    {
        return if (hasRoomFor(values.size))
        {
            addAllMatching(values.iterator(), matcher)
        }
        else -1
    }

    /**
     * Adds the given values
     *
     * @param values A sequence of values to add
     * @return The number of values added, or -1 if not all values could be added
     */
    fun addAllMatching(values: Iterator<Value>, matcher: Matcher<Value>): Int
    {
        val added = 0
        while (values.hasNext())
        {
            val value = values.next()
            if (matcher.matches(value))
            {
                if (!add(value))
                {
                    return -1
                }
            }
        }
        return added
    }

    /**
     * Adds the given values
     *
     * @param values The values to add
     * @return The number of values added, or -1 if not all values could be added
     */
    fun addAllMatching(values: Array<Value>, matcher: Matcher<Value>): Int
    {
        return if (hasRoomFor(values.size))
        {
            addAllMatching(listOf(*values), matcher)
        }
        else -1
    }

    /**
     * Adds the given value if it is not null
     *
     * @param value The value to add
     * @return True if the value was added, false otherwise
     */
    fun addIfNotNull(value: Value?): Boolean
    {
        return value?.let { add(it) } ?: false
    }

    /**
     * Adds the given value
     *
     * @param value The value to add
     * @return True if the value was added
     */
    fun onAdd(value: Value): Boolean
}
