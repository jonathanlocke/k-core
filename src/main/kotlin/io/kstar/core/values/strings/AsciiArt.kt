package io.kstar.core.values.strings

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.UNTESTED
import io.kstar.annotations.quality.TypeQuality
import java.util.*

/**
 * Provides methods to create "ASCII art", including:
 *
 *
 *  * Banners
 *  * Lines
 *  * Boxes
 *  * Bulleted lists
 *
 *
 * @author Jonathan Locke
 */
@Suppress("unused")
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = UNTESTED,
    documentation = DOCUMENTED
)
object AsciiArt
{
    val TITLE_LEFT_CHARACTER = if (isMac) '┫' else '|'
    val TITLE_RIGHT_CHARACTER = if (isMac) '┣' else '|'
    private var maximumWidth = 90

    /**
     * Returns the given message centered on a line as a banner
     */
    fun bannerLine(message: String): String
    {
        return center(" $message ", maximumWidth, HORIZONTAL_LINE_CHARACTER)
    }

    /**
     * Returns a bottom line of the given width
     */
    /**
     * Returns a bottom line
     */
    @JvmOverloads
    fun bottomLine(width: Int = maximumWidth): String
    {
        return BOTTOM_LEFT_LINE_CHARACTER.toString() + line(width - 2) + BOTTOM_RIGHT_LINE_CHARACTER
    }

    /**
     * Returns a bottom line with the given message
     */
    fun bottomLine(width: Int, message: String, vararg arguments: Any?): String
    {
        var message = message
        message = " " + format(message, arguments) + " "
        return (BOTTOM_LEFT_LINE_CHARACTER.toString() + line(4) + message
                + line(Math.max(4, width - 6 - message.length))
                + BOTTOM_RIGHT_LINE_CHARACTER)
    }

    /**
     * Returns a bottom line with the given message
     */
    fun bottomLine(message: String?, vararg arguments: Any?): String
    {
        return bottomLine(logFormatter().maximumColumnWidth(), message, arguments)
    }

    /**
     * Returns the string to use as a bullet
     */
    fun bullet(): String
    {
        return if (isMac) "○" else "o"
    }

    /**
     * Returns collection of values as a bulleted list
     */
    fun bulleted(values: Collection<*>): String
    {
        return bulleted(1, values)
    }

    /**
     * Returns collection of values as a bulleted list using the given bullet
     */
    fun bulleted(values: Collection<*>, bullet: String): String
    {
        return bulleted(1, values, bullet)
    }
    /**
     * Returns collection of values as an indented bulleted list using the given bullet
     */
    /**
     * Returns collection of values as an indented bulleted list
     */
    @JvmOverloads
    fun bulleted(indent: Int, values: Collection<*>, bullet: String = bullet()): String
    {
        return if (values.isEmpty())
        {
            ""
        }
        else
        {
            val prefix = spaces(indent) + bullet + " "
            prefix + join(values, """
     
     $prefix
     """.trimIndent())
        }
    }

    /**
     * Returns the given text clipped at n characters with "[...]" appended if it is longer than n characters
     */
    fun clip(text: String, n: Int): String
    {
        if (text.length < n)
        {
            return text
        }
        val suffix = " [...]"
        return leading(text, n - suffix.length) + suffix
    }
    /**
     * Returns a line of the given number of characters
     */
    /**
     * Returns a line
     */
    @JvmOverloads
    fun line(length: Int = maximumWidth): String
    {
        return repeat(length, HORIZONTAL_LINE_CHARACTER)
    }

    /**
     * Returns a left-justified line with the given message
     */
    fun line(message: String): String
    {
        return (line(4)
                + " "
                + message
                + " "
                + line(Math.max(4, maximumWidth - 6 - message.length)))
    }

    /**
     * Returns the number of lines in the string
     */
    fun lineCount(string: String?): Int
    {
        return occurrences(string, '\n') + 1
    }

    fun maximumWidth(): Int
    {
        return maximumWidth
    }

    /**
     * Sets the maximum width for ascii art
     *
     * @param maximumWidth The maximum width
     */
    fun maximumWidth(maximumWidth: Int)
    {
        AsciiArt.maximumWidth = maximumWidth
    }

    /**
     * Returns the given number of spaces
     */
    fun spaces(count: Int): String
    {
        return repeat(count, ' ')
    }

    fun textBox(message: String, horizontal: Char, vertical: Char): String
    {
        return textBox(TextBoxStyle.CLOSED, message, horizontal, vertical)
    }

