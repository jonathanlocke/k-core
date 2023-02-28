@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.core.values.bits

import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.language.Throw.illegalState
import io.kstar.core.values.Count.Companion.count
import io.kstar.core.values.bits.Bits.parseBits

/**
 * A [BitDiagram] is a character string that visually depicts one or more bit fields of a primitive value such as
 * an int or a long. Given a [BitDiagram], each depicted field can be retrieved through a [BitField]
 * accessor object by calling [field]. For bit positions that are not associated with any bit field, the
 * character '?' can be used.
 *
 * **Example**
 *
 * ```
 * // Define a BitDiagram for color values specified as ARGB (Alpha, Red, Green, and Blue).
 * BitDiagram COLOR = new BitDiagram("AAAAAAAA RRRRRRRR GGGGGGGG BBBBBBBB");
 *
 * // Define a BitField for each field in the bit diagram
 * val ALPHA = COLOR.field('A');
 * val RED   = COLOR.field('R');
 * val GREEN = COLOR.field('G');
 * val BLUE  = COLOR.field('B');
 *
 * // Start with an initial ARGB value
 * val rgb = 0xffff80ff;
 *
 * // Show ALPHA, RED, GREEN, and BLUE values from the Int variable rgb
 * println("Alpha = ${ALPHA.intFrom(rgb)}");      // 255
 * println("Red   = ${RED.intFrom(rgb))}");       // 255
 * println("Green = ${GREEN.intFrom(rgb)}");      // 128
 * println("Blue  = ${BLUE.intFrom(rgb)}");       // 255
 *
 * // Set new ALPHA, RED, GREEN, and BLUE values into the Int variable rgb
 * rgb = ALPHA.setInt(rgb, 0x80);
 * rgb = RED.setInt(rgb, 0x80);
 * rgb = GREEN.setInt(rgb, 0x80);
 * rgb = BLUE.setInt(rgb, 0x80);
 *
 * // Show ALPHA, RED, GREEN, and BLUE values from the Int variable rgb
 * println("Alpha = ${ALPHA.intFrom(rgb)}");      // 128
 * println("Red   = ${RED.intFrom(rgb))}");       // 128
 * println("Green = ${GREEN.intFrom(rgb)}");      // 128
 * println("Blue  = ${BLUE.intFrom(rgb)}");       // 128
 * ```
 *
 * @author jonathanl (shibo)
 */
@Suppress("SpellCheckingInspection")
@TypeQuality
(
    stability = STABLE,
    testing = TESTING_INSUFFICIENT,
    documentation = DOCUMENTED
)
class BitDiagram(diagram: String)
{
    /** The bit diagram */
    private val diagram: String

    init
    {
        // Remove whitespace from diagram
        this.diagram = diagram.replace(" ".toRegex(), "")

        // We can't allow 0 or 1 in a bit diagram because we're going to use those when converting
        // to binary when we create the field below
        check(!diagram.contains("1")) { "'1' is not a valid bit diagram character" }
        check(!diagram.contains("0")) { "'0' is not a valid bit diagram character" }
    }

    /**
     * Returns the [BitField] from this bit diagram for the given bit field character. For example, in a bit
     * diagram of "AAA BBB", `field("A")` would return a bit field accessor for the top three bits, the "A"
     * bitfield.
     *
     * @return The bit field
     * @throws IllegalStateException Thrown if the bitfield or bit diagram is invalid
     */
    fun field(fieldCharacter: Char): BitField
    {
        val bits = diagram
            .replace(fieldCharacter.toString().toRegex(), "1")
            .replace("[^1]".toRegex(), "0")

        val field = BitField(fieldCharacter, parseBits(bits))

        for (shift in 0..63)
        {
            val index = bits.length - shift - 1
            if (index < 0)
            {
                break
            }
            if (bits[index] == '1')
            {
                field.shift = shift
                return field
            }
        }

        illegalState("Invalid bitfield or diagram")
    }

    override fun toString(): String
    {
        val result = StringBuilder()
        for (i in count(diagram.length).ints())
        {
            result.append(diagram[i])
            if ((i + 1) % 8 == 0 && i + 1 < diagram.length)
            {
                result.append(" ")
            }
        }
        return result.toString()
    }
}