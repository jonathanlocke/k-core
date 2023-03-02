@file:Suppress("unused")

package io.kstar.core.io.input

import io.kstar.core.values.Bytes
import io.kstar.core.values.Bytes.Companion.bytes
import io.kstar.receptors.code.ProblemHandler
import io.kstar.receptors.code.ProblemHandler.Companion.throwProblem
import io.kstar.receptors.collection.Sized
import io.kstar.receptors.values.ByteSized
import java.io.InputStream

/**
 * An input stream that limits reading to a given number of bytes. This can be useful in defending against certain kinds
 * of attacks.
 *
 * @author jonathanl (shibo)
 */
class LimitedInputStream
(
    /** The wrapped input stream */
    private val input: InputStream,

    /** The limit of bytes to read */
    private val limit: Bytes,

    /** The handler to call if the limit is reached */
    private val handler: ProblemHandler<Int> = throwProblem()

) : InputStream(), ByteSized, Sized, NestedInputStream
{
    /** The number of bytes that have been read */
    private var read: Long = 0

    override fun bytes(): Bytes = bytes(read)
    override fun close() = input.close()
    override fun size() = read.toInt()
    override fun nestedInputStream(): InputStream = input
    override fun read(): Int
    {
        return if (read++ > limit.asLong())
        {
            handler.onProblem("Exceeded limit of $limit") ?: -1
        }
        else
        {
            input.read()
        }
    }
}
