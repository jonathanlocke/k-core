@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.core.values.bits

import io.kstar.annotations.quality.Documentation
import io.kstar.annotations.quality.Stability
import io.kstar.annotations.quality.Testing
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.values.BitCount

/**
 * A bit field in a bit diagram
 *
 * @author Jonathan Locke
 */
@TypeQuality
(
    stability = Stability.STABLE,
    testing = Testing.TESTING_INSUFFICIENT,
    documentation = Documentation.DOCUMENTED
)
class BitField
(
    /** The character in the bit diagram for this bit field  */
    private val character: Char,

    /** The 'and' mask to access the bit field  */
    private val mask: Long
)
{
    /** The shift to access the bit field  */
    internal var shift = 0

    /**
     * @return The width of this bit field in bits
     */
    fun bits(): BitCount
    {
        return Bits.bits(mask())
    }

    /**
     * @return A boolean value for this bitfield extracted from the given value
     */
    fun booleanFrom(value: Long): Boolean
    {
        return intFrom(value) == 1
    }

    /**
     * @return A byte for this bitfield extracted from the given value
     */
    fun byteFrom(value: Long): Byte
    {
        return longFrom(value).toByte()
    }

    /**
     * @return An int value for this bitfield extracted from the given value
     */
    fun intFrom(value: Long): Int
    {
        return longFrom(value).toInt()
    }

    /**
     * @return A long value for this bitfield extracted from the given value
     */
    fun longFrom(value: Long): Long
    {
        return value and mask ushr shift
    }

    /**
     * @return A short value for this bitfield extracted from the given value
     */
    fun shortFrom(value: Long): Short
    {
        return longFrom(value).toShort()
    }

    /**
     * @return The mask for this bitfield
     */
    fun mask(): Long
    {
        return mask
    }

    /**
     * @return The larges value that can be contained in this bitfield
     */
    fun maximumValue(): Int
    {
        return 1 shl bits().asInt()
    }

    /**
     * @param value The value to modify
     * @param source The value to store in the bit field
     * @return The given value with this bit field set to the given source value
     */
    operator fun set(value: Int, source: Boolean): Int
    {
        return set(value, if (source) 1 else 0)
    }

    /**
     * @param value The value to modify
     * @param source The value to store in the bit field
     * @return The given value with this bit field set to the given source value
     */
    operator fun set(value: Int, source: Int): Int
    {
        return value and mask.toInt().inv() or (source shl shift)
    }

    /**
     * @param value The value to modify
     * @param source The value to store in the bit field
     * @return The given value with this bit field set to the given source value
     */
    operator fun set(value: Long, source: Boolean): Long
    {
        return set(value, (if (source) 1 else 0).toLong())
    }

    /**
     * @param value The value to modify
     * @param source The value to store in the bit field
     * @return The given value with this bit field set to the given source value
     */
    operator fun set(value: Long, source: Long): Long
    {
        return value and mask.inv() or (source shl shift)
    }

    /**
     * @param value The value to modify
     * @param source The value to store in the bit field
     * @return The given value with this bit field set to the given source value
     */
    operator fun set(value: Short, source: Boolean): Short
    {
        return set(value, (if (source) 1 else 0).toShort())
    }

    /**
     * @param value The value to modify
     * @param source The value to store in the bit field
     * @return The given value with this bit field set to the given source value
     */
    operator fun set(value: Short, source: Short): Short
    {
        return (value.toInt() and mask.inv().toInt() or (source.toInt() shl shift)).toShort()
    }

    /**
     * @return The character that this bitfield uses in bit diagrams
     */
    override fun toString(): String
    {
        return character.toString()
    }
}