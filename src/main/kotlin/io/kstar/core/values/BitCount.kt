@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.core.values

import io.kstar.receptors.numeric.Countable
import java.lang.Long.numberOfLeadingZeros

/**
 * A number of bits from 0 to 128
 *
 * @author Jonathan Locke
 */
@JvmInline
value class BitCount(private val bits: Int) : Countable<BitCount>
{
    companion object
    {
        fun bitsPerByte(): BitCount = bits(Byte.SIZE_BITS)
        fun bitsPerChar(): BitCount = bits(Char.SIZE_BITS)
        fun bitsPerInt(): BitCount = bits(Int.SIZE_BITS)
        fun bitsPerLong(): BitCount = bits(Long.SIZE_BITS)
        fun bitsPerShort(): BitCount = bits(Short.SIZE_BITS)
        fun bits(count: Int): BitCount = BitCount(count)
    }

    override fun onNewT(value: Long): BitCount = bits(value.toInt())

    override fun maximum(): Long = 128

    override fun asLong(): Long = bits.toLong()

    /**
     * Returns a mask for the values this number of bits can take on
     */
    fun mask(): Int = values() - 1

    /**
     * Returns the number of values this bit count can take on (2^n)
     */
    fun values(): Int
    {
        return 1 shl bits
    }

    fun bitsToRepresent(value: Int): BitCount = bitsToRepresent(value.toLong())
    fun bitsToRepresent(value: Long): BitCount = (bitsPerLong() - numberOfLeadingZeros(value)).maximum(bits(1))

    fun minimumUnsigned(): ULong = 0U
    fun maximumUnsigned(): ULong = if (asInt() == 64) ULong.MAX_VALUE else ((1L shl asInt()) - 1).toULong()

    fun maximumSigned(): Long = if (asInt() == 64) Long.MAX_VALUE else (1L shl asInt() - 1) - 1
    fun minimumSigned(): Long = if (asInt() == 64) Long.MIN_VALUE else -maximumSigned() - 1
}
