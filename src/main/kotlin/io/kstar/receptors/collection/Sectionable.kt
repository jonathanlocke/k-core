@file:Suppress("unused")

package io.kstar.receptors.collection

import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.STABILITY_UNDETERMINED
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality
import io.kstar.receptors.objects.Factory
import kotlin.math.max
import kotlin.math.min

/**
 * Retrieves different subsections from an [Indexable].
 *
 * @param <Value> The value being indexed
 * @param <Section> A type that can contain subsections of an [Indexable], normally a list
 * @author  Jonathan Locke
</Section></Value> */
@TypeQuality
(
    stability = STABILITY_UNDETERMINED,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
interface Sectionable<Value, Section> :
    Indexable<Value>,
    Factory<Section> where Section : Addable<Value>, Section : Indexable<Value>
{
    /**
     * Returns the first n values in this object. If there are fewer than count values, all values are returned.
     */
    fun first(count: Int): Section
    {
        val list = new()
        for (i in 0 until min(count, size()))
        {
            list.addIfNotNull(get(i))
        }
        return list
    }

    /**
     * Returns the last n values in this object. If there are fewer than count values, all values are returned.
     */
    fun last(count: Int): Section
    {
        val list = new()
        for (i in max(size() - count - 1, 0) until size())
        {
            list.addIfNotNull(get(i))
        }
        return list
    }

    /**
     * Returns the values in this object to the left of the index, exclusive
     */
    fun leftOf(index: Int): Section
    {
        val left = new()
        for (i in 0 until index)
        {
            left.addIfNotNull(get(i))
        }
        return left
    }

    /**
     * Returns the values in this object to the right of the index, exclusive
     */
    fun rightOf(index: Int): Section
    {
        val right = new()
        for (i in index + 1 until size())
        {
            right.addIfNotNull(get(i))
        }
        return right
    }
}