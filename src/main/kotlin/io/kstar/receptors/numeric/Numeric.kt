@file:Suppress("unused")

package io.kstar.receptors.numeric

import kotlin.math.max
import kotlin.math.min

/**
 * Interface to a numeric object
 *
 * Subclasses must implement one or both of the [onNewT] methods
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

    fun newT(value: Long): T
    {
        require(value >= minimum())
        require(value <= maximum())
        return onNewT(value)
    }

    fun onNewT(value: Long): T

    fun maximum(): Long = Long.MAX_VALUE
    fun minimum(): Long = 0

    fun maximum(that: Numeric<*>): T = newT(max(asLong(), that.asLong()))
    fun minimum(that: Numeric<*>): T = newT(min(asLong(), that.asLong()))

    override fun isZero(): Boolean = asLong() == 0L

    operator fun T.plus(that: Numeric<*>): T = newT(asLong() + that.asLong())
    operator fun T.minus(that: Numeric<*>): T = newT(asLong() - that.asLong())
    operator fun T.times(that: Numeric<*>): T = newT(asLong() * that.asLong())
    operator fun T.div(that: Numeric<*>): T = newT(asLong() / that.asLong())
    operator fun T.compareTo(that: Numeric<*>) = asDouble().compareTo(that.asDouble())

    operator fun T.plus(that: Long): T = newT(asLong() + that)
    operator fun T.minus(that: Long): T = newT(asLong() - that)
    operator fun T.times(that: Long): T = newT(asLong() * that)
    operator fun T.div(that: Long): T = newT(asLong() / that)
    operator fun T.compareTo(that: Long) = asDouble().compareTo(that)

    operator fun T.plus(that: Int): T = newT(asLong() + that)
    operator fun T.minus(that: Int): T = newT(asLong() - that)
    operator fun T.times(that: Int): T = newT(asLong() * that)
    operator fun T.div(that: Int): T = newT(asLong() / that)
    operator fun T.compareTo(that: Int) = asDouble().compareTo(that)
}
