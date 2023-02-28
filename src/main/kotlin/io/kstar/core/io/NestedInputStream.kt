@file:Suppress("unused")

package io.kstar.core.io

import io.kstar.core.io.NestedInputStream.Companion.isBuffered
import java.io.BufferedInputStream
import java.io.InputStream

/**
 * Interface implemented by an [InputStream] that contains a nested input stream.
 * The nested stream can be retrieved with [nestedInputStream]. The extension
 * method [isBuffered] can be used to determine if an input stream or any nested
 * input stream is buffered.
 *
 * @author Jonathan Locke
 */
interface NestedInputStream
{
    companion object
    {
        /**
         * Returns true if this input stream, or any nested input stream is buffered
         */
        fun InputStream.isBuffered(): Boolean
        {
            var input = this
            while (true)
            {
                if (input is BufferedInputStream)
                {
                    return true
                }
                if (input is NestedInputStream)
                {
                    input = input.nestedInputStream()
                }
                else
                {
                    return false
                }
            }
        }
    }

    /**
     * Returns the nested input stream
     */
    fun nestedInputStream(): InputStream
}
