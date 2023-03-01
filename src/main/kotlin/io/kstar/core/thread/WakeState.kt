@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.core.thread

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality

/**
 * The reason why a thread completed, either it was interrupted, it timed out or it succeeded.
 *
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = STABLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
enum class WakeState
{
    /** Waiting was interrupted  */
    INTERRUPTED,

    /** Waiting timed out  */
    TIMED_OUT,

    /** The awaited operation completed  */
    COMPLETED,

    /** The awaited operation failed due to an exception  */
    TERMINATED;

    /**
     * Returns true if the operation was completed
     */
    fun completed(): Boolean
    {
        return this == COMPLETED
    }

    /**
     * Returns true if the operation did not complete
     */
    fun failed(): Boolean
    {
        return !completed()
    }
}
