@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.kstar.core.language

import io.kstar.core.language.Ints.asCommaSeparatedString
import io.kstar.core.language.Longs.asCommaSeparatedString
import io.kstar.core.language.Throw.illegalArgument
import io.kstar.receptors.objects.Source
import io.kstar.receptors.strings.AsString
import io.kstar.receptors.strings.StringFormat

object Objects
{
    /**
     * Converts this object to a debug string. If the object supports the [AsString] interface, the
     * [AsString.asString] method is called with [StringFormat.DEBUG]. If it does not, [toHumanizedString]
     * is called.
     *
     * @return A debug string for the object
     */
    fun <T> T.toDebugString(): String
    {
        return if (this is AsString) asString(StringFormat.DEBUG) else toHumanizedString()
    }

    /**
     * Returns this object as a string. [Long] and [Int] values are converted to comma separated strings
     * for ease of reading. If this object implements [Source], the value provided by the source is converted.
     */
    fun <T> T.toHumanizedString(): String
    {
        if (this is Source<*>)
        {
            return get()?.toHumanizedString() ?: "null"
        }
        if (this is Long)
        {
            return asCommaSeparatedString()
        }
        if (this is Int)
        {
            return asCommaSeparatedString()
        }
        return toString()
    }

    /**
     * Returns true if this object is equal to any of the given objects
     */
    fun <T> T.equalsAny(vararg objects: T): Boolean
    {
        for (it in objects)
        {
            if (this == it)
            {
                return true
            }
        }
        return false
    }

    /**
     * Returns true if the given variable number of values contains a series of equal pairs
     */
    fun areEqualPairs(vararg values: Any?): Boolean
    {
        if (values.size % 2 != 0)
        {
            illegalArgument("Must supply an even number of objects")
        }

        var i = 0
        while (i < values.size)
        {
            if (values[i] != values[i + 1])
            {
                return false
            }
            i += 2
        }
        return true
    }
}