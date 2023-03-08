@file:Suppress("unused")

package io.kstar.core.values.strings

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality
import io.kstar.internal.Diagrams.DiagramStrings

/**
 * Strips leading and ending values and quotes from strings.
 *
 * @author Jonathan Locke
 */
@UmlIncludeType(inDiagrams = [DiagramStrings::class])
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = UNTESTED,
    documentation = DOCUMENTED
)
object Strip
{
    /**
     * Strips the given ending from this string if it exists
     *
     * @param trailer The ending to strip off
     * @return The stripped string or the original string if the ending did not exist
     */
    fun String.stripTrailing(trailer: String): String
    {
        if (trailer.isEmpty())
        {
            return this
        }

        val endingLength = trailer.length
        val stringLength = length

        // When the length of the ending string is larger
        // than the original string, the original string is returned.
        if (endingLength > stringLength)
        {
            return this
        }

        val index = this.lastIndexOf(trailer)
        val end = stringLength - endingLength

        return if (index == end) this.substring(0, end) else this
    }

    /**
     * Returns the text with the leading string stripped if it exists
     */
    fun String.stripLeading(leading: String): String
    {
        if (leading.isEmpty())
        {
            return this
        }

        return if (startsWith(leading)) substring(leading.length) else this
    }

    /**
     * Strips double quotes from this string
     */
    fun String.stripDoubleQuotes(): String
    {
        if (length >= 2 && startsWith("\"") && endsWith("\""))
        {
            return substring(1, length - 1)
        }
        return this
    }

    /**
     * Strips single quotes from this string
     */
    fun String.stripSingleQuotes(): String
    {
        if (length >= 2 && startsWith("'") && endsWith("'"))
        {
            return substring(1, length - 1)
        }
        return this
    }
}