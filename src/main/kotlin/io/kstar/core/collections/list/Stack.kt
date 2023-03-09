package io.kstar.core.collections.list

import io.kstar.annotations.documentation.UmlIncludeType
import io.kstar.annotations.quality.Documentation.DOCUMENTED
import io.kstar.annotations.quality.Stability.STABLE_EXTENSIBLE
import io.kstar.annotations.quality.Testing.TESTING_INSUFFICIENT
import io.kstar.annotations.quality.TypeQuality
import io.kstar.core.values.Maximum
import io.kstar.internal.Diagrams.DiagramCollections

/**
 * [ObjectList] already contains push and pop functionality, but this class can make it clear when an
 * [ObjectList] is being used as a stack.
 *
 * @author jonathanl (shibo)
 */
@UmlIncludeType(inDiagrams = [DiagramCollections::class])
@TypeQuality
(
    stability = STABLE_EXTENSIBLE,
    testing = TESTING_INSUFFICIENT, documentation = DOCUMENTED
)
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