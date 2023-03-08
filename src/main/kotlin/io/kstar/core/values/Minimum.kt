@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.core.values

import io.kstar.receptors.numeric.Countable

@JvmInline
value class Minimum(private val value: Long) : Countable<Minimum>
{
    constructor(value: Int) : this(value.toLong())

    companion object
    {
        fun minimum(value: Long): Minimum = Minimum(value)
    }

    override fun asLong(): Long = value

    override fun maximum(): Minimum = Minimum(Long.MAX_VALUE)
    override fun minimum(): Minimum = Minimum(Long.MIN_VALUE)

    override fun onNew(scalar: Long): Minimum = Minimum(scalar)
}
