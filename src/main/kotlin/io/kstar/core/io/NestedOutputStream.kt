@file:Suppress("unused")

package io.kstar.core.io

import java.io.BufferedOutputStream
import java.io.OutputStream

/**
 * Interface implemented by an [OutputStream] that contains a nested output stream.
 * The nested stream can be retrieved with [nestedOutputStream]. The extension
 * method [isBuffered] can be used to determine if an output stream or any nested
 * output stream is buffered.
 *
 * @author Jonathan Locke
 */
interface NestedOutputStream
{
    companion object
    {
        /**
         * Returns true if this output stream, or any nested output stream is buffered
         */
        fun OutputStream.isBuffered(): Boolean
        {
            var output = this
            while (true)
            {
                if (output is BufferedOutputStream)
                {
                    return true
                }
                if (output is NestedOutputStream)
                {
                    output = output.nestedOutputStream()
                }
                else
                {
                    return false
                }
            }
        }
    }
    
    /**
     * Returns the nested output stream
     */
    fun nestedOutputStream(): OutputStream
}