    /**
     * Returns a box using the given horizontal and vertical line drawing characters that contains the given message
     */
    fun textBox(style: TextBoxStyle?, message: String, horizontal: Char, vertical: Char): String
    {
        val builder = StringBuilder()
        val width = Math.min(maximumWidth, widestLine(message))
        builder.append(repeat(width + 6, horizontal))
        builder.append('\n')
        val lines = message.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (line in lines)
        {
            builder.append(vertical)
            builder.append("  ")
            builder.append(leftAlign(line, width, ' '))
            builder.append("  ")
            builder.append(vertical)
            builder.append('\n')
        }
        builder.append(repeat(width + 6, horizontal))
        return builder.toString()
    }

    fun textBox(message: String?, vararg arguments: Any?): String
    {
        return textBox(TextBoxStyle.CLOSED, message, *arguments)
    }

    /**
     * Returns an ASCII art box containing the given message
     */
    fun textBox(style: TextBoxStyle?, message: String?, vararg arguments: Any?): String
    {
        return textBox(style, format(message, arguments), HORIZONTAL_LINE_CHARACTER, VERTICAL_LINE_CHARACTER)
    }

    fun textBox(title: String, message: String, vararg arguments: Any?): String
    {
        return textBox(TextBoxStyle.CLOSED, title, message, *arguments)
    }

    /**
     * Returns an ASCII art box with the given title and message
     */
    fun textBox(style: TextBoxStyle, title: String, message: String, vararg arguments: Any?): String
    {
        return textBox(style, maximumWidth(), title, message, *arguments)
    }

    fun textBox(width: Int, title: String, message: String, vararg arguments: Any?): String
    {
        return textBox(TextBoxStyle.CLOSED, width, title, message, *arguments)
    }

    /**
     * Returns an ASCII art box with the given title and message
     */
    fun textBox(style: TextBoxStyle, width: Int, title: String, message: String, vararg arguments: Any?): String
    {
        var width = width
        var title = title
        var message = message
        title = title(title)
        message = format(message, arguments)
        width = Math.min(width, widestLine("""
    $title
    $message
    """.trimIndent()) + 4)
        val builder = StringBuilder()

        // Add the top of the box
        builder.append(TOP_LEFT_LINE_CHARACTER)
            .append(center(title, width - 2, HORIZONTAL_LINE_CHARACTER))
            .append(if (style == TextBoxStyle.OPEN) "" else TOP_RIGHT_LINE_CHARACTER)
            .append("\n")

        // For each line in the message,
        for (line in message.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        {
            if (style == TextBoxStyle.OPEN)
            {
                appendTextBoxLine(style, builder, line, Int.MAX_VALUE)
            }
            else
            {
                do
                {
                    // add the line, wrapping if necessary
                    val next = line.substring(0, Math.min(width - 4, line.length))
                    appendTextBoxLine(style, builder, next, width - 4)
                    line = line.substring(next.length)
                }
                while (line.length > width)
                if (!line.isEmpty())
                {
                    appendTextBoxLine(style, builder, line, width - 4)
                }
            }
        }

        // Add the bottom of the box
        builder.append(BOTTOM_LEFT_LINE_CHARACTER)
            .append(line(width - 2))
            .append(if (style == TextBoxStyle.OPEN) "" else BOTTOM_RIGHT_LINE_CHARACTER)
        return builder.toString()
    }

    /**
     * Returns a top line with the given title
     */
    fun topLine(title: String?, vararg arguments: Any?): String
    {
        return topLine(logFormatter().maximumColumnWidth(), title, arguments)
    }

    /**
     * Returns a top line with the given title
     */
    fun topLine(width: Int, title: String, vararg arguments: Any?): String
    {
        var title = title
        title = title(title, *arguments)
        return ((if (width > 0) " \n" else "")
                + TOP_LEFT_LINE_CHARACTER + line(4) + title
                + line(Math.max(4, width - 6 - title.length))
                + TOP_RIGHT_LINE_CHARACTER)
    }

    /**
     * Returns the length of the widest line in potentially multi-line text
     */
    fun widestLine(text: String): Int
    {
        var width = 0
        val lines = text.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (line in lines)
        {
            width = Math.max(width, line.length)
        }
        return width
    }

    private fun appendTextBoxLine(style: TextBoxStyle, builder: StringBuilder, text: String, width: Int)
    {
        builder.append(VERTICAL_LINE_CHARACTER).append(" ")
        if (style == TextBoxStyle.CLOSED)
        {
            builder.append(leftAlign(text, width, ' '))
            builder.append(" ").append(VERTICAL_LINE_CHARACTER)
        }
        else
        {
            builder.append(text)
        }
        builder.append('\n')
    }

    private fun title(title: String, vararg arguments: Any): String
    {
        return TITLE_LEFT_CHARACTER.toString() + " " + format(title, arguments) + " " + TITLE_RIGHT_CHARACTER
    }

    enum class TextBoxStyle
    {
        OPEN, CLOSED
    }
}