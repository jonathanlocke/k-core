package io.kstar.core.collections.map

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A map that has a fixed size and that deletes the oldest entries when that size is exceeded. It also removes entries
 * that are older than the maximum age.
 *
 * @param <Key> The Key
 * @param <Value> The Value
 * @author jonathanl (shibo)
</Value></Key> */
@UmlClassDiagram(diagram = DiagramCollections::class)
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = TESTING_INSUFFICIENT, documentation = DOCUMENTED)
class CacheMap<Key, Value>(cacheSize: Maximum, maximumEntryAge: Duration) : BaseMap<Key, Value?>(MAXIMUM, object : LinkedHashMap<Any?, Any?>()
{
    protected fun removeEldestEntry(eldest: Map.Entry<Key, Value>?): Boolean
    {
        return size >= cacheSize.asInt()
    }
})
{
    /** The maximum allowed age of an entry  */
    private val maximumEntryAge: Duration

    /** The time that each entry was added or updated  */
    private val updated: MutableMap<Key, Time> = HashMap<Key, Time>()

    /**
     * Constructs a cache map with the given maximum size
     */
    constructor(cacheSize: Maximum) : this(cacheSize, FOREVER)

    /**
     * / ** Constructs a cache map of the given maximum size
     *
     * @param cacheSize The size after which the eldest entry will be deleted to leave room for new entries.
     * @param maximumEntryAge The maximum age of an entry before it is expired
     */
    init
    {
        this.maximumEntryAge = maximumEntryAge
    }

    /**
     * {@inheritDoc}
     */
    override operator fun get(key: Any): Value?
    {
        if (expireOldEntries())
        {
            updated.compute(key as Key, BiFunction<Key, Time, Time> { ignored: Key, value: Time? ->
                // If the value's age is greater than the maximum age for an entry,
                if (value == null || value.elapsedSince().isGreaterThan(maximumEntryAge))
                {
                    // returning null to compute, which causes it to remove this key.
                    return@compute null
                }
                value
            })
        }
        return super.get(key)
    }

    /**
     * {@inheritDoc}
     */
    override fun put(key: Key, value: Value?): Value?
    {
        if (expireOldEntries())
        {
            updated[key] = now()
        }
        return super.put(key, value)
    }

    /**
     * {@inheritDoc}
     */
    override fun remove(key: Any): Value?
    {
        if (expireOldEntries())
        {
            updated.remove(key)
        }
        return super.remove(key)
    }

    private fun expireOldEntries(): Boolean
    {
        return !maximumEntryAge.isMaximum()
    }
}