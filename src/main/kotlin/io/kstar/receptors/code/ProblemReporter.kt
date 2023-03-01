@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.receptors.code

import io.kstar.core.language.Throw.fail

/**
 * An interface that allows clients of a method to vary the problem reporting mechanism.
 * This may be useful for performance reasons when processing dirty data as exceptions are
 * an expensive way to report a problem
 *
 * **Examples**
 *
 * Throw an exception if parsing bits fails:
 * ```
 * var value = parseBits("101010", throwProblem())
 * ```
 *
 * Return -1 if parsing bits fails:
 * ```
 * var value = parseBits("123", returnValue(-1))
 * ```
 *
 * @author Jonathan Locke
 */
fun interface ProblemReporter<T>
{
    companion object
    {
        fun <T> throwProblem(): ProblemReporter<T> = ProblemReporter { message, _ -> fail(message) }
        fun <T> returnValue(value: T): ProblemReporter<T> = ProblemReporter<T> { _, _ -> value }
    }

    /**
     * Reports the given message, or returns the given flag value, depending on the implementation
     *
     * @param message The message to report
     * @param flagValue The flag value to return if an exception is not thrown
     * @return The flag value (or nothing is returned if an exception is thrown)
     */
    fun report(message: String, flagValue: T): T
}
