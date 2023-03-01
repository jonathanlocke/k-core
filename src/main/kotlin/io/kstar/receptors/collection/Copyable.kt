@file:Suppress("unused")

package io.kstar.receptors.collection

import io.kstar.receptors.objects.Factory
import io.kstar.receptors.objects.Matcher

/**
 * Makes a copy of a given sequence
 *
 * @param <Value> The sequence value
 * @param <Copy> The copy
 * @author  Jonathan Locke
</Copy></Value> */
interface Copyable<Value, Copy> :
    Sequence<Value>,
    Factory<Copy> where Copy : Addable<Value>, Copy : Sequence<Value>
{
    /**
     * Returns a copy of this object
     */
    fun copy(): Copy
    {
        val set = new()
        set.addAll(iterator())
        return set
    }

    /**
     * Returns this bounded list filtered to only the elements that match the given matcher
     */
    fun matching(matcher: Matcher<Value>): Copy
    {
        val filtered = new()
        filtered.addAllMatching(iterator(), matcher)
        return filtered
    }

    /**
     * Returns this list without the matching elements
     */
    fun without(matcher: Matcher<Value>): Copy
    {
        val iterator = iterator()
        val without = new()
        while (iterator.hasNext())
        {
            val element = iterator.next()
            if (!matcher.matches(element))
            {
                without.add(element)
            }
        }
        return without
    }
}
