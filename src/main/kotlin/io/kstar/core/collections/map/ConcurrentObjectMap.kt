package io.kstar.core.collections.map

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * Class for concurrent maps with a bounded number of values.
 *
 * @author jonathanl
 * @author Junwei
 * @version 1.0.0 2012-12-27
 */
@UmlClassDiagram(diagram = DiagramCollections::class)
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = TESTING_INSUFFICIENT, documentation = DOCUMENTED)
class ConcurrentObjectMap<Key, Value>
/**
 * A bounded concurrent map with the given implementation
 */
(maximumSize: Maximum, map: ConcurrentMap<Key, Value>) : ObjectMap<Key, Value>(maximumSize, map), ConcurrentMap<Key, Value>
{
    /**
     * A bounded concurrent map
     */
    constructor(maximumSize: Maximum) : this(maximumSize, ConcurrentHashMap<Key, Value>())

    /**
     * An unbounded concurrent map
     */
    constructor() : this(MAXIMUM)

    /**
     * An unbounded concurrent map with the given implementation
     */
    protected constructor(map: ConcurrentMap<Key, Value>) : this(MAXIMUM, map)

    override fun replace(key: Key, oldValue: Value, newValue: Value): Boolean
    {
        return concurrentMap().replace(key, oldValue, newValue)
    }

    private fun concurrentMap(): ConcurrentHashMap<Key?, Value?>
    {
        return backingMap() as ConcurrentHashMap<Key?, Value?>
    }
}