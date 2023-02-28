@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.core.values

import io.kstar.receptors.numeric.Countable

@JvmInline
value class Maximum(private val value: Long) : Countable<Maximum>
{
    companion object
    {
        fun maximum(value: Long): Maximum = Maximum(value)
    }

    override fun asLong(): Long = value

    override fun onNew(value: Long): Maximum = Maximum(value)
}
