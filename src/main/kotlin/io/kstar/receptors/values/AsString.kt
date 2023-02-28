@file:Suppress("unused")

package io.kstar.receptors.values

import io.kstar.annotations.quality.Documentation.*
import io.kstar.annotations.quality.Stability.*
import io.kstar.annotations.quality.Testing.*
import io.kstar.annotations.quality.TypeQuality
import io.kstar.receptors.values.StringFormat.TO_STRING
import java.util.*

/**
 * Interface to an object that can produce one or more different kinds of string representations. This can be useful
 * when the [Object.toString] method is already being used or when other kinds of strings are needed for
 * specific purposes.
 *
 *
 * The [StringFormat] enum defines different kinds of string representations which can be retrieved with
 * [asString]. The object can override this method to provide multiple string representations for
 * specific purposes.
 *
 * @author jonathanl (shibo)
 */
@TypeQuality
(
    stability = STABLE,
    testing = UNTESTED,
    documentation = DOCUMENTED
)
interface AsString
{
    /**
     * Returns a string representation of this object that is suitable for the given purpose
     */
    fun asString(format: StringFormat = TO_STRING): String
    {
        return toString()
    }
}


