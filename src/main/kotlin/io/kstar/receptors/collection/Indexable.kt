@file:Suppress("unused", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package io.kstar.receptors.collection

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABILITY_UNDETERMINED
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality

/**
 * A sequence that has a known size and can be indexed, like a list, although not necessarily a collection. For example,
 * a random access file is [Indexable] because its length is known and values can be retrieved from any index in
 * the file.
 *
 *
 * Provides a default implementation of [Iterable] and [Iterator] accessible through [.asIterable]
 * and [.asIterator] as well as equals and hashcode accessible through [.isEqualTo] and
 * [Any.hashCode].
 *
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = STABILITY_UNDETERMINED,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
interface Indexable<Value> :
    Sized,
    Sequence<Value>,
    Keyed<Int, Value>
{
    /**
     * Returns the value for the given index
     */
    override operator fun get(index: Int): Value?

    /**
     * Returns true if this [Indexable] ends with the given [Indexable]
     */
    fun endsWith(that: Indexable<Value>): Boolean
    {
        return if (that.size() > size())
        {
            false
        }
        else
        {
            var thisIndex = this.size() - 1
            var thatIndex = that.size() - 1
            while (thatIndex >= 0 && thisIndex >= 0)
            {
                if (get(thisIndex) != that[thatIndex])
                {
                    return false
                }
                thisIndex--
                thatIndex--
            }
            true
        }
    }

    /**
     * Returns true if this indexable object and that indexable object have all the same values
     */
    fun isEqualTo(that: Indexable<Value>): Boolean
    {
        if (size() == that.size())
        {
            for (index in 0 until size())
            {
                if (get(index) != that[index])
                {
                    return false
                }
            }
            return true
        }
        return false
    }

    /**
     * Returns true if this [Indexable] starts with the given [Indexable]
     */
    fun startsWith(that: Indexable<Value>): Boolean
    {
        return if (that.size() > size())
        {
            false
        }
        else
        {
            for (i in 0 until that.size())
            {
                if (get(i) != that[i])
                {
                    return false
                }
            }
            true
        }
    }
}