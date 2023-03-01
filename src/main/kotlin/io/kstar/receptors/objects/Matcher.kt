package io.kstar.receptors.objects

import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABILITY_UNDETERMINED
import io.kstar.annotations.quality.Testing.TESTING_NOT_NEEDED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.receptors.objects.Matcher.Companion.matchAll
import io.kstar.receptors.objects.Matcher.Companion.matchNothing
import io.kstar.receptors.objects.Matcher.Companion.matching
import java.util.function.Predicate
import java.util.regex.Pattern

/**
 * A matcher that implements [Predicate], but adds some KivaKit-specific functionality.
 *
 * **Interface Methods**
 *
 *  * [matches] - Functional interface that matches against a single value, returning
 *                true if it matches and false if it does not
 *  * [matcher] - Default method that implements [Matchable] so that all [Matcher]s
 *                are [Matchable]
 *  * [test]    - Implementation of [Predicate.test]
 *
 * **Factories**
 *
 *
 *  * [matchAll]     - Matches all values
 *  * [matchNothing] - Matches nothing
 *  * [matching]     - Matches the given regular expression [Pattern]
 *  * [matching]     - Matches the given [Predicate]
 *
 *
 * @param <Value> The type of value to match
 * @author  Jonathan Locke
 */
@TypeQuality
(
    stability = STABILITY_UNDETERMINED,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
fun interface Matcher<Value> : Matchable<Value>, Predicate<Value>
{
    /**
     * {@inheritDoc}
     */
    override fun matcher(): Matcher<Value>
    {
        return this
    }

    /**
     * Returns true if the given value matches this matcher
     */
    override fun matches(value: Value): Boolean

    override fun test(value: Value): Boolean
    {
        return matches(value)
    }

    companion object
    {
        /**
         * Returns a matcher that matches all values
         */
        fun <T> matchAll(): Matcher<T>
        {
            return Matcher { true }
        }

        /**
         * Returns a matcher that matches nothing
         */
        fun <T> matchNothing(): Matcher<T>
        {
            return Matcher { false }
        }

        /**
         * Returns a matcher matching the given predicate
         */
        fun <T> matching(predicate: Predicate<T>): Matcher<T>
        {
            return Matcher { predicate.test(it) }
        }

        /**
         * @param pattern The pattern
         * @return A matcher that matches a regular expression [Pattern]
         */
        fun matching(pattern: Pattern): Matcher<String>
        {
            return Matcher { pattern.matcher(it).matches() }
        }
    }
}