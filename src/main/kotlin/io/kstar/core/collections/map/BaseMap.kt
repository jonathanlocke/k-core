package io.kstar.core.collections.map

import com.telenav.kivakit.annotations.code.quality.TypeQuality
import java.util.function.Function

/**
 * A base class for bounded maps which wraps a [Map] implementation. The following methods are added to the base
 * functionality:
 *
 *
 *  * [.get] - Typesafe version of [.getOrDefault]
 *  * [.getOrCreate] - Creates missing values using [.onCreateValue]
 *  * [.put] - Puts the given value. Uses the default value if the value is null
 *
 *
 * @author jonathanl (shibo)
 */
@Suppress("unused")
@UmlClassDiagram(diagram = DiagramCollections::class)
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = TESTING_INSUFFICIENT, documentation = DOCUMENTED)
open class BaseMap<Key, Value>(maximumSize: Maximum, map: MutableMap<Key, Value>) : MutableMap<Key, Value?>, SpaceLimited, GlobalRepeater
{
    /** The map to wrap  */
    private val map: MutableMap<Key, Value>

    /** The maximum number of values that can be stored in this list  */
    private val maximumSize: Int

    /** True if this set ran out of room, and we've already warned about it  */
    private var warnedAboutOutOfRoom = false

    /**
     * Unbounded map
     */
    constructor() : this(MAXIMUM)

    /**
     * Bounded map
     */
    constructor(maximumSize: Maximum) : this(maximumSize, HashMap<Key, Value>())

    /**
     * A bounded map with the given implementation
     */
    init
    {
        this.maximumSize = ensureNotNull(maximumSize.asInt())
        this.map = map
    }

    /**
     * An unbounded map with the given implementation
     */
    constructor(map: MutableMap<Key, Value>) : this(MAXIMUM, map)

    /**
     * Returns the underlying map data structure
     */
    fun backingMap(): Map<Key, Value>
    {
        return map
    }

    /**
     * {@inheritDoc}
     */
    override fun clear()
    {
        map.clear()
    }

    /**
     * {@inheritDoc}
     */
    override fun compute(key: Key,
                         remappingFunction: BiFunction<in Key, in Value?, out Value?>): Value?
    {
        return map.compute(key, remappingFunction)
    }

    /**
     * {@inheritDoc}
     */
    override fun computeIfAbsent(key: Key, mappingFunction: Function<in Key, out Value>): Value?
    {
        return map.computeIfAbsent(key, mappingFunction)
    }

    /**
     * {@inheritDoc}
     */
    override fun computeIfPresent(key: Key,
                                  remappingFunction: BiFunction<in Key, in Value, out Value?>): Value?
    {
        return map.computeIfPresent(key, remappingFunction)
    }

    /**
     * {@inheritDoc}
     */
    override fun containsKey(key: Any): Boolean
    {
        return map.containsKey(key)
    }

    /**
     * {@inheritDoc}
     */
    override fun containsValue(value: Any): Boolean
    {
        return map.containsValue(value)
    }

    /**
     * {@inheritDoc}
     */
    override fun entrySet(): Set<Map.Entry<Key, Value>>
    {
        return map.entries
    }

    /**
     * {@inheritDoc}
     */
    override fun equals(that: Any?): Boolean
    {
        return map == that
    }

    /**
     * {@inheritDoc}
     */
    override fun forEach(action: BiConsumer<in Key, in Value>)
    {
        map.forEach(action)
    }

    /**
     * {@inheritDoc}
     */
    override operator fun get(key: Any): Value?
    {
        return map.get(key)
    }

    /**
     * Typesafe version of [.getOrDefault]
     *
     * @param key The key
     * @param defaultValue The default value
     * @return The retrieved value
     */
    open operator fun get(key: Key?, defaultValue: Value): Value?
    {
        val value: `var`? = get(key)
        return if (value == null) defaultValue else value
    }

    /**
     * Gets the value associated with the given key. If no value exists, the value returned by
     * [.onCreateValue] is stored in the map, and returned.
     *
     * @param key The key
     * @return The value returned by [.onCreateValue]
     */
    open fun getOrCreate(key: Key): Value?
    {
        var value: `var`? = map[key]
        if (value == null)
        {
            value = onCreateValue(key)
            if (value != null)
            {
                put(key, value)
            }
        }
        return value
    }

