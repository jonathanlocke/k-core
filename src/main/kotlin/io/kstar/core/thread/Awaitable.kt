@file:Suppress("unused")

package io.kstar.core.thread

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality
import java.util.concurrent.TimeUnit

/**
 * Interface to a JDK method that takes a duration and time unit.
 *
 * @author jonathanl (shibo)
 */
@TypeQuality
(
    stability = STABLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
fun interface Awaitable
{
    /**
     * A method that accepts a wait time in a given unit
     *
     * @param wait The time to wait
     * @param unit The time unit
     * @return True if the awaited condition occurred, false if it timed out.
     */
    @Throws(InterruptedException::class)
    fun await(wait: Long, unit: TimeUnit): Boolean
}
