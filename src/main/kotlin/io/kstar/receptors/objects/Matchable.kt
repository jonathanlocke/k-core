package io.kstar.receptors.objects

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABILITY_UNDETERMINED
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality
import java.util.function.Predicate

/**
 * Interface to code that has a [Matcher], and therefore can be matched
 *
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = STABILITY_UNDETERMINED,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
interface Matchable<Value>
{
    /**
     * Returns the matcher
     */
    fun matcher(): Predicate<Value>

    /**
     * Convenience method that uses this [Matchable]'s [Matcher] to match against the given value
     *
     * @param value The value to match
     * @return True if the given value matches this [Matchable]
     */
    fun matches(value: Value): Boolean
    {
        return matcher().test(value)
    }
}