    /**
     * {@inheritDoc}
     */
    override fun getOrDefault(key: Any, defaultValue: Value): Value?
    {
        return map.getOrDefault(key, defaultValue)
    }

    /**
     * {@inheritDoc}
     */
    override fun hashCode(): Int
    {
        return map.hashCode()
    }

    /**
     * {@inheritDoc}
     */
    override fun isEmpty(): Boolean
    {
        return map.isEmpty()
    }

    /**
     * {@inheritDoc}
     */
    override fun keySet(): Set<Key>
    {
        return map.keys
    }

    /**
     * Returns the maximum size of this map
     */
    fun maximumSize(): Int
    {
        return maximumSize
    }

    /**
     * {@inheritDoc}
     */
    override fun merge(key: Key, value: Value,
                       remappingFunction: BiFunction<in Value, in Value, out Value?>): Value?
    {
        return map.merge(key, value, remappingFunction)
    }

    /**
     * Called when a bounded list runs out of room
     */
    fun onOutOfRoom(values: Int)
    {
        if (!warnedAboutOutOfRoom)
        {
            warnedAboutOutOfRoom = true
            warning(Throwable(), "Adding $ values, would exceed maximum size of $. Ignoring operation.", values, totalRoom())
        }
    }

    /**
     * Stores the given value under the given key. Checks that there is room to do so first.
     *
     *
     * {@inheritDoc}
     *
     */
    override fun put(key: Key, value: Value?): Value?
    {
        return if (hasRoomFor(1))
        {
            map.put(key, ensureNotNull(value))
        }
        else null
    }

    /**
     * Stores the given value under the given key. If the value is null, uses the defaultValue instead.
     *
     * @param key The key
     * @param value The value
     * @param defaultValue The default value to use if the value is null
     * @return Any value that was replaced
     */
    open fun put(key: Key, value: Value?, defaultValue: Value): Value?
    {
        ensureNotNull(value)
        ensureNotNull(defaultValue)
        return put(key, value ?: defaultValue)
    }

    /**
     * Stores the given values from the given map in this map. Checks that there is room to do so first.
     *
     *
     * {@inheritDoc}
     *
     */
    override fun putAll(that: Map<out Key, Value?>)
    {
        if (hasRoomFor(that.size))
        {
            map.putAll(that)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun putIfAbsent(key: Key, value: Value): Value?
    {
        return map.putIfAbsent(key, value)
    }

    /**
     * Stores the given value under the given key, if the value is non-null.
     *
     * @return True if the value was stored
     */
    open fun putIfNotNull(key: Key?, value: Value?): Boolean
    {
        if (key != null && value != null)
        {
            put(key, value)
            return true
        }
        return false
    }

    override fun remove(key: Any, value: Any?): Boolean
    {
        return map.remove(key, value)
    }

    /**
     * {@inheritDoc}
     */
    override fun remove(key: Any): Value?
    {
        return map.remove(key)
    }

    /**
     * {@inheritDoc}
     */
    override fun replace(key: Key, value: Value): Value?
    {
        return map.replace(key, value)
    }

    /**
     * {@inheritDoc}
     */
    override fun replaceAll(function: BiFunction<in Key, in Value, out Value>)
    {
        map.replaceAll(function)
    }

    /**
     * {@inheritDoc}
     */
    override fun size(): Int
    {
        return map.size
    }

    /**
     * {@inheritDoc}
     */
    override fun toString(): String
    {
        val list: `var` = StringList()
        for (entry in entries)
        {
            list.add(entry.getKey() + " = " + entry.getValue())
        }
        return "[" + list.join() + "]"
    }

    /**
     * {@inheritDoc}
     */
    fun totalRoom(): Int
    {
        return maximumSize
    }

    /**
     * {@inheritDoc}
     */
    override fun values(): Collection<Value>
    {
        return map.values
    }

    protected open fun onCreateValue(key: Key): Value?
    {
        return null
    }
}