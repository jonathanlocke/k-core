package io.kstar.core.collections.map

import com.telenav.kivakit.annotations.code.quality.TypeQuality
import java.util.function.Function

/**
 * Keeps a [ConcurrentMutableCount] for each key.
 *
 *
 * **Adding**
 *
 *
 *  * [.decrement]
 *  * [.increment]
 *  * [.mergeIn]
 *  * [.plus]
 *  * [.plus]
 *
 *
 *
 * **Values**
 *
 *
 *  * [.count]
 *  * [.minimum]
 *  * [.maximum]
 *  * [.total]
 *
 *
 *
 * **Limiting**
 *
 *
 *  * [.bottom]
 *  * [.bottom]
 *  * [.top]
 *  * [.top]
 *  * [.pruneCountsLessThan]
 *
 *
 *
 * **Sorting**
 *
 *
 *  * [.ascendingEntries]
 *  * [.descendingEntries]
 *  * [.sortedByDescendingCount]
 *  * [.sortedKeys]
 *  * [.sortedKeys]
 *
 *
 * @author jonathanl (shibo)
 */
@Suppress("unused")
@UmlClassDiagram(diagram = DiagramCollections::class)
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = TESTING_INSUFFICIENT, documentation = DOCUMENTED)
class CountMap<Key> : ObjectMap<Key, ConcurrentMutableCount?>
{
    private var total: AtomicLong = AtomicLong()

    constructor() : this(MAXIMUM)
    constructor(maximum: Maximum?) : super(maximum)
    constructor(that: CountMap<Key>) : super(MAXIMUM)
    {
        mergeIn(that)
        total = that.total
    }

    /**
     * Returns entries sorted in ascending order with the given comparator
     */
    fun ascendingEntries(
        maximum: Maximum?,
        comparator: Comparator<in MutableMap.MutableEntry<Key, ConcurrentMutableCount?>?>?): ObjectList<Map.Entry<Key, ConcurrentMutableCount>>
    {
        assert(maximum != null)
        val sorted = ObjectList(entries)
        sorted.sort(comparator)
        return sorted.subList(0, min(sorted.size(), maximum.asInt()))
    }

    /**
     * Returns the bottom entries, up to the maximum
     */
    fun bottom(maximum: Maximum?): CountMap<Key>
    {
        return bottom(maximum, java.util.Map.Entry.comparingByValue<Key, ConcurrentMutableCount?>())
    }

    /**
     * Returns the bottom entries, up to the maximum, in sorted order
     */
    fun bottom(maximum: Maximum?, comparator: Comparator<in MutableMap.MutableEntry<Key, ConcurrentMutableCount?>?>?): CountMap<Key>
    {
        val bottom = CountMap<Key>()
        for (entry in ascendingEntries(maximum, comparator))
        {
            bottom.plus(entry.getKey(), entry.getValue())
        }
        return bottom
    }

    /**
     * Returns the count for the given key
     */
    fun count(key: Key): Count
    {
        return computeIfAbsent(key, Function { ignored: Key? -> ConcurrentMutableCount() }).count()
    }

    /**
     * Decrements the count for the given key
     *
     * @return This object, for chaining
     */
    fun decrement(key: Key): CountMap<Key>
    {
        val count? = computeIfAbsent(key, Function { ignored: Key? -> ConcurrentMutableCount() })
        count.decrement()
        total.decrementAndGet()
        return this
    }

    /**
     * Returns entries sorted in descending order with the given comparator
     */
    fun descendingEntries(
        maximum: Maximum?,
        comparator: Comparator<in MutableMap.MutableEntry<Key, ConcurrentMutableCount?>?>): ObjectList<Map.Entry<Key, ConcurrentMutableCount>>
    {
        assert(maximum != null)
        val sorted: ObjectList<Map.Entry<Key, ConcurrentMutableCount>> = ObjectList(entries)
        sorted.sort(comparator.reversed())
        return sorted.subList(0, min(sorted.size(), maximum.asInt()))
    }

    /**
     * Increments the count for the given key
     */
    fun increment(key: Key): CountMap<Key>
    {
        val count? = computeIfAbsent(key, Function { ignored: Key? -> ConcurrentMutableCount() })
        count.increment()
        total.incrementAndGet()
        return this
    }

