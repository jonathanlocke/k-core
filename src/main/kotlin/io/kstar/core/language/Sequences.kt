@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.core.language

import io.kstar.core.language.Iterators.size

object Sequences
{
    fun <T> Sequence<T>.size(): Int = iterator().size()
    fun <T> Sequence<T>.isEmpty(): Boolean = !iterator().hasNext()
    fun <T> Sequence<T>.first(): T? = if (isEmpty()) null else iterator().next()
    fun <T> Sequence<T>.head(): T? = if (isEmpty()) null else iterator().next()

    fun <T> Sequence<T>.tail(): List<T>
    {
        val tail = mutableListOf<T>()
        var index = 0
        for (element in this)
        {
            if (index++ > 0)
            {
                tail.add(element)
            }
        }
        return tail.toList()
    }

    fun <T> Sequence<T>.last(): T?
    {
        var last: T? = null
        for (element in this)
        {
            last = element
        }
        return last
    }
}
