@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.core.values

import io.kstar.receptors.numeric.Countable

value class Count(private val value: Long) : Countable<Count>
{
    companion object
    {
        fun count(value: Long): Count = Count(value)
        fun count(value: Int): Count = Count(value.toLong())
    }

    override fun count(): Count
    {
        return this
    }

    override fun asLong(): Long = value

    override fun onNew(value: Long): Count = Count(value)
}