    /**
     * Joins the key/value pairs in this map using the given separator
     */
    fun join(separator: String?): String
    {
        val list = StringList()
        for (key in sortedKeys())
        {
            list.add(key.toString() + " = " + count(key))
        }
        return list.join(separator)
    }

    /**
     * Returns the key with the maximum count
     */
    fun maximum(): Key?
    {
        var maximum = Long.MIN_VALUE
        var minimumKey: Key? = null
        for (entry in entries)
        {
            if (entry.getValue().asLong() > maximum)
            {
                minimumKey = entry.getKey()
                maximum = entry.getValue().asLong()
            }
        }
        return minimumKey
    }

    /**
     * Adds all counts from the given map to this mqp
     */
    fun mergeIn(that: CountMap<Key>)
    {
        for (entry in that.entries)
        {
            plus(entry.getKey(), entry.getValue())
        }
    }

    /**
     * Returns the key with the minimum count
     */
    fun minimum(): Key?
    {
        var minimum = Long.MAX_VALUE
        var minimumKey: Key? = null
        for (entry in entries)
        {
            if (entry.getValue().asLong() < minimum)
            {
                minimumKey = entry.getKey()
                minimum = entry.getValue().asLong()
            }
        }
        return minimumKey
    }

    /**
     * Adds the given count to the given key
     */
    fun plus(key: Key, value: Countable): CountMap<Key>
    {
        plus(key, value.count().asLong())
        return this
    }

    /**
     * Adds the given count to the given key
     */
    fun plus(key: Key, value: Long): Count
    {
        val count? = computeIfAbsent(key, Function { ignored: Key? -> ConcurrentMutableCount() })
        count.plus(value)
        val total = total.addAndGet(value)
        return Count.count(total)
    }

    /**
     * Prunes all counts less than the given minimum
     */
    fun pruneCountsLessThan(minimum: Count): CountMap<Key>
    {
        val counts = CountMap<Key>()
        for (entry in entries)
        {
            if (entry.getValue().asLong() >= minimum.get())
            {
                counts.plus(entry.getKey(), entry.getValue())
            }
        }
        return counts
    }

    /**
     * Removes all counts in the given map
     */
    fun removeAll(map: CountMap<Key>)
    {
        for (value in map.keys)
        {
            remove(value)
        }
    }

    /**
     * Returns a list of keys sorted by descending count
     */
    fun sortedByAscendingCount(): ObjectList<Key>
    {
        return sortedByDescendingCount().reversed()
    }

    /**
     * Returns a list of keys sorted by descending count
     */
    fun sortedByDescendingCount(): ObjectList<Key>
    {
        val entries: List<Map.Entry<Key, ConcurrentMutableCount>> = ArrayList<Map.Entry<Key, ConcurrentMutableCount>>(entries)
        entries.sort(java.util.Map.Entry.comparingByValue<Key, ConcurrentMutableCount>())
        val sorted = ObjectList<Key>()
        for (entry in entries)
        {
            sorted.add(entry.getKey())
        }
        return sorted
    }

    /**
     * Returns a list of keys in sorted order
     */
    fun sortedKeys(): ObjectList<Key>
    {
        return sortedKeys { a: Key, b: Key -> (a as Comparable<Key>).compareTo(b) }
    }

    /**
     * Returns a list of keys in sorted order, using the given comparator
     */
    fun sortedKeys(comparator: Comparator<Key>?): ObjectList<Key>
    {
        val keys: ObjectList<Key> = ObjectList(keys)
        keys.sort(comparator)
        return keys
    }

    override fun toString(): String
    {
        return join(", ")
    }

    /**
     * Returns the top entries, up to the maximum
     */
    fun top(maximum: Maximum?): CountMap<Key>
    {
        return top(maximum, java.util.Map.Entry.comparingByValue<Key, ConcurrentMutableCount?>())
    }

    /**
     * Returns the top entries, up to the maximum, in sorted order
     */
    fun top(maximum: Maximum?, comparator: Comparator<Map.Entry<Key, ConcurrentMutableCount?>?>): CountMap<Key>
    {
        val top = CountMap<Key>()
        for (entry in descendingEntries(maximum, comparator))
        {
            top.plus(entry.getKey(), entry.getValue())
        }
        return top
    }

    /**
     * Returns the total of all counts in this map
     */
    fun total(): Long
    {
        return total.get()
    }
}