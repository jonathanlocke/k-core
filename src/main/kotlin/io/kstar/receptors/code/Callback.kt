@file:Suppress("unused")

package io.kstar.receptors.code

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality
import java.util.function.Consumer

/**
 * A simple callback interface extending [Consumer]
 *
 * @param <Value> The type of object to be passed to the callback
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = STABLE,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED,
    reviewers = ["Jonathan Locke"]
)
interface Callback<Value> : Consumer<Value>
{
    /**
     * Calls this callback with the given value
     *
     * @param value The value
     */
    fun call(value: Value)
    {
        accept(value)
    }
}
