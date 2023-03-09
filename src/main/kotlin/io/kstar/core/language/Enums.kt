@file:Suppress("unused")

package io.kstar.core.language

import io.kstar.core.collections.Lists.join
import io.kstar.core.collections.list.StringList.Companion.words
import io.kstar.core.values.strings.CaseFormat.capitalizeOnlyFirstLetter

class Enums
{
    /**
     * Returns the given enum value as a displayable string
     */
    fun Enum<*>.toDisplayString(): String
    {
        val display = mutableListOf<String>()
        for (word in name.words("_"))
        {
            display.add(word.lowercase().capitalizeOnlyFirstLetter())
        }
        return display.join(" ")
    }
}
