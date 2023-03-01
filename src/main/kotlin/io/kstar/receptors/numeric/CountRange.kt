package io.kstar.receptors.numeric

import io.kstar.core.values.Count

class CountRange
(
    override val start: Count,
    override val endInclusive: Count
) : ClosedRange<Count>, Iterable<Count>
{
    override fun iterator(): Iterator<Count>
    {
        return object : Iterator<Count>
        {
            var at = start

            override fun hasNext(): Boolean = at <= endInclusive
            override fun next(): Count = at.incremented()
        }
    }
}
