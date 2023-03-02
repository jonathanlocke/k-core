@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.receptors.code

import io.kstar.core.language.Throw.fail

/**
 * An interface that allows callers of a method to vary the action taken when a problem occurs.
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
 * var value = parseBits("011011", returnValue(-1))
 * ```
 *
 * Return null if parsing bits fails:
 * ```
 * var value = parseBits("011011", returnNull())
 * ```
 *
 * @author Jonathan Locke
 */
fun interface ProblemHandler<T>
{
    companion object
    {
        fun <T> throwProblem(): ProblemHandler<T> = ProblemHandler { message -> fail(message) }
        fun <T> returnValue(value: T): ProblemHandler<T> = ProblemHandler { _ -> value }
        fun <T> returnNull(): ProblemHandler<T> = ProblemHandler { _ -> null }
    }

    /**
     * Reports the given message, or returns the given flag value, depending on the implementation
     *
     * @param message The message to report
     * @return The flag value (or nothing is returned if an exception is thrown)
     */
    fun onProblem(message: String): T?

    /**
     * Called to report a problem via the [onProblem] method
     */
    fun reportProblem(message: String): T? = onProblem(message)
}
