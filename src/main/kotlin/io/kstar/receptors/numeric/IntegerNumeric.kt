@file:Suppress("unused")

package io.kstar.receptors.numeric

import io.kstar.core.language.Longs.inRange
import io.kstar.core.values.Count
import io.kstar.core.values.Count.Companion.count
import io.kstar.receptors.numeric.IntegerNumeric.Companion.div
import io.kstar.receptors.numeric.IntegerNumeric.Companion.minus
import io.kstar.receptors.numeric.IntegerNumeric.Companion.plus
import io.kstar.receptors.numeric.IntegerNumeric.Companion.times
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
interface IntegerNumeric<Value : IntegerNumeric<Value>> :
    ScalarValueFactory<Long, Value>,
    Zeroable,
    Comparable<Value>,
    Countable
{
    fun asLong(): Long

    override fun count(): Count = count(asLong())

    fun asByte(): Byte = asLong().toByte()
    fun asChar(): Char = asInt().toChar()
    fun asDouble(): Double = asLong().toDouble()
    fun asFloat(): Float = asDouble().toFloat()
    fun asInt(): Int = asLong().toInt()
    fun asShort(): Short = asLong().toShort()
    fun asUInt(): UInt = asLong().toUInt()
    fun asULong(): ULong = asLong().toULong()

    fun maximum(): Value
    fun minimum(): Value

    override fun isValidScalar(scalar: Long): Boolean
    {
        return scalar in minimum().asLong()..maximum().asLong()
    }

    /**
     * Returns a sequence of the [IntegerNumeric] values from zero to this value, exclusive
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

    override fun compareTo(other: Value): Int = asLong().compareTo(other.asLong())

    fun maximum(that: IntegerNumeric<*>): Value = new(max(asLong(), that.asLong()))
    fun minimum(that: IntegerNumeric<*>): Value = new(min(asLong(), that.asLong()))

    fun decremented() = new(asLong() - 1)
    fun incremented() = new(asLong() + 1)

    fun inRange(minimum: Value, maximum: Value): Value
    {
        return new(asLong().inRange(minimum.asLong()..maximum.asLong()))
    }

    override fun isZero(): Boolean = asLong() == 0L

    companion object
    {
        operator fun <N : IntegerNumeric<N>> N.plus(that: IntegerNumeric<*>): N = new(asLong() + that.asLong())
        operator fun <N : IntegerNumeric<N>> N.minus(that: IntegerNumeric<*>): N = new(asLong() - that.asLong())
        operator fun <N : IntegerNumeric<N>> N.times(that: IntegerNumeric<*>): N = new(asLong() * that.asLong())
        operator fun <N : IntegerNumeric<N>> N.div(that: IntegerNumeric<*>): N = new(asLong() / that.asLong())
        operator fun <N : IntegerNumeric<N>> N.compareTo(that: IntegerNumeric<*>) = asLong().compareTo(that.asLong())

        operator fun <N : IntegerNumeric<N>> N.plus(that: Long): N = new(asLong() + that)
        operator fun <N : IntegerNumeric<N>> N.minus(that: Long): N = new(asLong() - that)
        operator fun <N : IntegerNumeric<N>> N.times(that: Long): N = new(asLong() * that)
        operator fun <N : IntegerNumeric<N>> N.div(that: Long): N = new(asLong() / that)
        operator fun <N : IntegerNumeric<N>> N.compareTo(that: Long) = asLong().compareTo(that)

        operator fun <N : IntegerNumeric<N>> N.plus(that: Int): N = new(asLong() + that)
        operator fun <N : IntegerNumeric<N>> N.minus(that: Int): N = new(asLong() - that)
        operator fun <N : IntegerNumeric<N>> N.times(that: Int): N = new(asLong() * that)
        operator fun <N : IntegerNumeric<N>> N.div(that: Int): N = new(asLong() / that)
        operator fun <N : IntegerNumeric<N>> N.compareTo(that: Int) = asLong().compareTo(that)
    }
}
