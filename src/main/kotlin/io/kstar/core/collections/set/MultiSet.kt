package io.kstar.core.collections.set

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A map from key to an [ObjectSet] of values. Values can be added with [.add]. A flattened
 * set of all values in the map can be retrieved with [.flatValues]. The [ObjectSet] for a key can be
 * retrieved with [.set]. If the set for the given key does not yet exist, one is created.
 *
 *
 * **Adding Values**
 *
 *
 *  * [.add]
 *
 *
 *
 * **Retrieving Values**
 *
 *
 *  * [.flatValues]
 *  * [.set]
 *
 *
 * @author jonathanl (shibo)
 */
@Suppress("unused")
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = UNTESTED, documentation = DOCUMENTED)
class MultiSet<Key, Value> @JvmOverloads constructor(maximumKeys: Maximum? = MAXIMUM, maximumValues: Maximum = MAXIMUM) : BaseMap<Key, ObjectSet<Value>?>(maximumKeys)
{
    /** The maximum number of values in the set for each key  */
    private val maximumValues: Maximum
    /**
     * Creates a "multi-set" where each key has a set of values rather than a single value
     *
     * @param maximumKeys The maximum number of keys
     * @param maximumValues The maximum number of values in the set for each key
     */
    /**
     * Creates an unbounded "multi-set" where each key has a set of values rather than a single value
     */
    init
    {
        this.maximumValues = maximumValues
    }

    /**
     * Adds the given value to the set under the given key
     *
     * @param key The key
     * @param value The value to add
     */
    fun add(key: Key, value: Value)
    {
        getOrCreate(key).add(value)
    }

    /**
     * Returns a list with all values in this multimap
     */
    fun flatValues(): Iterable<Value>
    {
        return iterable {
            object : NextIterator()
            {
                private val sets: Iterator<ObjectSet<Value?>> = values().iterator()
                private var values: Iterator<Value?>? = null
                operator fun next(): Value
                {
                    while (values == null || !values!!.hasNext())
                    {
                        values = if (sets.hasNext())
                        {
                            sets.next().iterator()
                        }
                        else
                        {
                            return@iterable null
                        }
                    }
                    return@iterable values!!.next()
                }
            }
        }
    }

    /**
     * Gets the set of values for the given key. If there is no set, returns the empty set.
     *
     * @param key The key to access
     * @return The set
     */
    fun getOrEmptySet(key: Any?): Set<Value>
    {
        return getOrDefault(key, ObjectSet<Value>())
    }

    /**
     * Returns the size of the largest set in this map
     */
    fun maximumSetSize(): Count
    {
        var maximum: `var`? = 0
        for (set in values())
        {
            maximum = max(set.size(), maximum)
        }
        return count(maximum)
    }

    /**
     * Removes the given value from the set found under the given key
     *
     * @param key The key
     * @param value The value to remove
     */
    fun removeFromSet(key: Key, value: Value)
    {
        val set: `var` = getOrEmptySet(key)
        set.remove(value)
    }

    /**
     * Replaces the given value from the set found under the given key
     *
     * @param key The key
     * @param value The value to remove
     */
    fun replaceValue(key: Key, value: Value)
    {
        removeFromSet(key, value)
        add(key, value)
    }

    /**
     * Returns the set found under the given key
     *
     * @param key The key
     * @return The set
     */
    fun set(key: Key): ObjectSet<Value>
    {
        return computeIfAbsent(key) { ignored -> ObjectSet<Value?>() }
    }

    /**
     * The total number of values in this map
     */
    fun valueCount(): Int
    {
        var count: `var` = 0
        for (set in values())
        {
            if (set != null)
            {
                count += set.size()
            }
        }
        return count
    }

    protected fun onCreateValue(key: Key): ObjectSet<Value>
    {
        return ObjectSet<Any?>(maximumValues)
    }
}