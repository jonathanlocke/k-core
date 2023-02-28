@file:Suppress("unused")

package io.kstar.receptors.numeric

import kotlin.math.max
import kotlin.math.min

/**
 * Interface to a numeric object
 *
 * Subclasses must implement one or both of the [onNew] methods
 *
 * @author Jonathan Locke
 */
interface Numeric<T> : Zeroable
{
    fun asDouble(): Double = asLong().toDouble()
    fun asFloat(): Float = asDouble().toFloat()
    fun asLong(): Long = asDouble().toLong()
    fun asInt(): Int = asLong().toInt()
    fun asShort(): Short = asLong().toShort()
    fun asChar(): Char = asInt().toChar()
    fun asByte(): Byte = asLong().toByte()
    fun asULong(): ULong = asLong().toULong()
    fun asUInt(): UInt = asLong().toUInt()

    fun new(value: Long): T
    {
        require(value >= minimum())
        require(value <= maximum())
        return onNew(value)
    }

    fun onNew(value: Long): T

    fun maximum(): Long = Long.MAX_VALUE
    fun minimum(): Long = 0

    fun maximum(that: Numeric<*>): T = new(max(asLong(), that.asLong()))
    fun minimum(that: Numeric<*>): T = new(min(asLong(), that.asLong()))

    override fun isZero(): Boolean = asLong() == 0L

    companion object
    {
        operator fun <N : Numeric<N>> N.plus(that: Numeric<*>): N = new(asLong() + that.asLong())
        operator fun <N : Numeric<N>> N.minus(that: Numeric<*>): N = new(asLong() - that.asLong())
        operator fun <N : Numeric<N>> N.times(that: Numeric<*>): N = new(asLong() * that.asLong())
        operator fun <N : Numeric<N>> N.div(that: Numeric<*>): N = new(asLong() / that.asLong())
        operator fun <N : Numeric<N>> N.compareTo(that: Numeric<*>) = asDouble().compareTo(that.asDouble())

        operator fun <N : Numeric<N>> N.plus(that: Long): N = new(asLong() + that)
        operator fun <N : Numeric<N>> N.minus(that: Long): N = new(asLong() - that)
        operator fun <N : Numeric<N>> N.times(that: Long): N = new(asLong() * that)
        operator fun <N : Numeric<N>> N.div(that: Long): N = new(asLong() / that)
        operator fun <N : Numeric<N>> N.compareTo(that: Long) = asDouble().compareTo(that)

        operator fun <N : Numeric<N>> N.plus(that: Int): N = new(asLong() + that)
        operator fun <N : Numeric<N>> N.minus(that: Int): N = new(asLong() - that)
        operator fun <N : Numeric<N>> N.times(that: Int): N = new(asLong() * that)
        operator fun <N : Numeric<N>> N.div(that: Int): N = new(asLong() / that)
        operator fun <N : Numeric<N>> N.compareTo(that: Int) = asDouble().compareTo(that)
    }
}
