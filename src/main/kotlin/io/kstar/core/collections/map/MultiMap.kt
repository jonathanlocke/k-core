package io.kstar.core.collections.map

import com.telenav.kivakit.annotations.code.quality.TypeQuality
import java.util.function.Function

/**
 * A map from key to an [ObjectList] of values. Values can be added with [.add] and
 * [.addAll]. A flat list of all values in the map can be retrieved with
 * [.flatValues]. The [ObjectList] for a key can be retrieved with [.get] or
 * [.list]. If the list for the given key does not yet exist, [.list] will create a new
 * one.
 *
 *
 * **Adding Values**
 *
 *
 *  * [.add]
 *  * [.addAll]
 *  * [.addAll]
 *  * [.addIfNotNull]
 *
 *
 *
 * **Retrieving Values**
 *
 *
 *  * [.flatValues]
 *  * [.list]
 *
 *
 * @author jonathanl (shibo)
 */
@Suppress("unused")
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = UNTESTED, documentation = DOCUMENTED)
class MultiMap<Key, Value> : BaseMap<Key, ObjectList<Value>>
{
    @JvmOverloads
    constructor(maximumKeys: Maximum? = MAXIMUM, maximumValues: Maximum? = MAXIMUM) : super(maximumKeys)
    constructor(maximumKeys: Maximum, maximumValues: Maximum?, map: MutableMap<Key, ObjectList<Value>>) : super(maximumKeys, map)
    constructor(map: MutableMap<Key, ObjectList<Value>>) : this(MAXIMUM, MAXIMUM, map)

    /**
     * Adds the given value to the list found under the given key
     *
     * @param key The key
     * @param value The value to add
     */
    fun add(key: Key, value: Value)
    {
        getOrCreate(key).add(value)
    }

    /**
     * Adds the given value to each list found under the given keys
     *
     * @param keys The keys
     * @param value The value to add under each key
     */
    fun addAll(keys: Collection<Key>, value: Value)
    {
        for (key in keys)
        {
            getOrCreate(key).add(value)
        }
    }

    /**
     * Adds the given values to the list found under the given key
     *
     * @param key The key
     * @param values The values to add
     */
    fun addAll(key: Key, values: Collection<Value>?)
    {
        getOrCreate(key).addAll(values)
    }

    /**
     * Adds the given value to the list found under the given key if the value is not nul
     *
     * @param key The key
     * @param value The value to add
     */
    fun addIfNotNull(key: Key?, value: Value?): Boolean
    {
        if (key != null && value != null)
        {
            add(key, value)
            return true
        }
        return false
    }

    /**
     * Returns a list with all values in this multimap
     */
    fun flatValues(): ObjectList<Value>
    {
        val values: `var` = ObjectList<Value>()
        for (list in values)
        {
            values.addAll(list)
        }
        return values
    }

    /**
     * Returns the list found under the given key
     *
     * @param key The key
     * @return The object list
     */
    fun list(key: Key): ObjectList<Value>
    {
        return computeIfAbsent(key, Function { ignored: Key? -> ObjectList() })
    }

    /**
     * Returns the size of the longest list in this map
     */
    fun maximumListSize(): Count
    {
        var maximum: `var`? = 0
        for (list in values)
        {
            maximum = max(list.size, maximum)
        }
        return count(maximum)
    }

    /**
     * Sorts the values in each list in this map
     *
     * @param comparator The comparator to use when sorting
     */
    fun sort(comparator: Comparator<in Value>?)
    {
        for (entry in entries)
        {
            entry.getValue().sort(comparator)
        }
    }

    /**
     * The total number of values in this map
     */
    fun valueCount(): Int
    {
        var count: `var` = 0
        for (list in values)
        {
            if (list != null)
            {
                count += list.size
            }
        }
        return count
    }

    override fun onCreateValue(key: Key): ObjectList<Value>
    {
        return ObjectList(maximum(maximumSize()))
    }
}