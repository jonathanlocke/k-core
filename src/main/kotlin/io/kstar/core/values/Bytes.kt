@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.core.values

import io.kstar.core.language.Throw.illegalArgument
import io.kstar.core.values.Bytes.Companion.gigabytes
import io.kstar.core.values.Bytes.Companion.kilobytes
import io.kstar.core.values.Bytes.Companion.megabytes
import io.kstar.core.values.Bytes.Companion.petabytes
import io.kstar.core.values.Bytes.Companion.terabytes
import io.kstar.receptors.numeric.Countable
import io.kstar.receptors.values.AsString
import io.kstar.receptors.values.StringFormat
import io.kstar.receptors.values.StringFormat.*
import java.util.regex.Pattern
import java.util.regex.Pattern.compile
import kotlin.Long.Companion.MAX_VALUE
import kotlin.math.roundToLong

/**
 * Represents an immutable byte count.
 *
 * **Factories**
 *
 * These factory functions allow easy construction of [Byte] objects using either integer values like `megabytes(3)`,
 * or floating-point values like `megabytes(3.2)`:
 *
 *  - [bytes]
 *  - [kilobytes]
 *  - [megabytes]
 *  - [gigabytes]
 *  - [terabytes]
 *  - [petabytes]
 *
 * **Conversions**
 *
 * The precise number of bytes can be retrieved by calling [bytes]. Approximate values for different units can be
 * retrieved as double precision numbers using these methods:
 *
 * - [Bytes.asKilobytes]
 * - [Bytes.asMegabytes]
 * - [Bytes.asGigabytes]
 * - [Bytes.asTerabytes]
 * - [Bytes.asPetabytes]
 *
 * Strings may be converted to [Bytes] objects using [bytes]. A string value may contain a decimal or floating point
 * number followed by optional whitespace followed by a unit (nothing for bytes, K for kilobyte, M
 * for megabytes, G for gigabytes or T for terabytes) optionally followed by a B (for bytes). Any of
 * these letters can be any case. So, examples of permissible string values are:
 *
 *  - 37 (37 bytes)
 *  - 2.3K (2.3 kilobytes)
 *  - 2.5 kb (2.5 kilobytes)
 *  - 4k (4 kilobytes)
 *  - 35.2GB (35.2 gigabytes)
 *  - 1024M (1024 megabytes)
 *
 * The [toString] method is smart enough to convert a given value object to the most appropriate
 * units for the given value.
 *
 * @author Jonathan Locke
 * @see Countable
 */
class Bytes(val bytes: Long) : Countable<Bytes>, AsString
{
    companion object
    {
        private const val KILO = 1024
        private const val BYTES_PER_LONG = 8L
        private const val BYTES_PER_INT = 4L

        /** Pattern for string parsing.  */
        private val pattern = compile("(<?number>[0-9]+([.,][0-9]+)?)\\s*(<?units>|K|KB|M|MB|G|GB|T|TB|P|PB|bytes)", Pattern.CASE_INSENSITIVE)

        fun bytes(bytes: Double): Bytes = Bytes(bytes.roundToLong())
        fun bytes(bytes: Long): Bytes = Bytes(bytes)
        fun bytes(array: LongArray): Bytes = Bytes(array.size * BYTES_PER_LONG)
        fun bytes(array: IntArray): Bytes = Bytes(array.size * BYTES_PER_INT)
        fun bytes(array: ByteArray): Bytes = Bytes(array.size.toLong())
        fun bytes(count: Count): Bytes = bytes(count.asLong())
        fun kilobytes(kilobytes: Double): Bytes = bytes(kilobytes * KILO)
        fun kilobytes(kilobytes: Long): Bytes = bytes(kilobytes * KILO)
        fun megabytes(megabytes: Double): Bytes = kilobytes(megabytes * KILO)
        fun megabytes(megabytes: Long): Bytes = kilobytes(megabytes * KILO)
        fun gigabytes(gigabytes: Double): Bytes = megabytes(gigabytes * KILO)
        fun gigabytes(gigabytes: Long): Bytes = megabytes(gigabytes * KILO)
        fun terabytes(terabytes: Double): Bytes = gigabytes(terabytes * KILO)
        fun terabytes(terabytes: Long): Bytes = gigabytes(terabytes * KILO)
        fun petabytes(petabytes: Double): Bytes = terabytes(petabytes * KILO)
        fun petabytes(petabytes: Long): Bytes = terabytes(petabytes * KILO)

        val ZERO_BYTES: Bytes = Bytes(0)
        val MAXIMUM_BYTES: Bytes = Bytes(MAX_VALUE)

        /**
         * Parses the given text into a number of bytes.  For example, "6 kb" or "1.5M", etc.
         *
         * @param text The text to parse
         * @return The number of bytes
         * @throws IllegalStateException Thrown if the text cannot be parsed
         */
        fun bytes(text: String): Bytes
        {
            val matcher = pattern.matcher(text)
            if (matcher.matches())
            {
                val number: Double = matcher.group("number")
                    .replace(",", "")
                    .toDouble()

                return when (matcher.group("units").uppercase())
                {
                    "K", "KB" -> kilobytes(number)
                    "M", "MB" -> megabytes(number)
                    "G", "GB" -> gigabytes(number)
                    "T", "TB" -> terabytes(number)
                    "P", "PB" -> petabytes(number)
                    else -> bytes(number)
                }
            }
            else
            {
                illegalArgument("Unable to parse: $text")
            }
        }
    }

    override fun onNew(value: Long): Bytes = bytes(value)

    override fun maximum(): Long = 0
    override fun minimum(): Long = MAX_VALUE

    override fun asLong(): Long = bytes
    override fun asDouble(): Double = bytes.toDouble()

    fun asKilobytes(): Double = asDouble() / KILO
    fun asMegabytes(): Double = asKilobytes() / KILO
    fun asGigabytes(): Double = asMegabytes() / KILO
    fun asTerabytes(): Double = asGigabytes() / KILO
    fun asPetabytes(): Double = asTerabytes() / KILO

    override fun asString(format: StringFormat): String
    {
        return when (format)
        {
            USER_LABEL, USER_SINGLE_LINE, USER_MULTILINE, TO_STRING, TEXT, HTML, DEBUG -> toString()
            else -> bytes.toString()
        }
    }

    override fun toString(): String
    {
        return when
        {
            asGigabytes() >= 1000 -> "${asTerabytes()}T"
            asMegabytes() >= 1000 -> "${asGigabytes()}G"
            asKilobytes() >= 1000 -> "${asMegabytes()}M"
            bytes >= 1000 -> "${asKilobytes()}K"
            else -> "$bytes bytes"
        }
    }
}
