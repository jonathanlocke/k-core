@file:Suppress("unused")

package io.kstar.core.values.strings

import io.kstar.annotations.quality.Documentation
import io.kstar.annotations.quality.Stability
import io.kstar.annotations.quality.Testing
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.language.Characters.repeat

/**
 * Aligns a string within a given width using a padding character: [.center],
 * [.leftAlign] or [.rightAlign].
 *
 * @author Jonathan Locke
 */

@TypeQuality
(
    stability = Stability.STABLE_EXTENSIBLE,
    testing = Testing.UNTESTED,
    documentation = Documentation.DOCUMENTED
)
object Align
{
    /**
     * Centers the given text to a given length, with a given character used for padding
     */
    fun String.centered(width: Int, c: Char = ' '): String
    {
        return if (this.length < width)
        {
            val left = (width - this.length) / 2
            val right = width - this.length - left
            c.repeat(left) + this + c.repeat(right)
        }
        else
        {
            this
        }
    }

    /**
     * Right pads the given text to the given length, with the given character
     */
    fun String.leftAligned(length: Int, c: Char): String
    {
        return if (length > this.length)
        {
            this + c.repeat(length - this.length)
        }
        else
        {
            this
        }
    }

    /**
     * Left pads the given text to the given length, with the given character
     */
    fun String.rightAligned(length: Int, c: Char): String
    {
        return if (length > this.length)
        {
            c.repeat(length - this.length) + this
        }
        else
        {
            this
        }
    }
}