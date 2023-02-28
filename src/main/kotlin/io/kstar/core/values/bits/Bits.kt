@file:Suppress("unused")

package io.kstar.core.values.bits

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.UNTESTED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.language.Throw.illegalState
import io.kstar.core.values.BitCount
import io.kstar.core.values.BitCount.Companion.bits
import io.kstar.core.values.Count
import io.kstar.core.values.bits.Bits.oneBits
import io.kstar.receptors.numeric.Countable

/**
 * Utility methods for manipulating bits.
 *
 * **Functions**
 *
 *  * [oneBits] - Composes a long value with the given number of one bits
 *
 * @author jonathanl (shibo)
 */
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = UNTESTED,
    documentation = DOCUMENTED
)
object Bits
{
    /**
     * Returns a long value containing the given number of '1' bits, starting from the least-significant bit. For
     * example, if count is 5, the return value would be 0b11111 or 31.
     */
    fun oneBits(count: Countable<*>): Long
    {
        var bit = 1L
        var value = 0L
        for (i in count.count())
        {
            value = value or bit
            bit = bit shl 1
        }
        return value
    }

    /**
     * Returns a Long value for the given bit string
     *
     * @return The parsed value
     * @throws IllegalStateException Thrown if the string is not a binary number
     */
    fun parseBits(string: String): Long
    {
        var value = 0L
        for (i in Count.count(string.length).ints())
        {
            value = when (string[i])
            {
                '1' -> value or 1
                '0' -> value or 0

                else -> illegalState("$string is not a binary number")
            }
            value = value shl 1
        }
        return value
    }

    /**
     * Returns the number of '1' bits in the given value
     *
     * @return The number of bits
     */
    fun bits(value: Long): BitCount = bits(java.lang.Long.bitCount(value))
}