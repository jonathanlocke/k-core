@file:Suppress("unused")

package io.kstar.core.language

import java.util.Arrays.fill

object Characters
{
    private val isMac: Boolean = operatingSystem().isMac()

    private val HORIZONTAL_LINE_CHARACTER = if (isMac) '━' else '-'
    private val VERTICAL_LINE_CHARACTER = if (isMac) '┋' else '|'
    private val TOP_LEFT_LINE_CHARACTER = if (isMac) '┏' else '/'
    private val TOP_RIGHT_LINE_CHARACTER = if (isMac) '┓' else '\\'
    private val BOTTOM_LEFT_LINE_CHARACTER = if (isMac) '┗' else '\\'
    private val BOTTOM_RIGHT_LINE_CHARACTER = if (isMac) '┛' else '/'

    /**
     * Repeats this character the given number of times
     *
     * @param times Number of times to repeat character
     * @return Repeated character string
     */
    fun Char.repeat(times: Int): String
    {
        check(times >= 0) { "Times cannot be $times" }

        val buffer = CharArray(times)
        fill(buffer, this)
        return buffer.toString()
    }
}