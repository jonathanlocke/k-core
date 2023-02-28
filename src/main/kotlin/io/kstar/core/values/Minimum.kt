@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.core.values

import io.kstar.receptors.numeric.Countable

@JvmInline
value class Minimum(private val value: Long) : Countable<Minimum>
{
    companion object
    {
        fun minimum(value: Long): Minimum = Minimum(value)
    }

    override fun asLong(): Long = value

    override fun onNewT(value: Long): Minimum = Minimum(value)
}
