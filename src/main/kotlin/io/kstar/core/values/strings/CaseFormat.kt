@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package io.kstar.core.values.strings

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.UNTESTED
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.values.strings.Strip.stripLeading
import io.kstar.internal.Diagrams.DiagramStrings
import java.util.*
import java.util.regex.MatchResult
import java.util.regex.Pattern

/**
 * Converts between different styles of casing:
 *
 *
 *  * camelCase
 *  * Capitalized
 *  * lower-hyphenated
 *  * UPPER_UNDERSCORE
 *
 *
 * @author jonathanl (shibo)
 */
@UmlIncludeType(inDiagrams = [DiagramStrings::class])
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = UNTESTED,
    documentation = DOCUMENTED
)
object CaseFormat
{
    /**
     * "WebServer -> "web-server" and "webServer -> "web-server"
     */
    fun String.camelCaseToHyphenated(): String
    {
        return replace("(?=[A-Z][a-z0-9])".toRegex(), "-")
            .stripLeading("-")
            .lowercase()
    }

    /**
     * "webServer" -> "WebServer"
     */
    fun String.capitalize(): String
    {
        return if (isEmpty()) this else this[0].uppercaseChar().toString() + substring(1)
    }

    /**
     * "webServer" -> "Webserver"
     */
    fun String.capitalizeOnlyFirstLetter(): String
    {
        return if (isEmpty()) this else this[0].uppercaseChar().toString() + lowercase(Locale.getDefault()).substring(1)
    }

    /**
     * "WebServer" -> "webServer"
     */
    fun String.decapitalize(): String
    {
        return if (isEmpty()) this else this[0].lowercaseChar().toString() + substring(1)
    }

    /**
     * "web-server" -> "webServer"
     */
    fun String.hyphenatedToCamel(): String
    {
        if (this.contains("-"))
        {
            val matcher = Pattern.compile("-[a-zA-Z]").matcher(this)
            return matcher.replaceAll { result: MatchResult -> result.group().substring(1).uppercase(Locale.getDefault()) }
        }
        return this
    }

    /**
     * Returns true if the text starts with an uppercase letter or non-letter, false otherwise
     */
    fun String.isCapitalized(): Boolean
    {
        return if (isEmpty()) false else Character.isUpperCase(this[0])
    }

    /**
     * Returns true if the given text contains a hyphen
     */
    fun String.isHyphenated(): Boolean
    {
        return contains("-")
    }

    /**
     * Returns true if the given text is in lowercase
     */
    fun String.isLowercase(): Boolean
    {
        return this == lowercase()
    }

    /**
     * Returns true if the given text is in lowercase
     */
    fun String.isUppercase(): Boolean
    {
        return this == uppercase()
    }

    /**
     * "web-server" -> "WEB_SERVER"
     */
    fun String.lowerHyphenToUpperUnderscore(): String
    {
        return replace("-".toRegex(), "_").uppercase()
    }

    /**
     * "WEB_SERVER" -&gt; "web-server"
     */
    fun String.upperUnderscoreToLowerHyphen(): String
    {
        return if (!contains("-"))
        {
            replace("_".toRegex(), "-").lowercase()
        }
        else this
    }
}