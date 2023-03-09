package io.kstar.core.collections.map

import com.telenav.kivakit.annotations.code.quality.TypeQuality
import java.net.URI
import java.util.function.Function

/**
 * A bounded map from string to value. Because KivaKit string maps support type conversion convenience methods, they
 * implement [RepeaterMixin] and broadcast [Problem]s when conversions fail.
 *
 * @author jonathanl (shibo)
 */
@Suppress("unused")
@UmlClassDiagram(diagram = DiagramCollections::class)
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = TESTING_INSUFFICIENT, documentation = DOCUMENTED)
abstract class BaseStringMap<Value> : BaseMap<String?, Value>, GlobalRepeater, TryTrait
{
    /**
     * Constructs a map with the given maximum size
     */
    protected constructor(maximumSize: Maximum?) : super(maximumSize)

    /**
     * Constructs a map with the given maximum size and initial values
     */
    protected constructor(maximumSize: Maximum, that: MutableMap<String, Value>) : super(maximumSize, that)

    /**
     * Retrieves the value for the given key as a boolean. If the conversion from String to object fails, a problem is
     * transmitted.
     *
     * @return The boolean value for the given key, or the default value if there is no value for the key
     */
    fun asBoolean(key: String, defaultValue: Boolean): Boolean
    {
        return if (keyMissing(key)) defaultValue else asBoolean(key)
    }

    /**
     * Retrieves the value for the given key as a [Boolean]. If the conversion from String to object fails, a
     * problem is transmitted.
     *
     * @return The [Boolean] value for the given key, or null if there is no value for the key
     */
    fun asBoolean(key: String?): Boolean
    {
        return convert(key, Booleans::isTrue, Boolean::class.java)!!
    }

    /**
     * Retrieves the value for the given key as a [Bytes] object. If the conversion from String to object fails, a
     * problem is transmitted.
     *
     * @return The [Bytes] value for the given key, or null if there is no value for the key
     */
    fun asBytes(key: String?): Bytes
    {
        return convert(this, key, Bytes::parseBytes, Bytes::class.java)
    }

    /**
     * Retrieves the value for the given key as a [Count] object. If the conversion from String to object fails, a
     * problem is transmitted.
     *
     * @return The [Count] value for the given key, or null if there is no value for the key
     */
    fun asCount(key: String?): Count
    {
        return convert(this, key, Count::parseCount, Count::class.java)
    }

    /**
     * Returns the value of the given key as a double. If the conversion from String to object fails, a problem is
     * transmitted.
     */
    fun asDouble(key: String?): Double
    {
        return convert(key, { s: String -> s.toDouble() }, Double::class.java)!!
    }

    /**
     * Retrieves the value for the given key as an [Estimate] object. If the conversion from String to object
     * fails, a problem is transmitted.
     *
     * @return The [Estimate] value for the given key, or null if there is no value for the key
     */
    fun asEstimate(key: String?): Estimate
    {
        return convert(this, key, Estimate::parseEstimate, Estimate::class.java)
    }

    /**
     * Retrieves the value for the given key as an [Identifier] object. If the conversion from String to object
     * fails, a problem is transmitted.
     *
     * @return The [Estimate] value for the given key, or null if there is no value for the key
     */
    fun asIdentifier(key: String?): Identifier
    {
        return convert(key, Function { value: String -> Identifier(value.toLong()) }, Identifier::class.java)
    }

    /**
     * Retrieves the value for the given key as an [IntegerIdentifier] object. If the conversion from String to
     * object fails, a problem is transmitted.
     *
     * @return The [IntegerIdentifier] value for the given key, or null if there is no value for the key
     */
    fun asIntegerIdentifier(key: String?): IntegerIdentifier
    {
        return convert(key, Function { value: String -> IntegerIdentifier(value.toInt()) }, IntegerIdentifier::class.java)
    }

    /**
     * Returns the value of the given key as a [Integer] object, or broadcasts a [Problem] and returns null
     * if the value is invalid or missing
     */
    fun asIntegerObject(key: String?): Int
    {
        return convert(key, { s: String -> s.toInt() }, Int::class.java)!!
    }

    /**
     * Returns the value of the given key as a [Long] object, or broadcasts a [Problem] and returns null if
     * the value is invalid or missing
     */
    fun asLong(key: String?): Long
    {
        return convert(key, { s: String -> s.toLong() }, Long::class.java)!!
    }

