@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.core.values

import io.kstar.core.language.Iterables.size
import io.kstar.core.language.Iterators.size
import io.kstar.receptors.numeric.CountRange
import io.kstar.receptors.numeric.IntegerNumeric

@JvmInline
value class Count(private val value: Long) : IntegerNumeric<Count>
{
    constructor(value: Int) : this(value.toLong())

    companion object
    {
        fun count(value: Long): Count = Count(value)
        fun count(value: Int): Count = Count(value.toLong())

        fun Short.asCount(): Count = Count(this.toInt())
        fun Int.asCount(): Count = Count(this)
        fun Long.asCount(): Count = Count(this)

        fun String.count(): Count = Count(length)
        fun Iterable<*>.count(): Count = Count(size())
        fun Iterator<*>.count(): Count = Count(size())
        fun Array<*>.count(): Count = Count(size)
        fun Collection<*>.count(): Count = Count(size)
    }

    operator fun rangeTo(that: Count) = CountRange(this, that)

    fun loop(code: Runnable)
    {
        asSequence().forEach { _ -> code.run() }
    }

    override fun count(): Count
    {
        return this
    }

    override fun asLong(): Long = value

    override fun maximum(): Count = Count(Long.MAX_VALUE)
    override fun minimum(): Count = Count(Long.MIN_VALUE)

    override fun onNew(scalar: Long): Count = Count(scalar)
}
