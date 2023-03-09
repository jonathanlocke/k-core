package io.kstar.core.collections.list

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * An [ObjectList] backed by a [LinkedList].
 *
 * @author jonathanl (shibo)
 */
@Suppress("unused")
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = TESTING_INSUFFICIENT, documentation = DOCUMENTED)
class LinkedObjectList<Value> : ObjectList<Value>
{
    constructor() : super(LinkedList<Value>())
    constructor(maximumSize: Maximum?) : super(maximumSize)
    constructor(collection: Collection<Value>?) : super(collection)

    /**
     * Removes the first element of this list
     */
    fun removeFirst()
    {
        (backingList() as LinkedList<Value?>).removeFirst()
    }
}