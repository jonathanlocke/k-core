@file:Suppress("unused")

package io.kstar.receptors.collection

import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.language.Iterables.isEmpty
import io.kstar.receptors.strings.ValueToString
import io.kstar.receptors.strings.ValueToString.Companion.callToString
import java.util.function.Function

/**
 * An object that is [Joinable] is [Iterable]. Objects are iterated through and joined by separator values
 * into a String.
 *
 *
 * **Methods**
 *
 *
 *  * [.join] - Returns a string where each value in this [Iterable] is separated from
 * the next with the given separator
 *  * [.join] - Returns a string where each value in this [Iterable] is separated
 * from the next with the given separator
 *  * [.joinOrDefault] - Returns a string where each value in this
 * [Iterable] is separated from the next with the given separator. If there are no values the default value
 * is returned.
 *  * [.join] - Returns a string where each value in this
 * [Iterable] is separated from the next with the given separator. Each value is transformed into a string
 * using the given function
 *
 *
 * @author  Jonathan Locke
 * @see Iterable
 *
 * @see Function
 */
@TypeQuality
(
    stability = STABILITY_UNDETERMINED,
    testing = TESTING_NOT_NEEDED,
    documentation = DOCUMENTED
)
interface Joinable<Value> : Iterable<Value>
{
    /**
     * Returns the values in this sequence joined as a string with the given separator
     */
    fun join(separator: Char): String
    {
        return join(separator = separator.toString())
    }

    /**
     * Returns the values in this sequence transformed into strings by the given function and joined together with the
     * given separator.
     *
     * @param separator The separator to put between values
     * @param stringer The function to convert values into strings
     * Returns the elements in this sequence joined as a string with the given separator
     */
    fun join(separator: String = separator(), stringer: ValueToString<Value> = callToString()): String
    {
        val builder = StringBuilder()
        for (value in this)
        {
            if (builder.isNotEmpty())
            {
                builder.append(separator)
            }
            builder.append(stringer.toString(value))
        }
        return builder.toString()
    }

    /**
     * Returns the values in this sequence joined as a string with the given separator or the default value if this
     * sequence is empty.
     *
     * @param separator The separator to put between values
     * @param default The value to return if this joinable has nothing to join
     * @return The joined values in this joinable, or the default value if there are none
     */
    fun joinOrDefault(separator: String, default: String): String
    {
        return if (isEmpty()) default else join(separator)
    }

    /**
     * Returns the separator to use when joining
     */
    fun separator(): String
    {
        return ", "
    }
}