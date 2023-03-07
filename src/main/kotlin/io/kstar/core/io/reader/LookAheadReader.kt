@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.core.io.reader

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE
import io.kstar.annotations.quality.Testing.TESTING_INSUFFICIENT
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.io.IO.END_OF_STREAM
import io.kstar.receptors.numeric.Numbered
import java.io.Reader

/**
 * A simple stream that allows you to peek at what is coming next in an input stream, with [lookAhead]. The
 * current line number can also be retrieved with [lineNumber].
 *
 * @author Jonathan Locke
 */
@TypeQuality
(
    stability = STABLE,
    testing = TESTING_INSUFFICIENT,
    documentation = DOCUMENTED
)
class LookAheadReader
(
    /** The input */
    private val input: Reader

) : Reader(), Numbered
{
    /** The current line number  */
    var lineNumber = 1
        private set

    /** The current character, or [END_OF_STREAM] if there is no character available  */
    var current = END_OF_STREAM
        private set

    /** Any next character, or [END_OF_STREAM] if there is no next character available  */
    var lookAhead = END_OF_STREAM
        private set

    /**
     * Read the current and lookahead values
     */
    init
    {
        next()

        if (hasNext())
        {
            next()
        }
    }

    override fun number(): Int = lineNumber
    override fun close() = input.close()

    /**
     * Returns true if the stream can be advanced with [next]
     */
    operator fun hasNext(): Boolean = current != END_OF_STREAM

    /**
     * Returns true if the input is at the end of a line. Note that when the method returns true the stream should
     * be pointing to the line terminator, 0x0A.
     *
     * @return true if the input stream is at the end of a line, false otherwise.
     */
    fun atEndOfLine(): Boolean
    {
        return if (current == 0x0A)
        {
            true
        }
        else if (current == 0x0D && lookAhead == 0x0A)
        {
            next()
            true
        }
        else
        {
            false
        }
    }

    /**
     * Moves the stream to the next value
     */
    operator fun next(): Int
    {
        current = lookAhead
        if (current == '\n'.code)
        {
            lineNumber++
        }
        lookAhead = input.read()
        return current
    }

    /**
     * @param buffer Destination buffer
     * @param offset Offset at which to start storing characters
     * @param length Maximum number of characters to read
     */
    override fun read(buffer: CharArray, offset: Int, length: Int): Int
    {
        var i = offset
        var count = 0
        while (current != END_OF_STREAM && count++ < length)
        {
            buffer[i++] = current.toChar()
            next()
        }
        return if (count > 0) count else -1
    }
}