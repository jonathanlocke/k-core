@file:Suppress("MemberVisibilityCanBePrivate")

package io.kstar.core.io

import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.io.InputStreams.buffered
import io.kstar.core.io.InputStreams.copyTo
import io.kstar.core.io.InputStreams.copyToAndClose
import io.kstar.core.io.NestedInputStream.Companion.isBuffered
import io.kstar.core.io.OutputStreams.buffered
import java.io.*

/**
 * Utility methods for buffering and copying input streams.
 *
 * **Buffering**
 *
 *  * [buffered]
 *
 * **Copying**
 *
 *  * [copyTo]
 *  * [copyToAndClose]
 *
 * @author Jonathan Locke
 */
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = UNTESTED,
    documentation = DOCUMENTED
)
object InputStreams
{
    /**
     * Returns the input stream buffered, if it is not already. [InputStream]s that
     * implement [NestedInputStream] will be explored to determine if some nested
     * stream is already buffered
     */
    fun InputStream.buffered(): InputStream = if (isBuffered()) this else BufferedInputStream(this)
    
    /**
     * Copies an input stream to an output stream. The streams will be automatically buffered for efficiency
     * if they are not already buffered.
     *
     * @throws Exception Thrown if the copy operation fails
     */
    fun InputStream.copyTo(output: OutputStream, bufferSize: Int = 8192)
    {
        val buffer = ByteArray(bufferSize)
        var bytesRead: Int
        val bufferedInput = buffered()
        val bufferedOutput = output.buffered()
        while (bufferedInput.read(buffer).also { bytesRead = it } > 0)
        {
            bufferedOutput.write(buffer, 0, bytesRead)
        }
        bufferedOutput.flush()
    }

    /**
     * Copies an input stream to an output stream, closing both streams on completion
     *
     * @throws Exception Thrown if the copy operation fails
     */
    fun InputStream.copyToAndClose(output: OutputStream, bufferSize: Int = 8192)
    {
        copyTo(output, bufferSize)
        close()
        output.close()
    }
}