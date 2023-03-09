package io.kstar.core.collections.list

import com.telenav.kivakit.annotations.code.quality.TypeQuality

/**
 * [ObjectList] already contains push and pop functionality, but this class can make it clear when an
 * [ObjectList] is being used as a stack.
 *
 * @author jonathanl (shibo)
 */
@UmlClassDiagram(diagram = DiagramCollections::class)
@TypeQuality(stability = STABLE_EXTENSIBLE, testing = TESTING_INSUFFICIENT, documentation = DOCUMENTED)
class Stack<Value> : ObjectList<Value>
{
    constructor()
    constructor(maximumSize: Maximum?) : super(maximumSize)
    constructor(maximumSize: Maximum, values: MutableCollection<Value>) : super(maximumSize, values)
    constructor(values: Collection<Value>?) : super(values)

    fun top(): Value?
    {
        return if (size > 0) get(size - 1) else null
    }
}