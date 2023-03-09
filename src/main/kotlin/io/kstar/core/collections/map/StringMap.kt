package io.kstar.core.collections.map

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A bounded string map using a [TreeMap] implementation.
 *
 * @author jonathanl (shibo)
 */
@UmlClassDiagram(diagram = DiagramCollections::class)
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = TESTING_INSUFFICIENT, documentation = DOCUMENTED)
open class StringMap<Value> : BaseStringMap<Value>
{
    enum class KeyCaseSensitivity
    {
        PRESERVE_CASE, FOLD_CASE_LOWER, FOLD_CASE_UPPER
    }

    private val keyCaseSensitivity = KeyCaseSensitivity.FOLD_CASE_LOWER

    constructor() : super(MAXIMUM, LinkedHashMap<String, Value>())
    constructor(maximumSize: Maximum) : super(maximumSize, LinkedHashMap<String, Value>())

    /**
     * Returns a copy of this string map
     */
    open fun copy(): StringMap<Value>?
    {
        val copy = StringMap<Value>()
        copy.putAll(this)
        return copy
    }

    override operator fun get(key: Any): Value?
    {
        ensure(key is String)
        return super.get(fold(key as String))
    }

    override fun get(key: String?, defaultValue: Value): Value?
    {
        return super.get(fold(key), defaultValue)
    }

    override fun getOrCreate(key: String): Value?
    {
        return super.getOrCreate(fold(key))
    }

    override fun getOrDefault(key: Any, defaultValue: Value): Value?
    {
        ensure(key is String)
        return super.getOrDefault(fold(key as String), defaultValue)
    }

    override fun put(key: String, value: Value?, defaultValue: Value): Value?
    {
        return super.put(fold(key), value, defaultValue)
    }

    override fun put(key: String, value: Value?): Value?
    {
        return super.put(fold(key), value)
    }

    override fun putAll(that: Map<out String, Value?>)
    {
        for (entry in that.entries)
        {
            put(entry.getKey(), entry.getValue())
        }
    }

    override fun putIfAbsent(key: String, value: Value): Value?
    {
        return super.putIfAbsent(fold(key), value)
    }

    override fun putIfNotNull(key: String?, value: Value?): Boolean
    {
        return super.putIfNotNull(fold(key), value)
    }

    fun withKeyCaseSensitivity(keyCaseSensitivity: KeyCaseSensitivity): StringMap<Value>
    {
        val copy = copy()
        copy.keyCaseSensitivity = keyCaseSensitivity
        return copy
    }

    private fun fold(key: String?): String
    {
        ensureNotNull(key)
        return when (keyCaseSensitivity)
        {
            KeyCaseSensitivity.PRESERVE_CASE -> key!!
            KeyCaseSensitivity.FOLD_CASE_LOWER -> key!!.lowercase(Locale.getDefault())
            KeyCaseSensitivity.FOLD_CASE_UPPER -> key!!.uppercase(Locale.getDefault())
        }
    }
}