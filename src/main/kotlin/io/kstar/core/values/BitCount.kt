@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.core.values

import io.kstar.core.values.bits.Bits.parseBits
import io.kstar.receptors.numeric.Countable
import io.kstar.receptors.numeric.IntegerNumeric.Companion.minus
import java.lang.Long.numberOfLeadingZeros

/**
 * A number of bits from 0 to 128
 *
 * @author Jonathan Locke
 */
@JvmInline
value class BitCount(private val bits: Long) : Countable<BitCount>
{
    constructor(value: Int) : this(value.toLong())

    companion object
    {
        fun bits(count: Int): BitCount = BitCount(count.toLong())
        fun bits(count: Long): BitCount = BitCount(count)
        fun bits(count: String): BitCount = BitCount(parseBits(count))
        fun bitsPerByte(): BitCount = bits(Byte.SIZE_BITS)
        fun bitsPerChar(): BitCount = bits(Char.SIZE_BITS)
        fun bitsPerInt(): BitCount = bits(Int.SIZE_BITS)
        fun bitsPerLong(): BitCount = bits(Long.SIZE_BITS)
        fun bitsPerShort(): BitCount = bits(Short.SIZE_BITS)
        fun bitsToRepresent(value: Int): BitCount = bitsToRepresent(value.toLong())
        fun bitsToRepresent(value: Long): BitCount
        {
            return (bitsPerLong() - numberOfLeadingZeros(value)).maximum(bits(1))
        }
    }

    override fun maximum(): BitCount = BitCount(Long.MAX_VALUE)
    override fun minimum(): BitCount = BitCount(Long.MIN_VALUE)

    override fun onNew(scalar: Long): BitCount = BitCount(scalar)

    override fun asLong(): Long = bits

    /**
     * Returns a mask for the values this number of bits can take on
     */
    fun mask(): Int = values() - 1

    /**
     * Returns the number of values this bit count can take on (2^n)
     */
    fun values(): Int
    {
        return 1 shl bits.toInt()
    }

    fun minimumUnsigned(): ULong = 0U
    fun maximumUnsigned(): ULong = if (asInt() == 64) ULong.MAX_VALUE else ((1L shl asInt()) - 1).toULong()

    fun maximumSigned(): Long = if (asInt() == 64) Long.MAX_VALUE else (1L shl asInt() - 1) - 1
    fun minimumSigned(): Long = if (asInt() == 64) Long.MIN_VALUE else -maximumSigned() - 1
}
