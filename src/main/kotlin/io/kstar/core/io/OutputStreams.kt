@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.core.io

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.UNTESTED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.io.NestedOutputStream.Companion.isBuffered
import io.kstar.core.io.OutputStreams.buffered
import io.kstar.core.io.OutputStreams.tryToFlush
import io.kstar.core.language.Try
import java.io.BufferedOutputStream
import java.io.OutputStream

/**
 * Utility methods for buffering and flushing output streams.
 *
 * **Buffering**
 *
 *  * [buffered]
 *  * [isBuffered]
 *
 * **Flushing**
 *
 *  * [tryToFlush]
 *
 * @author Jonathan Locke
 */
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = UNTESTED,
    documentation = DOCUMENTED
)
object OutputStreams
{
    /**
     * Returns the output stream buffered, if it is not already. [OutputStream]s that
     * implement [NestedOutputStream] will be explored to determine if some nested
     * stream is already buffered
     */
    fun OutputStream.buffered(): OutputStream = if (isBuffered()) this else BufferedOutputStream(this)

    /**
     * Makes an attempt to flushe the given output stream
     *
     * @return True if the stream was successfully flushed
     */
    fun tryToFlush(out: OutputStream): Boolean
    {
        return Try.tryRunning(out::flush)
    }
}
