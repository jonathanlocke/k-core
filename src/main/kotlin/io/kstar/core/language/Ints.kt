@file:Suppress("unused", "UnusedReceiverParameter")

package io.kstar.core.language

import java.lang.String.format

object Ints
{
    fun Int.inRange(range: ClosedRange<Int>): Int
    {
        if (this < range.start) return range.start
        if (this > range.endInclusive) return range.endInclusive
        return this
    }

    fun Int.asCommaSeparatedString(): String
    {
        return format("%,d", this)
    }

    /**
     * Returns the lowest bits of the given value as a binary string
     *
     * @param places The number of bits in the string
     */
    fun Int.toBinaryString(places: Int): String
    {
        var bits = places
        val builder = StringBuilder()
        var mask = 1 shl bits - 1
        while (bits-- > 0)
        {
            builder.append(if (this and mask == 0) "0" else "1")
            mask = mask ushr 1
        }
        return builder.toString()
    }
}
