@file:Suppress("unused")

package io.kstar.core.language

import io.kstar.core.language.Iterators.isEmpty

object Iterables
{
    /**
     * @return The number of items in this iterable
     */
    fun Iterable<*>.size(): Int
    {
        if (this is Collection<*>)
        {
            return size
        }
        var counter = 0
        for (ignored in this)
        {
            counter++
        }
        return counter
    }

    fun Iterable<*>.asList(): List<*> = asSequence().toList()

    fun Iterable<*>.isEmpty(): Boolean = iterator().isEmpty()
}
