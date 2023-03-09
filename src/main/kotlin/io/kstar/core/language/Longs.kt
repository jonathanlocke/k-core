@file:Suppress("unused", "UnusedReceiverParameter")

package io.kstar.core.language

import java.lang.String.format

object Longs
{
    fun Long.inRange(range: ClosedRange<Long>): Long
    {
        if (this < range.start) return range.start
        if (this > range.endInclusive) return range.endInclusive
        return this
    }

    fun Long.asCommaSeparatedString(): String
    {
        return format("%,d", this)
    }
}
