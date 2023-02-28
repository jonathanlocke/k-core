@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.core.values

import io.kstar.receptors.numeric.Countable

@JvmInline
value class Count(private val value: Long) : Countable<Count>
{
    companion object
    {
        fun count(value: Long): Count = Count(value)
    }

    override fun count(): Count
    {
        return this
    }

    override fun asLong(): Long = value

    override fun onNewT(value: Long): Count = Count(value)
}
