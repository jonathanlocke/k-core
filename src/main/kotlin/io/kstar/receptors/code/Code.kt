@file:Suppress("unused")

package io.kstar.receptors.code

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.language.Try.tryOr

/**
 * Code that can be executed, returning a value (and not throwing an exception), as opposed to [java.lang.Runnable],
 * which does not return a value.
 *
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = STABLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED,
    reviewers = ["Jonathan Locke"]
)
fun interface Code<Value>
{
    /**
     * Returns the value returned by the code
     */
    fun run(): Value

    /**
     * Returns the result of running this code, or if it throws an exception,
     * the result of running the given default code
     */
    fun runOr(default: Code<Value>): Value = tryOr({ run() }) { default.run() }

    /**
     * Returns the result of running this code, or if it throws an exception,
     * the default value
     */
    fun runOr(default: Value): Value = tryOr({ run() }) { default }
}
