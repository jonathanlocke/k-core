@file:Suppress("unused")

package io.kstar.core.values.bits

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.UNTESTED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.values.BitCount
import io.kstar.core.values.BitCount.Companion.bits
import io.kstar.core.values.Count
import io.kstar.core.values.bits.Bits.bits
import io.kstar.core.values.bits.Bits.oneBits
import io.kstar.core.values.bits.Bits.parseBits
import io.kstar.receptors.code.ProblemReporter
import io.kstar.receptors.code.ProblemReporter.Companion.throwProblem
import io.kstar.receptors.numeric.Countable

/**
 * Utility methods for manipulating bits.
 *
 * **Functions**
 *
 *  * [bits] - Returns the number of '1' bits in the given [Long]
 *  * [oneBits] - Composes a long value with the given number of '1' bits
 *  * [parseBits] - Parses text into a [Long] value
 *
 * @author  Jonathan Locke
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
     * Returns the number of '1' bits in the given value
     *
     * @return The number of bits
     */
    fun bits(value: Long): BitCount = bits(java.lang.Long.bitCount(value))

    /**
     * Returns a long value containing the given number of '1' bits, starting from the least-significant bit. For
     * example, if count is 5, the return value would be 0b11111 or 31.
     */
    fun oneBits(count: Countable<*>): Long
    {
        var bit = 1L
        var value = 0L
        for (i in count.count().zeroTo())
        {
            value = value or bit
            bit = bit shl 1
        }
        return value
    }

    /**
     * Returns a Long value for the given bit string
     *
     * @param text The text to parse
     * @param reporter The reporter to call if something goes wrong
     * @return The parsed value
     * @throws IllegalStateException Thrown if the string is not a binary number
     */
    fun parseBits(text: String, reporter: ProblemReporter<Long> = throwProblem()): Long
    {
        var value = 0L
        for (i in Count.count(text.length).zeroToInts())
        {
            value = when (text[i])
            {
                '1' -> value or 1
                '0' -> value or 0

                else -> reporter.report("$text is not a binary number", -1)
            }
            value = value shl 1
        }
        return value
    }
}