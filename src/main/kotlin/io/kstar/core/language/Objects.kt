@file:Suppress("unused")

package io.kstar.core.language

import io.kstar.core.language.Throws.illegalArgument

object Objects
{
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