@file:Suppress("unused")

package io.kstar.receptors.numeric

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE
import io.kstar.annotations.quality.Testing.UNTESTED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.values.Count


/**
 * Interface to something that has a [Count] value.
 *
 * @author  Jonathan Locke
 * @see Count
 */
@TypeQuality
(
    stability = STABLE,
    testing = UNTESTED,
    documentation = DOCUMENTED
)
interface Countable<T : Countable<T>> : Numeric<T>
{
    fun count(): Count = Count(asLong())

    fun zeroTo(): Sequence<T>
    {
        return object : Iterator<T>
        {
            var at = 0L

            override fun hasNext(): Boolean = at < asLong()
            override fun next(): T = new(at++)

        }.asSequence()
    }

    fun zeroToInts(): Sequence<Int>
    {
        return object : Iterator<Int>
        {
            var at = 0

            override fun hasNext(): Boolean = at < asInt()
            override fun next(): Int = at++

        }.asSequence()
    }

    fun zeroToLongs(): Sequence<Long>
    {
        return object : Iterator<Long>
        {
            var at = 0L

            override fun hasNext(): Boolean = at < asLong()
            override fun next(): Long = at++

        }.asSequence()
    }
}
