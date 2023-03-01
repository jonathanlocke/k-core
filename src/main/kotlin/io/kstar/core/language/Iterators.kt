@file:Suppress("unused")

package io.kstar.core.language

import java.util.*
import kotlin.collections.Collection
import kotlin.collections.Iterator
import kotlin.collections.List

object Iterators
{
    /**
     * @return The number of items in this iterable
     */
    fun Iterator<*>.size(): Int
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

    fun <T> Iterator<T>.asList(): List<T>
    {
        val list = mutableListOf<T>()
        while (hasNext())
        {
            list.add(next())
        }
        return list.toList()
    }


    fun Iterator<*>.isEmpty(): Boolean = hasNext()
}
