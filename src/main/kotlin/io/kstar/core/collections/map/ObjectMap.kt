package io.kstar.core.collections.map

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * A trivial extension of [BaseMap] for storing objects
 *
 * @author jonathanl (shibo)
 */
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = TESTING_INSUFFICIENT, documentation = DOCUMENTED)
open class ObjectMap<Key, Value> : BaseMap<Key, Value>
{
    constructor()
    constructor(maximumSize: Maximum?) : super(maximumSize)
    constructor(maximumSize: Maximum, map: MutableMap<Key, Value>) : super(maximumSize, map)
    constructor(map: Map<Key, Value>?) : super(map)

    fun copy(): ObjectMap<Key, Value>
    {
        val copy: `var` = ObjectMap<Key, Value>()
        copy.putAll(this)
        return copy
    }

    companion object
    {
        /**
         * Returns an [ObjectMap] with the given maximum size
         */
        fun <Key, Value> map(maximumSize: Maximum?): ObjectMap<Key, Value>
        {
            return ObjectMap<Any?, Any?>(maximumSize)
        }

        /**
         * Returns a new [ObjectMap]
         */
        fun <Key, Value> map(): ObjectMap<Key, Value>
        {
            return map(MAXIMUM)
        }
    }
}