@file:Suppress("unused")

package io.kstar.receptors.code

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality

/**
 * Code that can be executed, returning a value (and not throwing an exception), as opposed to [Runnable] which
 * does not return a value.
 *
 * @author jonathanl (shibo)
 */
@TypeQuality
(
    stability = STABLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED,
    reviewers = ["shibo"]
)
fun interface Code<Value>
{
    /**
     * Returns the value returned by the code
     */
    fun run(): Value
}
