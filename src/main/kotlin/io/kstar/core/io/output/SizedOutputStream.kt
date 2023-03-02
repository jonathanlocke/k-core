@file:Suppress("unused")

package io.kstar.core.io.output

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.values.Bytes
import io.kstar.core.values.Bytes.Companion.bytes
import io.kstar.receptors.collection.Sized
import io.kstar.receptors.values.ByteSized
import java.io.OutputStream

/**
 * An output stream that keeps track of how many bytes have been written. The number of bytes written is available
 * via [bytes].
 *
 * @author jonathanl (shibo)
 */
@TypeQuality
(
    stability = STABLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
class SizedOutputStream
(
    /** The underlying output stream  */
    private val out: OutputStream

) : OutputStream(), ByteSized, Sized
{
    /** The number of bytes written  */
    private var size: Long = 0

    override fun close() = out.close()
    override fun flush() = out.flush()
    override fun size() = size.toInt()
    override fun bytes(): Bytes = bytes(size)
    override fun write(b: ByteArray)
    {
        out.write(b)
        size += b.size.toLong()
    }

    override fun write(b: ByteArray, off: Int, length: Int)
    {
        out.write(b, off, length)
        size += length.toLong()
    }

    override fun write(b: Int)
    {
        out.write(b)
        size++
    }
}
