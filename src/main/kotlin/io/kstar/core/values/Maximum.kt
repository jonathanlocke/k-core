@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.core.values

import io.kstar.receptors.numeric.IntegerNumeric

@JvmInline
value class Maximum(private val value: Long) : IntegerNumeric<Maximum>
{
    constructor(value: Int) : this(value.toLong())

    companion object
    {
        fun maximum(value: Long): Maximum = Maximum(value)
    }

    override fun asLong(): Long = value

    override fun maximum(): Maximum = Maximum(Long.MAX_VALUE)
    override fun minimum(): Maximum = Maximum(Long.MIN_VALUE)

    override fun onNew(scalar: Long): Maximum = Maximum(scalar)
}