    /**
     * Returns the value of the given key as a [Maximum] object, or broadcasts a [Problem] and returns null
     * if the value is invalid or missing
     */
    fun asMaximum(key: String?): Maximum
    {
        return convert(this, key, Maximum::parseMaximum, Maximum::class.java)
    }

    /**
     * Returns the value of the given key as a [Minimum] object, or broadcasts a [Problem] and returns null
     * if the value is invalid or missing
     */
    fun asMinimum(key: String?): Minimum
    {
        return convert(this, key, Minimum::parseMinimum, Minimum::class.java)
    }

    /**
     * Returns the value of the given key as a [String] path, with no trailing slash, or broadcasts a
     * [Problem] and returns null if the value is invalid or missing
     *
     * @return The given key as a path with no trailing slash
     */
    fun asPathString(key: String?): String?
    {
        val value: `var`? = asString(key)
        return if (value == null) null else Strip.stripTrailing(value, "/")
    }

    /**
     * Returns the value of the given key as a [Percent] object, or broadcasts a [Problem] and returns null
     * if the value is invalid or missing
     *
     * @return The given value as a [Percent]
     */
    fun asPercent(key: String?): Percent
    {
        return convert(this, key, Percent::parsePercent, Percent::class.java)
    }

    /**
     * Returns the value of the given key as string
     */
    fun asString(key: String?): String?
    {
        return super.get(key!!) as String?
    }

    /**
     * Returns the value of the given key as a [StringIdentifier] object, or broadcasts a [Problem] and
     * returns null if the value is invalid or missing
     */
    fun asStringIdentifier(key: String?): StringIdentifier
    {
        val value: `var` = get(key).toString()
        return StringIdentifier(value)
    }

    /**
     * Returns the value of the given key as a sorted [StringList] object, or broadcasts a [Problem] and
     * returns null if the value is invalid or missing
     */
    fun asStringList(): StringList
    {
        val entries: `var` = StringList()
        val keys: `var` = ArrayList(keys)
        keys.sort(Comparator.naturalOrder())
        for (key in keys)
        {
            entries.add(key.toString() + " = " + get(key))
        }
        return entries
    }

    /**
     * Returns the value of the given key as a [URI] object, or broadcasts a [Problem] and returns null if
     * the value is invalid or missing
     *
     * @return The given value as a [URI]
     */
    fun asUri(key: String?): URI
    {
        return convert(key, { str: String? -> URI.create(str) }, URI::class.java)!!
    }

    /**
     * Returns the value of the given key as a [Version] object, or broadcasts a [Problem] and returns null
     * if the value is invalid or missing
     *
     * @return The given value as a [Version]
     */
    fun asVersion(key: String?): Version
    {
        return convert(key, Version::version, Version::class.java)
    }

    /**
     * Converts the value of the given key using the given function to the given type, or broadcasts a [Problem]
     * and returns null if the value is invalid or missing
     */
    fun <T> convert(key: String?, converter: Function<String, T>, type: Class<T>): T?
    {
        if (key != null)
        {
            val value: `var`? = get(key)
            if (value != null)
            {
                return tryCatch({ converter.apply(value.toString()) },
                    "Unable to convert [$] ==> $ to $", key, value, type.simpleName)
            }
            else
            {
                problem("Null value for key: $", key)
            }
        }
        else
        {
            problem("Null key")
        }
        return null
    }

    /**
     * Converts the value of the given key using the given function to the given type, or broadcasts a [Problem]
     * and returns null if the value is invalid or missing
     */
    fun <T> convert(listener: Listener?, key: String?, converter: BiFunction<Listener?, String?, T>, type: Class<T>): T?
    {
        if (key != null)
        {
            val value: `var`? = get(key)
            if (value != null)
            {
                return tryCatch({ converter.apply(listener, value.toString()) },
                    "Unable to convert [$] ==> $ to $", key, value, type.simpleName)
            }
            else
            {
                problem("Null value for key: $", key)
            }
        }
        else
        {
            problem("Null key")
        }
        return null
    }

    /**
     * Returns the keys and values of this map separated by the given separator
     */
    fun join(separator: String?): String
    {
        return asStringList().join(separator)
    }

    /**
     * {@inheritDoc}
     */
    override fun toString(): String
    {
        return join("\n")
    }

    private fun keyMissing(key: String): Boolean
    {
        return get(key) == null
    }
}