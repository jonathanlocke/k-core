@file:Suppress("unused", "UnusedReceiverParameter")

package io.kstar.core.language

object Doubles
{
    fun Double.format(digits: Int): String = java.lang.String.format("%.${digits}f", this)

    fun Double.inRange(range: ClosedRange<Double>): Double
    {
        if (this < range.start) return range.start
        if (this > range.endInclusive) return range.endInclusive
        return this
    }
}
