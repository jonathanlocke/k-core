@file:Suppress("unused")

package io.kstar.core.io.writer

import io.kstar.annotations.quality.Documentation
import io.kstar.annotations.quality.Stability
import io.kstar.annotations.quality.Testing
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.io.writer.Writers.tryToFlush
import io.kstar.core.language.Try
import java.io.Writer


/**
 * Utility methods for working with [Writer]s
 *
 * **Flushing**
 *
 *  * [tryToFlush]
 *
 * @author Jonathan Locke
 */
@TypeQuality
(
    stability = Stability.STABLE_EXTENSIBLE,
    testing = Testing.UNTESTED,
    documentation = Documentation.DOCUMENTED
)
object Writers
{
    /**
     * Makes an attempt to flushe the given writer
     *
     * @return True if the writer was successfully flushed
     */
    fun tryToFlush(out: Writer): Boolean
    {
        return Try.tryRunning(out::flush)
    }
}