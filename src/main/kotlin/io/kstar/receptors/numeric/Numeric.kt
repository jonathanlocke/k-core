@file:Suppress("unused")

package io.kstar.receptors.numeric

import io.kstar.core.language.Doubles.inRange
import io.kstar.receptors.numeric.Numeric.Companion.div
import io.kstar.receptors.numeric.Numeric.Companion.minus
import io.kstar.receptors.numeric.Numeric.Companion.plus
import io.kstar.receptors.numeric.Numeric.Companion.times
import io.kstar.receptors.objects.ScalarValueFactory
import kotlin.math.max
import kotlin.math.min

/**
 * Interface to a numeric object
 *
 * Subclasses must implement [ScalarValueFactory.onNew] to create new instances of [Value]
 *
 * **Factory**
 *
 *  * [ScalarValueFactory.onNew]
 *  * [ScalarValueFactory.isValidScalar]
 *
 * **Operators**
 *
 *  * [plus]
 *  * [minus]
 *  * [times]
 *  * [div]
 *
 * **Operations**
 *
 *  * [incremented]
 *  * [decremented]
 *  * [reciprocal]
 *
 * **Tests**
 *
 *  * [isZero]
 *  * [isNonZero]
 *
 * **Comparison**
 *
 *  * [compareTo]
 *
 * **Range**
 *
 *  * [maximum]
 *  * [minimum]
 *  * [inRange]
 *
 * **Sequences**
 *
 *  * [asSequence]
 *  * [asInts]
 *  * [asLongs]
 *
 * **Conversions**
 *
 *  * [asByte]
 *  * [asChar]
 *  * [asDouble]
 *  * [asFloat]
 *  * [asInt]
 *  * [asLong]
 *  * [asShort]
 *  * [asUInt]
 *  * [asULong]
 *
 * @author Jonathan Locke
 */
interface Numeric<Value : Numeric<Value>> :
    ScalarValueFactory<Double, Value>,
    Zeroable,
    Comparable<Value>
{
    fun asDouble(): Double

    fun asByte(): Byte = asLong().toByte()
    fun asChar(): Char = asInt().toChar()
    fun asFloat(): Float = asDouble().toFloat()
    fun asInt(): Int = asLong().toInt()
    fun asLong(): Long = asDouble().toLong()
    fun asShort(): Short = asLong().toShort()
    fun asUInt(): UInt = asLong().toUInt()
    fun asULong(): ULong = asLong().toULong()

    fun maximum(): Value
    fun minimum(): Value

    override fun isValidScalar(scalar: Double): Boolean
    {
        return scalar in minimum().asDouble()..maximum().asDouble()
    }

    /**
     * Returns a sequence of the [Numeric] values from zero to this value, exclusive
     */
    fun asSequence(): Sequence<Value>
    {
        return object : Iterator<Value>
        {
            var at = 0L

            override fun hasNext(): Boolean = at < asLong()
            override fun next(): Value = new(at++)

        }.asSequence()
    }

    /**
     * Returns a sequence of [Int] values from zero to [asInt], exclusive
     */
    fun asInts(): Sequence<Int>
    {
        return object : Iterator<Int>
        {
            var at = 0

            override fun hasNext(): Boolean = at < asInt()
            override fun next(): Int = at++

        }.asSequence()
    }

    /**
     * Returns a sequence of [Long] values from zero to [asInt], exclusive
     */
    fun asLongs(): Sequence<Long>
    {
        return object : Iterator<Long>
        {
            var at = 0L

            override fun hasNext(): Boolean = at < asLong()
            override fun next(): Long = at++

        }.asSequence()
    }

    override fun compareTo(other: Value): Int = asDouble().compareTo(other.asDouble())

    fun maximum(that: Numeric<*>): Value = new(max(asDouble(), that.asDouble()))
    fun minimum(that: Numeric<*>): Value = new(min(asDouble(), that.asDouble()))

    fun new(scalar: Long): Value = super.new(scalar.toDouble())

    fun reciprocal(): Value = new(1.0 / asDouble())

    fun decremented() = new(asLong() - 1)
    fun incremented() = new(asLong() + 1)

    fun inRange(minimum: Value, maximum: Value): Value
    {
        return new(asDouble().inRange(minimum.asDouble()..maximum.asDouble()))
    }

    override fun isZero(): Boolean = asLong() == 0L

    companion object
    {
        operator fun <N : Numeric<N>> N.plus(that: Numeric<*>): N = new(asDouble() + that.asDouble())
        operator fun <N : Numeric<N>> N.minus(that: Numeric<*>): N = new(asDouble() - that.asDouble())
        operator fun <N : Numeric<N>> N.times(that: Numeric<*>): N = new(asDouble() * that.asDouble())
        operator fun <N : Numeric<N>> N.div(that: Numeric<*>): N = new(asDouble() / that.asDouble())
        operator fun <N : Numeric<N>> N.compareTo(that: Numeric<*>) = asDouble().compareTo(that.asDouble())

        operator fun <N : Numeric<N>> N.plus(that: Double): N = new(asDouble() + that)
        operator fun <N : Numeric<N>> N.minus(that: Double): N = new(asDouble() - that)
        operator fun <N : Numeric<N>> N.times(that: Double): N = new(asDouble() * that)
        operator fun <N : Numeric<N>> N.div(that: Double): N = new(asDouble() / that)
        operator fun <N : Numeric<N>> N.compareTo(that: Double) = asDouble().compareTo(that)

        operator fun <N : Numeric<N>> N.plus(that: Long): N = new(asDouble() + that)
        operator fun <N : Numeric<N>> N.minus(that: Long): N = new(asDouble() - that)
        operator fun <N : Numeric<N>> N.times(that: Long): N = new(asDouble() * that)
        operator fun <N : Numeric<N>> N.div(that: Long): N = new(asDouble() / that)
        operator fun <N : Numeric<N>> N.compareTo(that: Long) = asDouble().compareTo(that)

        operator fun <N : Numeric<N>> N.plus(that: Int): N = new(asDouble() + that)
        operator fun <N : Numeric<N>> N.minus(that: Int): N = new(asDouble() - that)
        operator fun <N : Numeric<N>> N.times(that: Int): N = new(asDouble() * that)
        operator fun <N : Numeric<N>> N.div(that: Int): N = new(asDouble() / that)
        operator fun <N : Numeric<N>> N.compareTo(that: Int) = asDouble().compareTo(that)
    }
}
