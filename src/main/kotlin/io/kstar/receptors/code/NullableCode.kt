@file:Suppress("unused")

package io.kstar.receptors.code

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.language.Try.tryOr

/**
 * Code that can be executed, returning a nullable value
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
fun interface NullableCode<Value>
{
    /**
     * Returns the value returned by the code
     */
    fun run(): Value?

    /**
     * Returns the result of running this code. If the code throws an exception or returns null,
     * the return value is the result of running given default code
     */
    fun runOr(default: NullableCode<Value?>): Value? = tryOr({ run() ?: default.run() }, default::run)

    /**
     * Returns the result of running this code. If the code throws an exception or returns null,
     * the given default value is returned
     */
    fun runOr(default: Value): Value = tryOr({ run() }) { default }
}
