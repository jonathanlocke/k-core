@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package io.kstar.core.values.strings

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.UNTESTED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.internal.Diagrams.DiagramStrings

/**
 * String splitting utilities.
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
object Split
{
    /**
     * Returns the two strings resulting from splitting the given text using the given separator,
     * or null if the given separator cannot be found
     */
    fun String.splitAtFirst(separator: Char): Pair<String, String>?
    {
        val index = indexOf(separator)
        if (index >= 0)
        {
            return Pair(substring(0, index), substring(index + 1))
        }
        return null
    }
}