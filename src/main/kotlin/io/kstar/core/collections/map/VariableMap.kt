package io.kstar.core.collections.map

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.TESTING_INSUFFICIENT
import io.kstar.annotations.quality.TypeQuality
import io.kstar.internal.Diagrams.DiagramCollections

/**
 * A bounded map from string to value which supports variable interpolation into a string via [.expand].
 * For example, a [VariableMap] of [Integer]s might have the entries "x=9" and "y=3" in it. In this case,
 * interpolate("${x} = ${y}") would yield the string "9 = 3". An example use of this class can be found in
 * File.withVariables(VariableMap&lt;?&gt; variables), which substitutes the name of the file with values from the
 * variable map.
 *
 * @author jonathanl (shibo)
 */
@Suppress("unused")
@UmlIncludeType(inDiagrams = [DiagramCollections::class])
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = TESTING_INSUFFICIENT,
    documentation = DOCUMENTED
)
class VariableMap<Value> : StringMap<Value?>, PropertyValue
{
    /**
     * An unbounded variable map
     */
    constructor() : super(MAXIMUM)

    /**
     * A bounded variable map
     */
    constructor(maximum: Maximum) : super(maximum)

    /**
     * Add the given variable to this variable map
     */
    fun add(name: String?, value: Value): VariableMap<Value>
    {
        put(name, value)
        return this
    }

    /**
     * Add the given variables to this variable map
     */
    fun addAll(variables: VariableMap<Value?>?): VariableMap<Value>
    {
        putAll(variables!!)
        return this
    }

    /**
     * Returns a copy of this variable map
     */
    override fun copy(): VariableMap<Value?>
    {
        return super.copy() as VariableMap<Value?>
    }

    /**
     * Returns this variable map with all the keys and values as double-quoted strings.
     */
    fun doubleQuoted(): VariableMap<String>
    {
        val quoted = newStringMap()
        for (key in keys)
        {
            quoted.add("\"" + key + "\"", "\"" + get(key) + "\"")
        }
        return quoted
    }
    /**
     * Interpolates the values in this map into the given string. Substitutions occur when the names of variables appear
     * inside curly braces after a '$', like "${x}". Any such substitution markers that do not correspond to a variable
     * in the map are substituted with the given default value, or if that value is null, they are left unchanged.
     *
     * @param text The string to interpolate values into
     * @param defaultValue The value to use for missing variables, or null to leave the ${x} marker unexpanded
     * @return The interpolated string
     */
    /**
     * Expands the given text, leaving any ${x} markers for which there is no value in place.
     *
     * @param text The string to interpolate values into
     * @return The interpolated string
     */
    @JvmOverloads
    fun expand(text: String, defaultValue: String? = null): String
    {
        if (text.contains("\${"))
        {
            val builder = StringBuilder()
            var pos = 0
            while (true)
            {
                val next = text.indexOf("\${", pos)
                if (next < 0)
                {
                    break
                }
                builder.append(text, pos, next)
                pos = next + 2
                val start = pos
                while (pos < text.length && isVariableCharacter(text[pos]))
                {
                    pos++
                }
                if (pos > start && text[pos] == '}')
                {
                    val variable = text.substring(start, pos)
                    val value? = get(variable)
                    if (value != null)
                    {
                        builder.append(value)
                    }
                    else
                    {
                        if (defaultValue != null)
                        {
                            builder.append(defaultValue)
                        }
                        else
                        {
                            builder.append("\${").append(variable).append("}")
                        }
                    }
                    pos++
                }
            }
            builder.append(text.substring(pos))
            return builder.toString()
        }
        return text
    }

    /**
     * Returns this variable map with all string values expanded by interpolating values for other keys in the map. For
     * example, the entry for key "coordinate" might be the value "${location-x}, ${location-y}". If the value for the
     * key "location-x" is "9" and the value for "location-y" is "81", then the expanded variable map will have the
     * value "9, 81" for the key "coordinate"
     */
    fun expanded(): VariableMap<String>
    {
        val expanded = newStringMap()
        for (key in HashSet(keys))
        {
            val value? = get(key)
            if (value != null)
            {
                expanded.put(key, expand(value.toString()))
            }
        }
        return expanded
    }

    /**
     * {@inheritDoc}
     */
    fun propertyValue(property: Property): Any?
    {
        return get(property.name())
    }

    /**
     * Returns this variable map with all the values as quoted strings.
     */
    fun withQuotedValues(): VariableMap<String>
    {
        val quoted = newStringMap()
        for (key in keys)
        {
            quoted.add(key, "'" + get(key) + "'")
        }
        return quoted
    }

    protected fun newStringMap(): VariableMap<String>
    {
        return VariableMap()
    }

    private fun isVariableCharacter(character: Char): Boolean
    {
        return Character.isLetterOrDigit(character) || character == '.' || character == '_' || character == '-'
    }

    companion object
    {
        /**
         * Returns a string variable map for the given string-to-string map
         */
        fun variableMap(that: Map<String?, String?>): VariableMap<String>
        {
            val copy = VariableMap<String>()
            for (key in that.keys)
            {
                copy.put(key, that[key])
            }
            return copy
        }

        /**
         * Returns this list of strings as a variable map where the even elements are keys and the odd elements are values.
         */
        fun variableMap(list: StringList): VariableMap<String>
        {
            val variables = VariableMap<String>()
            var i = 0
            while (i < list.size())
            {
                if (i + 1 < list.size())
                {
                    variables.add(list.get(i), list.get(i + 1))
                }
                i += 2
            }
            return variables
        }
    }
}