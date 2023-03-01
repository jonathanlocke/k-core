@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.core.values

import io.kstar.receptors.numeric.Countable

value class Minimum(private val value: Long) : Countable<Minimum>
{
    constructor(value: Int) : this(value.toLong())

    companion object
    {
        fun minimum(value: Long): Minimum = Minimum(value)
    }

    override fun asLong(): Long = value

    override fun onNew(value: Long): Minimum = Minimum(value)
}
